package com.educandoweb.course.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educandoweb.course.entities.Category;
import com.educandoweb.course.entities.Product;
import com.educandoweb.course.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	@Autowired
	private CategoryService service;

	public List<Product> findAll() {
		return repository.findAll();
	}

	public Product findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		return obj.get();
	}

	public Product insert(Product obj) {
		Long id = obj.getId();
		Product entity = new Product(null, obj.getName(), obj.getDescription(), obj.getPrice(), obj.getImgUrl());
		Category category = service.findById(id);

		entity.getCategories().add(category);

		return repository.save(entity);
	}
	
	public Product update(Product obj) {
		Product entity = repository.getReferenceById(obj.getId());
		updateData(entity, obj);
		return repository.save(entity);
	}

	private void updateData(Product entity, Product obj) {
		entity.setName(obj.getName());
		entity.setDescription(obj.getDescription());
		entity.setPrice(obj.getPrice());
		entity.getCategories().addAll(obj.getCategories());
	}
}