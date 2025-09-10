package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "PRODUCT_IMG_TB")
public class ProductImg {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_img_seq_gen")
	@SequenceGenerator(name = "product_img_seq_gen", sequenceName = "PRODUCT_IMG_SQ", allocationSize = 1)
	@Column(name = "IMG_NO")
	private Long imgNo;

	@ManyToOne
	@JoinColumn(name = "PRODUCT_NO")
	private Product productNo;

	@Column(name = "IMG_URL")
	private String imgUrl;

	@Column(name = "IS_MAIN")
	private String isMain;

	public Long getImgNo() {
		return imgNo;
	}

	public void setImgNo(Long imgNo) {
		this.imgNo = imgNo;
	}

	public Product getProductNo() {
		return productNo;
	}

	public void setProductNo(Product productNo) {
		this.productNo = productNo;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getIsMain() {
		return isMain;
	}

	public void setIsMain(String isMain) {
		this.isMain = isMain;
	}
	


	
}
