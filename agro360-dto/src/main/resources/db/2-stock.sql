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
  `alias` varchar(32) not null,
  
  `description` varchar(64) default null,
  primary key `stock_tbl_variant_pk`(`article_code`,`variant_code`),
  key `stock_tbl_variant_fk_stock_tbl_article` (`article_code`),
  constraint `stock_tbl_variant_fk_stock_tbl_article` 
	foreign key (`article_code`) 
	references `stock_tbl_article` (`article_code`)
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

drop table if exists `stock_tbl_article_taxe`;
create table `stock_tbl_article_taxe` (
  `article_code` varchar(16) not null,
  `taxe_code` varchar(16) not null,
  
  `created_at` datetime(6) not null,
  `created_by` varchar(16) not null,
  `updated_at` datetime(6) not null,
  `updated_by` varchar(16) not null,
  
  primary key `stock_tbl_article_taxe_pk`(`article_code`, `taxe_code`),
  
  key `stock_tbl_article_taxe_fk_fin_tbl_taxe` (`taxe_code`),
  key `stock_tbl_article_taxe_fk_fin_tbl_ligne` (`article_code`),
  
  constraint `stock_tbl_article_taxe_fk_fin_tbl_taxe` foreign key (`taxe_code`) references `fin_tbl_taxe` (`taxe_code`),
  constraint `stock_tbl_article_taxe_stock_tbl_article` foreign key (`article_code`) references `stock_tbl_article`(`article_code`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

drop table if exists `stock_tbl_inventaire`;
create table `stock_tbl_inventaire` (
  `created_at` datetime not null,
  `created_by` varchar(16) not null,
  `updated_at` datetime not null,
  `updated_by` varchar(16) not null,
  
  `magasin_code` varchar(16) not null,
  `article_code` varchar(16) not null,
  `variant_code` varchar(16) not null,
  `prix_unitaire_achat` decimal(16,4) not null,
  `prix_unitaire_vente` decimal(16,4) not null,
  `unite_achat_code` varchar(16) not null,
  `unite_vente_code` varchar(16) not null,
  `unite_stock_code` varchar(16) not null,
  
  `quantite` double not null,
  primary key `stock_tbl_inventaire_pk`(`magasin_code`, `article_code`, `variant_code`),
  
  key `stock_tbl_inventaire_fk_stock_tbl_magasin` (`magasin_code`),
  key `stock_tbl_inventaire_fk_stock_tbl_variant` (`article_code`, `variant_code`),
  key `stock_tbl_inventaire_fk_stock_tbl_unite_achat` (`unite_achat_code`),
  key `stock_tbl_inventaire_fk_stock_tbl_unite_vente` (`unite_vente_code`),
  key `stock_tbl_inventaire_fk_stock_tbl_unite_stock` (`unite_stock_code`),
  
  constraint `stock_tbl_inventaire_fk_stock_tbl_magasin` 
	foreign key (`magasin_code`) 
	references `stock_tbl_magasin` (`magasin_code`),
  constraint `stock_tbl_inventaire_fk_stock_tbl_variant` 
	foreign key (`article_code`, `variant_code`) 
	references `stock_tbl_variant` (`article_code`, `variant_code`),
  constraint `stock_tbl_inventaire_fk_stock_tbl_unite_achat` 
	foreign key (`unite_achat_code`) 
	references `stock_tbl_unite` (`unite_code`),
  constraint `stock_tbl_inventaire_fk_stock_tbl_unite_vente` 
	foreign key (`unite_vente_code`) 
	references `stock_tbl_unite` (`unite_code`),
  constraint `stock_tbl_inventaire_fk_stock_tbl_unite_stock` 
	foreign key (`unite_stock_code`) 
	references `stock_tbl_unite` (`unite_code`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

drop table if exists `stock_tbl_operation`;
create table `stock_tbl_operation` (
  `created_at` datetime not null,
  `created_by` varchar(16) not null,
  `updated_at` datetime not null,
  `updated_by` varchar(16) not null,
  
  `operation_id` bigint not null auto_increment,
  
  `magasin_code` varchar(16) not null,
  `article_code` varchar(16) not null,
  `variant_code` varchar(16) not null,
  `operation_type` varchar(4) not null,
  `operation_date` datetime not null,
  `prix_unitaire` decimal(16,4) default null,
  
  `quantite` double not null,
  `inventaire_avant` double not null,
  primary key `stock_tbl_operation_pk`(`operation_id`),
  
  key `stock_tbl_operation_stock_tbl_inventaire` (`magasin_code`, `article_code`, `variant_code`),
  constraint `stock_tbl_operation_stock_tbl_inventaire` 
	foreign key (`magasin_code`, `article_code`, `variant_code`) 
	references `stock_tbl_inventaire` (`magasin_code`, `article_code`, `variant_code`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;