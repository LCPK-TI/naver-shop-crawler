package com.example.demo.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
public class NaverShopService {

	private final RestTemplate restTemplate = new RestTemplate();
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final ProductRepository productRepository;

	private final String CLIENT_ID = "";
	private final String CLIENT_SECRET = "";

	public NaverShopService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Transactional
	public List<Product> searchAndSave(String query, int display, int start) {
		try {
			// 1️⃣ URI 생성
			URI uri = UriComponentsBuilder.newInstance().scheme("https").host("openapi.naver.com")
					// 상품
					// .path("/v1/search/shop.json")
					// 도서
					.path("/v1/search/shop.json").queryParam("query", query).queryParam("display", display)
					.queryParam("start", start).queryParam("sort", "sim").build().encode().toUri();

			// 2️⃣ 헤더 설정
			HttpHeaders headers = new HttpHeaders();
			headers.set("X-Naver-Client-Id", CLIENT_ID);
			headers.set("X-Naver-Client-Secret", CLIENT_SECRET);
			headers.set("User-Agent", "Mozilla/5.0");

			HttpEntity<String> entity = new HttpEntity<>(headers);

			// 3️⃣ API 호출
			ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

			if (response.getStatusCode() != HttpStatus.OK) {
				return Collections.emptyList();
			}

			// 4️⃣ JSON 파싱
			String body = response.getBody();
			JsonNode rootNode = objectMapper.readTree(body);
			JsonNode itemsNode = rootNode.path("items");

			if (itemsNode.isMissingNode() || !itemsNode.isArray()) {
				return Collections.emptyList();
			}

			List<Map<String, Object>> items = objectMapper.convertValue(itemsNode,
					new TypeReference<List<Map<String, Object>>>() {
					});

			// 5️⃣ DB 저장 (상품)
			List<Product> savedProducts = new ArrayList<>();
			for (Map<String, Object> item : items) {
				String rawTitle = (String) item.get("title");

				// HTML 태그 제거 
				String cleanTitle = Jsoup.parse(rawTitle).text();

				// 중복 체크
				Optional<Product> existing = productRepository.findByProductName(cleanTitle);
				if (existing.isPresent()) {
					savedProducts.add(existing.get());
					continue;
				}

				// 상품 생성
				Product p = new Product();
				p.setSellerNo(1L); // 기본값
				p.setProductName(cleanTitle);

				// 가격 처리
				Long price = 0L;
				try {
					price = Long.parseLong(String.valueOf(item.get("lprice")));
				} catch (NumberFormatException e) {
					price = 0L;
				}
				p.setPrice(price);
				
				p.setCategoryNo(59L); //카테고리 번호 수정
				
				p.setStock(10L); // 기본값
				p.setLink(String.valueOf(item.get("link")));

				// 1차 저장 (productNo 채워짐)
				Product saved = productRepository.save(p);

				// 상품번호 기반 파일명 규칙 적용
				String fileName = saved.getProductNo() + "-detail.jpg";
				saved.setDetailImgUrl("/product_img/" + fileName);

				// 2차 저장 (detailImgUrl 반영)
				savedProducts.add(productRepository.save(saved));
			}
			return savedProducts;

		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
}