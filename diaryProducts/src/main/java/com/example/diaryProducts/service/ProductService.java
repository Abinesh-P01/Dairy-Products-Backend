package com.example.diaryProducts.service;

import java.io.IOException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.diaryProducts.model.Products;
import com.example.diaryProducts.model.orders;
import com.example.diaryProducts.repository.ProductsRepo;
import com.example.diaryProducts.repository.orderRepo;

@Service
public class ProductService {

	@Autowired
	orderRepo orderRepo;
	@Autowired
	ProductsRepo productsRepo;
	
	public List<Products> availableProducts(){
		return productsRepo.findAll();
	}
	
	public orders createOrder(orders orders) {
		return orderRepo.save(orders);

	}
	public Products createProducts(Products products) {
		return productsRepo.save(products);
	}
	

	public List<orders> availableOrders() {
		
		return orderRepo.findAll();
	}
	public Products updateStock(int product_id, int stock_quantity) {
	    Products product = productsRepo.findById(product_id).orElseThrow(() -> new RuntimeException("Product not found"));
	    product.setStock_quantity(stock_quantity);
	    return productsRepo.save(product);
	}
	
	 public Products updateProduct(int id, Products product) {

	        Products existingProduct = productsRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

	        if (existingProduct != null) {
	        	existingProduct.setProduct_name(product.getProduct_name());
	            existingProduct.setDescription(product.getDescription());
	            existingProduct.setPrice(product.getPrice());
	            existingProduct.setStock_quantity(product.getStock_quantity());

	            return productsRepo.save(existingProduct);
	        }

	        return null;
	    }
	 public List<Products> filterByProductName(String product_name) {
			return productsRepo.findByProductNameContainingIgnoreCase(product_name);
		}

		public List<Products> filterByPriceRange(double minPrice, double maxPrice) {
			return productsRepo.findByPriceBetween(minPrice, maxPrice);
		}

		public List<Products> sortByPriceAsc() {
			return productsRepo.findAllByOrderByPriceAsc();
		}

		public List<Products> sortByPriceDesc() {
			return productsRepo.findAllByOrderByPriceDesc();
		}
		
		public Products saveProductWithImage(String productName, String description, double price,
                int stockQuantity, MultipartFile imageFile) throws IOException {
Products product = new Products();
product.setProduct_name(productName);
product.setDescription(description);
product.setPrice(price);
product.setStock_quantity(stockQuantity);

if (imageFile != null && !imageFile.isEmpty()) {
product.setImage(imageFile.getBytes());
product.setImageType(imageFile.getContentType());
}
return productsRepo.save(product);
}

public Products updateProductImage(int productId, MultipartFile imageFile) throws IOException {
Products product = productsRepo.findById(productId)
.orElseThrow(() -> new RuntimeException("Product not found"));
product.setImage(imageFile.getBytes());
product.setImageType(imageFile.getContentType());
return productsRepo.save(product);
}

public Products getProductById(int productId) {
return productsRepo.findById(productId)
.orElseThrow(() -> new RuntimeException("Product not found"));
}

public void deleteProduct(int productId) {
if (!productsRepo.existsById(productId)) {
throw new RuntimeException("Product not found");
}
productsRepo.deleteById(productId);
}
public orders createOrders(orders order) {
    Products product = productsRepo.findById(order.getProduct_id())
            .orElseThrow(() -> new RuntimeException("Product not found"));

    if (product.getStock_quantity() < order.getQuantity()) {
        throw new RuntimeException("Insufficient stock for product: " + product.getProduct_name());
    }

    product.setStock_quantity(product.getStock_quantity() - order.getQuantity());
    productsRepo.save(product);

    order.setTotal_price(product.getPrice() * order.getQuantity());
    order.setOrder_status("PLACED");
    order.setUsername(order.getUsername() != null && !order.getUsername().isEmpty()
            ? order.getUsername() : order.getCustomer_name());

    return orderRepo.save(order);
}

public List<orders> getOrdersByUsername(String username) {
    return orderRepo.findByUsername(username);
}

public orders cancelOrder(int orderId, String username) {
    orders order = orderRepo.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));

    if (username != null && !username.isEmpty() && !username.equals(order.getUsername())) {
        throw new RuntimeException("Order does not belong to this user");
    }

    if ("CANCELLED".equalsIgnoreCase(order.getOrder_status())) {
        throw new RuntimeException("Order already cancelled");
    }

    Products product = productsRepo.findById(order.getProduct_id())
            .orElseThrow(() -> new RuntimeException("Product not found"));

    product.setStock_quantity(product.getStock_quantity() + order.getQuantity());
    productsRepo.save(product);

    order.setOrder_status("CANCELLED");
    return orderRepo.save(order);
}

public orders getOrderById(int orderId, String username) {
    orders order = orderRepo.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));

    if (username != null && !username.isEmpty() && !username.equals(order.getUsername())) {
        throw new RuntimeException("Order does not belong to this user");
    }

    return order;
}

public orders updateOrderStatus(int orderId, String status) {
    orders order = orderRepo.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));

    if (status == null || status.trim().isEmpty()) {
        throw new RuntimeException("Status cannot be empty");
    }

    if ("CANCELLED".equalsIgnoreCase(order.getOrder_status())) {
        throw new RuntimeException("Cancelled orders cannot change status");
    }

    order.setOrder_status(status.trim().toUpperCase());
    return orderRepo.save(order);
}
}