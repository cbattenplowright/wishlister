package com.caldev.wishlister.services;

import com.caldev.wishlister.dtos.NewUserDto;
import com.caldev.wishlister.dtos.UserAccountDto;
import com.caldev.wishlister.entities.Authority;
import com.caldev.wishlister.entities.Product;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.Wishlist;
import com.caldev.wishlister.exceptions.UserNotFoundException;
import com.caldev.wishlister.repositories.AuthorityRepository;
import com.caldev.wishlister.repositories.ProductRepository;
import com.caldev.wishlister.repositories.UserManagementRepository;
import com.caldev.wishlister.repositories.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final AuthorityRepository authorityRepository;

    private final ProductRepository productRepository;

    private final WishlistRepository wishlistRepository;

    private final UserManagementRepository userManagementRepository;

    public UserService(AuthorityRepository authorityRepository, ProductRepository productRepository, UserManagementRepository userManagementRepository, WishlistRepository wishlistRepository) {
        this.authorityRepository = authorityRepository;
        this.productRepository = productRepository;
        this.userManagementRepository = userManagementRepository;
        this.wishlistRepository = wishlistRepository;
    }

    public List<UserAccount> getAllUsers(){
        return userManagementRepository.findAll();
    }

    public UserAccount getUserById(UUID requestedId){
        return userManagementRepository.findById(requestedId).orElse(null);
    }

    public UserAccount createUser(NewUserDto newUserDto){
        Set<Authority> userAuthority = new HashSet<>(List.of(authorityRepository.findByAuthority("ROLE_USER")));

        UserAccount newUserAccount = new UserAccount(
                newUserDto.getEmail(),
                newUserDto.getPassword(),
                newUserDto.getName(),
                newUserDto.getDateOfBirth(),
                userAuthority
                );
        return userManagementRepository.save(newUserAccount);
    }

    public UserAccount updateUser(UUID requestedId, UserAccountDto userAccountDto){
        UserAccount userAccountToUpdate = userManagementRepository.findById(requestedId).orElse(null);

        if (userAccountToUpdate == null){
            throw new UserNotFoundException("User not found with id: " + requestedId);
        }

        if (userAccountDto.getEmail() != null){
            userAccountToUpdate.setEmail(userAccountDto.getEmail());
        }

        if(userAccountDto.getPassword() != null){
            userAccountToUpdate.setPassword(userAccountDto.getPassword());
        }

        if(userAccountDto.getName() != null){
            userAccountToUpdate.setName(userAccountDto.getName());
        }

        if(userAccountDto.getDateOfBirth() != null){
            userAccountToUpdate.setDateOfBirth(userAccountDto.getDateOfBirth());
        }

        if(userAccountDto.getAuthorityIds() != null){
            List<Authority> authorities = new ArrayList<>();

            for (Long authorityId : userAccountDto.getAuthorityIds()) {
                Authority authority = authorityRepository.findById(authorityId).orElse(null);
                if (authority != null){
                    authorities.add(authority);
                }
            }
            userAccountToUpdate.setAuthorities(new HashSet<>(authorities));
        }

        if (userAccountDto.getWishlistIds() != null){
            List<Wishlist> wishlists = new ArrayList<>();

            for (Long wishlistId : userAccountDto.getWishlistIds()) {
                Wishlist wishlist = wishlistRepository.findById(wishlistId).orElse(null);
                if (wishlist != null){
                    wishlists.add(wishlist);
                }
            }
            userAccountToUpdate.setWishlists(wishlists);
        }

        if (userAccountDto.getProductIds() != null) {
            List<Product> products = new ArrayList<>();

            for (Long productId : userAccountDto.getProductIds()) {
                Product product = productRepository.findById(productId).orElse(null);
                if (product != null){
                    products.add(product);
                }
            }
            userAccountToUpdate.setProducts(products);
        }

        return userManagementRepository.save(userAccountToUpdate);
    }

    @Transactional
    public void deleteUser(UUID requestedId){
        if (userManagementRepository.findById(requestedId).isEmpty()){
            throw new UserNotFoundException("User not found with id: " + requestedId);
        }
        userManagementRepository.deleteById(requestedId);
    }
}
