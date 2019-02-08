CREATE TABLE IF NOT EXISTS product_service.service_industry_group
(
   industry_group_code character varying(4) NOT NULL,
   industry_group_name character varying(107) ,
   sub_sector_code character varying(3) NOT NULL,
   CONSTRAINT service_industry_group_pkey PRIMARY KEY (industry_group_code),
   CONSTRAINT service_industry_group_sub_sector_code_fkey FOREIGN KEY (sub_sector_code)
       REFERENCES product_service.service_sub_sector (sub_sector_code));
