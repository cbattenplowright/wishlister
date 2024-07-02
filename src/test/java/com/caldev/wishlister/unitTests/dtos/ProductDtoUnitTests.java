package com.caldev.wishlister.unitTests.dtos;

import com.caldev.wishlister.dtos.ProductDto;
import com.caldev.wishlister.entities.PrioritySelection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDtoUnitTests {

    private ProductDto productDto;
    private UUID userId;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        userId = UUID.randomUUID();
        productDto = new ProductDto("productName", userId, 1, new URL("https://url.com"), new URL("https://imageUrl.com"), PrioritySelection.IMPORTANT, "description", LocalDate.of(2024, 2, 2));
    }

    @Test
    public void shouldGetProductName() {
        String productName = productDto.getProductName();
        assertThat(productName).isEqualTo("productName");
    }

    @Test
    public void shouldGetUserId() {
        UUID userId = productDto.getUserId();
        assertThat(userId).isEqualTo(this.userId);
    }

    @Test
    public void shouldGetPrice() {
        int price = productDto.getPrice();
        assertThat(price).isEqualTo(1);
    }

    @Test
    public void shouldGetUrl() throws MalformedURLException {
        URL url = productDto.getUrl();
        assertThat(url).isEqualTo(new URL("https://url.com"));
    }

    @Test
    public void shouldGetImageUrl() throws MalformedURLException {
        URL imageUrl = productDto.getImageUrl();
        assertThat(imageUrl).isEqualTo(new URL("https://imageUrl.com"));
    }

    @Test
    public void shouldGetPrioritySelection() {
        PrioritySelection prioritySelection = productDto.getPrioritySelection();
        assertThat(prioritySelection).isEqualTo(PrioritySelection.IMPORTANT);
    }

    @Test
    public void shouldGetDescription() {
        String description = productDto.getDescription();
        assertThat(description).isEqualTo("description");
    }

    @Test
    public void shouldGetDateAdded() {
        LocalDate dateAdded = productDto.getDateAdded();
        assertThat(dateAdded).isEqualTo(LocalDate.of(2024, 2, 2));
    }

    @Test
    public void shouldSetProductName() {
        productDto.setProductName("newProductName");
        assertThat(productDto.getProductName()).isEqualTo("newProductName");
    }

    @Test
    public void shouldSetUserId() {
        UUID newUserId = UUID.randomUUID();
        productDto.setUserId(newUserId);
        assertThat(productDto.getUserId()).isEqualTo(newUserId);
    }

    @Test
    public void shouldSetPrice() {
        productDto.setPrice(2);
        assertThat(productDto.getPrice()).isEqualTo(2);
    }

    @Test
    public void shouldSetUrl() throws MalformedURLException {
        URL newUrl = new URL("https://newUrl.com");
        productDto.setUrl(newUrl);
        assertThat(productDto.getUrl()).isEqualTo(newUrl);
    }

    @Test
    public void shouldSetImageUrl() throws MalformedURLException {
        URL newImageUrl = new URL("https://newImageUrl.com");
        productDto.setImageUrl(newImageUrl);
        assertThat(productDto.getImageUrl()).isEqualTo(newImageUrl);
    }

    @Test
    public void shouldSetPrioritySelection() {
        PrioritySelection newPrioritySelection = PrioritySelection.NON_URGENT;
        productDto.setPrioritySelection(newPrioritySelection);
        assertThat(productDto.getPrioritySelection()).isEqualTo(newPrioritySelection);
    }

    @Test
    public void shouldSetDescription() {
        productDto.setDescription("newDescription");
        assertThat(productDto.getDescription()).isEqualTo("newDescription");
    }

    @Test
    public void shouldSetDateAdded() {
        LocalDate newDateAdded = LocalDate.of(2022, 2, 2);
        productDto.setDateAdded(newDateAdded);
        assertThat(productDto.getDateAdded()).isEqualTo(newDateAdded);
    }

    @Test
    public void shouldToString(){
        assertThat(productDto.toString()).isEqualTo("ProductDto{productName='productName', userId=" + userId + ", price=1, url=https://url.com, imageUrl=https://imageUrl.com, prioritySelection=IMPORTANT, description='description', dateAdded=2024-02-02}");

    }

}
