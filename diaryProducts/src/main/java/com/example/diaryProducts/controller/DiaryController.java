package com.example.diaryProducts.controller;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.diaryProducts.model.Products;
import com.example.diaryProducts.model.orders;
import com.example.diaryProducts.service.ProductService;

@RestController
public class DiaryController {
	@Autowired
	ProductService productService;
	
	@GetMapping("/products")
	public List<Products> availableProducts(){
		return productService.availableProducts();
	}
	
	@PostMapping(value="/createorders",consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
	public orders createOrder(@RequestBody orders order) {
		
		return productService.createOrder(order);
	}
	
	@GetMapping("/orderDetails")
	public List<orders> availableOrders(){
		return productService.availableOrders();
	}
	@PostMapping("/createProduct")
	public Products createpro(@RequestBody Products products) {
		return productService.createProducts(products);
	}
	@PutMapping("/updateStock/{product_id}")
	public Products updateStock(@PathVariable int product_id, @RequestParam int stock_quantity) {
		return productService.updateStock(product_id, stock_quantity);
	}
	 @PutMapping("/update/{id}")
	    public Products updateProduct(@PathVariable int id,@RequestBody Products product) {
		 return productService.updateProduct(id, product);
	    }
		@GetMapping("/filterByName")
		public List<Products> filterByName(@RequestParam String product_name) {
			return productService.filterByProductName(product_name);
		}
		@GetMapping("/filterByPrice")
		public List<Products> filterByPrice(@RequestParam double minPrice, @RequestParam double maxPrice) {
			return productService.filterByPriceRange(minPrice, maxPrice);
		}
		@GetMapping("/sortByPriceAsc")
		public List<Products> sortByPriceAsc() {
			return productService.sortByPriceAsc();
		}
		@GetMapping("/sortByPriceDesc")
		public List<Products> sortByPriceDesc() {
			return productService.sortByPriceDesc();
		}
		
		@PutMapping(value = "/updateImage/{product_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
		public Products updateImage(@PathVariable int product_id,
		                             @RequestParam("image") MultipartFile image) throws IOException {
		    return productService.updateProductImage(product_id, image);
		}

		@GetMapping(value = "/image/{product_id}", produces = MediaType.IMAGE_JPEG_VALUE)
		public ResponseEntity<byte[]> getProductImage(@PathVariable int product_id) {
		    Products product = productService.getProductById(product_id);
		    byte[] image = product.getImage();
		    if (image == null || image.length == 0) {
		        return ResponseEntity.notFound().build();
		    }
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.parseMediaType(product.getImageType() != null ? product.getImageType() : MediaType.IMAGE_JPEG_VALUE));
		    return ResponseEntity.ok().headers(headers).body(image);
		}

		@PostMapping(value = "/createProductWithImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
		public Products createProductWithImage(
		        @RequestParam("productName") String productName,
		        @RequestParam(value = "description", required = false) String description,
		        @RequestParam("price") double price,
		        @RequestParam("stock_quantity") int stockQuantity,
		        @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
		    return productService.saveProductWithImage(productName, description, price, stockQuantity, image);
		}

		@DeleteMapping("/deleteProduct/{product_id}")
		public ResponseEntity<String> deleteProduct(@PathVariable int product_id) {
		    productService.deleteProduct(product_id);
		    return ResponseEntity.ok("Product deleted successfully with id: " + product_id);
		}
		
		@PostMapping("/createorders")
		public orders createOrder1(@RequestBody orders order) {
		    return productService.createOrders(order);
		}

		@GetMapping("/orders/{username}")
		public List<orders> getOrdersByUsername(@PathVariable String username) {
		    return productService.getOrdersByUsername(username);
		}

		@PostMapping("/orders/cancel/{orderId}")
		public ResponseEntity<orders> cancelOrder(@PathVariable int orderId,
		                                          @RequestParam(required = false) String username) {
		    return ResponseEntity.ok(productService.cancelOrder(orderId, username));
		}

		@GetMapping("/order/{orderId}")
		public ResponseEntity<orders> getOrderById(@PathVariable int orderId,
		                                           @RequestParam(required = false) String username) {
		    return ResponseEntity.ok(productService.getOrderById(orderId, username));
		}

		@PostMapping("/orders/status/{orderId}")
		public ResponseEntity<orders> updateOrderStatus(@PathVariable int orderId,
		                                                @RequestParam String status) {
		    return ResponseEntity.ok(productService.updateOrderStatus(orderId, status));
		}
	
	
	
}
