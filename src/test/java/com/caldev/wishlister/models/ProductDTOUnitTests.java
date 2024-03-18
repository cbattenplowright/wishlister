package com.caldev.wishlister.models;

import com.caldev.wishlister.enums.PrioritySelection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ProductDTOUnitTests {

    private ProductDTO productDTO;
    UUID mockUserEntityId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO(mockUserEntityId, "productName", 125, "url", "imageUrl", PrioritySelection.IMPORTANT, "description");
    }

    @Test
    void getUserEntityId(){
        assertEquals(mockUserEntityId, productDTO.getUserEntityId());
    }

    @Test
    void setUserEntityId(){
        UUID newUserEntityId = UUID.randomUUID();
        productDTO.setUserEntityId(newUserEntityId);
        assertEquals(newUserEntityId, productDTO.getUserEntityId());
    }

    @Test
    void getProductName() {
        assertEquals("productName", productDTO.getProductName());
    }

    @Test
    void setProductName() {
        String newProductName = "newProductName";
        productDTO.setProductName(newProductName);
        assertEquals(newProductName, productDTO.getProductName());
    }

    @Test
    void getPrice() {
        assertEquals(125, productDTO.getPrice());
    }

    @Test
    void setPrice() {
        int newPrice = 2932;
        productDTO.setPrice(newPrice);
        assertEquals(2932, productDTO.getPrice());
    }

    @Test
    void getUrl() {
        assertEquals("url", productDTO.getUrl());
    }

    @Test
    void setUrl() {
        String newUrl = "newUrl";
        productDTO.setUrl(newUrl);
        assertEquals(newUrl, productDTO.getUrl());
    }

    @Test
    void getPriority() {
        assertEquals(PrioritySelection.IMPORTANT, productDTO.getPriority());
    }

    @Test
    void setPriority(){
        productDTO.setPriority(PrioritySelection.NON_URGENT);
        assertEquals(PrioritySelection.NON_URGENT, productDTO.getPriority());
    }

    @Test
    void getDescription() {
        assertEquals("description", productDTO.getDescription());
    }

    @Test
    void setDescription() {
        String newDescription = "new description";
        productDTO.setDescription("new description");
        assertEquals("new description", productDTO.getDescription());
    }

    @Test
    void testToString(){
            String expected = "ProductDTO{userEntityId=" + mockUserEntityId + ", productName='productName', price=125, url='url', imageUrl='imageUrl', priority=IMPORTANT, description='description'}";
            assertEquals(expected, productDTO.toString());
    }
}
