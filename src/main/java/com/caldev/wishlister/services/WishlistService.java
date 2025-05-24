package com.caldev.wishlister.services;

import com.caldev.wishlister.dtos.ProductDto;
import com.caldev.wishlister.dtos.WishlistDto;
import com.caldev.wishlister.entities.*;
import com.caldev.wishlister.exceptions.WishlistsNotFoundException;
import com.caldev.wishlister.repositories.PendingShareRepository;
import com.caldev.wishlister.repositories.SharedWishlistRepository;
import com.caldev.wishlister.repositories.WishlistRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WishlistService {

    public final EmailService emailService;

    public final PendingShareRepository pendingShareRepository;

    private final UserService userService;

    private final WishlistProductService wishlistProductService;

    private final SharedWishlistRepository sharedWishlistRepository;

    private final WishlistRepository wishlistRepository;

    public WishlistService(EmailService emailService, PendingShareRepository pendingShareRepository, UserService userService, WishlistProductService wishlistProductService, SharedWishlistRepository sharedWishlistRepository, WishlistRepository wishlistRepository) {
        this.emailService = emailService;
        this.pendingShareRepository = pendingShareRepository;
        this.wishlistProductService = wishlistProductService;
        this.userService = userService;
        this.sharedWishlistRepository = sharedWishlistRepository;
        this.wishlistRepository = wishlistRepository;
    }

    public List<Wishlist> findAllWishlists() {
        return wishlistRepository.findAll();
    }

    public List<Wishlist> findAllUserWishlists(UUID requestedId) {
        List<Wishlist> wishlistList = wishlistRepository.findAllByUserAccount_Id(requestedId);
        return wishlistList;
    }

//    Added this method to get all wishlists for a user account and products in those wishlists
    public Optional<WishlistDto> findWishlistByIdWithProducts(Long wishlistId) {
        return wishlistRepository.findByIdWithProducts(wishlistId)
                .map(wishlist -> new WishlistDto (
                        wishlist.getWishlistId(),
                        wishlist.getUserAccount().getId(),
                        wishlist.getWishlistName(),
                        wishlist.getWishlistProducts().stream()
                                .map(wp -> new ProductDto(
                                        wp.getProduct().getProductId(),
                                        wp.getProduct().getProductName(),
                                        wp.getProduct().getPrice(),
                                        wp.isPurchased(),
                                        wp.getWishlistProductId()
                                ))
                                .toList()
                ));
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

    public String getWishlistNameById(Long requestedWishlistId) {
        return wishlistRepository.findWishlistNameById(requestedWishlistId);
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
        String shareToken = initiateShare(wishlistToShare, sendUserAccount, recipientUserEmail);

        if (sharedUserAccount != null) {
            sendShareEmail(sendUserAccount.getName(), recipientUserEmail, shareToken);
        }
        else {
            sendRegisterUserEmail(sendUserAccount.getName(), recipientUserEmail, shareToken);
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

        String confirmationUrl = "http://localhost:8080/api/wishlists/confirm-share?shareToken=" + shareToken;
//        emailService.sendEmail();
        emailService.sendEmail(
                recipientUserEmail,
                "%s has shared a wishlist with you!".formatted(name),
                "Click the link below to see your friend's wishlist with: " + confirmationUrl
        );
    }

    public void sendRegisterUserEmail(String name, String recipientUserEmail, String shareToken) {

//        TODO need to add the front end url to the sendRegisterUserEmail to navigate to the account creation page
        String confirmationUrl = "http://localhost:8080/register?shareToken=" + shareToken;
        emailService.sendEmail(recipientUserEmail,
                "Your friend %s wants to share their wishlist with you!".formatted(name),
                "Click the link below to join Wishlister and access their wishlist: " + confirmationUrl
        );
    }

    public boolean verifyShareToken(String shareToken) {
        PendingShare pendingShare = pendingShareRepository.findByToken(shareToken);

        return pendingShare != null;
    }

    public boolean userAccountMatchesWithRecipientEmail(String shareToken, String recipientEmail) {
        PendingShare pendingShare = pendingShareRepository.findByToken(shareToken);

        return pendingShare != null && pendingShare.getRecipientEmail().equals(recipientEmail);
    }

    public void confirmShare(String shareToken, UserAccount userAccount) {
        PendingShare pendingShare = pendingShareRepository.findByToken(shareToken);

        Wishlist wishlistToShare = wishlistRepository.findById(pendingShare.getWishlistId()).get();

        SharedWishlist newSharedWishlist = new SharedWishlist(
                userAccount,
                wishlistToShare
        );

        sharedWishlistRepository.save(newSharedWishlist);

        pendingShareRepository.delete(pendingShare);

    }

    public List<PendingShare> findPendingSharesByRecipientEmail(String recipientEmail) {
        return pendingShareRepository.findAllByRecipientEmail(recipientEmail);
    }
}
