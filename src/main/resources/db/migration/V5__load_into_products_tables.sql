--Inserting data into product_segment table
INSERT INTO product_service.product_segment (segment_code,
segment_description)
SELECT DISTINCT "2017_Segment_Code","2017_Segment_Description" FROM product_service.raw_table_for_products_2017
WHERE NOT EXISTS (SELECT * FROM product_service.product_segment);

--Inserting data into product_family table
INSERT INTO  product_service.product_family
(family_code, family_description, segment_code)
SELECT DISTINCT "2017_Family_Code","2017_Family_Description", "2017_Segment_Code"
from product_service.raw_table_for_products_2017
WHERE NOT EXISTS (SELECT * FROM product_service.product_family);

--Inserting data into product_class table
INSERT INTO  product_service.product_class(class_code, class_description, family_code)
SELECT DISTINCT "2017_Class_Code","2017_Class_Description", "2017_Family_Code" FROM product_service.raw_table_for_products_2017
WHERE NOT EXISTS (SELECT * FROM product_service.product_class);

--Inserting data into product_brick table
INSERT INTO  product_service.product_brick (brick_code, brick_description, class_code)
SELECT DISTINCT "2017_Brick Code","2017_Brick_Description", "2017_Class_Code" FROM product_service.raw_table_for_products_2017
WHERE NOT EXISTS (SELECT * FROM product_service.product_brick);