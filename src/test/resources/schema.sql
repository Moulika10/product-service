CREATE SCHEMA IF NOT EXISTS product_service;

--Create RawTable for Products.
CREATE TABLE IF NOT EXISTS product_service.raw_table_for_products_2017
(
    "2017_Segment_Code" bigint,
    "2017_Segment_Description" character varying(45),
    "2017_Family_Code" bigint,
    "2017_Family_Description" character varying(60),
    "2017_Class_Code" bigint,
    "2017_Class_Description" character varying(65),
    "2017_Brick Code" bigint,
    "2017_Brick_Description" character varying(90),
    "Brick Definition" character varying(2000)
);

--Create RawTable for Services.
CREATE TABLE IF NOT EXISTS product_service.raw_table_for_services_2017
(
    "Seq_No" integer,
    "2017_NAICS_US_Code" character(20),
    "2017_NAICS_US_Title" character varying(150)
);

--Inserting Dummy Data into RawTable for Services.
INSERT INTO product_service.raw_table_for_services_2017
VALUES
    (1, '10','Agriculture'),
    (2, '111', 'Crop Production'),
    (3, '1111', 'OilSeed'),
    (4, '21', 'Mining'),
    (5, '211', 'Oil and Gas'),
    (6, '2111', 'Oil and Gas'),
    (7, '22', 'Utilities'),
    (8, '221', 'Utilities'),
    (9, '92', 'National Security');


