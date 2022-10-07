CREATE DATABASE  IF NOT EXISTS `birdmeal` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `birdmeal`;
-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: j7d101.p.ssafy.io    Database: birdmeal
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `authority`
--

DROP TABLE IF EXISTS `authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authority` (
  `authority_name` varchar(30) NOT NULL,
  PRIMARY KEY (`authority_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authority`
--

LOCK TABLES `authority` WRITE;
/*!40000 ALTER TABLE `authority` DISABLE KEYS */;
INSERT INTO `authority` VALUES ('ROLE_ADMIN'),('ROLE_CHILD');
/*!40000 ALTER TABLE `authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_category`
--

DROP TABLE IF EXISTS `t_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_category` (
  `category_seq` bigint NOT NULL AUTO_INCREMENT,
  `category_icon` varchar(256) DEFAULT NULL,
  `category_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`category_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_category`
--

LOCK TABLES `t_category` WRITE;
/*!40000 ALTER TABLE `t_category` DISABLE KEYS */;
INSERT INTO `t_category` VALUES (1,'category1','고기'),(2,'category2','채소/과일'),(3,'category3','밀키트/간편식'),(4,'category4','냉동식품'),(5,'category5','과자류'),(6,'category6','음료'),(7,'category7','베이커리'),(8,'category8','쌀/반찬'),(9,'category9','양념/오일');
/*!40000 ALTER TABLE `t_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_child_nft`
--

DROP TABLE IF EXISTS `t_child_nft`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_child_nft` (
  `nft_seq` bigint NOT NULL AUTO_INCREMENT,
  `nft_cnt` int DEFAULT NULL,
  `nft_create_date` varchar(30) DEFAULT NULL,
  `nft_img` varchar(255) DEFAULT NULL,
  `user_seq` bigint DEFAULT NULL,
  PRIMARY KEY (`nft_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_child_nft`
--

LOCK TABLES `t_child_nft` WRITE;
/*!40000 ALTER TABLE `t_child_nft` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_child_nft` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_donation`
--

DROP TABLE IF EXISTS `t_donation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_donation` (
  `donation_seq` bigint NOT NULL AUTO_INCREMENT,
  `donation_date` varchar(30) DEFAULT NULL,
  `donation_price` int DEFAULT NULL,
  `donation_type` tinyint(1) DEFAULT '0',
  `user_seq` bigint DEFAULT NULL,
  PRIMARY KEY (`donation_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_donation`
--

LOCK TABLES `t_donation` WRITE;
/*!40000 ALTER TABLE `t_donation` DISABLE KEYS */;
INSERT INTO `t_donation` VALUES (1,'20221006',50000,1,1),(2,'20221006',50000,1,1);
/*!40000 ALTER TABLE `t_donation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_order`
--

DROP TABLE IF EXISTS `t_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_order` (
  `order_seq` bigint NOT NULL AUTO_INCREMENT,
  `order_date` varchar(30) DEFAULT NULL,
  `order_price` int DEFAULT NULL,
  `order_state` tinyint(1) DEFAULT '0',
  `user_seq` bigint DEFAULT NULL,
  PRIMARY KEY (`order_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_order`
--

LOCK TABLES `t_order` WRITE;
/*!40000 ALTER TABLE `t_order` DISABLE KEYS */;
INSERT INTO `t_order` VALUES (1,'20221007',30000,0,1),(2,'20221007',17000,0,1);
/*!40000 ALTER TABLE `t_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_order_child`
--

DROP TABLE IF EXISTS `t_order_child`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_order_child` (
  `order_child_detail_seq` bigint NOT NULL AUTO_INCREMENT,
  `order_date` varchar(30) DEFAULT NULL,
  `order_quantity` int DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `product_price` int DEFAULT NULL,
  `product_thumbnail_img` varchar(255) DEFAULT NULL,
  `user_nickname` varchar(255) DEFAULT NULL,
  `user_seq` bigint DEFAULT NULL,
  PRIMARY KEY (`order_child_detail_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_order_child`
--

LOCK TABLES `t_order_child` WRITE;
/*!40000 ALTER TABLE `t_order_child` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_order_child` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_order_detail`
--

DROP TABLE IF EXISTS `t_order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_order_detail` (
  `order_detail_seq` bigint NOT NULL AUTO_INCREMENT,
  `category_seq` bigint DEFAULT NULL,
  `order_delivery_company` varchar(32) DEFAULT NULL,
  `order_delivery_number` varchar(64) DEFAULT NULL,
  `order_is_canceled` tinyint(1) DEFAULT '0',
  `order_is_refunded` tinyint(1) DEFAULT '0',
  `order_quantity` int DEFAULT NULL,
  `order_seq` bigint DEFAULT NULL,
  `order_t_hash` varchar(1024) DEFAULT NULL,
  `order_to_state` tinyint(1) DEFAULT '0',
  `product_ca` varchar(256) DEFAULT NULL,
  `product_is_deleted` tinyint(1) DEFAULT '0',
  `product_name` varchar(256) DEFAULT NULL,
  `product_price` int DEFAULT NULL,
  `product_seq` bigint DEFAULT NULL,
  `product_thumbnail_img` varchar(1024) DEFAULT NULL,
  `seller_seq` bigint DEFAULT NULL,
  PRIMARY KEY (`order_detail_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_order_detail`
--

LOCK TABLES `t_order_detail` WRITE;
/*!40000 ALTER TABLE `t_order_detail` DISABLE KEYS */;
INSERT INTO `t_order_detail` VALUES (1,1,NULL,NULL,0,0,1,1,'0xd6609196f3f50d20d5eb3c3552daa8a97de0a2fb9d250b5c3a547f37d5503214',0,'0x12BE0Ab8884E5Caab0BbB8459bF55416025F2134',0,'도드람한돈 1등급삼겹살 구이용 (냉장) 1kg',30000,1,'https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.go_1665077361627.jpg',1),(2,2,NULL,NULL,0,0,1,2,'0x843b28e2c3df48d4d6e863df25f8a29bfe2600b1a83945f6c5a4fc4ec50bc580',0,'0x4101BD516141D2E0A22c4491265DaF6A24d7eeEd',0,'첫 출하 빨간 가정용 햇 사과 5KG',17000,6,'https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.v_1665077893186.jpg',1);
/*!40000 ALTER TABLE `t_order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_product`
--

DROP TABLE IF EXISTS `t_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_product` (
  `product_seq` bigint NOT NULL AUTO_INCREMENT,
  `category_seq` bigint DEFAULT NULL,
  `product_ca` varchar(256) DEFAULT NULL,
  `product_create_date` varchar(30) DEFAULT NULL,
  `product_description_img` varchar(1024) DEFAULT NULL,
  `product_is_deleted` tinyint(1) DEFAULT '0',
  `product_name` varchar(256) DEFAULT NULL,
  `product_price` int DEFAULT NULL,
  `product_thumbnail_img` varchar(1024) DEFAULT NULL,
  `product_update_date` varchar(30) DEFAULT NULL,
  `seller_seq` bigint DEFAULT NULL,
  PRIMARY KEY (`product_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_product`
--

LOCK TABLES `t_product` WRITE;
/*!40000 ALTER TABLE `t_product` DISABLE KEYS */;
INSERT INTO `t_product` VALUES (1,1,'0x12BE0Ab8884E5Caab0BbB8459bF55416025F2134','20221006','https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.ggg_1665077372424.jpg',0,'도드람한돈 1등급삼겹살 구이용 (냉장) 1kg',30000,'https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.go_1665077361627.jpg','20221006',1),(2,1,'0x46c3e6a003B18D8D6d738e412f2f797CE8D6E555','20221006','https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.zz_1665077481821.jpg',0,'생생포크 한돈 항정살 300g',16000,'https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.z_1665077474625.jpg','20221006',1),(3,1,'0x3c830e00CB47705C0181Da6E58785DbDCE1E79Dd','20221006','https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.aa_1665077584055.jpg',0,'곰곰 호주산 소고기 치마살 구이용 (냉장) 300g',13000,'https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.a_1665077575838.jpg','20221006',1),(4,1,'0x6DCb7ADa68494026deC8f0B1331b8a24Ec0dbA3a','20221006','https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.bb_1665077687369.png',0,'[도누꼬기] LA한쪽갈비 양념육 500g*2팩',30000,'https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.b_1665077679346.png','20221006',1),(5,1,'0xefbe3f7765EA389213b568e9D568B7439d516EF7','20221006','https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.vvvv_1665078483976.png',0,'미트엔조이 호주산 곡물비육 소고기 안창살 구이용 (냉장) 300g',17000,'https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.vvv_1665078473638.jpg','20221006',1),(6,2,'0x4101BD516141D2E0A22c4491265DaF6A24d7eeEd','20221006','https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.vv_1665077901293.png',0,'첫 출하 빨간 가정용 햇 사과 5KG',17000,'https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.v_1665077893186.jpg','20221006',1),(7,2,'0x3BD54F676f85a0B1EE94109b13387218dAC194ea','20221006','https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.aaaaa_1665077999516.jpg',0,'GAP 인증 해풍맞은 올레길 당도선별 하우스감귤 1.5kg',20000,'https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.aaaa_1665077993444.jpg','20221006',1),(8,2,'0x9dfAEdA0d90375ffaFAF1A34A2D7fbFa3f717Eb4','20221006','https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.nn_1665078077922.jpg',0,'과일꾼 22년 갓 수확한 나주배 선물세트 5kg',16000,'https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.n_1665078070064.jpg','20221006',1),(9,2,'0xf96764c44379BEF4C43b8Aa6d5a838BBD4231166','20221006','https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.pp_1665078224064.jpg',0,'고랭지 여름딸기 못난이 주스용 1kg 1상자',20000,'https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.p_1665078210710.jpg','20221006',1),(10,2,'0x0A9268Af1F6a7EFAF11a3f1d04dF6740bcC7F6b3','20221006','https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.lkj_1665078317343.jpg',0,'한반도 당도선별 수박 5kg',13000,'https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.lk_1665078309034.jpg','20221006',1),(11,1,'0xD9487EafB88F0d34Fae69c490ECe782705aaB56A','20221006','https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.%ED%94%84%EB%A6%AC%EB%AF%B8%EC%97%84%20%EC%83%81%EC%84%B8%EC%84%A4%EB%AA%85_1665100584406.jpg',1,'테스트',111,'https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.%EB%AF%B8%EA%B5%AD%EC%82%B0%20%EC%86%8C%EA%B3%A0%EA%B8%B0%20%EC%B5%9C%EA%B3%A0%EB%93%B1%EA%B8%89%20%ED%94%84%EB%9D%BC%EC%9E%84%20%EC%B9%98%EB%A7%88%EC%82%B4%20%ED%8A%B9%EC%88%98%EB%B6%80%EC%9C%84_1665100577780.png','20221006',3),(12,1,'0x1aB237F0e235a68F78525122aA7cbddc87aa70Fb','20221007','https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.%ED%94%84%EB%A6%AC%EB%AF%B8%EC%97%84%20%EC%83%81%EC%84%B8%EC%84%A4%EB%AA%85_1665101606416.jpg',1,'프리미엄 치마살',2000,'https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.%EB%AF%B8%EA%B5%AD%EC%82%B0%20%EC%86%8C%EA%B3%A0%EA%B8%B0%20%EC%B5%9C%EA%B3%A0%EB%93%B1%EA%B8%89%20%ED%94%84%EB%9D%BC%EC%9E%84%20%EC%B9%98%EB%A7%88%EC%82%B4%20%ED%8A%B9%EC%88%98%EB%B6%80%EC%9C%84_1665101600101.png','20221007',3),(13,1,'0x85Cb0931346a2fccCd08347d39d7965184eCad93','20221007','https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.%ED%94%84%EB%A6%AC%EB%AF%B8%EC%97%84%20%EC%83%81%EC%84%B8%EC%84%A4%EB%AA%85_1665102953030.jpg',1,'프리미엄 치마살',2000,'https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.%EB%AF%B8%EA%B5%AD%EC%82%B0%20%EC%86%8C%EA%B3%A0%EA%B8%B0%20%EC%B5%9C%EA%B3%A0%EB%93%B1%EA%B8%89%20%ED%94%84%EB%9D%BC%EC%9E%84%20%EC%B9%98%EB%A7%88%EC%82%B4%20%ED%8A%B9%EC%88%98%EB%B6%80%EC%9C%84_1665102945462.png','20221007',3);
/*!40000 ALTER TABLE `t_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_seller`
--

DROP TABLE IF EXISTS `t_seller`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_seller` (
  `seller_seq` bigint NOT NULL AUTO_INCREMENT,
  `seller_address` varchar(50) DEFAULT NULL,
  `seller_create_date` varchar(30) DEFAULT NULL,
  `seller_email` varchar(256) DEFAULT NULL,
  `seller_img` varchar(1024) DEFAULT NULL,
  `seller_info` varchar(1024) DEFAULT NULL,
  `seller_nickname` varchar(30) DEFAULT NULL,
  `seller_pass` varchar(100) DEFAULT NULL,
  `seller_tel` varchar(20) DEFAULT NULL,
  `seller_update_date` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`seller_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_seller`
--

LOCK TABLES `t_seller` WRITE;
/*!40000 ALTER TABLE `t_seller` DISABLE KEYS */;
INSERT INTO `t_seller` VALUES (1,'구미시 임수동 수출대로 14길 42','20221006','kimjs1133557799@gmail.com','https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.q_1665077279651.png','싸피빌라 201호','kimjs1133557799','$2a$10$DCdlWcEHtsri6N6IMnhXTe4smMSEisYb7zJXa.EgnaG0nguJFe13m','01012345678','20221006'),(2,'구미시 진평동 어딘가','20221006','siryeongchoi@gmail.com','https://birdmeal.s3.us-west-2.amazonaws.com/imgFile/image.%EC%A6%9D%EB%AA%85%EC%82%AC%EC%A7%84_1665077116994.jpg','당신의 엘레나는 이제 제껍니다.','최시령','$2a$10$WEHd7f4TbKnc4rylamS./uPpmanu/cKH85efVXcOk7lL0WUAaH6Sm','01084310233','20221006'),(3,NULL,'20221006','alqp118@gmail.com',NULL,NULL,'alqp118','$2a$10$PruanMcJB4fdvO12PxnsU.fQ2q7jcHfcHAxF7FjHjdDBH70UMp09a',NULL,'20221006');
/*!40000 ALTER TABLE `t_seller` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_starving_child`
--

DROP TABLE IF EXISTS `t_starving_child`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_starving_child` (
  `starving_child_seq` bigint NOT NULL AUTO_INCREMENT,
  `child_card_num` varchar(50) DEFAULT NULL,
  `user_email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`starving_child_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_starving_child`
--

LOCK TABLES `t_starving_child` WRITE;
/*!40000 ALTER TABLE `t_starving_child` DISABLE KEYS */;
INSERT INTO `t_starving_child` VALUES (1,'4140123456789012',NULL);
/*!40000 ALTER TABLE `t_starving_child` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user` (
  `user_seq` bigint NOT NULL AUTO_INCREMENT,
  `user_add` varchar(256) DEFAULT NULL,
  `user_add_detail` varchar(256) DEFAULT NULL,
  `user_charge_state` bit(1) DEFAULT b'0',
  `user_email` varchar(50) NOT NULL,
  `user_eoa` varchar(1024) DEFAULT NULL,
  `user_is_mint` bit(1) DEFAULT b'0',
  `user_month_money` int DEFAULT '0',
  `user_nickname` varchar(30) NOT NULL,
  `user_pass` varchar(100) DEFAULT NULL,
  `user_regist_date` varchar(30) DEFAULT NULL,
  `user_role` tinyint(1) DEFAULT '0',
  `user_tel` varchar(20) DEFAULT NULL,
  `user_update_date` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`user_seq`),
  UNIQUE KEY `UK_bv4irau025kecgcf0p6qbng4n` (`user_email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (1,NULL,NULL,_binary '','vkdnjxodnd3@gmail.com','0x0f69d767241166f87ea3db28624140603038bef0',_binary '',308490,'파워기부자','$2a$10$/ywGpJg/2uuTMhsrheNvIemEh6t1SO9nbCmeTHVccenNJVuhV1CvW','20221006',0,'01012345678','20221007'),(2,NULL,NULL,_binary '','vkdnjxodnd00@gmail.com','0xcbab9dff11032e8110e58d27f8f3633125530ac4',_binary '\0',0,'vkdnjxodnd','$2a$10$cfGB3btc18eE7ETvjoec0u4whYdyc4cEmCUy3lPqlwaQ1WYRHJvhy','20221006',1,NULL,'20221006'),(3,NULL,NULL,_binary '\0','vkdnjxodnd01@gmail.com','0x5d5a45a7c4c6152f6c83523d782f7df8aeffca3c',_binary '\0',0,'vkdnjxodnd','$2a$10$Ap7a2kw9UHYAdkq50bYdw.3mAzBSMoLrKCHtvPYbPyMrQN/fmWI2e','20221007',1,NULL,'20221007'),(4,NULL,NULL,_binary '\0','vkdnjxodnd02@gmail.com','0x9ef0384fbf85a932536c5f30780886d59fd265ad',_binary '\0',0,'vkdnjxodnd','$2a$10$Ndo0IXRjQSbhLHf3dXahteQaS.KLeP6bQPKWVmUqKmmbJXJfD8eiO','20221007',1,NULL,'20221007'),(5,NULL,NULL,_binary '','vkdnjxodnd03@gmail.com','0xc7ddcce878c08e5693f028ef2270c08df26c0a76',_binary '\0',0,'vkdnjxodnd','$2a$10$RznPKPSBp.jLDOG57bKP8eXKwaKEtOrDZ4aZYlwnuS/b.dsqGPGdm','20221007',1,NULL,'20221007');
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_authority`
--

DROP TABLE IF EXISTS `user_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_authority` (
  `email` varchar(50) NOT NULL,
  `authority_name` varchar(30) NOT NULL,
  PRIMARY KEY (`email`,`authority_name`),
  KEY `FK6ktglpl5mjosa283rvken2py5` (`authority_name`),
  CONSTRAINT `FK6ktglpl5mjosa283rvken2py5` FOREIGN KEY (`authority_name`) REFERENCES `authority` (`authority_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_authority`
--

LOCK TABLES `user_authority` WRITE;
/*!40000 ALTER TABLE `user_authority` DISABLE KEYS */;
INSERT INTO `user_authority` VALUES ('vkdnjxodnd3@gmail.com','ROLE_ADMIN'),('vkdnjxodnd00@gmail.com','ROLE_CHILD'),('vkdnjxodnd01@gmail.com','ROLE_CHILD'),('vkdnjxodnd02@gmail.com','ROLE_CHILD'),('vkdnjxodnd03@gmail.com','ROLE_CHILD');
/*!40000 ALTER TABLE `user_authority` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-07 10:02:15
