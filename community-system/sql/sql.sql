-- MySQL dump 10.13  Distrib 5.7.26, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: community_system
-- ------------------------------------------------------
-- Server version	5.7.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名称',
  `password` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `nickname` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `avatar_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
  `tel` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `status` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` VALUES (1,'admin','123456','管理员','http://localhost:1000/file/8826e8c280cb3bec6a4fbeb61514ee74.png','123456','123456@qq.com','启用','2026-02-27 17:29:35');

--
-- Table structure for table `announcement`
--

DROP TABLE IF EXISTS `announcement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `announcement` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `content` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='公告';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcement`
--

INSERT INTO `announcement` VALUES ();

--
-- Table structure for table `building`
--

DROP TABLE IF EXISTS `building`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `building` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '楼宇编号',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '楼宇地址',
  `number` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '楼层数',
  `element_number` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '单元数',
  `house_number` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '户数',
  `type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分类',
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='楼宇信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `building`
--

INSERT INTO `building` VALUES ();

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `type` int(11) DEFAULT NULL COMMENT '评论类型：1 新闻评论 2 论坛评论',
  `source_id` int(11) DEFAULT NULL COMMENT '新闻Id/论坛Id',
  `content` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '评论内容',
  `parent_id` int(11) DEFAULT NULL COMMENT '父评论ID (用于回复评论)',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=150 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='评论记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--


--
-- Table structure for table `complaint`
--

DROP TABLE IF EXISTS `complaint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `complaint` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '投诉类型',
  `content` text COLLATE utf8mb4_unicode_ci COMMENT '描述',
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='投诉信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complaint`
--

INSERT INTO `complaint` VALUES ();

--
-- Table structure for table `forum`
--

DROP TABLE IF EXISTS `forum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `forum` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `content` text COLLATE utf8mb4_unicode_ci COMMENT '内容',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='论坛信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forum`
--

INSERT INTO `forum` VALUES ();

--
-- Table structure for table `forum_comment`
--

DROP TABLE IF EXISTS `forum_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `forum_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `forum_id` int(11) NOT NULL COMMENT '论坛信息ID',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `content` text COLLATE utf8mb4_unicode_ci COMMENT '内容',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='论坛评论信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forum_comment`
--

INSERT INTO `forum_comment` VALUES ();

--
-- Table structure for table `house`
--

DROP TABLE IF EXISTS `house`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `house` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `building_id` int(11) NOT NULL COMMENT '楼宇Id',
  `user_id` int(11) NOT NULL COMMENT '户主id',
  `house_number` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '房屋编号',
  `area` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '房屋面积',
  `house_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '户型',
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '房屋状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='房屋信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `house`
--

INSERT INTO `house` VALUES ();

--
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `content` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `image` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '封面图',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='新闻信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news`
--

INSERT INTO `news` VALUES ();

--
-- Table structure for table `news_comment`
--

DROP TABLE IF EXISTS `news_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `news_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `news_id` int(11) NOT NULL COMMENT '新闻id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `content` text COLLATE utf8mb4_unicode_ci COMMENT '内容',
  `parent_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '父级id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='新闻评论信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news_comment`
--


--
-- Table structure for table `parking_fee`
--

DROP TABLE IF EXISTS `parking_fee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parking_fee` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parking_space_id` int(11) NOT NULL COMMENT '车位ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `fee` float NOT NULL COMMENT '费用',
  `payment_status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='车位收费信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parking_fee`
--

INSERT INTO `parking_fee` VALUES ();

--
-- Table structure for table `parking_lot`
--

DROP TABLE IF EXISTS `parking_lot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parking_lot` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '停车场名称',
  `address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '停车场地址',
  `parking_num` int(11) NOT NULL COMMENT '车位数量',
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '停车场状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='停车场信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parking_lot`
--

INSERT INTO `parking_lot` VALUES ();

--
-- Table structure for table `parking_space`
--

DROP TABLE IF EXISTS `parking_space`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parking_space` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parking_lot_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '停车场Id',
  `space_number` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '车位编号',
  `type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '车位类型',
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '位置',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `price` float NOT NULL COMMENT '小时费用',
  `month_price` float NOT NULL COMMENT '月租价格',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态',
  `remake` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='车位信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parking_space`
--

INSERT INTO `parking_space` VALUES ();

--
-- Table structure for table `property_fee`
--

DROP TABLE IF EXISTS `property_fee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `property_fee` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `house_id` int(11) NOT NULL COMMENT '房屋Id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `fee` float DEFAULT NULL COMMENT '费用',
  `payment_status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='物业收费信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `property_fee`
--


--
-- Table structure for table `repair`
--

DROP TABLE IF EXISTS `repair`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repair` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `house_id` int(11) NOT NULL COMMENT '房屋id',
  `user_id` int(11) NOT NULL,
  `content` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '描述',
  `urgency_level` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '紧急程度',
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `appointment_time` datetime DEFAULT NULL COMMENT '预约时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='报修信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repair`
--


--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `nickname` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '昵称',
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `avatar_url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '头像',
  `tel` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT ' 电话',
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `balance` float(11,2) NOT NULL COMMENT '余额',
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

INSERT INTO `user` VALUES (3,'user1','user1','123456','http://localhost:1000/file/4a257f65c36236982d6d89bf55bdedf7.png','user1user1','user1',10.00,'启用','2026-02-27 10:59:36'),(4,'user2','user2','123456','http://localhost:1000/file/4a257f65c36236982d6d89bf55bdedf7.png',NULL,NULL,100.00,'启用','2026-02-27 06:19:12');

--
-- Table structure for table `utility_bill_fee`
--

DROP TABLE IF EXISTS `utility_bill_fee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utility_bill_fee` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `house_id` int(11) NOT NULL COMMENT '房屋Id',
  `user_id` int(11) DEFAULT NULL,
  `type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '收费类型',
  `reading` float DEFAULT NULL COMMENT '读数',
  `fee` float DEFAULT NULL COMMENT '费用',
  `reading_date` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '抄表日期',
  `payment_status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付状态',
  `charge_time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收费时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='水电收费信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utility_bill_fee`
--

INSERT INTO `utility_bill_fee` VALUES ();
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-27 14:37:45
