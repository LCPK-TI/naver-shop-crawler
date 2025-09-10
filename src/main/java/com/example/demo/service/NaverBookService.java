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

import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
public class NaverBookService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BookRepository bookRepository;
    
    private final String CLIENT_ID = "";
    private final String CLIENT_SECRET = "";

    public NaverBookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @Transactional
    public List<Book> searchAndSave(String query, int display, int start) {
        try {
            // 1️⃣ URI 생성
            URI uri = UriComponentsBuilder.newInstance()
                    .scheme("https")
                    .host("openapi.naver.com")
                    //상품
                    //.path("/v1/search/shop.json")
                    //도서
                    .path("/v1/search/book.json")
                    .queryParam("query", query)
                    .queryParam("display", display)
                    .queryParam("start", start)
                    .queryParam("sort", "sim")
                    .build()
                    .encode()
                    .toUri();

            // 2️⃣ 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Naver-Client-Id", CLIENT_ID);
            headers.set("X-Naver-Client-Secret", CLIENT_SECRET);
            headers.set("User-Agent", "Mozilla/5.0");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            // 3️⃣ API 호출
            ResponseEntity<String> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

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

            List<Map<String, Object>> items = objectMapper.convertValue(
                    itemsNode,
                    new TypeReference<List<Map<String, Object>>>() {}
            );
            
         // 5️⃣ DB 저장 (도서)
         // 5️⃣ Book 엔티티 변환 및 저장
            List<Book> savedBooks = new ArrayList<>();
            for (Map<String,Object> item : items) {
                String isbn = (String) item.get("isbn");
                if (isbn == null || isbn.isEmpty()) continue;

                Optional<Book> existing = bookRepository.findById(isbn);
                if (existing.isPresent()) {
                    savedBooks.add(existing.get());
                    continue;
                }

                Book book = new Book();
                book.setIsbn(isbn); // PK
                
                book.setCategoryNo(44L); //카테고리 수정
                book.setStock(15); //재고 수정
                
                
                book.setTitle(Jsoup.parse((String)item.get("title")).text());
                book.setLink((String)item.get("link"));
                book.setImgUrl((String)item.get("image"));
                book.setAuthor((String)item.get("author"));
                book.setPrice(Long.parseLong(String.valueOf(item.get("discount"))));
                book.setPublisher((String)item.get("publisher"));
                book.setPubDate((String)item.get("pubdate"));
                book.setDescription((String)item.get("description"));
                book.setContents((String)item.get("contents"));
                book.setAuthor_intro((String)item.get("author_intro"));
                savedBooks.add(bookRepository.save(book));
            }

            return savedBooks;
            
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}