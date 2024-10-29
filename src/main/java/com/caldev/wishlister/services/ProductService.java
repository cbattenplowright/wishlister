package com.caldev.wishlister.services;

import com.caldev.wishlister.dtos.ProductDto;
import com.caldev.wishlister.entities.Product;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.WishlistProduct;
import com.caldev.wishlister.exceptions.ProductNotFoundException;
import com.caldev.wishlister.repositories.ProductRepository;
import com.caldev.wishlister.repositories.WishlistProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    private final WishlistProductService wishlistProductService;

    private final ProductRepository productRepository;

    public ProductService(WishlistProductService wishlistProductService, ProductRepository productRepository) {
        this.wishlistProductService = wishlistProductService;
        this.productRepository = productRepository;
    }

    public ProductDto findProductById(Long requestedProductId) {


        Optional<Product> product = productRepository.findById(requestedProductId); // <Product>

        if (product.isEmpty())  {
            throw new ProductNotFoundException("Product not found");
        }

        ProductDto productDto = new ProductDto(
                product.get().getProductId(),
                product.get().getProductName(),
                product.get().getUserAccount().getId(),
                product.get().getPrice(),
                product.get().getUrl(),
                product.get().getImageUrl(),
                product.get().getPriority(),
                product.get().getDescription(),
                product.get().getDateAdded(),
                new ArrayList<>(product.get().getWishlistProducts().stream().map(WishlistProduct::getWishlistProductId).toList())
        );

        return productDto;
    }

    public boolean existsByProductNameAndUserAccount(String productName, UserAccount userAccount) {
        return productRepository.existsByProductNameAndUserAccount(productName, userAccount);
    }

    public Product createProduct(ProductDto newProductDto, UserAccount userAccount) {

/*        if wishlist products list exists
            for every id in list
                if wishlistProductRepository.findById(id)
                    add to newProductDto.wishlistProducts
*/

        List<WishlistProduct> wishlistProducts = new ArrayList<>();

        if (newProductDto.getWishlistProductIds() != null) {
            for (Long wishlistProductId : newProductDto.getWishlistProductIds()){
                WishlistProduct wishlistProduct = wishlistProductService.getWishlistProductById(wishlistProductId);
                wishlistProducts.add(wishlistProduct);
            }
        }

        if (newProductDto.getPrioritySelection() == null) {

        }

        Product newProduct = new Product(
                newProductDto.getProductName(),
                newProductDto.getPrice(),
                newProductDto.getUrl(),
                newProductDto.getImageUrl(),
                newProductDto.getDescription(),
                newProductDto.getPrioritySelection(),
                LocalDate.now(),
                wishlistProducts,
                userAccount
        );

        return productRepository.save(newProduct);
    }

    public Product updateProduct(Long requestedProductId,
                                 ProductDto updatedProductDto,
                                 UserAccount userAccount) {

        Optional<Product> productToUpdate = findProductById(requestedProductId);

        if (productToUpdate.isEmpty()) {
            throw new ProductNotFoundException("Product not found");
        }


        if (updatedProductDto.getUserId() != null && userAccount.getId() == updatedProductDto.getUserId()) {
            productToUpdate.get().setUserAccount(userAccount);
        }

        if (updatedProductDto.getProductName() != null) {
            productToUpdate.get().setProductName(updatedProductDto.getProductName());
        }

        if (Objects.nonNull(updatedProductDto.getPrice())) {
            productToUpdate.get().setPrice(updatedProductDto.getPrice());
        }

        if (updatedProductDto.getUrl() != null) {
            productToUpdate.get().setUrl(updatedProductDto.getUrl());
        }

        if (updatedProductDto.getImageUrl() != null) {
            productToUpdate.get().setImageUrl(updatedProductDto.getImageUrl());
        }

        if (updatedProductDto.getPrioritySelection() != null) {
            productToUpdate.get().setPriority(updatedProductDto.getPrioritySelection());
        }

        if (updatedProductDto.getDescription() != null) {
            productToUpdate.get().setDescription(updatedProductDto.getDescription());
        }

        if (updatedProductDto.getDateAdded() != null) {
            productToUpdate.get().setDateAdded(updatedProductDto.getDateAdded());
        }

        if(updatedProductDto.getWishlistProductIds() != null) {
            List<WishlistProduct> wishlistProducts = new ArrayList<>();
            for (Long wishlistProductId : updatedProductDto.getWishlistProductIds()){
                WishlistProduct wishlistProduct = wishlistProductService.getWishlistProductById(wishlistProductId);
                wishlistProducts.add(wishlistProduct);
            }
            productToUpdate.get().setWishlistProducts(wishlistProducts);
        }

        return productRepository.save(productToUpdate.get());
    }

    @Transactional
    public void deleteProduct(Long requestedProductId) {

       wishlistProductService.deleteByProductId(requestedProductId);

        productRepository.deleteByProductId(requestedProductId);
    }

    public boolean userAccountOwnsProduct(Long productId, UserAccount userAccount){
        return productRepository.existsByProductIdAndUserAccount(productId, userAccount);
    }
}
