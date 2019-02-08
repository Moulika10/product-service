CREATE TABLE IF NOT EXISTS product_service.owner_service
(
id Bigserial primary key,
owner_id character varying(36) NOT NULL,
service_name character varying(500) ,
industry_group_code character varying(4) Not null,
deleted boolean NOT NULL DEFAULT false,
UNIQUE (owner_id, industry_group_code, service_name),
CONSTRAINT owner_service_industry_group_code_fkey FOREIGN KEY (industry_group_code)
        REFERENCES product_service.service_industry_group (industry_group_code));
