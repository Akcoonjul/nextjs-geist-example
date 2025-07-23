-- SQL schema for restaurant management system

CREATE DATABASE IF NOT EXISTS restaurantdb;
USE restaurantdb;

CREATE TABLE menu_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DOUBLE NOT NULL
);

CREATE TABLE `order` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id BIGINT NOT NULL,
    status VARCHAR(100),
    CONSTRAINT fk_order_table FOREIGN KEY (table_id) REFERENCES `table`(id)
);

CREATE TABLE order_menu_item (
    order_id BIGINT NOT NULL,
    menu_item_id BIGINT NOT NULL,
    PRIMARY KEY (order_id, menu_item_id),
    CONSTRAINT fk_order_menu_order FOREIGN KEY (order_id) REFERENCES `order`(id),
    CONSTRAINT fk_order_menu_item FOREIGN KEY (menu_item_id) REFERENCES menu_item(id)
);

CREATE TABLE `table` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    capacity INT NOT NULL,
    available BOOLEAN NOT NULL
);

CREATE TABLE staff (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    role VARCHAR(255),
    contact VARCHAR(255)
);
