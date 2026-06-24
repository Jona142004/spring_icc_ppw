package ec.edu.ups.icc.fundamentos01.products.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.mappers.ProductMapper;
import ec.edu.ups.icc.fundamentos01.products.models.ProductModel;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private List<ProductModel> products = new ArrayList<>();
    private Long currentId = 1L;

    @GetMapping
    public List<ProductResponseDto> findAll() {
        return products.stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public Object findOne(@PathVariable Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .map(product -> (Object) ProductMapper.toResponse(product))
                .orElseGet(() -> new ErrorResponse("Product not found"));
    }

    @PostMapping
    public ProductResponseDto create(@RequestBody CreateProductDto dto) {
        ProductModel product = ProductMapper.toModel(dto);
        product.setId(currentId++);
        products.add(product);
        return ProductMapper.toResponse(product);
    }

    @PutMapping("/{id}")
    public Object update(@PathVariable Long id, @RequestBody UpdateProductDto dto) {
        ProductModel product = products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (product == null) {
            return new ErrorResponse("Product not found");
        }

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());

        return ProductMapper.toResponse(product);
    }

    @PatchMapping("/{id}")
    public Object partialUpdate(@PathVariable Long id, @RequestBody PartialUpdateProductDto dto) {
        ProductModel product = products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (product == null) {
            return new ErrorResponse("Product not found");
        }

        if (dto.getName() != null) {
            product.setName(dto.getName());
        }
        if (dto.getPrice() != null) {
            product.setPrice(dto.getPrice());
        }
        if (dto.getStock() != null) {
            product.setStock(dto.getStock());
        }

        return ProductMapper.toResponse(product);
    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable Long id) {
        boolean exists = products.removeIf(p -> p.getId().equals(id));
        if (!exists) {
            return new ErrorResponse("Product not found");
        }
        return new SuccessResponse("Deleted successfully");
    }

    static class ErrorResponse {
        public String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }

    static class SuccessResponse {
        public String message;

        public SuccessResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
