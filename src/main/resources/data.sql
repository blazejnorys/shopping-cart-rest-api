-- DROP
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS shopping_cart;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS shopping_cart_product;
DROP SEQUENCE IF EXISTS user_s;
DROP SEQUENCE IF EXISTS shopping_cart_s;
DROP SEQUENCE IF EXISTS product_s;
DROP SEQUENCE IF EXISTS shopping_cart_product_s;

-- CREATE
CREATE TABLE product (
  id INTEGER NOT NULL,
  code VARCHAR(50) NOT NULL,
  description VARCHAR(100) NOT NULL,
  price DECIMAL,
  quantity INTEGER NOT NULL,
  version INTEGER NOT NULL
);

CREATE TABLE shopping_cart (
  id INTEGER NOT NULL,
  total_price DECIMAL
);

CREATE TABLE user (
  id INTEGER NOT NULL,
  shopping_cart_id INTEGER NULL,
  login VARCHAR(50)
);

CREATE TABLE shopping_cart_product (
  id INTEGER NOT NULL,
  shopping_cart_id INTEGER NULL,
  product_id INTEGER NULL
);

-- PRIMAEY KEYS
ALTER TABLE product ADD CONSTRAINT PK_product_id PRIMARY KEY (id);
ALTER TABLE shopping_cart ADD CONSTRAINT PK_shopping_rest_id PRIMARY KEY (id);
ALTER TABLE user ADD CONSTRAINT PK_user_id PRIMARY KEY (id);
ALTER TABLE shopping_cart_product ADD CONSTRAINT PK_shopping_cart_product_id PRIMARY KEY (id);

-- FOREIGN KEYS
ALTER TABLE shopping_cart_product ADD CONSTRAINT FK_shopping_cart_product_sc FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart (id);
ALTER TABLE shopping_cart_product ADD CONSTRAINT FK_shopping_cart_product_p FOREIGN KEY (product_id) REFERENCES product(id);
ALTER TABLE user ADD CONSTRAINT FK_user_sc FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart(id);

-- SEQUENCES
CREATE SEQUENCE user_s START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE shopping_cart_s START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE shopping_cart_product_s START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE product_s START WITH 1 INCREMENT BY 1;

-- INSERT
-- INSERT INTO shopping_cart(id,total_price);
INSERT INTO shopping_cart (id) VALUES (nextval('shopping_cart_s'));
INSERT INTO user (id,login,shopping_cart_id) VALUES (nextval('user_s'),'kowalski',1);
INSERT INTO product (id,code,description,price,quantity,version) VALUES (nextval('product_s'),'MILK_L','Mleko 0%',3.52,100,0);
INSERT INTO product (id,code,description,price,quantity,version) VALUES (nextval('product_s'),'MILK_F','Mleko 3%',3.29,100,0);
INSERT INTO product (id,code,description,price,quantity,version) VALUES (nextval('product_s'),'MILK','Mleko pełnotłuste',3,100,0);
INSERT INTO product (id,code,description,price,quantity,version) VALUES (nextval('product_s'),'WATER_S','Woda mineralna gazowana',1.5,100,0);
INSERT INTO product (id,code,description,price,quantity,version) VALUES (nextval('product_s'),'BEER','Piwerko',1,100,0);
INSERT INTO product (id,code,description,price,quantity,version) VALUES (nextval('product_s'),'RICE_J','Ryż jaśminowy',4,100,0);
INSERT INTO product (id,code,description,price,quantity,version) VALUES (nextval('product_s'),'RICE_B','Ryż basmati',3,100,0);
INSERT INTO product (id,code,description,price,quantity,version) VALUES (nextval('product_s'),'MANGO','Mango',3.5,100,0);
INSERT INTO product (id,code,description,price,quantity,version) VALUES (nextval('product_s'),'BREAD','Chlebek',2.2,100,0);
INSERT INTO product (id,code,description,price,quantity,version) VALUES (nextval('product_s'),'ORANGE','Pomarańcz',2.2,0,0);
