CREATE TABLE IF NOT EXISTS product_service.service_sub_sector
(
   sub_sector_code character varying(5) NOT NULL,
   sub_sector_name character varying(100),
   super_sector_code character varying(5) ,
   CONSTRAINT service_subsector_pkey PRIMARY KEY (sub_sector_code),
   CONSTRAINT service_subsector_supersector_code_fkey FOREIGN KEY (super_sector_code)
       REFERENCES product_service.service_super_sector (super_sector_code));
