/*
 Navicat Premium Data Transfer

 Source Server         : guli_mall
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : 192.168.61.132:3306
 Source Schema         : gulimall-wms

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 04/11/2022 21:09:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

-- ----------------------------
-- Table structure for wms_purchase
-- ----------------------------
DROP TABLE IF EXISTS `wms_purchase`;
CREATE TABLE `wms_purchase`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `assignee_id` bigint NULL DEFAULT NULL,
  `assignee_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` char(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `priority` int NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL COMMENT '0、新建；1、已分配；2、已领取；3、已完成；4、有异常',
  `ware_id` bigint NULL DEFAULT NULL,
  `amount` decimal(18, 4) NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '采购信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wms_purchase
-- ----------------------------
INSERT INTO `wms_purchase` VALUES (6, 1, 'admin', '13612345678', 1, 4, NULL, 950000.0000, '2022-08-11 21:05:36', '2022-08-11 23:54:06');

-- ----------------------------
-- Table structure for wms_purchase_detail
-- ----------------------------
DROP TABLE IF EXISTS `wms_purchase_detail`;
CREATE TABLE `wms_purchase_detail`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `purchase_id` bigint NULL DEFAULT NULL COMMENT '采购单id',
  `sku_id` bigint NULL DEFAULT NULL COMMENT '采购商品id',
  `sku_num` int NULL DEFAULT NULL COMMENT '采购数量',
  `sku_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '采购金额',
  `ware_id` bigint NULL DEFAULT NULL COMMENT '仓库id',
  `status` int NULL DEFAULT NULL COMMENT '状态[0新建，1已分配，2正在采购，3已完成，4采购失败]',
  `sku_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wms_purchase_detail
-- ----------------------------
INSERT INTO `wms_purchase_detail` VALUES (16, 6, 35, 100, 490000.0000, 1, 4, 'IPhone13 以官网信息为准 白色 8GB 白色 135.9 以官网信息为准 A13 A13 WIFI 14', '应采购100台，实际采购80台');
INSERT INTO `wms_purchase_detail` VALUES (17, 6, 36, 100, 460000.0000, 1, 3, '华为p40 A2217 白色 12GB 白色 158.3 陶瓷 海思（Hisilicon） HUAWEI Kirin 980 WIFI 15.6', '');

-- ----------------------------
-- Table structure for wms_ware_info
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_info`;
CREATE TABLE `wms_ware_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '仓库名',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '仓库地址',
  `areacode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区域编码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '仓库信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wms_ware_info
-- ----------------------------
INSERT INTO `wms_ware_info` VALUES (1, '北京一号仓', '北京市东城区', '100000');

-- ----------------------------
-- Table structure for wms_ware_order_task
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_order_task`;
CREATE TABLE `wms_ware_order_task`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint NULL DEFAULT NULL COMMENT 'order_id',
  `order_sn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'order_sn',
  `consignee` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货人',
  `consignee_tel` char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货人电话',
  `delivery_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '配送地址',
  `order_comment` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单备注',
  `payment_way` tinyint(1) NULL DEFAULT NULL COMMENT '付款方式【 1:在线付款 2:货到付款】',
  `task_status` tinyint NULL DEFAULT NULL COMMENT '任务状态',
  `order_body` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单描述',
  `tracking_no` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '物流单号',
  `create_time` datetime NULL DEFAULT NULL COMMENT 'create_time',
  `ware_id` bigint NULL DEFAULT NULL COMMENT '仓库id',
  `task_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工作单备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 69 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '库存工作单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wms_ware_order_task
-- ----------------------------
INSERT INTO `wms_ware_order_task` VALUES (1, NULL, '202210081150253881578593632046899201', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-08 11:50:26', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (2, NULL, '202210081213212341578599402771599361', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-08 12:13:21', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (3, NULL, '202210081219465811578601019029872641', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-08 12:19:47', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (4, NULL, '202210081223491951578602036626771969', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-08 12:23:49', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (5, NULL, '202210081227511031578603051266695170', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-08 12:27:51', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (6, NULL, '202210081231539771578604069949820929', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-08 12:31:54', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (7, NULL, '202210081235101461578604892742316034', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-08 12:35:10', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (8, NULL, '202210081307447541578613090962456578', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-08 13:07:45', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (9, NULL, '202210081318191691578615751841497089', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-08 13:18:19', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (10, NULL, '202210081325419421578617609016369153', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-08 13:25:42', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (11, NULL, '202210081347014341578622975573811202', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-08 13:47:02', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (12, NULL, '202210081347043991578622988014116866', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-08 13:47:05', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (13, NULL, '202210081347323301578623105173610498', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-08 13:47:32', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (14, NULL, '202210081349182921578623549610450946', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-08 13:49:18', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (15, NULL, '202210081431344781578634187149692930', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-08 14:31:35', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (16, NULL, '202210081502031551578641857172754434', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-08 15:02:03', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (17, NULL, '202210081504529371578642569252327426', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-08 15:04:53', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (18, NULL, '202210081508172951578643426417070081', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-08 15:08:17', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (19, NULL, '202210081512587331578644606874607617', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-08 15:12:59', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (20, NULL, '202210081513593581578644861103955970', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-08 15:13:59', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (21, NULL, '202210081519305661578646250341351425', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-08 15:19:31', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (22, NULL, '202210081805244871578688000112005122', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-08 18:05:25', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (23, NULL, '202210081809061121578688929674645506', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-08 18:09:06', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (24, NULL, '202210081913214751578705100243255298', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-08 19:13:22', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (25, NULL, '202210081952356651578714974431772674', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-08 19:52:36', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (26, NULL, '202210112157042521579833463552946177', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-11 21:57:04', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (27, NULL, '202210112230218461579841842073673730', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-11 22:30:22', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (28, NULL, '202210112234441601579842942294110209', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-11 22:34:44', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (29, NULL, '202210112248112601579846327466536962', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-11 22:48:11', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (30, NULL, '202210112250380981579846943395901441', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-11 22:50:38', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (31, NULL, '202210112252180541579847362633363457', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-11 22:52:18', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (32, NULL, '202210112256174211579848366548414466', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-11 22:56:17', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (33, NULL, '202210112337556121579858844796420097', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-11 23:37:56', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (34, NULL, '202210112343446901579860308931481602', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-11 23:43:45', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (35, NULL, '202210112357527111579863865793486849', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-11 23:57:53', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (36, NULL, '202210120014563111579868159078903810', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 00:14:56', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (37, NULL, '202210120020500401579869642704891905', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 00:20:50', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (38, NULL, '202210120024038191579870455447760898', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 00:24:04', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (39, NULL, '202210120034016051579872962743971842', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 00:34:02', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (40, NULL, '202210120038459981579874155583057921', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 00:38:46', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (41, NULL, '202210120040437841579874649625931777', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 00:40:44', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (42, NULL, '202210120043445131579875407633133569', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 00:43:45', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (43, NULL, '202210120045075481579875755932332034', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 00:45:08', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (44, NULL, '202210120045590711579875972022874113', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 00:45:59', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (45, NULL, '202210120046581071579876219679789057', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 00:46:58', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (46, NULL, '202210120048376341579876637126217730', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 00:48:38', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (47, NULL, '202210120054216691579878080080039938', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 00:54:22', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (48, NULL, '202210120055283681579878359865319425', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 00:55:29', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (49, NULL, '202210120102158861579880069119684609', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 01:02:16', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (50, NULL, '202210120107597471579881511318532097', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 01:08:00', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (51, NULL, '202210120117583211579884021949214722', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 01:17:58', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (52, NULL, '202210120127033541579886308004278274', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 01:27:03', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (53, NULL, '202210120128506691579886758103429121', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 01:28:51', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (54, NULL, '202210120133541661579888031074693122', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 01:33:54', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (55, NULL, '202210120140250991579889670762344450', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 01:40:25', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (56, NULL, '202210120932284551580008467544420354', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 09:32:29', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (57, NULL, '202210120937056911580009630339706882', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 09:37:06', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (58, NULL, '202210120940326081580010498187341825', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 09:40:33', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (59, NULL, '202210120941283751580010732086898690', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 09:41:28', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (60, NULL, '202210120942230441580010961427333121', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 09:42:23', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (62, NULL, '202210121131039721580038312185921537', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 11:31:04', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (65, NULL, '202210121151025461580043339302531074', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-12 11:51:03', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (66, NULL, '202210151347100651581159726855049217', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-15 13:47:10', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (67, NULL, '202210151350333211581160579372519425', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-15 13:50:33', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (68, NULL, '202210151352374971581161100158275586', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-15 13:52:38', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (69, NULL, '202210161314085551581513803669504001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-10-16 13:14:09', NULL, NULL);

-- ----------------------------
-- Table structure for wms_ware_order_task_detail
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_order_task_detail`;
CREATE TABLE `wms_ware_order_task_detail`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint NULL DEFAULT NULL COMMENT 'sku_id',
  `sku_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'sku_name',
  `sku_num` int NULL DEFAULT NULL COMMENT '购买个数',
  `task_id` bigint NULL DEFAULT NULL COMMENT '工作单id',
  `ware_id` bigint NULL DEFAULT NULL COMMENT '仓库id',
  `lock_status` int NULL DEFAULT NULL COMMENT '1-已锁定  2-已解锁  3-扣减',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '库存工作单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wms_ware_order_task_detail
-- ----------------------------
INSERT INTO `wms_ware_order_task_detail` VALUES (1, 36, '', 1, 1, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (2, 36, '', 1, 2, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (3, 36, '', 1, 3, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (4, 36, '', 1, 4, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (5, 36, '', 1, 5, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (6, 36, '', 1, 6, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (7, 36, '', 1, 7, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (8, 36, '', 1, 8, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (9, 35, '', 1, 9, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (10, 35, '', 1, 10, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (11, 35, '', 1, 13, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (12, 35, '', 1, 14, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (13, 36, '', 1, 15, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (14, 36, '', 1, 16, 1, 1);
INSERT INTO `wms_ware_order_task_detail` VALUES (15, 35, '', 1, 17, 1, 1);
INSERT INTO `wms_ware_order_task_detail` VALUES (16, 36, '', 1, 18, 1, 1);
INSERT INTO `wms_ware_order_task_detail` VALUES (17, 36, '', 1, 19, 1, 1);
INSERT INTO `wms_ware_order_task_detail` VALUES (18, 35, '', 1, 20, 1, 1);
INSERT INTO `wms_ware_order_task_detail` VALUES (19, 35, '', 1, 21, 1, 1);
INSERT INTO `wms_ware_order_task_detail` VALUES (20, 35, '', 1, 22, 1, 1);
INSERT INTO `wms_ware_order_task_detail` VALUES (21, 36, '', 1, 23, 1, 1);
INSERT INTO `wms_ware_order_task_detail` VALUES (22, 35, '', 1, 24, 1, 1);
INSERT INTO `wms_ware_order_task_detail` VALUES (23, 36, '', 1, 25, 1, 1);
INSERT INTO `wms_ware_order_task_detail` VALUES (24, 44, '', 1, 26, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (25, 44, '', 1, 27, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (26, 44, '', 1, 28, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (27, 44, '', 1, 29, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (28, 44, '', 1, 30, 1, 1);
INSERT INTO `wms_ware_order_task_detail` VALUES (29, 44, '', 1, 31, 1, 1);
INSERT INTO `wms_ware_order_task_detail` VALUES (30, 44, '', 1, 32, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (31, 44, '', 1, 33, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (32, 44, '', 1, 34, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (33, 44, '', 1, 35, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (34, 44, '', 1, 36, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (35, 44, '', 1, 37, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (36, 40, '', 1, 38, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (37, 44, '', 1, 39, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (38, 44, '', 1, 40, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (39, 44, '', 1, 41, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (40, 44, '', 1, 42, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (41, 44, '', 1, 43, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (42, 44, '', 1, 44, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (43, 44, '', 1, 45, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (44, 44, '', 1, 46, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (45, 44, '', 1, 47, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (46, 44, '', 1, 48, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (47, 44, '', 1, 49, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (48, 44, '', 1, 50, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (49, 44, '', 1, 51, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (50, 44, '', 1, 52, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (51, 44, '', 1, 53, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (52, 44, '', 1, 54, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (53, 44, '', 1, 55, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (54, 44, '', 2, 56, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (55, 44, '', 2, 57, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (56, 44, '', 1, 58, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (57, 44, '', 2, 59, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (58, 44, '', 1, 60, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (59, 37, '', 1, 62, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (60, 83, '', 1, 65, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (61, 35, '', 1, 66, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (62, 36, '', 1, 67, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (63, 35, '', 1, 68, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (64, 35, '', 1, 69, 1, 2);

-- ----------------------------
-- Table structure for wms_ware_sku
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_sku`;
CREATE TABLE `wms_ware_sku`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint NULL DEFAULT NULL COMMENT 'sku_id',
  `ware_id` bigint NULL DEFAULT NULL COMMENT '仓库id',
  `stock` int NULL DEFAULT NULL COMMENT '库存数',
  `sku_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'sku_name',
  `stock_locked` int NULL DEFAULT 0 COMMENT '锁定库存',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sku_id`(`sku_id`) USING BTREE,
  INDEX `ware_id`(`ware_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品库存' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wms_ware_sku
-- ----------------------------
INSERT INTO `wms_ware_sku` VALUES (11, 35, 1, 80, 'IPhone13 以官网信息为准 白色 8GB 白色 135.9 以官网信息为准 A13 A13 WIFI 14', 6);
INSERT INTO `wms_ware_sku` VALUES (12, 36, 1, 100, '华为p40 A2217 白色 12GB 白色 158.3 陶瓷 海思（Hisilicon） HUAWEI Kirin 980 WIFI 15.6', 6);
INSERT INTO `wms_ware_sku` VALUES (13, 44, 1, 100, '荣耀70 A2217 白色 12GB 白色 158.3 玻璃 海思（Hisilicon） HUAWEI Kirin 980 NFC 15.6', 2);
INSERT INTO `wms_ware_sku` VALUES (14, 40, 1, 100, 'oppo C3J 蓝色 8GB 黑色 158.3 以官网信息为准 高通(Qualcomm) 骁龙855 蓝牙 15.6', 0);
INSERT INTO `wms_ware_sku` VALUES (15, 37, 1, 100, '小米12max A2217 黑色 12GB 黑色 158.3 陶瓷 高通(Qualcomm) 骁龙855 WIFI 15.6', 0);
INSERT INTO `wms_ware_sku` VALUES (16, 83, 1, 100, '测试商品 以官网信息为准 黑色 8GB 黑色 158.3 玻璃 以官网信息为准 WIFI 15.6', 0);

SET FOREIGN_KEY_CHECKS = 1;
