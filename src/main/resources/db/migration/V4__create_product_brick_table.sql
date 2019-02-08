CREATE TABLE IF NOT EXISTS product_service.product_brick
(
brick_code bigint NOT NULL,
brick_description character varying(90),
class_code bigint NOT NULL,
CONSTRAINT product_brick_pkey PRIMARY KEY (brick_code),
CONSTRAINT product_brick_class_code_fkey FOREIGN KEY (class_code)
REFERENCES product_service.product_class (class_code));