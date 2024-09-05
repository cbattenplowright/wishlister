package com.caldev.wishlister.integrationTests.controllers.WishlistProductController;

import com.caldev.wishlister.controllers.WishlistProductController;
import com.caldev.wishlister.security.SecurityConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = WishlistProductController.class)
public class WishlistProductControllerShowWishlistProductIntegrationTests {
}
