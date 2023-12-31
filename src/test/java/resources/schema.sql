CREATE TABLE userEntities
(
    user_id SERIAL NOT NULL UNIQUE PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL
);

CREATE TABLE products
(
    products_id SERIAL NOT NULL UNIQUE PRIMARY KEY,
    user_id BIGINT,
    product_name VARCHAR(255) NOT NULL,
    price INT,
    url VARCHAR(255),
    image_url VARCHAR(255),
    priority VARCHAR(255),
    description VARCHAR(255),
    date_added DATE DEFAULT CURRENT_DATE,
    FOREIGN KEY (user_id) REFERENCES userEntities(user_id)
);

CREATE TABLE lists
(
    list_id SERIAL NOT NULL UNIQUE PRIMARY KEY,
    user_id BIGINT,
    name VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES userEntities(user_id)
);

CREATE TABLE shared_user_lists
(
    shared_user_lists_id SERIAL NOT NULL UNIQUE PRIMARY KEY,
    list_id BIGINT,
    shared_user_id BIGINT,
    FOREIGN KEY (list_id) REFERENCES lists(list_id),
    FOREIGN KEY (shared_user_id) REFERENCES userEntities(user_id)
);

CREATE TABLE list_products
(
    list_products_id SERIAL NOT NULL UNIQUE PRIMARY KEY,
    list_id BIGINT,
    product_id BIGINT,
    is_purchased BOOLEAN DEFAULT FALSE NOT NULL,
    FOREIGN KEY (list_id) REFERENCES lists(list_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

