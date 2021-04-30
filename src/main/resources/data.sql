
DROP TABLE IF EXISTS product;

CREATE TABLE product_entity (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  product_name VARCHAR(250) ,
  product_content VARCHAR(250) ,
  product_price VARCHAR(250) ,
  product_category VARCHAR(250)
);