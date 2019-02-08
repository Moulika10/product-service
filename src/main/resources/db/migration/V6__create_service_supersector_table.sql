CREATE TABLE IF NOT EXISTS  product_service.service_super_sector
(
super_sector_code character varying(5) NOT NULL,
super_sector_name character varying(100),
CONSTRAINT service_supersector_pkey PRIMARY KEY (super_sector_code)
);