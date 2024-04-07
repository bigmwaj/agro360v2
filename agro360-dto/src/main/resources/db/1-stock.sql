drop table if exists `stock_tbl_unite`;
create table `stock_tbl_unite` (
  `created_at` datetime not null,
  `created_by` varchar(16) not null,
  `updated_at` datetime not null,
  `updated_by` varchar(16) not null,
  
  `unite_code` varchar(16) not null,
  
  `description` varchar(64) default null,
  primary key `stock_tbl_unite_pk`(`unite_code`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

drop table if exists `stock_tbl_article`;
create table `stock_tbl_article` (
  `created_at` datetime not null,
  `created_by` varchar(16) not null,
  `updated_at` datetime not null,
  `updated_by` varchar(16) not null,
  
  `article_code` varchar(16) not null,
  
  `description` varchar(64) default null,
  `article_type` varchar(4) not null,
  `unite_code` varchar(16) not null,
  primary key `stock_tbl_article_pk`(`article_code`),
  key `stock_tbl_article_fk_stock_tbl_unite` (`unite_code`),
  constraint `stock_tbl_article_fk_stock_tbl_unite` foreign key (`unite_code`) references `stock_tbl_unite` (`unite_code`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

drop table if exists `stock_tbl_variant`;
create table `stock_tbl_variant` (
  `created_at` datetime not null,
  `created_by` varchar(16) not null,
  `updated_at` datetime not null,
  `updated_by` varchar(16) not null,
  
  `article_code` varchar(255) not null,
  `variant_code` varchar(16) not null,
  
  `description` varchar(64) default null,
  primary key `stock_tbl_variant_pk`(`article_code`,`variant_code`),
  key `stock_tbl_variant_fk_stock_tbl_article` (`article_code`),
  constraint `stock_tbl_variant_fk_stock_tbl_article` foreign key (`article_code`) references `stock_tbl_article` (`article_code`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

drop table if exists `stock_tbl_conversion`;
create table `stock_tbl_conversion` (
  `created_at` datetime not null,
  `created_by` varchar(16) not null,
  `updated_at` datetime not null,
  `updated_by` varchar(16) not null,
  
  `article_code` varchar(255) not null,
  `unite_code` varchar(255) not null,
  
  `facteur` double not null,
  primary key `stock_tbl_conversion_pk`(`article_code`,`unite_code`),
  
  key `stock_tbl_conversion_fk_stock_tbl_article` (`article_code`),
  key `stock_tbl_conversion_fk_stock_tbl_unite` (`unite_code`),
  constraint `stock_tbl_conversion_fk_stock_tbl_article` foreign key (`article_code`) references `stock_tbl_article` (`article_code`),
  constraint `stock_tbl_conversion_fk_stock_tbl_unite` foreign key (`unite_code`) references `stock_tbl_unite` (`unite_code`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

drop table if exists `stock_tbl_magasin`;
create table `stock_tbl_magasin` (
  `created_at` datetime not null,
  `created_by` varchar(16) not null,
  `updated_at` datetime not null,
  `updated_by` varchar(16) not null,
  
  `magasin_code` varchar(16) not null,
  `description` varchar(64) default null,
  primary key (`magasin_code`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

drop table if exists `stock_tbl_casier`;
create table `stock_tbl_casier` (
  `created_at` datetime not null,
  `created_by` varchar(16) not null,
  `updated_at` datetime not null,
  `updated_by` varchar(16) not null,
  
  `casier_code` varchar(16) not null,
  `magasin_code` varchar(255) not null,
  
  `description` varchar(64) default null,
  primary key (`magasin_code`, `casier_code`),
  key `stock_tbl_casier_fk_stock_tbl_magasin` (`magasin_code`),
  constraint `stock_tbl_casier_fk_stock_tbl_magasin` foreign key (`magasin_code`) references `stock_tbl_magasin` (`magasin_code`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;