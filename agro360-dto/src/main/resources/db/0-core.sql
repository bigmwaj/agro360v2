drop table if exists `core_tbl_code_gen`;
create table `core_tbl_code_gen`(
  	`created_at` 	datetime not null,
  	`created_by` 	varchar(16) not null,
  	`updated_at` 	datetime not null,
  	`updated_by` 	varchar(16) not null,
  	
 	`code_gen_code` varchar(16) not null,
 	
  	`seq` 			long not null,
  	`pref` 		varchar(16) not null,
	primary key `core_tbl_code_gen_pk`(`code_gen_code`)
) 
engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

drop table if exists `core_tbl_partner`;
create table `core_tbl_partner`(
  	`created_at` 	datetime not null,
  	`created_by` 	varchar(16) not null,
  	`updated_at` 	datetime not null,
  	`updated_by` 	varchar(16) not null,
  	
 	`partner_code` 	varchar(16) not null,
 	
  	`address` 		varchar(64) default null,
  	`city` 			varchar(16) default null,
  	`country` 		varchar(16) default null,
  	`email` 		varchar(32) not null,
  	`first_name` 	varchar(32) not null,
  	`last_name` 	varchar(32) not null,
  	`name` 			varchar(32) default null,
  	`phone` 		varchar(16) not null,
  	`status` 		varchar(8) not null,
  	`partner_type` 	varchar(8) default null,
  	`title` 		varchar(8) default null,
  	`status_date` 	datetime default null,
	primary key `core_tbl_partner_pk`(`partner_code`)
) 
engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

drop table if exists `core_tbl_cat`;
create table `core_tbl_cat` (
  	`created_at` datetime not null,
  	`created_by` varchar(16) not null,
  	`updated_at` datetime not null,
  	`updated_by` varchar(16) not null,
  
  	`category_code` varchar(16) not null,
  
  	`description` varchar(64) default null,
  	`parent_category_code` varchar(16) default null,
  	
  	primary key `core_tbl_cat_pk`(`category_code`),
  	
  	key `core_tbl_cat_fk_core_tbl_cat` (`parent_category_code`),
  	constraint `core_tbl_cat_fk_core_tbl_cat` foreign key (`parent_category_code`) references `core_tbl_cat` (`category_code`)
) 
engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

drop table if exists `core_tbl_partner_cat`;
create table `core_tbl_partner_cat` (
  	`created_at` 	datetime not null,
  	`created_by` 	varchar(16) not null,
  	`updated_at` 	datetime not null,
  	`updated_by` 	varchar(16) not null,
  	
  	`category_code` varchar(255) not null,
  	`partner_code` 	varchar(255) not null,
  	
  	primary key `core_tbl_partner_cat_pk`(`category_code`,`partner_code`),
  	
	key `core_tbl_partner_cat_fk_core_tbl_cat` (`category_code`),
	key `core_tbl_partner_cat_fk_core_tbl_partner` (`partner_code`),
  	constraint `core_tbl_partner_cat_fk_core_tbl_cat` foreign key (`category_code`) references `core_tbl_cat` (`category_code`),
  	constraint `core_tbl_partner_cat_fk_core_tbl_partner` foreign key (`partner_code`) references `core_tbl_partner` (`partner_code`)
) 
engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

drop table if exists `core_tbl_user_account`;
create table `core_tbl_user_account`(
  	`created_at` 	datetime not null,
  	`created_by` 	varchar(16) not null,
  	`updated_at` 	datetime not null,
  	`updated_by` 	varchar(16) not null,
  	
 	`partner_code` 	varchar(16) not null,
 	
  	`password` 		varchar(512) not null,
  	`lang` 			varchar(5) null,
  	`magasin` 		varchar(16) null,
  	`roles` 		varchar(32) null,
  	`status` 		varchar(8) not null,
  	`status_date` 	datetime default null,
	primary key `core_tbl_user_account_pk`(`partner_code`),
	key `core_tbl_user_account_fk_core_tbl_partner` (`partner_code`),
  	constraint `core_tbl_user_account_fk_core_tbl_partner` foreign key (`partner_code`) references `core_tbl_partner` (`partner_code`)
) 
engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

