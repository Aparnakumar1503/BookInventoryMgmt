-- Destructive setup script for a fresh database.
-- Do not run this automatically on every application startup if you want data to persist.
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

-- STATE
CREATE TABLE state (
                       state_code CHAR(2) PRIMARY KEY,
                       state_name VARCHAR(50)
);

-- CATEGORY
CREATE TABLE category (
                          cat_id INT PRIMARY KEY,
                          cat_description VARCHAR(50)
);

-- PERMROLE
CREATE TABLE permrole (
                          role_number SERIAL PRIMARY KEY,
                          perm_role VARCHAR(30)
);

-- USERS (renamed from user)
CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       last_name VARCHAR(30),
                       first_name VARCHAR(20),
                       phone_number VARCHAR(20),
                       user_name VARCHAR(50),
                       password VARCHAR(100),
                       role_number INT REFERENCES permrole(role_number)
);

ALTER SEQUENCE users_user_id_seq RESTART WITH 1000000;

-- AUTHOR
CREATE TABLE author (
                        author_id SERIAL PRIMARY KEY,
                        last_name VARCHAR(50),
                        first_name VARCHAR(50),
                        photo VARCHAR(10)
);

-- REVIEWER
CREATE TABLE reviewer (
                          reviewer_id SERIAL PRIMARY KEY,
                          name VARCHAR(50),
                          employed_by VARCHAR(50)
);

-- PUBLISHER
CREATE TABLE publisher (
                           publisher_id INT PRIMARY KEY,
                           name VARCHAR(50),
                           city VARCHAR(50),
                           state_code CHAR(2) REFERENCES state(state_code)
);

-- BOOK
CREATE TABLE book (
                      isbn VARCHAR(20) PRIMARY KEY,
                      title VARCHAR(100),
                      description TEXT,
                      cat_id INT REFERENCES category(cat_id),
                      edition VARCHAR(10),
                      publisher_id INT REFERENCES publisher(publisher_id)
);

-- BOOK CONDITION
CREATE TABLE bookcondition (
                               ranks INT PRIMARY KEY,
                               description VARCHAR(50),
                               full_description TEXT,
                               price DECIMAL(10,2)
);

-- INVENTORY
CREATE TABLE inventory (
                           inventory_id SERIAL PRIMARY KEY,
                           isbn VARCHAR(20) REFERENCES book(isbn),
                           ranks INT REFERENCES bookcondition(ranks),
                           purchased BOOLEAN DEFAULT FALSE
);

ALTER SEQUENCE inventory_inventory_id_seq RESTART WITH 1000000;

-- BOOK AUTHOR
CREATE TABLE bookauthor (
                            isbn VARCHAR(20),
                            author_id INT,
                            primary_author CHAR(1),
                            PRIMARY KEY (isbn, author_id),
                            FOREIGN KEY (isbn) REFERENCES book(isbn),
                            FOREIGN KEY (author_id) REFERENCES author(author_id)
);

-- BOOK REVIEW
CREATE TABLE book_review (
                             id SERIAL PRIMARY KEY,
                             isbn VARCHAR(20),
                             reviewer_id INT,
                             rating INT,
                             comments TEXT,
                             FOREIGN KEY (isbn) REFERENCES book(isbn),
                             FOREIGN KEY (reviewer_id) REFERENCES reviewer(reviewer_id)
);

-- SHOPPING CART
CREATE TABLE shoppingcart (
                              user_id INT,
                              isbn VARCHAR(20),
                              PRIMARY KEY (user_id, isbn),
                              FOREIGN KEY (user_id) REFERENCES users(user_id),
                              FOREIGN KEY (isbn) REFERENCES book(isbn)
);

-- PURCHASE LOG
CREATE TABLE purchaselog (
                             user_id INT,
                             inventory_id INT,
                             PRIMARY KEY (user_id, inventory_id),
                             FOREIGN KEY (user_id) REFERENCES users(user_id),
                             FOREIGN KEY (inventory_id) REFERENCES inventory(inventory_id)
);
