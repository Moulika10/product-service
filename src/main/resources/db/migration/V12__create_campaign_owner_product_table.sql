CREATE TABLE IF NOT EXISTS product_service.campaign_owner_product
(
id Bigserial primary key,
campaign_id integer NOT NULL,
owner_product_id bigint NOT NULL,
deleted boolean NOT NULL DEFAULT false,
UNIQUE (campaign_id, owner_product_id),
CONSTRAINT campaign_owner_product_owner_product_id_fkey FOREIGN KEY (owner_product_id)
REFERENCES product_service.owner_product (id));
