
insert into `stock_tbl_magasin` (`magasin_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`) values ('CENTRAL','2023-08-12 21:54:31','A.MOUAFO','2023-09-03 19:13:22','A.MOUAFO','Magasin Central');
insert into `stock_tbl_magasin` (`magasin_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`) values ('TEST','2023-08-17 14:17:08','A.MOUAFO','2024-03-26 20:31:52','A.MOUAFO','Magasin Test');
insert into `stock_tbl_magasin` (`magasin_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`) values ('TEST02','2024-03-26 20:33:15','A.MOUAFO','2024-03-26 20:35:01','A.MOUAFO','Test 02');

insert into `stock_tbl_unite` (`unite_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`) values ('ALV','2023-09-03 12:32:58','A.MOUAFO','2023-09-03 12:32:58','A.MOUAFO','Alvéole');
insert into `stock_tbl_unite` (`unite_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`) values ('CTN','2023-08-12 21:52:22','A.MOUAFO','2023-08-12 21:52:22','A.MOUAFO','Carton');
insert into `stock_tbl_unite` (`unite_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`) values ('KG','2023-08-12 21:53:05','A.MOUAFO','2023-08-12 21:53:05','A.MOUAFO','Kilograme');
insert into `stock_tbl_unite` (`unite_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`) values ('L','2023-08-17 07:58:34','A.MOUAFO','2023-08-17 07:58:34','A.MOUAFO','Litre');
insert into `stock_tbl_unite` (`unite_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`) values ('SAC','2023-10-08 10:48:52','A.MOUAFO','2023-10-08 10:48:52','A.MOUAFO','Sac');
insert into `stock_tbl_unite` (`unite_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`) values ('U','2023-09-03 12:27:51','A.MOUAFO','2023-09-03 12:27:51','A.MOUAFO','Unité');

insert into `stock_tbl_article` (`article_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`,`article_type`,`unite_code`) values ('COPEAUX','2023-10-08 10:55:00','A.MOUAFO','2023-10-22 19:44:31','A.MOUAFO','Copeaux','ARTC','SAC');
insert into `stock_tbl_article` (`article_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`,`article_type`,`unite_code`) values ('MAIS','2023-08-15 13:23:48','A.MOUAFO','2023-08-15 13:23:48','A.MOUAFO','Carton','ARTC','KG');
insert into `stock_tbl_article` (`article_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`,`article_type`,`unite_code`) values ('OEUF','2023-09-03 12:28:12','A.MOUAFO','2023-10-04 20:53:23','A.MOUAFO','Oeuf','ARTC','U');
insert into `stock_tbl_article` (`article_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`,`article_type`,`unite_code`) values ('PHYTO','2023-10-08 12:40:59','A.MOUAFO','2023-10-14 13:46:12','A.MOUAFO','Phyto','ARTC','U');
insert into `stock_tbl_article` (`article_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`,`article_type`,`unite_code`) values ('POULE','2023-10-08 10:45:55','A.MOUAFO','2023-10-08 10:45:55','A.MOUAFO','Poule','ARTC','U');
insert into `stock_tbl_article` (`article_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`,`article_type`,`unite_code`) values ('TEST01','2024-03-26 20:30:11','A.MOUAFO','2024-03-26 20:30:58','A.MOUAFO','Test 02','ARTC','SAC');
insert into `stock_tbl_article` (`article_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`,`article_type`,`unite_code`) values ('TRANSPORT','2023-08-15 13:46:39','A.MOUAFO','2023-08-15 13:46:39','A.MOUAFO','Transport','SSTD','KG');

insert into `stock_tbl_variant` (`article_code`,`variant_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`) values ('COPEAUX','BLANC','2023-10-19 21:01:19','A.MOUAFO','2023-10-20 12:21:56','A.MOUAFO',NULL);
insert into `stock_tbl_variant` (`article_code`,`variant_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`) values ('COPEAUX','NOIR','2023-10-20 12:18:42','A.MOUAFO','2023-10-20 12:21:56','A.MOUAFO',NULL);
insert into `stock_tbl_variant` (`article_code`,`variant_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`) values ('COPEAUX','ROUGE','2023-10-19 21:01:19','A.MOUAFO','2023-10-20 12:18:42','A.MOUAFO',NULL);
insert into `stock_tbl_variant` (`article_code`,`variant_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`) values ('OEUF','BO','2023-09-03 18:31:07','A.MOUAFO','2023-09-03 18:31:07','A.MOUAFO','Bille');
insert into `stock_tbl_variant` (`article_code`,`variant_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`) values ('OEUF','CASSE','2023-09-03 18:44:52','A.MOUAFO','2023-09-03 18:44:52','A.MOUAFO','Oeuf Cassé');
insert into `stock_tbl_variant` (`article_code`,`variant_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`) values ('OEUF','GO','2023-09-03 18:44:52','A.MOUAFO','2023-09-03 18:44:52','A.MOUAFO','Gros Oeuf');
insert into `stock_tbl_variant` (`article_code`,`variant_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`) values ('OEUF','MO','2023-09-03 18:31:07','A.MOUAFO','2023-09-03 18:31:07','A.MOUAFO','Moyen Oeuf');
insert into `stock_tbl_variant` (`article_code`,`variant_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`) values ('OEUF','PO','2023-09-03 18:31:07','A.MOUAFO','2023-09-03 18:31:07','A.MOUAFO','Petit Oeuf');
insert into `stock_tbl_variant` (`article_code`,`variant_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`) values ('OEUF','SGO','2023-10-04 20:53:24','A.MOUAFO','2023-10-04 20:53:24','A.MOUAFO','Super Gros Oeuf');
insert into `stock_tbl_variant` (`article_code`,`variant_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`) values ('PHYTO','V1','2023-10-08 12:40:59','A.MOUAFO','2023-10-08 12:40:59','A.MOUAFO','Phyto 1');
insert into `stock_tbl_variant` (`article_code`,`variant_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`) values ('PHYTO','V2','2023-10-08 12:40:59','A.MOUAFO','2023-10-08 12:40:59','A.MOUAFO','Phyto 2');
insert into `stock_tbl_variant` (`article_code`,`variant_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`) values ('TEST01','V1','2024-03-26 20:30:11','A.MOUAFO','2024-03-26 20:30:11','A.MOUAFO','Variante 1');
insert into `stock_tbl_variant` (`article_code`,`variant_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`description`) values ('TEST01','V2','2024-03-26 20:30:58','A.MOUAFO','2024-03-26 20:30:58','A.MOUAFO','Variante 2');

insert into `stock_tbl_conversion` (`article_code`,`unite_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`facteur`) values ('COPEAUX','SAC','2023-10-08 10:55:01','A.MOUAFO','2023-10-08 10:55:01','A.MOUAFO',50);
insert into `stock_tbl_conversion` (`article_code`,`unite_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`facteur`) values ('OEUF','ALV','2023-09-03 18:54:19','A.MOUAFO','2023-09-03 18:54:19','A.MOUAFO',30);
insert into `stock_tbl_conversion` (`article_code`,`unite_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`facteur`) values ('OEUF','CTN','2023-09-03 18:54:19','A.MOUAFO','2023-09-03 18:54:19','A.MOUAFO',360);
insert into `stock_tbl_conversion` (`article_code`,`unite_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`facteur`) values ('PHYTO','CTN','2023-10-14 13:46:12','A.MOUAFO','2023-10-14 13:46:12','A.MOUAFO',3);
insert into `stock_tbl_conversion` (`article_code`,`unite_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`facteur`) values ('POULE','CTN','2023-10-08 10:45:55','A.MOUAFO','2023-10-08 10:45:55','A.MOUAFO',50);
insert into `stock_tbl_conversion` (`article_code`,`unite_code`,`created_at`,`created_by`,`updated_at`,`updated_by`,`facteur`) values ('TEST01','SAC','2024-03-26 20:30:11','A.MOUAFO','2024-03-26 20:30:11','A.MOUAFO',2);

