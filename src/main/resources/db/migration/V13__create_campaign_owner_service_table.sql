CREATE TABLE IF NOT EXISTS product_service.campaign_owner_service
(
id Bigserial primary key,
campaign_id integer NOT NULL,
owner_service_id bigint NOT NULL,
deleted boolean NOT NULL DEFAULT false,
UNIQUE (campaign_id, owner_service_id),
CONSTRAINT campaign_owner_service_owner_service_id_fkey FOREIGN KEY (owner_service_id)
REFERENCES product_service.owner_service (id));