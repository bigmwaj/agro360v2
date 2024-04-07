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
  `paiement_comptant` decimal(16,4) default null,
  `compte_code` varchar(16) default null,
  
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
  `montant` decimal(16,4) not null,
  `status` varchar(4) not null,
  `facture_type` varchar(8) not null,
  `commande_code` varchar(16) default null,
  `partner_code` varchar(16) not null,
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
  `montant` decimal(16,4) not null,
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
  `montant` decimal(16,4) not null,
  `commande_code` varchar(16) not null,
  `transaction_code` varchar(20) default null,
  primary key `av_tbl_reg_cmd_pk`(`reg_id`),
  key `av_tbl_reg_cmd_fk_av_tbl_commande` (`commande_code`),
  key `av_tbl_reg_cmd_fk_fin_tbl_transaction` (`transaction_code`),
  constraint `av_tbl_reg_cmd_fk_fin_tbl_transaction` foreign key (`transaction_code`) references `fin_tbl_transaction` (`transaction_code`),
  constraint `av_tbl_reg_cmd_fk_av_tbl_commande` foreign key (`commande_code`) references `av_tbl_commande` (`commande_code`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

drop table if exists `av_tbl_ligne_recep`;
create table `av_tbl_ligne_recep` (
  `reception_id` bigint not null auto_increment,
  `created_at` datetime(6) not null,
  `created_by` varchar(16) not null,
  `updated_at` datetime(6) not null,
  `updated_by` varchar(16) not null,
  `status_date` datetime(6) default null,
  `reception_date` datetime(6) not null,
  `description` varchar(128) default null,
  `quantite` double not null,
  `status` varchar(4) not null,
  `ligne_id` bigint not null,
  `unite_code` varchar(16) not null,
  primary key `av_tbl_ligne_recep_pk`(`reception_id`),
  key `av_tbl_ligne_recep_fk_av_tbl_ligne` (`ligne_id`),
  key `av_tbl_ligne_recep_fk_stock_tbl_unite` (`unite_code`),
  constraint `av_tbl_ligne_recep_fk_av_tbl_ligne` foreign key (`ligne_id`) references `av_tbl_ligne` (`ligne_id`),
  constraint `av_tbl_ligne_recep_fk_stock_tbl_unite` foreign key (`unite_code`) references `stock_tbl_unite` (`unite_code`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;


