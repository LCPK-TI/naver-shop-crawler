package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Book;
import com.example.demo.service.NaverBookService;

@RestController
public class BookController {

    private final NaverBookService naverBookService;

    public BookController(NaverBookService naverBookService) {
        this.naverBookService = naverBookService;
    }
    /**
     * /search-moodlight 호출 시
     * 1. 네이버 쇼핑 API에서 무드등 데이터 가져오기
     * 2. 오라클 DB PRODUCT 테이블에 저장 (중복체크 포함)
     * 3. 저장된 데이터 JSON 반환
     */
    @GetMapping("/book")
    public List<Book> searchAndSave() {
        return naverBookService.searchAndSave("철학", 1, 2);//검색어, 한번에 가져올 검색 결과 개수, 검색 시작 위치
        //검색 시작 위치가 21이면 21번째부터 40번째까지
    }
}