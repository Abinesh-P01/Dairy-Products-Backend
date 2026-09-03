package com.example.diaryProducts.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Products {
	
	public Products() {
	}
	
	public Products(String product_name, String description, double price,int stock_quantity) {
		super();
		
		this.productName = product_name;
		this.description = description;
		this.price = price;
		this.stock_quantity=stock_quantity;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int product_id;
	@Column(name = "product_name")
	private String productName;
	private String description;
	private double price;
	private int stock_quantity;
	
	 @Lob
	    @Column(name = "image", columnDefinition = "LONGBLOB")
	    private byte[] image;

	    @Column(name = "image_type")
	    private String imageType;
	
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return productName;
	}
	public void setProduct_name(String product_name) {
		this.productName = product_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	public int getStock_quantity() {
		return stock_quantity;
	}

	public void setStock_quantity(int stock_quantity) {
		this.stock_quantity = stock_quantity;
	}
	
	 public byte[] getImage() { return image; }
	    public void setImage(byte[] image) { this.image = image; }
	    public String getImageType() { return imageType; }
	    public void setImageType(String imageType) { this.imageType = imageType; }
}
