package com.caldev.wishlister.unitTests.dtos;

import com.caldev.wishlister.dtos.ProductDto;
import com.caldev.wishlister.entities.PrioritySelection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDtoUnitTests {

    private ProductDto testProductDto;
    private UUID testUserId;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        testUserId = UUID.randomUUID();
        testProductDto = new ProductDto(
                "testProduct",
                testUserId,
                1599,
                new URL("https://www.testUrl.com"),
                new URL("https://www.imageUrl.com"),
                PrioritySelection.NON_URGENT,
                "description",
                LocalDate.now(),
                new ArrayList<>()
        );
    }

    @Test
    public void shouldGetProductName() {
        String productName = testProductDto.getProductName();
        assertThat(productName).isEqualTo("productName");
    }

    @Test
    public void shouldGetUserId() {
        UUID userId = testProductDto.getUserId();
        assertThat(userId).isEqualTo(this.testUserId);
    }

    @Test
    public void shouldGetPrice() {
        int price = testProductDto.getPrice();
        assertThat(price).isEqualTo(1);
    }

    @Test
    public void shouldGetUrl() throws MalformedURLException {
        URL url = testProductDto.getUrl();
        assertThat(url).isEqualTo(new URL("https://url.com"));
    }

    @Test
    public void shouldGetImageUrl() throws MalformedURLException {
        URL imageUrl = testProductDto.getImageUrl();
        assertThat(imageUrl).isEqualTo(new URL("https://imageUrl.com"));
    }

    @Test
    public void shouldGetPrioritySelection() {
        PrioritySelection prioritySelection = testProductDto.getPrioritySelection();
        assertThat(prioritySelection).isEqualTo(PrioritySelection.IMPORTANT);
    }

    @Test
    public void shouldGetDescription() {
        String description = testProductDto.getDescription();
        assertThat(description).isEqualTo("description");
    }

    @Test
    public void shouldGetDateAdded() {
        LocalDate dateAdded = testProductDto.getDateAdded();
        assertThat(dateAdded).isEqualTo(LocalDate.of(2024, 2, 2));
    }

    @Test
    public void shouldSetProductName() {
        testProductDto.setProductName("newProductName");
        assertThat(testProductDto.getProductName()).isEqualTo("newProductName");
    }

    @Test
    public void shouldSetUserId() {
        UUID newUserId = UUID.randomUUID();
        testProductDto.setUserId(newUserId);
        assertThat(testProductDto.getUserId()).isEqualTo(newUserId);
    }

    @Test
    public void shouldSetPrice() {
        testProductDto.setPrice(2);
        assertThat(testProductDto.getPrice()).isEqualTo(2);
    }

    @Test
    public void shouldSetUrl() throws MalformedURLException {
        URL newUrl = new URL("https://newUrl.com");
        testProductDto.setUrl(newUrl);
        assertThat(testProductDto.getUrl()).isEqualTo(newUrl);
    }

    @Test
    public void shouldSetImageUrl() throws MalformedURLException {
        URL newImageUrl = new URL("https://newImageUrl.com");
        testProductDto.setImageUrl(newImageUrl);
        assertThat(testProductDto.getImageUrl()).isEqualTo(newImageUrl);
    }

    @Test
    public void shouldSetPrioritySelection() {
        PrioritySelection newPrioritySelection = PrioritySelection.NON_URGENT;
        testProductDto.setPrioritySelection(newPrioritySelection);
        assertThat(testProductDto.getPrioritySelection()).isEqualTo(newPrioritySelection);
    }

    @Test
    public void shouldSetDescription() {
        testProductDto.setDescription("newDescription");
        assertThat(testProductDto.getDescription()).isEqualTo("newDescription");
    }

    @Test
    public void shouldSetDateAdded() {
        LocalDate newDateAdded = LocalDate.of(2022, 2, 2);
        testProductDto.setDateAdded(newDateAdded);
        assertThat(testProductDto.getDateAdded()).isEqualTo(newDateAdded);
    }

    @Test
    public void shouldToString(){
        assertThat(testProductDto.toString()).isEqualTo("ProductDto{productName='productName', userId=" + testUserId + ", price=1, url=https://url.com, imageUrl=https://imageUrl.com, prioritySelection=IMPORTANT, description='description', dateAdded=2024-02-02}");

    }

}
