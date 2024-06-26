drop table if exists `av_tbl_commande`;
create table `av_tbl_commande` (
  `commande_code` varchar(16) not null,
  `created_at` datetime(6) not null,
  `created_by` varchar(16) not null,
  `updated_at` datetime(6) not null,
  `updated_by` varchar(16) not null,
  `status_date` datetime(6) default null,
  `commande_date` date not null,
  `description` varchar(256) default null,
  `status` varchar(4) not null,
  `commande_type` varchar(8) not null,
  `magasin_code` varchar(16) not null,
  `partner_code` varchar(16) not null,
  `cumul_paiement` decimal(16,4) not null,
  `compte_code` varchar(16) default null,  
  
  `taxe` decimal(16,4) not null,
  `remise` decimal(16,4) not null,
  `prix_total_ht` decimal(16,4) not null,
  `prix_total` decimal(16,4) not null,
  
  primary key `av_tbl_commande_pk`(`commande_code`),
  
  key `av_tbl_commande_fk_stock_tbl_magasin` (`magasin_code`),
  key `av_tbl_commande_fk_core_tbl_partner` (`partner_code`),
  key `av_tbl_commande_fk_fin_tbl_compte` (`compte_code`),
  
  constraint `av_tbl_commande_fk_core_tbl_partner` foreign key (`partner_code`) references `core_tbl_partner` (`partner_code`),
  constraint `av_tbl_commande_fk_stock_tbl_magasin` foreign key (`magasin_code`) references `stock_tbl_magasin` (`magasin_code`),
  constraint `av_tbl_commande_fk_fin_tbl_compte` foreign key (`compte_code`) references `fin_tbl_compte` (`compte_code`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

drop table if exists `av_tbl_ligne`;
create table `av_tbl_ligne` (
  `ligne_id` bigint not null auto_increment,
  `created_at` datetime(6) not null,
  `created_by` varchar(16) not null,
  `updated_at` datetime(6) not null,
  `updated_by` varchar(16) not null,
  `description` varchar(128) default null,
  `prix_unitaire` decimal(16,4) not null,
  `quantite` double not null,
  `ligne_type` varchar(4) not null,
  `variant_code` varchar(16) default null,
  `article_code` varchar(16) default null,
  `commande_code` varchar(16) not null,
  `unite_code` varchar(16) default null,
  
  `remise_raison` varchar(256) default null,
  `remise_type` varchar(8) default null,
  `remise_taux` double default null,
  `remise_montant` decimal(16,4) not null,
  
  `taxe` decimal(16,4) not null,
  `prix_total_ht` decimal(16,4) not null,
  `prix_total` decimal(16,4) not null,
  `remise` decimal(16,4) not null,
  
  primary key `av_tbl_ligne_pk`(`ligne_id`),
  key `av_tbl_ligne_fk_stock_tbl_article` (`article_code`),
  key `av_tbl_ligne_fk_av_tbl_commande` (`commande_code`),
  key `av_tbl_ligne_fk_stock_tbl_unite` (`unite_code`),
  constraint `av_tbl_ligne_fk_stock_tbl_unite` foreign key (`unite_code`) references `stock_tbl_unite` (`unite_code`),
  constraint `av_tbl_ligne_fk_av_tbl_commande` foreign key (`commande_code`) references `av_tbl_commande` (`commande_code`),
  constraint `av_tbl_ligne_fk_stock_tbl_article` foreign key (`article_code`) references `stock_tbl_article` (`article_code`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

drop table if exists `av_tbl_facture`;
create table `av_tbl_facture` (
  `facture_code` varchar(16) not null,
  `created_at` datetime(6) not null,
  `created_by` varchar(16) not null,
  `updated_at` datetime(6) not null,
  `updated_by` varchar(16) not null,
  `status_date` datetime(6) default null,
  `facture_date` date not null,
  `description` varchar(256) default null,
  `status` varchar(4) not null,
  `facture_type` varchar(8) not null,
  `commande_code` varchar(16) default null,
  `partner_code` varchar(16) not null,
  `remise` decimal(16,4) not null,
  `taxe` decimal(16,4) not null,
  `prix_total_ht` decimal(16,4) not null,
  `prix_total` decimal(16,4) not null,
  `cumul_paiement` decimal(16,4) not null,
  primary key `av_tbl_facture_pk`(`facture_code`),
  key `av_tbl_facture_fk_av_tbl_commande` (`commande_code`),
  key `av_tbl_facture_fk_core_tbl_partner` (`partner_code`),
  constraint `av_tbl_facture_fk_core_tbl_partner` foreign key (`partner_code`) references `core_tbl_partner` (`partner_code`),
  constraint `av_tbl_facture_fk_av_tbl_commande` foreign key (`commande_code`) references `av_tbl_commande` (`commande_code`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

drop table if exists `av_tbl_reg_fact`;
create table `av_tbl_reg_fact` (
  `reg_id` bigint not null auto_increment,
  `created_at` datetime(6) not null,
  `created_by` varchar(16) not null,
  `updated_at` datetime(6) not null,
  `updated_by` varchar(16) not null,
  `facture_code` varchar(16) not null,
  `transaction_code` varchar(20) not null,
  primary key `av_tbl_reg_fact_pk`(`reg_id`),
  key `av_tbl_reg_fact_fk_av_tbl_facture` (`facture_code`),
  key `av_tbl_reg_fact_fk_fin_tbl_transaction` (`transaction_code`),
  constraint `av_tbl_reg_fact_fk_av_tbl_facture` foreign key (`facture_code`) references `av_tbl_facture` (`facture_code`),
  constraint `av_tbl_reg_fact_fk_fin_tbl_transaction` foreign key (`transaction_code`) references `fin_tbl_transaction` (`transaction_code`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

drop table if exists `av_tbl_reg_cmd`;
create table `av_tbl_reg_cmd` (
  `reg_id` bigint not null auto_increment,
  `created_at` datetime(6) not null,
  `created_by` varchar(16) not null,
  `updated_at` datetime(6) not null,
  `updated_by` varchar(16) not null,
  `commande_code` varchar(16) not null,
  `transaction_code` varchar(20) default null,
  primary key `av_tbl_reg_cmd_pk`(`reg_id`),
  key `av_tbl_reg_cmd_fk_av_tbl_commande` (`commande_code`),
  key `av_tbl_reg_cmd_fk_fin_tbl_transaction` (`transaction_code`),
  constraint `av_tbl_reg_cmd_fk_fin_tbl_transaction` foreign key (`transaction_code`) references `fin_tbl_transaction` (`transaction_code`),
  constraint `av_tbl_reg_cmd_fk_av_tbl_commande` foreign key (`commande_code`) references `av_tbl_commande` (`commande_code`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

drop table if exists `av_tbl_ligne_taxe`;
create table `av_tbl_ligne_taxe` (
  `commande_code` varchar(16) not null,
  `ligne_id` bigint not null,
  `taxe_code` varchar(16) not null,
  
  `created_at` datetime(6) not null,
  `created_by` varchar(16) not null,
  `updated_at` datetime(6) not null,
  `updated_by` varchar(16) not null,
  
  `montant` decimal(16,4) not null,  
  `taux` double not null,
  
  primary key `av_tbl_ligne_taxe_pk`(`commande_code`, `ligne_id`, `taxe_code`),
  
  key `av_tbl_ligne_taxe_fk_fin_tbl_taxe` (`taxe_code`),
  key `av_tbl_ligne_taxe_fk_av_tbl_ligne` (`commande_code`, `ligne_id`),
  
  constraint `av_tbl_ligne_taxe_fk_fin_tbl_taxe` foreign key (`taxe_code`) references `fin_tbl_taxe` (`taxe_code`),
  constraint `av_tbl_ligne_taxe_fk_av_tbl_ligne` foreign key (`commande_code`, `ligne_id`) references `av_tbl_ligne`(`commande_code`, `ligne_id`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

drop table if exists `av_tbl_reception`;
create table `av_tbl_reception` (
  `reception_id` bigint not null auto_increment,
  `commande_code` varchar(16) not null,
  `ligne_id` bigint not null,
  
  `created_at` datetime(6) not null,
  `created_by` varchar(16) not null,
  `updated_at` datetime(6) not null,
  `updated_by` varchar(16) not null,
  
  `status` varchar(4) not null,
  `status_date` datetime(6) default null,
  
  `reception_date` datetime(6) not null,
  `description` varchar(128) default null,
  `quantite` double not null,
  `unite_code` varchar(16) not null,
  
  primary key `av_tbl_reception_pk`(`reception_id`),
  
  key `av_tbl_reception_fk_av_tbl_ligne` (`commande_code`, `ligne_id`),
  key `av_tbl_reception_fk_stock_tbl_unite` (`unite_code`),
  
  constraint `av_tbl_reception_fk_av_tbl_ligne` foreign key (`commande_code`, `ligne_id`) references `av_tbl_ligne` (`commande_code`, `ligne_id`),
  constraint `av_tbl_reception_fk_stock_tbl_unite` foreign key (`unite_code`) references `stock_tbl_unite` (`unite_code`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

drop table if exists `av_tbl_retour`;
create table `av_tbl_retour` (
  `retour_id` bigint not null auto_increment,
  `commande_code` varchar(16) not null,
  `ligne_id` bigint not null,
  
  `created_at` datetime(6) not null,
  `created_by` varchar(16) not null,
  `updated_at` datetime(6) not null,
  `updated_by` varchar(16) not null,
  
  `status` varchar(4) not null,
  `status_date` datetime(6) default null,
  
  `quantite` double not null,
  `retour_date` datetime(6) not null,
  `description` varchar(128) default null,
  `unite_code` varchar(16) not null,
  
  primary key `av_tbl_retour_pk`(`retour_id`),
  
  key `av_tbl_retour_fk_av_tbl_ligne` (`commande_code`, `ligne_id`),
  key `av_tbl_retour_fk_stock_tbl_unite` (`unite_code`),
  
  constraint `av_tbl_retour_fk_av_tbl_ligne` foreign key (`commande_code`, `ligne_id`) references `av_tbl_ligne` (`commande_code`, `ligne_id`),
  constraint `av_tbl_retour_fk_stock_tbl_unite` foreign key (`unite_code`) references `stock_tbl_unite` (`unite_code`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

drop table if exists `av_tbl_facture_taxe`;
create table `av_tbl_facture_taxe` (
  `facture_code` varchar(16) not null,
  `taxe_code` varchar(16) not null,
  
  `created_at` datetime(6) not null,
  `created_by` varchar(16) not null,
  `updated_at` datetime(6) not null,
  `updated_by` varchar(16) not null,
  
  `taux` double not null,
  `montant` decimal(16,4) not null,
  
  primary key `av_tbl_facture_taxe_pk`(`facture_code`, `taxe_code`),
  
  key `av_tbl_facture_taxe_fk_fin_tbl_taxe` (`taxe_code`),
  key `av_tbl_facture_taxe_fk_av_tbl_facture` (`facture_code`),
  
  constraint `av_tbl_facture_taxe_fk_fin_tbl_taxe` foreign key (`taxe_code`) references `fin_tbl_taxe` (`taxe_code`),
  constraint `av_tbl_facture_taxe_fk_av_tbl_facture` foreign key (`facture_code`) references `av_tbl_facture`(`facture_code`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;



