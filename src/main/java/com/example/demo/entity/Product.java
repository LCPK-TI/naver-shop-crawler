package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "PRODUCT_TB")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_gen")
	@SequenceGenerator(name = "product_seq_gen", sequenceName = "PRODUCT_SQ", allocationSize = 1)
	@Column(name = "PRODUCT_NO")
	private Long productNo;

	@Column(name = "CATEGORY_NO")
	private Long categoryNo;

	@Column(name = "SELLER_NO")
	private Long sellerNo;

	@Column(name = "PRODUCT_NM")
	private String productName;

	@Column(name = "PRODUCT_PRICE")
	private Long price;

	@Column(name = "PRODUCT_STOCK")
	private Long stock;

	@Column(name = "LINK")
	private String link;
	@Column(name="DETAIL_IMG_URL")
	private String detailImgUrl;
	
	public String getDetailImgUrl() {
		return detailImgUrl;
	}

	public void setDetailImgUrl(String detailImgUrl) {
		this.detailImgUrl = detailImgUrl;
	}

	// Getter/Setter
	public Long getProductNo() {
		return productNo;
	}

	public void setProductNo(Long productNo) {
		this.productNo = productNo;
	}

	public Long getSellerNo() {
		return sellerNo;
	}

	public void setSellerNo(Long sellerNo) {
		this.sellerNo = sellerNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public Long getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(Long categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
