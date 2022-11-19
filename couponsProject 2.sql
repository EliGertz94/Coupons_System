 create database coupons;
use coupons;

-- select * from coupons;
-- select * from company;
-- select * from customer;
-- select * from categories;
-- select * from CUSTOMERS_VS_COUPONS;



create table `company`(`id` int NOT NULL PRIMARY KEY auto_increment,
 `name` varchar(25),
 `email` varchar(50)  NOT NULL ,
 `password` varchar(60) NOT NULL );
 
 create table `customer`(`id` int NOT NULL PRIMARY KEY auto_increment,
 `FIRST_NAME` varchar(25),
  `LAST_NAME` varchar(25),
 `email` varchar(50)  NOT NULL,
 `password` varchar(60) NOT NULL );
 
 create table `categories`(`id` int NOT NULL PRIMARY KEY auto_increment,
 `name` varchar(25) );
 
 -- IMPORTANT to insert all the categories here--
 
insert into categories values(1,'Food');
insert into categories values(2,'Electricity');
insert into categories values(3,'Restaurant');
insert into categories values(4,'Vacation');



create table `coupons`(`id` int auto_increment,
 `COMPANY_ID` int NOT NULL,
  `CATEGORY_ID` int NOT NULL,
  `TITLE` varchar(50),
  `DESCRIPTION` varchar(50),
 `START_DATE` date,
 `END_DATE` date,
 `AMOUNT` int,
 `PRICE` double,
 `IMAGE` varchar(50),
 PRIMARY KEY (id),
 FOREIGN KEY (COMPANY_ID) REFERENCES company(id),
 FOREIGN KEY (CATEGORY_ID) REFERENCES categories(id));
 
 
 create table `CUSTOMERS_VS_COUPONS`
 (`CUSTOMER_ID` int  ,
 `COUPON_ID` int ,
	PRIMARY KEY (CUSTOMER_ID,COUPON_ID),
	FOREIGN KEY (CUSTOMER_ID) REFERENCES customer(id) ON DELETE CASCADE,
	FOREIGN KEY (COUPON_ID) REFERENCES coupons(id) ON DELETE CASCADE
);



