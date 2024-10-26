package com.caldev.wishlister.services;

import com.caldev.wishlister.dtos.WishlistDto;
import com.caldev.wishlister.entities.PendingShare;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.Wishlist;
import com.caldev.wishlister.entities.WishlistProduct;
import com.caldev.wishlister.exceptions.WishlistsNotFoundException;
import com.caldev.wishlister.repositories.PendingShareRepository;
import com.caldev.wishlister.repositories.WishlistRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WishlistService {

    public final PendingShareRepository pendingShareRepository;

    private final UserService userService;

    private final WishlistProductService wishlistProductService;

    private final WishlistRepository wishlistRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public WishlistService(PendingShareRepository pendingShareRepository, UserService userService,WishlistProductService wishlistProductService, WishlistRepository wishlistRepository) {
        this.pendingShareRepository = pendingShareRepository;
        this.wishlistProductService = wishlistProductService;
        this.userService = userService;
        this.wishlistRepository = wishlistRepository;
    }

    public List<Wishlist> findAllWishlists() {
        return wishlistRepository.findAll();
    }

    public List<Wishlist> findAllUserWishlists(UUID requestedId) {
        List<Wishlist> wishlistList = wishlistRepository.findAllByUserAccount_Id(requestedId);
        return wishlistList;
    }

    public Wishlist createWishlist(WishlistDto wishlistDto, UserAccount userAccount) {

        Wishlist newWishlist = new Wishlist(
                wishlistDto.getWishlistName(),
                userAccount,
                new ArrayList<>()
        );

        Wishlist savedWishlist = wishlistRepository.save(newWishlist);

        return savedWishlist;
    }

    public boolean existsByWishlistNameAndUserAccount(String wishlistName, UserAccount userAccount) {

        return wishlistRepository.existsByWishlistNameAndUserAccount(wishlistName, userAccount);
    }

    public Optional<Wishlist> findWishlistById(Long requestedWishlistId) {
        return wishlistRepository.findById(requestedWishlistId);
    }

    public Wishlist updateWishlist(Long requestedWishlistId, WishlistDto wishlistDto, UserAccount userAccount) {

/*

    PseudoCode for method

    find wishlist by id
        if wishlist not found, throw exception
    else
        if wishlist found, update wishlist
            if userId in wishlistDto is not null, update wishlist userId
            if name in wishlistDto is not null, update wishlist name

*/

        Optional<Wishlist> wishlistToUpdate = findWishlistById(requestedWishlistId);

        if (wishlistToUpdate.isEmpty()) {
            throw new WishlistsNotFoundException("Wishlist not found");
        }

        if (wishlistDto.getUserId() != null && wishlistDto.getUserId() == userAccount.getId()) {
            wishlistToUpdate.get().setUserAccount(userAccount);
        }

        if (wishlistDto.getWishlistName() != null) {
            wishlistToUpdate.get().setWishlistName(wishlistDto.getWishlistName());
        }

        return wishlistRepository.save(wishlistToUpdate.get());
    }

    @Transactional
    public void deleteWishlist(Long requestedWishlistId) {

        wishlistProductService.deleteByWishlistId(requestedWishlistId);

        wishlistRepository.deleteByWishlistId(requestedWishlistId);
    }

    public boolean userAccountOwnsWishlist(Long wishlistId, UserAccount userAccount) {
        return wishlistRepository.existsByWishlistIdAndUserAccount(wishlistId, userAccount);
    }

    public void shareWishlist(Wishlist wishlistToShare, UserAccount sendUserAccount, String recipientUserEmail) {
/*
    If user email exists in database, then:
    Generate Share Token:
        Use UUID or Spring’s Jwt for tokens with added expiration if needed.
    Store the Token with Share Details:
        Create a PendingShare entity to store the token, senderUserId, wishlistId, recipientEmail, and an optional expiryDate.
    Send the Email with Confirmation Link:
        Use an email service to send a "Share" button containing a link with the token.
    When shared user clicks link and logs in, add wishlist to the user adding record to shared_user_wishlist


    If user email does not exist in database, then send email to join wishlister to access friend's shared wishlist
 */

        UserAccount sharedUserAccount = userService.getUserByEmail(recipientUserEmail);

        if (sharedUserAccount != null) {
            String shareToken = initiateShare(wishlistToShare, sendUserAccount, recipientUserEmail);
            sendShareEmail(sendUserAccount.getName(), recipientUserEmail, shareToken);
        }
    }

    public String initiateShare(Wishlist wishlistToShare, UserAccount sendUserAccount, String recipientEmail) {

        String shareToken = UUID.randomUUID().toString();

        PendingShare pendingShare = new PendingShare(
                shareToken,
                sendUserAccount.getId(),
                wishlistToShare.getId(),
                recipientEmail
        );

        pendingShareRepository.save(pendingShare);

        return shareToken;
    }

    public void sendShareEmail(String name, String recipientUserEmail, String shareToken) {

        String confirmationUrl = "http://localhost:8080/wishlist/confirm-share/" + shareToken;
        emailService.send(
                recipientUserEmail,
                "%s has shared a wishlist with you!".formatted(name),
                "Click the link below to see your friend's wishlist with: " + confirmationUrl
                );
    }
}
