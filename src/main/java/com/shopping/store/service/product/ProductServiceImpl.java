package com.shopping.store.service.product;

import com.shopping.store.dto.ImageDto;
import com.shopping.store.dto.ProductDto;
import com.shopping.store.entity.Category;
import com.shopping.store.entity.Image;
import com.shopping.store.entity.Product;
import com.shopping.store.exception.AlreadyExistsException;
import com.shopping.store.exception.ResourceNotFoundException;
import com.shopping.store.repo.CategoryRepository;
import com.shopping.store.repo.ImageRepository;
import com.shopping.store.repo.ProductRepository;
import com.shopping.store.request.AddProductRequest;
import com.shopping.store.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;
    @Override
    public Product addProduct(AddProductRequest product) {

        if(productExists(product.getName(), product.getBrand())){
            throw new AlreadyExistsException(product.getBrand() +" "+product.getName()+
                    " already exists, you may update this product instead!");
        }

        Category category = Optional.ofNullable(
                categoryRepository.findByName(product.getCategory().getName()))
                .orElseGet(()->{
                    Category newCategory = new Category(product.getCategory().getName());
                 return  categoryRepository.save(newCategory);
                });

       product.setCategory(category);
        return productRepository.save(addProduct(product, category));
    }

    private Product addProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    private boolean productExists(String name , String brand) {
        return productRepository.existsByNameAndBrand(name, brand);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProductById(Long id) {
       productRepository.findById(id)
               .ifPresentOrElse(productRepository::delete,
                       () -> {throw new ResourceNotFoundException("Product not found!");});
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {


        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct,request))
                .map(productRepository::save)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);

        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }


    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos=images.stream()
                .map(image-> modelMapper.map(image, ImageDto.class))
                .toList();
        productDto.setImages(imageDtos);

        return productDto;


    }
}
