#Product-Service

##Requirements

* Java 8
* Unlimited Strength Java(TM) Cryptography Extension Policy Files
  for the Java(TM) Platform, Standard Edition Runtime Environment 8 - 
  http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html
* git
* gradle
* postgres

## Base Data Setup

1. The initial data set up is created manually here are the sequence of steps.

* Create a database for the product-service service in postgres by using the following sql script.

-- Database: product-service
CREATE DATABASE "product_service"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

* Create the following schema in the product_service database.

-- SCHEMA: product_service
CREATE SCHEMA product_service
    AUTHORIZATION postgres;

* Try to load the raw data into master table fro products and services from the .csv files by following the below steps.

-- Table: product_service.raw_table_for_products_2017
CREATE TABLE product_service.raw_table_for_products_2017
(
    "2017_Segment_Code" bigint,
    "2017_Segment_Description" character varying(45) COLLATE pg_catalog."default",
    "2017_Family_Code" bigint,
    "2017_Family_Description" character varying(60) COLLATE pg_catalog."default",
    "2017_Class_Code" bigint,
    "2017_Class_Description" character varying(65) COLLATE pg_catalog."default",
    "2017_Brick Code" bigint,
    "2017_Brick_Description" character varying(90) COLLATE pg_catalog."default",
    "Brick Definition" character varying(2000) COLLATE pg_catalog."default"
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

-- Table: product_service.raw_table_for_services_2017
CREATE TABLE product_service.raw_table_for_services_2017
(
    "Seq_No" integer,
    "2017_NAICS_US_Code" character(20) COLLATE pg_catalog."default",
    "2017_NAICS_US_Title" character varying(150) COLLATE pg_catalog."default"
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

--From pgAdmin4 GUI right click_on_the_raw_table_for_products_2017>Import/Export
select Import, choose the file, select yes for header, choose ',' as delimiter  and click on OK


3. using the "Get New Access Token" UI dialog in Postman

#Setting up the profiles when running the application

Data source configuration can be set up based on the profiles from different property files.
For local: application-local.properties
For develop: application-develop.properties
For production: application.properties
Please refer '/usr/local/src/product-service/archive/spring.profiles.active.png'
