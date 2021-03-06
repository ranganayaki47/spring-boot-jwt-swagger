package com.stackroute.keepnote.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.stackroute.keepnote.exception.CategoryDoesNoteExistsException;
import com.stackroute.keepnote.exception.CategoryNotCreatedException;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.service.CategoryService;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
public class CategoryController {

	/*
	 * Autowiring should be implemented for the CategoryService. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword
	 */
	private CategoryService categoryService;
	
	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	/*
	 * Define a handler method which will create a category by reading the
	 * Serialized category object from request body and save the category in
	 * database. Please note that the careatorId has to be unique.This
	 * handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 201(CREATED - In case of successful creation of the category
	 * 2. 409(CONFLICT) - In case of duplicate categoryId
	 *
	 * 
	 * This handler method should map to the URL "/api/v1/category" using HTTP POST
	 * method".
	 */
	@PostMapping("/api/v1/category")
	public ResponseEntity<Category> createCategory(@RequestBody Category category) {
		
		try {
			category.setCategoryCreationDate(new Date());
			categoryService.createCategory(category);
			return new ResponseEntity<Category>(category,HttpStatus.CREATED);
		} catch (CategoryNotCreatedException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	/*
	 * Define a handler method which will delete a category from a database.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the category deleted successfully from
	 * database. 2. 404(NOT FOUND) - If the category with specified categoryId is
	 * not found. 
	 * 
	 * This handler method should map to the URL "/api/v1/category/{id}" using HTTP Delete
	 * method" where "id" should be replaced by a valid categoryId without {}
	 */
	@DeleteMapping("/api/v1/category/{id}")
	public ResponseEntity<Category> deleteCategory(@PathVariable String id) {
		
		try {
			if(categoryService.deleteCategory(id)) {
				return new ResponseEntity<>(HttpStatus.OK);
			}else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (CategoryDoesNoteExistsException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/*
	 * Define a handler method which will update a specific category by reading the
	 * Serialized object from request body and save the updated category details in
	 * database. This handler method should return any one of the status
	 * messages basis on different situations: 1. 200(OK) - If the category updated
	 * successfully. 2. 404(NOT FOUND) - If the category with specified categoryId
	 * is not found. 
	 * This handler method should map to the URL "/api/v1/category/{id}" using HTTP PUT
	 * method.
	 */
	@PutMapping("/api/v1/category/{id}")
	public ResponseEntity<?> updateCategory(@RequestBody Category category) {
		try {
			if(categoryService.updateCategory(category, category.getId()) != null) {
				return new ResponseEntity<Category>(category,HttpStatus.OK);
			}
			else
				return new ResponseEntity<String>("Data not found", HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<String>("Data not found", HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping("/api/v1/category/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable String id) {
		Category category;
		try {
			category = categoryService.getCategoryById(id);
			if(category != null) {
				return new ResponseEntity<Category>(category,HttpStatus.OK);
			}
			else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (CategoryNotFoundException e) {
			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
		}
		
	}
	/*
	 * Define a handler method which will get us the category by a userId.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the category found successfully. 
	 * This handler method should map to the URL "/api/v1/category" using HTTP GET method
	 */
	@GetMapping("/api/v1/category")
	public ResponseEntity<List<Category>> getAllReminders(@RequestBody Category category) {
		List<Category> categories = categoryService.getAllCategoryByUserId(category.getCategoryCreatedBy());
		if(categories != null) {
			return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
		}
		else
			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
	}

}
