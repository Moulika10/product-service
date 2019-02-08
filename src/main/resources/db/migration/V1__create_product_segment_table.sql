CREATE TABLE IF NOT EXISTS product_service.product_segment
 (
 segment_code bigint NOT NULL,
 segment_description character varying(45) ,
 CONSTRAINT product_segment_pkey PRIMARY KEY (segment_code)
);