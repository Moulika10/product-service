CREATE TABLE IF NOT EXISTS product_service.owner_product
(
id Bigserial primary key,
owner_id character varying(36) NOT NULL,
product_name character varying(500),
brick_code bigint Not null,
deleted boolean NOT NULL DEFAULT false,
UNIQUE (owner_id, brick_code, product_name),
CONSTRAINT owner_product_brick_code_fkey FOREIGN KEY (brick_code)
        REFERENCES product_service.product_brick (brick_code));




