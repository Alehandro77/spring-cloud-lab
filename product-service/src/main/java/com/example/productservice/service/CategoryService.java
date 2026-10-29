package com.example.productservice.service;

import com.example.productservice.dto.CategoryResponse;
import com.example.productservice.dto.CreateCategoryRequest;
import com.example.productservice.entity.Category;
import com.example.productservice.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryService {

    private final CategoryRepository categoryRepo;

    public CategoryService(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public List<CategoryResponse> getAll() {
        log.debug("Fetching all categories");
        return categoryRepo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse getById(Long id) {
        log.debug("Fetching category by id={}", id);
        return toResponse(findOrThrow(id));
    }

    public CategoryResponse create(CreateCategoryRequest req) {
        log.info("Creating category: name={}", req.name());
        if (categoryRepo.existsByName(req.name())) {
            throw new RuntimeException("Категория уже существует: " + req.name());
        }
        Category category = new Category(req.name());
        Category saved = categoryRepo.save(category);
        log.info("Category created: id={}, name={}", saved.getId(), saved.getName());
        return toResponse(saved);
    }

    public void delete(Long id) {
        log.info("Deleting category id={}", id);
        if (!categoryRepo.existsById(id)) {
            throw new RuntimeException("Категория не найдена: " + id);
        }
        categoryRepo.deleteById(id);
        log.info("Category deleted: id={}", id);
    }

    private Category findOrThrow(Long id) {
        return categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Категория не найдена: " + id));
    }

    private CategoryResponse toResponse(Category c) {
        return new CategoryResponse(c.getId(), c.getName());
    }
}