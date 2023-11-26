# Wishlister

Wish list application for users to create wishlists

## API Contract

Request
  URI: /lists/{id}
  HTTP Verb: GET
  Body: None

Response:
  HTTP Status:
    200 OK if the user is authorized and the list was successfully retrieved
    403 UNAUTHORIZED if the user is unauthenticated or unauthorized
    404 NOT FOUND if the user is authenticated and authorized but the Cash Card cannot be found
  Response Body Type: JSON
  Example Response Body:
    {
      "id": 1,
      "list_name": "Christmas 2023",
      "user": {
          "username": "mike234"
          "name": "mike",
          "dob": 14-12-00,
          "email": "email@address.com"
        }
      "products": [
          {
            "id": 2,
            "product_name": "iPhone 13",
            "price": 2459,
            "category": "Technology",
            "URL": "https://www.apple.com/uk/shop/buy-iphone/iphone-13",
            "imageURL": "http://localhost:8081/image-server/iphone-13",
            "priority": "must-have",
            "description": The iPhone 13, Apple's latest flagship smartphone, is a true marvel of technology and design. Featuring a stunning Super Retina XDR display, it delivers vibrant colors and sharp details for an immersive visual experience. The powerful A15 Bionic chip ensures smooth performance, making every task, from gaming to multitasking, a breeze.",
            "isPurchased": false"
          },
          {
            "id": 3,
            "product_name": "The Silent Symphony",
            "price": 25.99,
            "category": "Books",
            "URL": "https://www.examplebookstore.com/the-silent-symphony",
            "imageURL": "http://localhost:8081/image-server/silent-symphony",
            "priority": "must-read",
            "description": "Dive into the enchanting world of 'The Silent Symphony,' a captivating novel that weaves together tales of love, mystery, and self-discovery. Written by acclaimed author Jane A. Writer, this literary masterpiece promises an immersive experience that will leave you spellbound.",
            "isPurchased": false
          },
          {
            "id": 4,
            "product_name": "Strategic Conquest",
            "price": 49.99,
            "category": "Board Games",
            "URL": "https://www.examplegamestore.com/strategic-conquest",
            "imageURL": "http://localhost:8081/image-server/strategic-conquest",
            "priority": "game-night-essential",
            "description": "Embark on a thrilling journey of strategy and conquest with the 'Strategic Conquest' board game. This epic game of skill and wit is designed for players who love tactical challenges and strategic maneuvers. Gather your friends for an unforgettable game night filled with excitement and friendly competition.",
            "isPurchased": false
          }
        ],
        "shared_users": [
          {
            "username": "janedoe123",
            "name": "Jane Doe",
            "dob": "05-22-89",
            "email": "jane@example.com"
          },
          {
            "username": "johnsmith456",
            "name": "John Smith",
            "dob": "11-10-92",
            "email": "john@example.com"
          }
        }]
    }

