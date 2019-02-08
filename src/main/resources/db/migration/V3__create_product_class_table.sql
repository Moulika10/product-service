CREATE TABLE IF NOT EXISTS product_service.product_class
(
    class_code bigint NOT NULL,
    class_description character varying(65) ,
    family_code bigint NOT NULL,
    CONSTRAINT product_class_pkey PRIMARY KEY (class_code),
    CONSTRAINT product_class_family_code_fkey FOREIGN KEY (family_code)
        REFERENCES product_service.product_family (family_code)
);