drop table if exists `fin_tbl_compte`;
create table `fin_tbl_compte` (
  `compte_code` varchar(32) not null,
  `created_at` datetime(6) not null,
  `created_by` varchar(16) not null,
  `updated_at` datetime(6) not null,
  `updated_by` varchar(16) not null,
  `libelle` varchar(32) not null,
  `description` varchar(255) default null,
  `partner_code` varchar(16) default null,
  `compte_type` varchar(16) not null,
  primary key `fin_tbl_compte_pk`(`compte_code`),
  
  key `fin_tbl_compte_fk_core_tbl_partner` (`partner_code`),
  constraint `fin_tbl_compte_fk_core_tbl_partner` 
  foreign key (`partner_code`) 
  references `core_tbl_partner` (`partner_code`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

drop table if exists `fin_tbl_rubrique`;
create table `fin_tbl_rubrique` (
  `rubrique_code` varchar(16) not null,
  `created_at` datetime(6) not null,
  `created_by` varchar(16) not null,
  `updated_at` datetime(6) not null,
  `updated_by` varchar(16) not null,
  `description` varchar(64) default null,
  `transaction_type` varchar(16) not null,
  `libelle` varchar(32) default null,
  primary key `fin_tbl_rubrique_pk`(`rubrique_code`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

drop table if exists `fin_tbl_transaction`;
create table `fin_tbl_transaction` (
  `transaction_code` varchar(20) not null,
  `created_at` datetime(6) not null,
  `created_by` varchar(16) not null,
  `updated_at` datetime(6) not null,
  `updated_by` varchar(16) not null,
  `status_date` datetime(6) default null,
  `transaction_date` date not null,
  `montant` decimal(16,4) not null,
  `note` varchar(128) default null,
  `status` varchar(16) not null,
  `transaction_type` varchar(16) not null,
  `partner_code` varchar(16) not null,
  `compte_code` varchar(16) not null,
  `rubrique_code` varchar(16) not null,
  primary key `fin_tbl_transaction_pk`(`transaction_code`),
  key `fin_tbl_transaction_fk_core_tbl_partner` (`partner_code`),
  key `fin_tbl_transaction_fk_fin_tbl_compte` (`compte_code`),
  key `fin_tbl_transaction_fk_fin_tbl_rubrique` (`rubrique_code`),
  constraint `fin_tbl_transaction_fk_core_tbl_partner` foreign key (`partner_code`) references `core_tbl_partner` (`partner_code`),
  constraint `fin_tbl_transaction_fk_fin_tbl_rubrique` foreign key (`rubrique_code`) references `fin_tbl_rubrique` (`rubrique_code`),
  constraint `fin_tbl_transaction_fk_fin_tbl_compte` foreign key (`compte_code`) references `fin_tbl_compte` (`compte_code`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

drop table if exists `fin_tbl_taxe`;
create table `fin_tbl_taxe` (
  `taxe_code` varchar(16) not null,
  
  `created_at` datetime(6) not null,
  `created_by` varchar(16) not null,
  `updated_at` datetime(6) not null,
  `updated_by` varchar(16) not null,
  
  `description` varchar(256) default null,
  `taux` double not null,
  
  primary key `fin_tbl_taxe_pk`(`taxe_code`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;
