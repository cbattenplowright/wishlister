package com.caldev.wishlister.unitTests;

import com.caldev.wishlister.entities.PrioritySelections;
import com.caldev.wishlister.entities.Product;
import com.caldev.wishlister.entities.User;
import com.caldev.wishlister.entities.Wishlist;
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
        wishlist = new Wishlist("wishlistName", user);
        product = new Product("productName", 100, new URL("https://url"), new URL("https://imageUrl"), "description", PrioritySelections.NON_URGENT, LocalDate.now(), List.of(wishlist), user);
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
        assertThat(product.getPriority()).isEqualTo(PrioritySelections.NON_URGENT);
    }

    @Test
    public void shouldGetDateAdded(){
        assertThat(product.getDateAdded()).isEqualTo(LocalDate.now());
    }

    @Test
    public void shouldGetWishlist(){
        assertThat(product.getWishlists()).isEqualTo(List.of(wishlist));
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
        product.setPriority(PrioritySelections.DESIRABLE);
        assertThat(product.getPriority()).isEqualTo(PrioritySelections.DESIRABLE);
    }

    @Test
    public void shouldSetDateAdded(){
        product.setDateAdded(LocalDate.of(2020, 1, 1));
        assertThat(product.getDateAdded()).isEqualTo(LocalDate.of(2020, 1, 1));
    }

    @Test
    public void shouldSetWishlist(){
        List<Wishlist> newWishlists = List.of(new Wishlist("newWishlistName", user));
        product.setWishlists(newWishlists);
        assertThat(product.getWishlists()).isEqualTo(newWishlists);
    }

    @Test
    public void shouldSetUser(){
        User newUser = new User("newUsername", "newPassword", "newName", "newEmail@email.com", LocalDate.of(2000, 1, 1), null);
        product.setUser(newUser);
        assertThat(product.getUser()).isEqualTo(newUser);
    }

    @Test
    public void shouldToString(){
        assertThat(product.toString()).isEqualTo("Product{productId=null, productName='productName', price=100, url=https://url, imageUrl=https://imageUrl, description='description', priority=NON_URGENT, dateAdded=" + LocalDate.now() + ", wishlist=[Wishlist{id=null, wishlistName='wishlistName', user=" + user + "}], user=" + user + "}");
    }
}
