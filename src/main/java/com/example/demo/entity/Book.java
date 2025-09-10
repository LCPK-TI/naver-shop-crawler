package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BOOK_TB")
@NoArgsConstructor
@AllArgsConstructor
public class Book {
	
	@Id
	@Column(name = "ISBN")
	private String isbn;

	@Column(name = "CATEGORY_NO")
	private Long categoryNo;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "LINK")
	private String link;

	@Column(name = "IMG_URL")
	private String imgUrl;

	@Column(name = "AUTHOR")
	private String author;

	@Column(name = "PRICE")
	private Long price;
	
	@Column(name="PUBLISHER")
	private String publisher;
	
	@Column(name = "PUB_DATE")
	private String pubDate;

	@Lob
	@Column(name = "DESCRIPTION")
	private String description;
	@Lob
	@Column(name = "CONTENTS")
	private String contents;
	
	@Lob
	@Column(name="AUTHOR_INTRO")
	private String author_intro;

	@Column(name="STOCK")
	private int stock;
	
	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Long getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(Long categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getAuthor_intro() {
		return author_intro;
	}

	public void setAuthor_intro(String author_intro) {
		this.author_intro = author_intro;
	}
}
	