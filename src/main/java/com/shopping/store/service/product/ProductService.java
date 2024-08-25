package com.shopping.store.service.product;

import com.shopping.store.dto.ProductDto;
import com.shopping.store.entity.Product;
import com.shopping.store.request.AddProductRequest;
import com.shopping.store.request.ProductUpdateRequest;
import com.shopping.store.response.PageResponse;

import java.util.List;

public interface ProductService {

    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest request, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);



    PageResponse<Product> getAllProductsByPage(int page, int pageSize, String sort);
}
