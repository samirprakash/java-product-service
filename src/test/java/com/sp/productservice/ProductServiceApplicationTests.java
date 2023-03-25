package com.sp.productservice;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.assertions.Assertions;
import com.sp.productservice.dto.ProductRequest;
import com.sp.productservice.repository.ProductRepository;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProductRepository productRepository;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}	

	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = generateProductRequest();
		String productRequestAsString = objectMapper.writeValueAsString(productRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product").contentType(MediaType.APPLICATION_JSON).content(productRequestAsString))
				.andExpect(MockMvcResultMatchers.status().isCreated());
		Assertions.assertTrue(productRepository.findAll().size() == 1);
	}

	@Test
	void shouldGetProducts() throws Exception {
		ProductRequest productRequest = generateProductRequest();
		String productRequestAsString = objectMapper.writeValueAsString(productRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product").contentType(MediaType.APPLICATION_JSON).content(productRequestAsString))
				.andExpect(MockMvcResultMatchers.status().isCreated());

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	private ProductRequest generateProductRequest() {
		return ProductRequest.builder().name("Product 1").description("Product 1 Description").price(BigDecimal.valueOf(1200)).build();
	}
}
