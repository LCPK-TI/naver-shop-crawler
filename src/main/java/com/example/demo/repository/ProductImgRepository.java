package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ProductImg;
@Repository
public interface ProductImgRepository extends JpaRepository<ProductImg, Long>{


}
