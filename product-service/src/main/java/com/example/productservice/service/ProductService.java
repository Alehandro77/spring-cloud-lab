package com.example.productservice.service;

import com.example.productservice.dto.CreateProductRequest;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.dto.UpdateProductRequest;
import com.example.productservice.entity.Category;
import com.example.productservice.entity.Gender;
import com.example.productservice.entity.Product;
import com.example.productservice.entity.Size;
import com.example.productservice.repository.CategoryRepository;
import com.example.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    public ProductService(ProductRepository productRepo, CategoryRepository categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    public List<ProductResponse> getAll() {
        log.debug("Fetching all products");
        List<ProductResponse> result = productRepo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        log.debug("Found {} products", result.size());
        return result;
    }

    public ProductResponse getById(Long id) {
        log.debug("Fetching product by id={}", id);
        return toResponse(findOrThrow(id));
    }

    public ProductResponse create(CreateProductRequest req) {
        log.info("Creating product: name={}, brand={}, price={}",
                req.name(), req.brand(), req.price());

        Category category = categoryRepo.findById(req.categoryId())
                .orElseThrow(() -> {
                    log.warn("Category not found: id={}", req.categoryId());
                    return new RuntimeException("Категория не найдена: " + req.categoryId());
                });

        Product p = new Product();
        p.setName(req.name());
        p.setDescription(req.description());
        p.setPrice(req.price());
        p.setBrand(req.brand());
        p.setCategory(category);
        p.setGender(Gender.valueOf(req.gender()));
        p.setColor(req.color());
        p.setStockQuantity(req.stockQuantity());
        p.setImageUrl(req.imageUrl());
        p.setIsActive(true);
        p.setSizes(req.sizes().stream().map(Size::valueOf).collect(Collectors.toSet()));

        Product saved = productRepo.save(p);
        log.info("Product created: id={}, name={}", saved.getId(), saved.getName());
        return toResponse(saved);
    }

    public ProductResponse update(Long id, UpdateProductRequest req) {
        log.info("Updating product id={}", id);
        Product p = findOrThrow(id);

        if (req.name() != null) p.setName(req.name());
        if (req.description() != null) p.setDescription(req.description());
        if (req.price() != null) p.setPrice(req.price());
        if (req.brand() != null) p.setBrand(req.brand());
        if (req.gender() != null) p.setGender(Gender.valueOf(req.gender()));
        if (req.color() != null) p.setColor(req.color());
        if (req.stockQuantity() != null) p.setStockQuantity(req.stockQuantity());
        if (req.imageUrl() != null) p.setImageUrl(req.imageUrl());
        if (req.isActive() != null) p.setIsActive(req.isActive());
        if (req.sizes() != null) {
            p.setSizes(req.sizes().stream().map(Size::valueOf).collect(Collectors.toSet()));
        }
        if (req.categoryId() != null) {
            Category cat = categoryRepo.findById(req.categoryId())
                    .orElseThrow(() -> new RuntimeException("Категория не найдена: " + req.categoryId()));
            p.setCategory(cat);
        }

        Product saved = productRepo.save(p);
        log.info("Product updated: id={}", saved.getId());
        return toResponse(saved);
    }

    public void delete(Long id) {
        log.info("Deleting product id={}", id);
        if (!productRepo.existsById(id)) {
            log.warn("Product not found for delete: id={}", id);
            throw new RuntimeException("Товар не найден: " + id);
        }
        productRepo.deleteById(id);
        log.info("Product deleted: id={}", id);
    }

    private Product findOrThrow(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found: id={}", id);
                    return new RuntimeException("Товар не найден: " + id);
                });
    }

    private ProductResponse toResponse(Product p) {
        return new ProductResponse(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getPrice(),
                p.getBrand(),
                p.getCategory() != null ? p.getCategory().getName() : null,
                p.getGender() != null ? p.getGender().name() : null,
                p.getColor(),
                p.getSizes().stream().map(Enum::name).toList(),
                p.getStockQuantity(),
                p.getImageUrl(),
                p.getIsActive()
        );
    }
}