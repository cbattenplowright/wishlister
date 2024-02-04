package com.caldev.wishlister.models;

import com.caldev.wishlister.enums.PrioritySelection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductModelUnitTests {

    private UserEntity userEntity;
    private Product product;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity("john_doe", "password123", "John Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1));
        product = new Product(userEntity,"Product 1", 10, "https://example.com/product1", "image1.png", PrioritySelection.NON_URGENT, "Description 1");
        product.setProductId(1L);
    }

    @Test
    public void testGetProductId() {
        assertEquals(1L, product.getProductId());
    }

    @Test
    public void testSetProductId() {
        Long newProductId = 2L;
        product.setProductId(newProductId);
        assertEquals(newProductId, product.getProductId());
    }

    @Test
    public void testGetUser() {
        assertEquals(userEntity, product.getUser());
    }

    @Test
    public void testSetUser() {
        UserEntity newUserEntity = new UserEntity("jane_doe", "password123", "Jane Doe", "jane.doe@example.com", LocalDate.of(1995, 1, 1));
        product.setUser(newUserEntity);
        assertEquals(newUserEntity, product.getUser());
    }

    @Test
    public void testGetProductName() {
        assertEquals("Product 1", product.getProductName());
    }

    @Test
    public void testSetProductName() {
        String newProductName = "Product 2";
        product.setProductName(newProductName);
        assertEquals(newProductName, product.getProductName());
    }

    @Test
    public void testGetPrice() {
        assertEquals(10, product.getPrice());
    }

    @Test
    public void testSetPrice() {
        int newPrice = 20;
        product.setPrice(newPrice);
        assertEquals(newPrice, product.getPrice());
    }

    @Test
    public void testGetUrl() {
        assertEquals("https://example.com/product1", product.getUrl());
    }

    @Test
    public void testSetUrl() {
        String newUrl = "https://example.com/product2";
        product.setUrl(newUrl);
        assertEquals(newUrl, product.getUrl());
    }

    @Test
    public void testGetImageUrl() {
        assertEquals("image1.png", product.getImageUrl());
    }

    @Test
    public void testSetImageUrl() {
        String newImageUrl = "image2.png";
        product.setImageUrl(newImageUrl);
        assertEquals(newImageUrl, product.getImageUrl());
    }

    @Test
    public void testGetPriority() {
        assertEquals(PrioritySelection.NON_URGENT, product.getPriority());
    }

    @Test
    public void testSetPriority() {
        PrioritySelection newPriority = PrioritySelection.DESIRABLE;
        product.setPriority(newPriority);
        assertEquals(newPriority, product.getPriority());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Description 1", product.getDescription());
    }

    @Test
    public void testSetDescription() {
        String newDescription = "Description 2";
        product.setDescription(newDescription);
        assertEquals(newDescription, product.getDescription());
    }

    @Test
    public void testGetDateAdded() {
        assertEquals(LocalDate.now(), product.getDateAdded());
    }

    @Test
    public void testSetDateAdded() {
        LocalDate newDateAdded = LocalDate.now();
        product.setDateAdded(newDateAdded);
        assertEquals(newDateAdded, product.getDateAdded());
    }

    @Test
    public void testToString() {
        String expected = "Product{productId=1, user=User{userId=null, username='john_doe', password='password123', name='John Doe', email='john.doe@example.com', dateOfBirth=1990-01-01, wishlists=null, roles=[Role{roleId=null, roleName=ROLE_USER}]}, productName='Product 1', price=10, url='https://example.com/product1', imageUrl='image1.png', priority=NON_URGENT, description='Description 1', dateAdded=2024-02-04}";
        assertEquals(expected, product.toString());
    }
}
