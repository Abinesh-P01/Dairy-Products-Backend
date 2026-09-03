package com.example.diaryProducts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.diaryProducts.model.Products;

public interface ProductsRepo extends JpaRepository<Products, Integer>{
	
	List<Products> findByProductNameContainingIgnoreCase(String product_name);
	List<Products> findByPriceBetween(double minPrice, double maxPrice);
	List<Products> findAllByOrderByPriceAsc();
	List<Products> findAllByOrderByPriceDesc();
	

}
