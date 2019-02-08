--Inserting data into service_super_sector table
INSERT INTO product_service.service_super_sector (super_sector_code, super_sector_name)
SELECT "2017_NAICS_US_Code","2017_NAICS_US_Title" FROM product_service.raw_table_for_services_2017
WHERE (LENGTH("2017_NAICS_US_Code") = 2 or "2017_NAICS_US_Code" LIKE '%-%')
AND NOT EXISTS (SELECT * FROM product_service.service_super_sector);

--Inserting data into service_sub_sector table
INSERT INTO product_service.service_sub_sector (sub_sector_code, sub_sector_name)
SELECT  "2017_NAICS_US_Code","2017_NAICS_US_Title"
FROM product_service.raw_table_for_services_2017 WHERE LENGTH("2017_NAICS_US_Code") = 3
AND NOT EXISTS (SELECT * FROM product_service.service_sub_sector);

--Updating foreign key constraint for service_sub_sector:
UPDATE product_service.service_sub_sector
SET super_sector_code = (select table2.super_sector_code
FROM (SELECT a.super_sector_code, b.sub_sector_code, b.sub_sector_name
FROM product_service.service_super_sector a
INNER JOIN product_service.service_sub_sector b
ON SUBSTR(a.super_sector_code,1,2)=SUBSTR(b.sub_sector_code,1,2)
OR SUBSTR(a.super_sector_code,4,2)>=SUBSTR(b.sub_sector_code,1,2)
AND SUBSTR(a.super_sector_code,1,2)<=SUBSTR(b.sub_sector_code,1,2)) table2
WHERE service_sub_sector.sub_sector_code = table2.sub_sector_code);

--Inserting data into service_industry_group table
INSERT INTO product_service.service_industry_group (industry_group_code,
 industry_group_name,sub_sector_code)
SELECT DISTINCT "2017_NAICS_US_Code","2017_NAICS_US_Title",
SUBSTRING("2017_NAICS_US_Code",1,3) tt
FROM product_service.raw_table_for_services_2017
WHERE LENGTH("2017_NAICS_US_Code") = 4
AND NOT EXISTS (SELECT * FROM product_service.service_industry_group);
