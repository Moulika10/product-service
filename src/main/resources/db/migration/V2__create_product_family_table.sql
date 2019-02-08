CREATE TABLE IF NOT EXISTS product_service.product_family
(
 family_code bigint NOT NULL,
 family_description character varying(60) ,
 segment_code bigint,
 CONSTRAINT product_family_pkey PRIMARY KEY (family_code),
 CONSTRAINT product_family_segment_code_fkey FOREIGN KEY (segment_code)
     REFERENCES product_service.product_segment (segment_code)
);


