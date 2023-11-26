# Wishlister

Wish list application for users to create wishlists

## API Contract

## List Endpoints

### SHOW
```
Request
  URI: /lists/{id}
  HTTP Verb: GET
  Body: None
  Authorization: Only the list owner or users with whom the list has been shared should be allowed.

Authorization Principles:
  List Owner Access: The authenticated user must be the owner of the requested list.
  Shared User Access: The authenticated user must be one of the users with whom the list has been shared.

Response:
  HTTP Status:
    200 OK if the user is authorized and the list was successfully retrieved
    403 FORBIDDEN if the user is unauthenticated or unauthorized
    404 NOT FOUND if the user is authenticated and authorized but the Cash Card cannot be found
  Response Body Type: JSON
  Example Response Body:
    {
      "id": 1,
      "list_name": "Christmas 2023",
      "items": [
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
          ...
        ],
        "owner": {
          "username": "mike234"
          "name": "mike",
          "dob": 14-12-00,
          "email": "email@address.com"
        }
        "shared_users": [
          {
            "username": "janedoe123",
            "name": "Jane Doe",
            "dob": "05-22-89",
            "email": "jane@example.com"
          },
          ...
        }]
    }
```
### CREATE
```
Request
  URI: /lists
  HTTP Verb: POST
  Body: {
      "listName": "24th Birthday"
    }

Response:
  HTTP Status:
    201 CREATED if the user is authorized and the list was successfully created
    400 BAD REQUEST if the client's request is malformed or missing required parameters.
    403 FORBIDDEN if the user is unauthenticated or unauthorized
    404 NOT FOUND if the user is authenticated and authorized but the Cash Card cannot be found

  Response Body Type: JSON
  Header: Location=/lists/42
```

