INSERT INTO product (product_name, product_content,product_price,product_category ) VALUES ('test', ' test', 9330,'test');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (1,'test1.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (1,'test1.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (1,'test1.jpg');

INSERT INTO product (product_name, product_content,product_price,product_category ) VALUES ('test2', ' test2', 8000,'test2');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (2,'test2.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (2,'test2.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (2,'test2.jpg');

INSERT INTO product (product_name, product_content,product_price,product_category ) VALUES ('test3', ' test3', 90000,'test3');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (3,'test3.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (3,'test3.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (3,'test3.jpg');

INSERT INTO product (product_name, product_content,product_price,product_category ) VALUES ('test4', ' test4', 36500,'test4');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (4,'test4.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (4,'test4.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (4,'test4.jpg');

INSERT INTO product (product_name, product_content,product_price,product_category ) VALUES ('텐셀 린넨 셔츠', ' 텐셀 린넨 셔츠', 18000,'shrits');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (5,'test5-1.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (5,'test5-2.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (5,'test5-3.jpg');

INSERT INTO product (product_name, product_content,product_price,product_category ) VALUES ('B-스테디 골지 니트 ', 'B-스테디 골지 니트 ', 33000,'Knit');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (6,'test6-1.jpg');
INSERT INTO product_imageurl (product_id, product_imageurl) VALUES (6,'test6-2.jpg');

INSERT INTO USER_ENTITY (EMAIL, PASSWORD, NICKNAME) VALUES ('test123@gmail.com', '$2a$10$mdJIjMG9plZ0lOilqe/hbOtHxFcRG.yo3dLz9RlJqHkGQT1lqyGVq', '괸리자');
INSERT INTO USER_ENTITY (EMAIL, PASSWORD, NICKNAME) VALUES ('test456@gmail.com', '$2a$10$mdJIjMG9plZ0lOilqe/hbOtHxFcRG.yo3dLz9RlJqHkGQT1lqyGVq', '일반유저');

INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_USER');
INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_ADMIN');

INSERT INTO USER_AUTHORITY (ID, AUTHORITY_NAME) values (1, 'ROLE_USER');
INSERT INTO USER_AUTHORITY (ID, AUTHORITY_NAME) values (1, 'ROLE_ADMIN');
INSERT INTO USER_AUTHORITY (ID, AUTHORITY_NAME) values (2, 'ROLE_USER');