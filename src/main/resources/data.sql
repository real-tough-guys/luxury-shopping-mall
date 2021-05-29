INSERT INTO product (product_name, product_content,product_price,product_category ) VALUES ('test', ' test', 9330,'outer');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (1,'test1.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (1,'test1.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (1,'test1.jpg');

INSERT INTO product (product_name, product_content,product_price,product_category ) VALUES ('test2', ' test2', 8000,'outer');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (2,'test2.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (2,'test2.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (2,'test2.jpg');

INSERT INTO product (product_name, product_content,product_price,product_category ) VALUES ('test3', ' test3', 90000,'top');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (3,'test3.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (3,'test3.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (3,'test3.jpg');

INSERT INTO product (product_name, product_content,product_price,product_category ) VALUES ('test4', ' test4', 36500,'top');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (4,'test4.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (4,'test4.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (4,'test4.jpg');

INSERT INTO product (product_name, product_content,product_price,product_category ) VALUES ('텐셀 린넨 셔츠', ' 텐셀 린넨 셔츠', 18000,'top');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (5,'test5-1.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (5,'test5-2.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (5,'test5-3.jpg');

INSERT INTO product (product_name, product_content,product_price,product_category ) VALUES ('B-스테디 골지 니트 ', 'B-스테디 골지 니트 ', 33000,'Knit');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (6,'test6-1.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (6,'test6-2.jpg');

INSERT INTO USER_ENTITY (EMAIL, PASSWORD, NICKNAME) VALUES ('test123@gmail.com', '$2a$10$N97lS2a6Bje23Ljjs6x4i.caq6LGhFatoq.UHZx349ygZ5w8mdq2m', '홍길동');
INSERT INTO USER_ENTITY (EMAIL, PASSWORD, NICKNAME) VALUES ('test456@gmail.com', '$2a$10$N97lS2a6Bje23Ljjs6x4i.caq6LGhFatoq.UHZx349ygZ5w8mdq2m', '강감찬');

INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_ADMIN');
INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_USER');

INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) values (1, 2);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) values (1, 1);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) values (2, 2);

INSERT INTO CART (PRODUCT_ID, USER_ID, COLOR) VALUES (1, 1, 'RED');
INSERT INTO CART (PRODUCT_ID, USER_ID, COLOR) VALUES (2, 1, 'RED');
INSERT INTO CART (PRODUCT_ID, USER_ID, COLOR) VALUES (1, 2, 'BLACK');
INSERT INTO CART (PRODUCT_ID, USER_ID, COLOR) VALUES (3, 2, 'YELLOW');