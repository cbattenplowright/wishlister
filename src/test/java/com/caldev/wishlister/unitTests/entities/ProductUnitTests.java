package com.caldev.wishlister.unitTests.entities;

import com.caldev.wishlister.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ProductUnitTests {

    private Product product;
    private User user;
    private Wishlist wishlist;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        user = new User("username", "password", "name", "email@email.com", LocalDate.of(2000, 1, 1), null);
        wishlist = new Wishlist("wishlistName", user, null);
        product = new Product("productName", 100, new URL("https://url"), new URL("https://imageUrl"), "description", PrioritySelection.NON_URGENT, LocalDate.now(), null, user);
    }

    @Test
    public void shouldGetProductId(){
        assertNull(product.getProductId());
    }

    @Test
    public void shouldGetProductName(){
        assertThat(product.getProductName()).isEqualTo("productName");
    }

    @Test
    public void shouldGetPrice(){
        assertThat(product.getPrice()).isEqualTo(100);
    }

    @Test
    public void shouldGetUrl() throws MalformedURLException {
        assertThat(product.getUrl()).isEqualTo(new URL("https://url"));
    }

    @Test
    public void shouldGetImageUrl() throws MalformedURLException {
        assertThat(product.getImageUrl()).isEqualTo(new URL("https://imageUrl"));
    }

    @Test
    public void shouldGetDescription(){
        assertThat(product.getDescription()).isEqualTo("description");
    }

    @Test
    public void shouldGetPriority(){
        assertThat(product.getPriority()).isEqualTo(PrioritySelection.NON_URGENT);
    }

    @Test
    public void shouldGetDateAdded(){
        assertThat(product.getDateAdded()).isEqualTo(LocalDate.now());
    }

    @Test
    public void shouldGetWishlistProducts(){
        assertNull(product.getWishlistProducts());
    }

    @Test
    public void shouldGetUser(){
        assertThat(product.getUser()).isEqualTo(user);
    }

    @Test
    public void shouldSetProductName(){
        product.setProductName("newProductName");
        assertThat(product.getProductName()).isEqualTo("newProductName");
    }

    @Test
    public void shouldSetPrice(){
        product.setPrice(200);
        assertThat(product.getPrice()).isEqualTo(200);
    }

    @Test
    public void shouldSetUrl() throws MalformedURLException {
        product.setUrl(new URL("https://newUrl"));
        assertThat(product.getUrl()).isEqualTo(new URL("https://newUrl"));
    }

    @Test
    public void shouldSetImageUrl() throws MalformedURLException {
        product.setImageUrl(new URL("https://newImageUrl"));
        assertThat(product.getImageUrl()).isEqualTo(new URL("https://newImageUrl"));
    }

    @Test
    public void shouldSetDescription(){
        product.setDescription("newDescription");
        assertThat(product.getDescription()).isEqualTo("newDescription");
    }

    @Test
    public void shouldSetPriority(){
        product.setPriority(PrioritySelection.DESIRABLE);
        assertThat(product.getPriority()).isEqualTo(PrioritySelection.DESIRABLE);
    }

    @Test
    public void shouldSetDateAdded(){
        product.setDateAdded(LocalDate.of(2020, 1, 1));
        assertThat(product.getDateAdded()).isEqualTo(LocalDate.of(2020, 1, 1));
    }

    @Test
    public void shouldSetWishlistProducts(){
        List<WishlistProduct> newWishlistProducts = null;
        product.setWishlistProducts(newWishlistProducts);
        assertNull(product.getWishlistProducts());
    }

    @Test
    public void shouldSetUser(){
        User newUser = new User("newUsername", "newPassword", "newName", "newEmail@email.com", LocalDate.of(2000, 1, 1), null);
        product.setUser(newUser);
        assertThat(product.getUser()).isEqualTo(newUser);
    }

    @Test
    public void shouldToString(){
        assertThat(product.toString()).isEqualTo("Product{productId=null, productName='productName', price=100, url=https://url, imageUrl=https://imageUrl, description='description', priority=NON_URGENT, dateAdded=" + LocalDate.now() + ", wishlistProducts=null, user=" + user + "}");
    }
}
