INSERT INTO product (product_name, product_content,product_price,product_category ) VALUES ('반폴라니트', ' ', 1,'outer');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (1,'test1.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (1,'test1.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (1,'test1.jpg');
INSERT INTO product_size (product_id, product_size) VALUES (1,'Free');
INSERT INTO product_color (product_id, product_color) VALUES (1,'Black');
INSERT INTO product_color (product_id, product_color) VALUES (1,'White');
INSERT INTO product (product_name, product_content,product_price,product_category ) VALUES ('test2', ' ', 1,'outer');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (2,'test2.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (2,'test2.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (2,'test2.jpg');
INSERT INTO product_size (product_id, product_size) VALUES (2,'S');
INSERT INTO product_size (product_id, product_size) VALUES (2,'M');
INSERT INTO product_size (product_id, product_size) VALUES (2,'L');
INSERT INTO product_color (product_id, product_color) VALUES (2,'Black');
INSERT INTO product_color (product_id, product_color) VALUES (2,'White');
INSERT INTO product (product_name, product_content,product_price,product_category ) VALUES ('스트라이프 티셔츠', ' ', 1,'top');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (3,'test3.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (3,'test3.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (3,'test3.jpg');
INSERT INTO product_size (product_id, product_size) VALUES (3,'Free');
INSERT INTO product_color (product_id, product_color) VALUES (3,'Black');
INSERT INTO product_color (product_id, product_color) VALUES (3,'White');
INSERT INTO product (product_name, product_content,product_price,product_category ) VALUES ('브이넥 니트', ' ', 36500,'top');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (4,'test4.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (4,'test4.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (4,'test4.jpg');
INSERT INTO product_size (product_id, product_size) VALUES (4,'S');
INSERT INTO product_size (product_id, product_size) VALUES (4,'M');
INSERT INTO product_size (product_id, product_size) VALUES (4,'L');
INSERT INTO product_color (product_id, product_color) VALUES (4,'Black');
INSERT INTO product_color (product_id, product_color) VALUES (4,'White');
INSERT INTO product (product_name, product_content,product_price,product_category ) VALUES ('텐셀 린넨 셔츠', ' ', 18000,'top');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (5,'test5-1.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (5,'test5-2.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (5,'test5-3.jpg');
INSERT INTO product_size (product_id, product_size) VALUES (5,'S');
INSERT INTO product_size (product_id, product_size) VALUES (5,'M');
INSERT INTO product_size (product_id, product_size) VALUES (5,'L');
INSERT INTO product_color (product_id, product_color) VALUES (5,'Grey');
INSERT INTO product_color (product_id, product_color) VALUES (5,'White');
INSERT INTO product (product_name, product_content,product_price,product_category ) VALUES ('B-스테디 골지 니트 ', '', 33000,'Knit');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (6,'test6-1.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (6,'test6-2.jpg');
INSERT INTO product_size (product_id, product_size) VALUES (6,'Free');
INSERT INTO product_color (product_id, product_color) VALUES (6,'Grey');
INSERT INTO product_color (product_id, product_color) VALUES (6,'Red');

INSERT INTO USER_ENTITY (EMAIL, PASSWORD, NICKNAME,ADDRESS) VALUES ('test123@gmail.com', '$2a$10$N97lS2a6Bje23Ljjs6x4i.caq6LGhFatoq.UHZx349ygZ5w8mdq2m', '홍길동','남양주 평내로');
INSERT INTO USER_ENTITY (EMAIL, PASSWORD, NICKNAME) VALUES ('test456@gmail.com', '$2a$10$N97lS2a6Bje23Ljjs6x4i.caq6LGhFatoq.UHZx349ygZ5w8mdq2m', '강감찬');

INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_ADMIN');
INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_USER');

INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) values (1, 2);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) values (1, 1);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) values (2, 2);

INSERT INTO CART (PRODUCT_ID, USER_ID, COLOR,SIZE ) VALUES (4, 1, 'RED','M');
INSERT INTO CART (PRODUCT_ID, USER_ID, COLOR,SIZE ) VALUES (5, 1, 'RED','M');
INSERT INTO CART (PRODUCT_ID, USER_ID, COLOR,SIZE ) VALUES (3, 1, 'RED','M');
INSERT INTO CART (PRODUCT_ID, USER_ID, COLOR,SIZE) VALUES (1, 2, 'BLACK','M');
INSERT INTO CART (PRODUCT_ID, USER_ID, COLOR,SIZE) VALUES (3, 2, 'YELLOW','M');


