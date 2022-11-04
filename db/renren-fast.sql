/*
 Navicat Premium Data Transfer

 Source Server         : guli_mall
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : 192.168.61.132:3306
 Source Schema         : renren-fast

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 04/11/2022 21:10:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for QRTZ_BLOB_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `BLOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `SCHED_NAME`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_BLOB_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_CALENDARS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_CALENDARS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_CRON_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_CRON_TRIGGERS
-- ----------------------------
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', '0 0/30 * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for QRTZ_FIRED_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ENTRY_ID` varchar(95) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `FIRED_TIME` bigint NOT NULL,
  `SCHED_TIME` bigint NOT NULL,
  `PRIORITY` int NOT NULL,
  `STATE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TRIG_INST_NAME`(`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY`(`SCHED_NAME`, `INSTANCE_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_FT_J_G`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_T_G`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TG`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_FIRED_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_JOB_DETAILS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `IS_DURABLE` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_J_REQ_RECOVERY`(`SCHED_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_J_GRP`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_JOB_DETAILS
-- ----------------------------
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', NULL, 'io.renren.modules.job.utils.ScheduleJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002E696F2E72656E72656E2E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200074C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001820F9B53B07874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000174000672656E72656E74000CE58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000007800);

-- ----------------------------
-- Table structure for QRTZ_LOCKS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `LOCK_NAME` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_LOCKS
-- ----------------------------
INSERT INTO `QRTZ_LOCKS` VALUES ('RenrenScheduler', 'STATE_ACCESS');
INSERT INTO `QRTZ_LOCKS` VALUES ('RenrenScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SCHEDULER_STATE
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `LAST_CHECKIN_TIME` bigint NOT NULL,
  `CHECKIN_INTERVAL` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_SCHEDULER_STATE
-- ----------------------------
INSERT INTO `QRTZ_SCHEDULER_STATE` VALUES ('RenrenScheduler', 'LAPTOP-U598EG0I1665897122784', 1665901477838, 15000);

-- ----------------------------
-- Table structure for QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `REPEAT_COUNT` bigint NOT NULL,
  `REPEAT_INTERVAL` bigint NOT NULL,
  `TIMES_TRIGGERED` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_SIMPLE_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `STR_PROP_1` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STR_PROP_2` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STR_PROP_3` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `INT_PROP_1` int NULL DEFAULT NULL,
  `INT_PROP_2` int NULL DEFAULT NULL,
  `LONG_PROP_1` bigint NULL DEFAULT NULL,
  `LONG_PROP_2` bigint NULL DEFAULT NULL,
  `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL,
  `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_SIMPROP_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint NULL DEFAULT NULL,
  `PREV_FIRE_TIME` bigint NULL DEFAULT NULL,
  `PRIORITY` int NULL DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `START_TIME` bigint NOT NULL,
  `END_TIME` bigint NULL DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MISFIRE_INSTR` smallint NULL DEFAULT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_J`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_C`(`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_T_G`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_STATE`(`SCHED_NAME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_STATE`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_G_STATE`(`SCHED_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NEXT_FIRE_TIME`(`SCHED_NAME`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST`(`SCHED_NAME`, `TRIGGER_STATE`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_TRIGGERS
-- ----------------------------
INSERT INTO `QRTZ_TRIGGERS` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', 'TASK_1', 'DEFAULT', NULL, 1665901800000, 1665900000000, 5, 'WAITING', 'CRON', 1658198868000, 0, NULL, 2, 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002E696F2E72656E72656E2E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200074C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001820F9B53B07874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000174000672656E72656E74000CE58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000007800);

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job`  (
  `job_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint NULL DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '定时任务' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
INSERT INTO `schedule_job` VALUES (1, 'testTask', 'renren', '0 0/30 * * * ?', 0, '参数测试', '2022-07-18 12:40:14');

-- ----------------------------
-- Table structure for schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log`  (
  `log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '参数',
  `status` tinyint NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '失败信息',
  `times` int NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`) USING BTREE,
  INDEX `job_id`(`job_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 265 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '定时任务日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule_job_log
-- ----------------------------
INSERT INTO `schedule_job_log` VALUES (1, 1, 'testTask', 'renren', 0, NULL, 1, '2022-07-19 11:00:00');
INSERT INTO `schedule_job_log` VALUES (2, 1, 'testTask', 'renren', 0, NULL, 1, '2022-07-19 11:30:00');
INSERT INTO `schedule_job_log` VALUES (3, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-20 13:30:00');
INSERT INTO `schedule_job_log` VALUES (4, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-20 14:00:00');
INSERT INTO `schedule_job_log` VALUES (5, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-20 14:30:00');
INSERT INTO `schedule_job_log` VALUES (6, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-20 15:00:00');
INSERT INTO `schedule_job_log` VALUES (7, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-20 15:30:00');
INSERT INTO `schedule_job_log` VALUES (8, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-20 16:00:00');
INSERT INTO `schedule_job_log` VALUES (9, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-20 16:30:00');
INSERT INTO `schedule_job_log` VALUES (10, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-20 17:30:00');
INSERT INTO `schedule_job_log` VALUES (11, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-20 18:00:00');
INSERT INTO `schedule_job_log` VALUES (12, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-20 18:30:00');
INSERT INTO `schedule_job_log` VALUES (13, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-20 19:30:00');
INSERT INTO `schedule_job_log` VALUES (14, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-21 15:30:00');
INSERT INTO `schedule_job_log` VALUES (15, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-21 16:00:00');
INSERT INTO `schedule_job_log` VALUES (16, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-23 21:30:00');
INSERT INTO `schedule_job_log` VALUES (17, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-23 22:00:00');
INSERT INTO `schedule_job_log` VALUES (18, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-23 22:30:00');
INSERT INTO `schedule_job_log` VALUES (19, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-23 23:00:00');
INSERT INTO `schedule_job_log` VALUES (20, 1, 'testTask', 'renren', 0, NULL, 1, '2022-07-23 23:30:00');
INSERT INTO `schedule_job_log` VALUES (21, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-30 20:30:00');
INSERT INTO `schedule_job_log` VALUES (22, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-30 21:00:00');
INSERT INTO `schedule_job_log` VALUES (23, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-30 21:30:00');
INSERT INTO `schedule_job_log` VALUES (24, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-30 22:00:00');
INSERT INTO `schedule_job_log` VALUES (25, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-30 22:30:00');
INSERT INTO `schedule_job_log` VALUES (26, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-30 23:00:00');
INSERT INTO `schedule_job_log` VALUES (27, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-30 23:30:00');
INSERT INTO `schedule_job_log` VALUES (28, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-31 00:00:00');
INSERT INTO `schedule_job_log` VALUES (29, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-31 16:30:00');
INSERT INTO `schedule_job_log` VALUES (30, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-31 17:00:00');
INSERT INTO `schedule_job_log` VALUES (31, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-31 17:30:00');
INSERT INTO `schedule_job_log` VALUES (32, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-31 18:00:00');
INSERT INTO `schedule_job_log` VALUES (33, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-31 18:30:00');
INSERT INTO `schedule_job_log` VALUES (34, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-31 19:00:00');
INSERT INTO `schedule_job_log` VALUES (35, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-31 20:00:00');
INSERT INTO `schedule_job_log` VALUES (36, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-31 20:30:00');
INSERT INTO `schedule_job_log` VALUES (37, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-31 21:00:00');
INSERT INTO `schedule_job_log` VALUES (38, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-31 21:30:00');
INSERT INTO `schedule_job_log` VALUES (39, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-31 22:00:00');
INSERT INTO `schedule_job_log` VALUES (40, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-31 22:30:00');
INSERT INTO `schedule_job_log` VALUES (41, 1, 'testTask', 'renren', 0, NULL, 0, '2022-07-31 23:00:00');
INSERT INTO `schedule_job_log` VALUES (42, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-01 10:30:00');
INSERT INTO `schedule_job_log` VALUES (43, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-01 16:00:00');
INSERT INTO `schedule_job_log` VALUES (44, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-01 16:30:00');
INSERT INTO `schedule_job_log` VALUES (45, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-01 17:00:00');
INSERT INTO `schedule_job_log` VALUES (46, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-01 17:30:00');
INSERT INTO `schedule_job_log` VALUES (47, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-01 22:30:00');
INSERT INTO `schedule_job_log` VALUES (48, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-01 23:00:00');
INSERT INTO `schedule_job_log` VALUES (49, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-01 23:30:00');
INSERT INTO `schedule_job_log` VALUES (50, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-02 09:30:00');
INSERT INTO `schedule_job_log` VALUES (51, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-02 10:00:00');
INSERT INTO `schedule_job_log` VALUES (52, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-02 10:30:00');
INSERT INTO `schedule_job_log` VALUES (53, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-02 11:00:00');
INSERT INTO `schedule_job_log` VALUES (54, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-02 11:30:00');
INSERT INTO `schedule_job_log` VALUES (55, 1, 'testTask', 'renren', 0, NULL, 1, '2022-08-02 12:00:00');
INSERT INTO `schedule_job_log` VALUES (56, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-02 14:00:00');
INSERT INTO `schedule_job_log` VALUES (57, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-02 14:30:00');
INSERT INTO `schedule_job_log` VALUES (58, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-02 15:00:00');
INSERT INTO `schedule_job_log` VALUES (59, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-02 15:30:00');
INSERT INTO `schedule_job_log` VALUES (60, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-02 16:00:00');
INSERT INTO `schedule_job_log` VALUES (61, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-02 16:30:00');
INSERT INTO `schedule_job_log` VALUES (62, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-02 17:00:00');
INSERT INTO `schedule_job_log` VALUES (63, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-02 17:30:00');
INSERT INTO `schedule_job_log` VALUES (64, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-02 18:00:00');
INSERT INTO `schedule_job_log` VALUES (65, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-02 18:30:00');
INSERT INTO `schedule_job_log` VALUES (66, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-02 19:00:00');
INSERT INTO `schedule_job_log` VALUES (67, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-02 19:30:00');
INSERT INTO `schedule_job_log` VALUES (68, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-02 20:30:00');
INSERT INTO `schedule_job_log` VALUES (69, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-02 21:00:00');
INSERT INTO `schedule_job_log` VALUES (70, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-02 21:30:00');
INSERT INTO `schedule_job_log` VALUES (71, 1, 'testTask', 'renren', 0, NULL, 1, '2022-08-02 22:00:00');
INSERT INTO `schedule_job_log` VALUES (72, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-02 22:30:00');
INSERT INTO `schedule_job_log` VALUES (73, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-02 23:00:00');
INSERT INTO `schedule_job_log` VALUES (74, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-02 23:30:00');
INSERT INTO `schedule_job_log` VALUES (75, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-03 00:00:00');
INSERT INTO `schedule_job_log` VALUES (76, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-03 09:30:00');
INSERT INTO `schedule_job_log` VALUES (77, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-03 10:00:00');
INSERT INTO `schedule_job_log` VALUES (78, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-03 10:30:00');
INSERT INTO `schedule_job_log` VALUES (79, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-03 11:00:00');
INSERT INTO `schedule_job_log` VALUES (80, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-03 11:30:00');
INSERT INTO `schedule_job_log` VALUES (81, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-03 12:00:00');
INSERT INTO `schedule_job_log` VALUES (82, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-03 12:30:00');
INSERT INTO `schedule_job_log` VALUES (83, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-03 13:00:00');
INSERT INTO `schedule_job_log` VALUES (84, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-03 13:30:00');
INSERT INTO `schedule_job_log` VALUES (85, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-03 14:00:00');
INSERT INTO `schedule_job_log` VALUES (86, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-03 14:30:00');
INSERT INTO `schedule_job_log` VALUES (87, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-03 15:00:00');
INSERT INTO `schedule_job_log` VALUES (88, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-03 15:30:00');
INSERT INTO `schedule_job_log` VALUES (89, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-03 16:00:00');
INSERT INTO `schedule_job_log` VALUES (90, 1, 'testTask', 'renren', 0, NULL, 1, '2022-08-03 17:00:00');
INSERT INTO `schedule_job_log` VALUES (91, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-03 17:30:00');
INSERT INTO `schedule_job_log` VALUES (92, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-03 18:00:00');
INSERT INTO `schedule_job_log` VALUES (93, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-03 19:00:00');
INSERT INTO `schedule_job_log` VALUES (94, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-03 19:30:00');
INSERT INTO `schedule_job_log` VALUES (95, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-03 20:00:00');
INSERT INTO `schedule_job_log` VALUES (96, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-03 20:30:00');
INSERT INTO `schedule_job_log` VALUES (97, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-03 21:30:00');
INSERT INTO `schedule_job_log` VALUES (98, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-03 22:00:00');
INSERT INTO `schedule_job_log` VALUES (99, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-03 22:30:00');
INSERT INTO `schedule_job_log` VALUES (100, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-03 23:00:00');
INSERT INTO `schedule_job_log` VALUES (101, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-04 12:00:00');
INSERT INTO `schedule_job_log` VALUES (102, 1, 'testTask', 'renren', 0, NULL, 1, '2022-08-04 13:00:00');
INSERT INTO `schedule_job_log` VALUES (103, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-04 13:30:00');
INSERT INTO `schedule_job_log` VALUES (104, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-04 14:00:00');
INSERT INTO `schedule_job_log` VALUES (105, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-04 14:30:00');
INSERT INTO `schedule_job_log` VALUES (106, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-04 15:00:00');
INSERT INTO `schedule_job_log` VALUES (107, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-04 15:30:00');
INSERT INTO `schedule_job_log` VALUES (108, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-04 20:30:00');
INSERT INTO `schedule_job_log` VALUES (109, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-04 21:00:00');
INSERT INTO `schedule_job_log` VALUES (110, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-04 21:30:00');
INSERT INTO `schedule_job_log` VALUES (111, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-04 22:00:00');
INSERT INTO `schedule_job_log` VALUES (112, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-04 22:30:00');
INSERT INTO `schedule_job_log` VALUES (113, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-06 18:00:00');
INSERT INTO `schedule_job_log` VALUES (114, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-06 18:30:00');
INSERT INTO `schedule_job_log` VALUES (115, 1, 'testTask', 'renren', 0, NULL, 1, '2022-08-06 19:30:00');
INSERT INTO `schedule_job_log` VALUES (116, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-06 20:00:00');
INSERT INTO `schedule_job_log` VALUES (117, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-06 20:30:00');
INSERT INTO `schedule_job_log` VALUES (118, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-06 21:00:00');
INSERT INTO `schedule_job_log` VALUES (119, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-06 21:30:00');
INSERT INTO `schedule_job_log` VALUES (120, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-06 22:00:00');
INSERT INTO `schedule_job_log` VALUES (121, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-06 22:30:00');
INSERT INTO `schedule_job_log` VALUES (122, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-06 23:00:00');
INSERT INTO `schedule_job_log` VALUES (123, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-06 23:30:00');
INSERT INTO `schedule_job_log` VALUES (124, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-07 00:00:00');
INSERT INTO `schedule_job_log` VALUES (125, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-07 11:00:00');
INSERT INTO `schedule_job_log` VALUES (126, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-07 11:30:00');
INSERT INTO `schedule_job_log` VALUES (127, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-07 12:00:00');
INSERT INTO `schedule_job_log` VALUES (128, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-07 13:00:00');
INSERT INTO `schedule_job_log` VALUES (129, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-07 13:30:00');
INSERT INTO `schedule_job_log` VALUES (130, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-07 14:00:00');
INSERT INTO `schedule_job_log` VALUES (131, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-07 15:00:00');
INSERT INTO `schedule_job_log` VALUES (132, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-07 15:30:00');
INSERT INTO `schedule_job_log` VALUES (133, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-07 16:00:00');
INSERT INTO `schedule_job_log` VALUES (134, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-07 16:30:00');
INSERT INTO `schedule_job_log` VALUES (135, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-07 17:00:00');
INSERT INTO `schedule_job_log` VALUES (136, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-07 17:30:00');
INSERT INTO `schedule_job_log` VALUES (137, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-07 18:00:00');
INSERT INTO `schedule_job_log` VALUES (138, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-07 18:30:00');
INSERT INTO `schedule_job_log` VALUES (139, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-07 20:30:00');
INSERT INTO `schedule_job_log` VALUES (140, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-07 21:00:00');
INSERT INTO `schedule_job_log` VALUES (141, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-07 21:30:00');
INSERT INTO `schedule_job_log` VALUES (142, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-07 22:00:00');
INSERT INTO `schedule_job_log` VALUES (143, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-07 22:30:00');
INSERT INTO `schedule_job_log` VALUES (144, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-07 23:00:00');
INSERT INTO `schedule_job_log` VALUES (145, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-08 10:30:00');
INSERT INTO `schedule_job_log` VALUES (146, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-08 11:00:00');
INSERT INTO `schedule_job_log` VALUES (147, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-08 11:30:00');
INSERT INTO `schedule_job_log` VALUES (148, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-08 12:00:00');
INSERT INTO `schedule_job_log` VALUES (149, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-08 12:30:00');
INSERT INTO `schedule_job_log` VALUES (150, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-08 13:00:00');
INSERT INTO `schedule_job_log` VALUES (151, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-08 13:30:00');
INSERT INTO `schedule_job_log` VALUES (152, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-08 17:00:00');
INSERT INTO `schedule_job_log` VALUES (153, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-08 17:30:00');
INSERT INTO `schedule_job_log` VALUES (154, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-08 18:00:00');
INSERT INTO `schedule_job_log` VALUES (155, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-08 18:30:00');
INSERT INTO `schedule_job_log` VALUES (156, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-08 19:30:00');
INSERT INTO `schedule_job_log` VALUES (157, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-08 20:00:00');
INSERT INTO `schedule_job_log` VALUES (158, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-08 20:30:00');
INSERT INTO `schedule_job_log` VALUES (159, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-09 15:00:00');
INSERT INTO `schedule_job_log` VALUES (160, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-09 15:30:00');
INSERT INTO `schedule_job_log` VALUES (161, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-09 16:00:00');
INSERT INTO `schedule_job_log` VALUES (162, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-09 16:30:00');
INSERT INTO `schedule_job_log` VALUES (163, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-09 17:00:00');
INSERT INTO `schedule_job_log` VALUES (164, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-09 17:30:00');
INSERT INTO `schedule_job_log` VALUES (165, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-09 18:00:00');
INSERT INTO `schedule_job_log` VALUES (166, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-09 18:30:00');
INSERT INTO `schedule_job_log` VALUES (167, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-09 19:00:00');
INSERT INTO `schedule_job_log` VALUES (168, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-09 20:00:00');
INSERT INTO `schedule_job_log` VALUES (169, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-09 20:30:00');
INSERT INTO `schedule_job_log` VALUES (170, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-09 21:00:00');
INSERT INTO `schedule_job_log` VALUES (171, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-09 21:30:00');
INSERT INTO `schedule_job_log` VALUES (172, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-09 22:00:00');
INSERT INTO `schedule_job_log` VALUES (173, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-09 22:30:00');
INSERT INTO `schedule_job_log` VALUES (174, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-09 23:00:00');
INSERT INTO `schedule_job_log` VALUES (175, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-09 23:30:00');
INSERT INTO `schedule_job_log` VALUES (176, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-10 00:00:00');
INSERT INTO `schedule_job_log` VALUES (177, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-10 10:30:00');
INSERT INTO `schedule_job_log` VALUES (178, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-10 11:00:00');
INSERT INTO `schedule_job_log` VALUES (179, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-10 11:30:00');
INSERT INTO `schedule_job_log` VALUES (180, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-10 13:00:00');
INSERT INTO `schedule_job_log` VALUES (181, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-10 13:30:00');
INSERT INTO `schedule_job_log` VALUES (182, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-10 14:00:00');
INSERT INTO `schedule_job_log` VALUES (183, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-10 14:30:00');
INSERT INTO `schedule_job_log` VALUES (184, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-10 15:00:00');
INSERT INTO `schedule_job_log` VALUES (185, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-10 16:00:00');
INSERT INTO `schedule_job_log` VALUES (186, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-10 16:30:00');
INSERT INTO `schedule_job_log` VALUES (187, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-10 17:00:00');
INSERT INTO `schedule_job_log` VALUES (188, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-10 17:30:00');
INSERT INTO `schedule_job_log` VALUES (189, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-10 18:00:00');
INSERT INTO `schedule_job_log` VALUES (190, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-10 18:30:00');
INSERT INTO `schedule_job_log` VALUES (191, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-10 19:00:00');
INSERT INTO `schedule_job_log` VALUES (192, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-10 22:00:00');
INSERT INTO `schedule_job_log` VALUES (193, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-10 22:30:00');
INSERT INTO `schedule_job_log` VALUES (194, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-10 23:00:00');
INSERT INTO `schedule_job_log` VALUES (195, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-11 13:00:00');
INSERT INTO `schedule_job_log` VALUES (196, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-11 13:30:00');
INSERT INTO `schedule_job_log` VALUES (197, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-11 16:30:00');
INSERT INTO `schedule_job_log` VALUES (198, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-11 17:00:00');
INSERT INTO `schedule_job_log` VALUES (199, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-11 17:30:00');
INSERT INTO `schedule_job_log` VALUES (200, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-11 18:00:00');
INSERT INTO `schedule_job_log` VALUES (201, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-11 18:30:00');
INSERT INTO `schedule_job_log` VALUES (202, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-11 19:00:00');
INSERT INTO `schedule_job_log` VALUES (203, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-11 20:30:00');
INSERT INTO `schedule_job_log` VALUES (204, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-11 21:00:00');
INSERT INTO `schedule_job_log` VALUES (205, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-11 21:30:00');
INSERT INTO `schedule_job_log` VALUES (206, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-11 22:00:00');
INSERT INTO `schedule_job_log` VALUES (207, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-11 22:30:00');
INSERT INTO `schedule_job_log` VALUES (208, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-11 23:00:00');
INSERT INTO `schedule_job_log` VALUES (209, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-11 23:30:00');
INSERT INTO `schedule_job_log` VALUES (210, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-16 12:00:00');
INSERT INTO `schedule_job_log` VALUES (211, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-16 12:30:00');
INSERT INTO `schedule_job_log` VALUES (212, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-16 13:00:00');
INSERT INTO `schedule_job_log` VALUES (213, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-16 13:30:00');
INSERT INTO `schedule_job_log` VALUES (214, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-16 14:00:00');
INSERT INTO `schedule_job_log` VALUES (215, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-16 14:30:00');
INSERT INTO `schedule_job_log` VALUES (216, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-16 15:00:00');
INSERT INTO `schedule_job_log` VALUES (217, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-16 15:30:00');
INSERT INTO `schedule_job_log` VALUES (218, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-16 16:00:00');
INSERT INTO `schedule_job_log` VALUES (219, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-16 16:30:00');
INSERT INTO `schedule_job_log` VALUES (220, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-16 17:00:00');
INSERT INTO `schedule_job_log` VALUES (221, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-16 17:30:00');
INSERT INTO `schedule_job_log` VALUES (222, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-16 18:00:00');
INSERT INTO `schedule_job_log` VALUES (223, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-16 18:30:00');
INSERT INTO `schedule_job_log` VALUES (224, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-16 20:30:00');
INSERT INTO `schedule_job_log` VALUES (225, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-16 21:00:00');
INSERT INTO `schedule_job_log` VALUES (226, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-16 21:30:00');
INSERT INTO `schedule_job_log` VALUES (227, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-16 22:00:00');
INSERT INTO `schedule_job_log` VALUES (228, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-16 22:30:00');
INSERT INTO `schedule_job_log` VALUES (229, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-16 23:00:00');
INSERT INTO `schedule_job_log` VALUES (230, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-16 23:30:00');
INSERT INTO `schedule_job_log` VALUES (231, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-19 10:30:00');
INSERT INTO `schedule_job_log` VALUES (232, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-22 12:00:00');
INSERT INTO `schedule_job_log` VALUES (233, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-22 13:00:00');
INSERT INTO `schedule_job_log` VALUES (234, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-22 13:30:00');
INSERT INTO `schedule_job_log` VALUES (235, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-22 14:00:00');
INSERT INTO `schedule_job_log` VALUES (236, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-22 14:30:00');
INSERT INTO `schedule_job_log` VALUES (237, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-22 15:00:00');
INSERT INTO `schedule_job_log` VALUES (238, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-22 15:30:00');
INSERT INTO `schedule_job_log` VALUES (239, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-22 16:00:00');
INSERT INTO `schedule_job_log` VALUES (240, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-22 16:30:00');
INSERT INTO `schedule_job_log` VALUES (241, 1, 'testTask', 'renren', 0, NULL, 0, '2022-08-22 20:00:00');
INSERT INTO `schedule_job_log` VALUES (242, 1, 'testTask', 'renren', 0, NULL, 0, '2022-10-02 19:30:00');
INSERT INTO `schedule_job_log` VALUES (243, 1, 'testTask', 'renren', 0, NULL, 0, '2022-10-02 20:00:00');
INSERT INTO `schedule_job_log` VALUES (244, 1, 'testTask', 'renren', 0, NULL, 0, '2022-10-02 20:30:00');
INSERT INTO `schedule_job_log` VALUES (245, 1, 'testTask', 'renren', 0, NULL, 0, '2022-10-02 21:00:00');
INSERT INTO `schedule_job_log` VALUES (246, 1, 'testTask', 'renren', 0, NULL, 0, '2022-10-02 21:30:00');
INSERT INTO `schedule_job_log` VALUES (247, 1, 'testTask', 'renren', 0, NULL, 0, '2022-10-02 22:00:00');
INSERT INTO `schedule_job_log` VALUES (248, 1, 'testTask', 'renren', 0, NULL, 0, '2022-10-02 22:30:00');
INSERT INTO `schedule_job_log` VALUES (249, 1, 'testTask', 'renren', 0, NULL, 0, '2022-10-02 23:00:00');
INSERT INTO `schedule_job_log` VALUES (250, 1, 'testTask', 'renren', 0, NULL, 0, '2022-10-02 23:30:00');
INSERT INTO `schedule_job_log` VALUES (251, 1, 'testTask', 'renren', 0, NULL, 0, '2022-10-03 00:00:00');
INSERT INTO `schedule_job_log` VALUES (252, 1, 'testTask', 'renren', 0, NULL, 0, '2022-10-03 00:30:00');
INSERT INTO `schedule_job_log` VALUES (253, 1, 'testTask', 'renren', 0, NULL, 0, '2022-10-03 01:00:00');
INSERT INTO `schedule_job_log` VALUES (254, 1, 'testTask', 'renren', 0, NULL, 1, '2022-10-10 20:00:00');
INSERT INTO `schedule_job_log` VALUES (255, 1, 'testTask', 'renren', 0, NULL, 0, '2022-10-10 20:30:00');
INSERT INTO `schedule_job_log` VALUES (256, 1, 'testTask', 'renren', 0, NULL, 0, '2022-10-10 21:00:00');
INSERT INTO `schedule_job_log` VALUES (257, 1, 'testTask', 'renren', 0, NULL, 0, '2022-10-10 21:30:00');
INSERT INTO `schedule_job_log` VALUES (258, 1, 'testTask', 'renren', 0, NULL, 0, '2022-10-10 22:00:00');
INSERT INTO `schedule_job_log` VALUES (259, 1, 'testTask', 'renren', 0, NULL, 0, '2022-10-10 22:30:00');
INSERT INTO `schedule_job_log` VALUES (260, 1, 'testTask', 'renren', 0, NULL, 0, '2022-10-10 23:00:00');
INSERT INTO `schedule_job_log` VALUES (261, 1, 'testTask', 'renren', 0, NULL, 0, '2022-10-12 11:00:00');
INSERT INTO `schedule_job_log` VALUES (262, 1, 'testTask', 'renren', 0, NULL, 0, '2022-10-12 11:30:00');
INSERT INTO `schedule_job_log` VALUES (263, 1, 'testTask', 'renren', 0, NULL, 0, '2022-10-12 12:00:00');
INSERT INTO `schedule_job_log` VALUES (264, 1, 'testTask', 'renren', 0, NULL, 0, '2022-10-16 13:30:00');
INSERT INTO `schedule_job_log` VALUES (265, 1, 'testTask', 'renren', 0, NULL, 0, '2022-10-16 14:00:00');

-- ----------------------------
-- Table structure for sys_captcha
-- ----------------------------
DROP TABLE IF EXISTS `sys_captcha`;
CREATE TABLE `sys_captcha`  (
  `uuid` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'uuid',
  `code` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '验证码',
  `expire_time` datetime NULL DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统验证码' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_captcha
-- ----------------------------
INSERT INTO `sys_captcha` VALUES ('0032888b-96a5-4284-8898-6639d05a39e4', '8x47p', '2022-08-02 23:28:27');
INSERT INTO `sys_captcha` VALUES ('00d7c7b7-27e5-44d4-8adc-a4f9bd5aebf7', 'e2c8e', '2022-08-08 12:42:17');
INSERT INTO `sys_captcha` VALUES ('012e995e-fb5d-4b6c-8ed4-0240192b640d', 'xpfby', '2022-08-02 22:04:18');
INSERT INTO `sys_captcha` VALUES ('02128db2-9003-4df3-81fb-3405a5fba21e', 'nwpxn', '2022-08-07 10:46:46');
INSERT INTO `sys_captcha` VALUES ('032880ed-6726-426d-83ef-9fc91b4d5573', '8wayc', '2022-08-02 16:38:07');
INSERT INTO `sys_captcha` VALUES ('0470aa8a-318b-4a5e-8833-1f54a80e55aa', 'abfd6', '2022-08-11 22:14:32');
INSERT INTO `sys_captcha` VALUES ('04766f41-8112-424c-8cfa-ab0ae60d6307', 'c7xnd', '2022-08-02 23:29:53');
INSERT INTO `sys_captcha` VALUES ('0520bed7-a3c4-4487-832d-1dc6da7dd2b8', 'x287p', '2022-08-11 23:39:36');
INSERT INTO `sys_captcha` VALUES ('06703ca5-efcd-41c2-86a1-3c68d7971216', '83cne', '2022-10-10 20:21:43');
INSERT INTO `sys_captcha` VALUES ('085af2b5-58a6-487a-8572-4dd728111aac', 'd6cy5', '2022-08-09 20:03:52');
INSERT INTO `sys_captcha` VALUES ('085edec7-9798-49ac-8703-e0fce4bf02e2', '2wgn3', '2022-08-09 22:15:10');
INSERT INTO `sys_captcha` VALUES ('0a523497-f18b-43d7-8554-ea629ac21382', 'b5xm4', '2022-08-02 14:56:19');
INSERT INTO `sys_captcha` VALUES ('0db74d99-aa6f-421f-8f75-506d677012a0', 'nwxf2', '2022-08-09 17:52:40');
INSERT INTO `sys_captcha` VALUES ('10e30626-40a2-4bed-8158-f15b44b9713e', 'd5xpf', '2022-08-09 16:32:41');
INSERT INTO `sys_captcha` VALUES ('15c3e10d-f62c-4b64-8852-958f298bb33d', 'ge848', '2022-08-09 23:58:42');
INSERT INTO `sys_captcha` VALUES ('16760ff0-f645-40a0-8a80-47aaf97b3777', 'yc6nc', '2022-08-10 13:29:16');
INSERT INTO `sys_captcha` VALUES ('1765b46b-745b-4d02-83e7-766e411bc85a', 'yagp8', '2022-08-10 11:32:45');
INSERT INTO `sys_captcha` VALUES ('17ae73e3-ec43-4375-8c5d-266358b5f8d6', '6mnaf', '2022-08-11 23:54:30');
INSERT INTO `sys_captcha` VALUES ('195a250c-5c50-4b4d-8880-d7301eef1715', '75xmx', '2022-08-08 13:32:41');
INSERT INTO `sys_captcha` VALUES ('1a1e3a4d-1304-49f1-8469-ee588ef2b5d3', 'ng425', '2022-08-06 16:12:19');
INSERT INTO `sys_captcha` VALUES ('1a875389-cf78-480b-89d3-7aa94681785a', 'b5a6a', '2022-08-09 21:05:34');
INSERT INTO `sys_captcha` VALUES ('1aa0f411-b29d-4ac6-8395-cf4893bf7dcd', 'c4df8', '2022-08-02 10:29:26');
INSERT INTO `sys_captcha` VALUES ('1c0c1d0f-8b1a-4e86-8324-06bfa7da5c91', 'bxn5e', '2022-08-16 20:00:49');
INSERT INTO `sys_captcha` VALUES ('1cbe58cd-5a55-42b3-81bd-ac20ba4f95d0', '7nc6c', '2022-08-10 00:07:49');
INSERT INTO `sys_captcha` VALUES ('1ce9688f-0153-4c66-8999-06de0081425b', 'maf4c', '2022-08-09 23:27:24');
INSERT INTO `sys_captcha` VALUES ('1dca0409-c253-4b69-8326-6630eb45ffcf', '7gg3m', '2022-08-02 14:53:01');
INSERT INTO `sys_captcha` VALUES ('1de6c97d-e1d1-4ebf-85d9-a1bf550e2799', '23x6p', '2022-08-02 16:31:53');
INSERT INTO `sys_captcha` VALUES ('1ed6577b-933e-4895-8185-d1df8cfbb880', 'wyaxx', '2022-08-06 16:11:48');
INSERT INTO `sys_captcha` VALUES ('20d12121-56ad-4bb9-8003-56a265c0fad9', '85ny2', '2022-08-02 13:55:56');
INSERT INTO `sys_captcha` VALUES ('21b990fe-a84d-4453-8145-ed4772b81357', 'a7pgf', '2022-10-10 20:18:31');
INSERT INTO `sys_captcha` VALUES ('21f4fb6a-47c1-463e-8969-152db6ab4e2f', 'pcc54', '2022-08-11 23:46:25');
INSERT INTO `sys_captcha` VALUES ('2241139a-b04e-46e1-88c0-af4001dfda71', 'yde7f', '2022-08-09 21:04:54');
INSERT INTO `sys_captcha` VALUES ('2337317e-0d6c-4f5c-843e-4d3d61fa7200', '672g6', '2022-08-10 16:38:10');
INSERT INTO `sys_captcha` VALUES ('252aa2b5-0e75-416f-82bf-a3feb2a1a792', 'fpfwm', '2022-08-04 12:00:58');
INSERT INTO `sys_captcha` VALUES ('29d3c8aa-22b9-423e-8d78-2c70c8752a2d', 'bx8n2', '2022-08-08 12:36:14');
INSERT INTO `sys_captcha` VALUES ('2c74d095-8edc-4aaa-876b-f2f53d5a5a6f', 'xwbn6', '2022-08-02 22:14:55');
INSERT INTO `sys_captcha` VALUES ('2ca37b61-94ba-4e53-851f-9176cb79b8fb', '4ndp8', '2022-08-02 10:00:44');
INSERT INTO `sys_captcha` VALUES ('2ebe9f35-c94f-48b2-8441-014ac58cd55c', 'en2my', '2022-08-22 12:19:28');
INSERT INTO `sys_captcha` VALUES ('301219fd-470d-4494-83a2-a0c88c8da27c', 'fd8cn', '2022-08-02 14:06:25');
INSERT INTO `sys_captcha` VALUES ('310a82ed-35ce-4557-8a03-d88c26057aa3', 'e5w43', '2022-08-11 23:59:13');
INSERT INTO `sys_captcha` VALUES ('3218ceb7-ea13-44a8-8b68-a81483e14029', '8yncn', '2022-08-10 13:23:08');
INSERT INTO `sys_captcha` VALUES ('322b7233-6a6b-4147-8f6c-376638cc3e89', 'cff52', '2022-08-10 10:44:02');
INSERT INTO `sys_captcha` VALUES ('33508641-cc32-4cdc-8870-9736e7ede389', 'cynb3', '2022-08-10 11:08:20');
INSERT INTO `sys_captcha` VALUES ('340c4cf6-e4e2-41af-8fe4-d6835854e893', '2c6yx', '2022-08-10 00:00:17');
INSERT INTO `sys_captcha` VALUES ('36084ba5-c296-4cd0-8c5b-230df182b031', '247cf', '2022-08-09 16:45:22');
INSERT INTO `sys_captcha` VALUES ('37468aa9-2c8a-4cd7-8d74-14da8d47be42', '45n2e', '2022-08-10 10:50:46');
INSERT INTO `sys_captcha` VALUES ('378cf676-43ed-4bf1-8293-6e6f44d781a0', '3437x', '2022-08-11 23:46:58');
INSERT INTO `sys_captcha` VALUES ('38477583-b1ed-4c6d-8730-35615418d7f1', 'g7pfp', '2022-08-09 16:53:21');
INSERT INTO `sys_captcha` VALUES ('38659ddf-28e4-4fcf-84a9-84c4b8279c3a', '2y78x', '2022-08-08 12:47:05');
INSERT INTO `sys_captcha` VALUES ('38f7dd29-2890-4dc0-8def-aa94e4d18818', '66db7', '2022-08-10 00:14:00');
INSERT INTO `sys_captcha` VALUES ('3991035d-9a40-483f-8052-b40ac149ac6a', '75cbn', '2022-08-08 18:08:23');
INSERT INTO `sys_captcha` VALUES ('3a990e40-d131-4f3a-8064-6081933af256', 'f4ye5', '2022-08-10 13:45:27');
INSERT INTO `sys_captcha` VALUES ('3bcc3c24-3e5e-45dc-8e95-698ddc5acd27', 'f4fc7', '2022-08-11 21:02:02');
INSERT INTO `sys_captcha` VALUES ('3d81cd65-609e-40f7-800e-58a94fd7fe20', '6mxm7', '2022-08-02 21:17:49');
INSERT INTO `sys_captcha` VALUES ('3ef4175d-0246-4963-8eff-3e6fb85b1c3e', 'bf8wp', '2022-08-09 22:13:44');
INSERT INTO `sys_captcha` VALUES ('3f9e6c36-1a7d-4c9b-8f66-9395a6a5ffb5', 'en8g2', '2022-08-03 21:09:02');
INSERT INTO `sys_captcha` VALUES ('40a26b5b-ba93-4de5-8408-3c5a80b3ad1d', 'cya8n', '2022-08-22 11:56:26');
INSERT INTO `sys_captcha` VALUES ('41007375-4ea8-4153-89fb-96268ca3bfc4', 'nx6aa', '2022-08-04 20:38:46');
INSERT INTO `sys_captcha` VALUES ('4232ba77-a059-4777-8f48-f276640767b0', '6gfxp', '2022-08-11 23:44:25');
INSERT INTO `sys_captcha` VALUES ('4301a030-556a-442c-8a38-a6339aa9cd67', 'gnd85', '2022-08-04 21:40:59');
INSERT INTO `sys_captcha` VALUES ('44bcca1c-00c6-479b-8d2d-6daaa6be0864', 'ygb37', '2022-08-08 18:27:27');
INSERT INTO `sys_captcha` VALUES ('4587b6f3-5a42-4c11-8b34-43b17fbb9f59', 'cwe28', '2022-08-11 23:51:08');
INSERT INTO `sys_captcha` VALUES ('46a2f376-afdc-4d65-87b2-aab40bb01c72', '542ba', '2022-08-09 23:56:23');
INSERT INTO `sys_captcha` VALUES ('4767a7b4-fb9a-4e3b-8286-17a1a38a00ee', '7f4dp', '2022-08-04 22:16:52');
INSERT INTO `sys_captcha` VALUES ('49dbcf4e-1281-4be1-84b2-9fb6688ea088', 'dacyy', '2022-08-22 12:06:04');
INSERT INTO `sys_captcha` VALUES ('4a8bbcc9-9eb5-4842-8a1e-8f2ed6be309b', 'a463m', '2022-08-09 23:38:06');
INSERT INTO `sys_captcha` VALUES ('4c12e020-e8fa-44a2-8f2f-5c92fb3a6ccd', '65w78', '2022-08-07 10:46:51');
INSERT INTO `sys_captcha` VALUES ('4c4946b0-9d3f-4aae-8d47-474b61b2de67', 'mxep6', '2022-08-10 14:05:22');
INSERT INTO `sys_captcha` VALUES ('4c9cc661-6c20-4425-8230-326cfd697d52', '7dfbm', '2022-08-02 12:06:41');
INSERT INTO `sys_captcha` VALUES ('4d0e7ffa-f232-4dfc-8ef4-4555adf7e314', 'afbc2', '2022-08-09 23:57:39');
INSERT INTO `sys_captcha` VALUES ('4fdf9aaa-e937-40ba-8e0a-7202aad53e74', '2g265', '2022-08-02 10:28:24');
INSERT INTO `sys_captcha` VALUES ('503b6bb8-0413-4a3f-8ee1-c0d318b427f5', 'p24w6', '2022-08-07 11:24:44');
INSERT INTO `sys_captcha` VALUES ('51f0d332-8ec8-49f2-8b97-4144ee0e799a', 'x5f62', '2022-08-02 17:06:09');
INSERT INTO `sys_captcha` VALUES ('51f13238-6340-49c7-8179-2c7c9d0dc339', '5f76c', '2022-10-12 10:48:02');
INSERT INTO `sys_captcha` VALUES ('540af6b8-6b21-4ec2-8415-62e0e7df21fa', 'ybd2d', '2022-08-10 10:47:46');
INSERT INTO `sys_captcha` VALUES ('5468fe73-7baa-4b39-841d-94d2f7601f89', 'ae88e', '2022-08-09 23:46:28');
INSERT INTO `sys_captcha` VALUES ('549907c7-ac2f-47e1-83a7-ea0b170a2aad', '22bmb', '2022-08-04 20:40:17');
INSERT INTO `sys_captcha` VALUES ('5b5b54e0-a86b-4a2f-8426-a5575ffbc77a', '5exw7', '2022-08-02 10:00:01');
INSERT INTO `sys_captcha` VALUES ('5dacb113-bf05-4d4d-8a6d-2eb47094f2a4', '23gpa', '2022-08-07 10:46:55');
INSERT INTO `sys_captcha` VALUES ('5f3b2618-0ef1-44fe-85d7-e139dca0d765', '8dx8x', '2022-08-10 00:00:35');
INSERT INTO `sys_captcha` VALUES ('5ff81399-5b4f-403e-8642-ca3d27df79e4', 'dx7nb', '2022-08-07 10:56:20');
INSERT INTO `sys_captcha` VALUES ('60a2be76-7aec-41f8-8381-b5f26e262a95', 'em2e5', '2022-08-08 12:49:22');
INSERT INTO `sys_captcha` VALUES ('63a627da-3596-4da0-8923-ab27e6e85a8e', 'xmf24', '2022-08-09 23:43:05');
INSERT INTO `sys_captcha` VALUES ('64a51f22-9d1b-41d6-824f-c50d9d905a83', 'f42am', '2022-08-02 18:26:32');
INSERT INTO `sys_captcha` VALUES ('64e82071-294c-4ef2-843c-2c58cee598d3', 'y5ywg', '2022-08-10 10:50:14');
INSERT INTO `sys_captcha` VALUES ('6563e9d5-2a8e-43bc-8436-f87172298b05', 'f57f3', '2022-08-02 10:36:10');
INSERT INTO `sys_captcha` VALUES ('65824655-14ac-4703-8e57-47f76e5be74e', 'ny42y', '2022-08-09 19:15:50');
INSERT INTO `sys_captcha` VALUES ('65928e55-948a-4a09-819c-5e0d64e64a03', 'mfn2m', '2022-08-11 23:44:03');
INSERT INTO `sys_captcha` VALUES ('6629eecb-dfc7-4452-84aa-8e609cee63e6', '46wna', '2022-08-08 11:31:23');
INSERT INTO `sys_captcha` VALUES ('67f5bacf-445a-4631-87e0-d6e2d1cd9433', '3bm2m', '2022-08-10 10:41:54');
INSERT INTO `sys_captcha` VALUES ('68befac6-f5b3-4203-86b8-520995219846', 'y2nfw', '2022-08-16 20:04:44');
INSERT INTO `sys_captcha` VALUES ('69ea460f-b83e-476f-8cea-911be102e5ad', '54p6d', '2022-08-11 23:40:55');
INSERT INTO `sys_captcha` VALUES ('6a993a70-11d5-4e9a-81c3-8dd14634866f', '8573g', '2022-08-02 18:28:06');
INSERT INTO `sys_captcha` VALUES ('6c6b4318-c2f1-4a31-8a8e-9ca0a027f1aa', 'fw4y5', '2022-08-10 17:42:12');
INSERT INTO `sys_captcha` VALUES ('6d5b1ba1-3d3d-43b5-84f7-74cf66ceb817', 'a24dg', '2022-08-09 21:53:07');
INSERT INTO `sys_captcha` VALUES ('6e87c4a2-cc2d-4058-8dd7-7f289c84d71a', 'xxcyd', '2022-08-09 16:46:44');
INSERT INTO `sys_captcha` VALUES ('6eceea56-b0c0-49c2-83fd-96bb175a0026', '2w445', '2022-08-02 11:19:53');
INSERT INTO `sys_captcha` VALUES ('705d859a-8261-4f01-8570-040af533606a', '2geg8', '2022-08-09 16:57:26');
INSERT INTO `sys_captcha` VALUES ('7131bff9-352d-4de7-8096-6b2a3edbee5b', 'wngfn', '2022-08-02 21:34:43');
INSERT INTO `sys_captcha` VALUES ('716ad3ac-bfda-4dc1-86ce-11a15ddb71a6', 'gby6n', '2022-08-09 16:27:34');
INSERT INTO `sys_captcha` VALUES ('72c776b9-afd0-4738-80f2-608310256765', 'y563f', '2022-08-02 18:33:47');
INSERT INTO `sys_captcha` VALUES ('72dfe31e-ce69-4218-8812-ef5d2780899a', 'cpeyw', '2022-08-09 22:36:42');
INSERT INTO `sys_captcha` VALUES ('738d27a8-1624-4847-809f-c050e3a97633', '2wyn3', '2022-08-02 16:34:27');
INSERT INTO `sys_captcha` VALUES ('73d6f629-28db-4f33-8eb3-0326627e041c', 'xenbe', '2022-08-11 23:32:24');
INSERT INTO `sys_captcha` VALUES ('74e89442-6c18-4d65-858a-872483ea7414', '56n4n', '2022-08-08 11:29:02');
INSERT INTO `sys_captcha` VALUES ('74fc87df-8bcf-4975-85f9-107f62e1a962', '5naf8', '2022-08-02 18:33:40');
INSERT INTO `sys_captcha` VALUES ('75c4eaad-4037-465e-8542-511694fdfff6', '5mb2a', '2022-08-08 12:47:22');
INSERT INTO `sys_captcha` VALUES ('76a2e98b-83fa-4b74-80e5-c845348513e1', '84ngp', '2022-08-09 22:38:30');
INSERT INTO `sys_captcha` VALUES ('78015aae-b8ee-4afd-8440-dcbc02029190', '6xnme', '2022-08-04 22:15:33');
INSERT INTO `sys_captcha` VALUES ('78f61ece-25de-4dc7-8e4e-1657dc829b67', 'ccbfn', '2022-08-06 20:12:16');
INSERT INTO `sys_captcha` VALUES ('7bb7df73-55cd-417c-81cb-ebfe60000343', 'n4ynf', '2022-08-11 23:41:40');
INSERT INTO `sys_captcha` VALUES ('7bddf566-fad9-4013-88f1-8d21beba3e42', 'bmne6', '2022-08-02 17:08:49');
INSERT INTO `sys_captcha` VALUES ('7d32c6c6-d6e5-437f-8aa0-ec699ba18a51', '6ycng', '2022-07-20 16:56:53');
INSERT INTO `sys_captcha` VALUES ('7e057d4d-081f-4eb3-8708-ec4020e21461', '78a32', '2022-08-09 23:33:15');
INSERT INTO `sys_captcha` VALUES ('7e6a16fb-690b-4873-823c-2fb9cfc4e163', 'apwb4', '2022-08-10 10:56:31');
INSERT INTO `sys_captcha` VALUES ('80cef39d-01af-40c6-8903-b6dba94f203f', '3wy8p', '2022-08-02 14:56:53');
INSERT INTO `sys_captcha` VALUES ('80d5371a-cd7b-444c-8650-c370f0d8221b', '65pf4', '2022-08-02 16:37:55');
INSERT INTO `sys_captcha` VALUES ('83893633-8609-4299-82e9-b385e0256d2c', '3856m', '2022-08-22 12:43:27');
INSERT INTO `sys_captcha` VALUES ('86af309a-93aa-4ba6-8aae-b10ff681229e', 'd5c7n', '2022-08-09 19:54:28');
INSERT INTO `sys_captcha` VALUES ('87c2f869-b642-4dcf-8edb-204e941774f6', '6nx2w', '2022-08-02 23:34:09');
INSERT INTO `sys_captcha` VALUES ('888198e3-5f45-4277-8666-0cacc566019f', '2dnf8', '2022-08-02 14:58:34');
INSERT INTO `sys_captcha` VALUES ('896aa04f-f135-40df-8ff5-2fdd06eb4e8f', 'emnxa', '2022-08-10 13:48:13');
INSERT INTO `sys_captcha` VALUES ('8b25b08a-cbf8-4c1d-8f81-21885a56a632', '3w53a', '2022-08-08 12:16:24');
INSERT INTO `sys_captcha` VALUES ('8b3505de-a009-41ca-87b5-1b4da592fc9c', 'cyeym', '2022-10-12 10:50:38');
INSERT INTO `sys_captcha` VALUES ('8c31f071-f5ca-424f-8e49-317a948ebfe0', 'n43nm', '2022-08-10 10:45:47');
INSERT INTO `sys_captcha` VALUES ('8e946a88-0503-45dc-8f8c-3c5fb2586dae', 'ynfmy', '2022-08-08 12:09:21');
INSERT INTO `sys_captcha` VALUES ('8ead79e8-1c0d-4145-8884-5bcbd44b104e', '4x533', '2022-08-10 10:30:50');
INSERT INTO `sys_captcha` VALUES ('9072b7ca-70cd-4b85-8ec8-dd0aa2db3f9c', 'eya2p', '2022-08-02 16:38:27');
INSERT INTO `sys_captcha` VALUES ('9109315f-b6fd-4156-84ba-1243b929100b', '54fen', '2022-08-11 21:31:16');
INSERT INTO `sys_captcha` VALUES ('91ede299-3fef-4fc2-8a7f-dc070f439a2c', '6aye5', '2022-08-08 10:43:34');
INSERT INTO `sys_captcha` VALUES ('922734ce-1880-46e7-8cfd-65bbf23cdac1', 'agypf', '2022-08-22 12:06:23');
INSERT INTO `sys_captcha` VALUES ('9266faa4-f96c-40d2-8427-142f96d56335', '5yw78', '2022-08-02 16:33:23');
INSERT INTO `sys_captcha` VALUES ('92d97385-353d-4747-8e4e-0d7fee23b794', 'c6xan', '2022-08-10 00:19:25');
INSERT INTO `sys_captcha` VALUES ('936300e2-6618-418f-8308-0eac4545f175', 'x5n34', '2022-08-10 13:18:10');
INSERT INTO `sys_captcha` VALUES ('9493645f-42f9-4eb5-88b6-c019bee4d269', 'ndgne', '2022-08-09 15:08:48');
INSERT INTO `sys_captcha` VALUES ('9546e078-780a-4cfc-80ca-e8adff2c0658', 'pm8fa', '2022-08-02 10:56:00');
INSERT INTO `sys_captcha` VALUES ('95fe5c14-848f-48eb-8a54-c002a90a5f27', 'wyp2c', '2022-08-10 10:39:57');
INSERT INTO `sys_captcha` VALUES ('97d4c266-f44b-415b-83c7-37d4a3394384', '8a3wf', '2022-08-09 23:29:53');
INSERT INTO `sys_captcha` VALUES ('99a42d4b-8a64-4e01-8609-0aae12d58976', 'c6f4f', '2022-08-09 23:26:46');
INSERT INTO `sys_captcha` VALUES ('9c6832a4-39cf-4c2e-8bb2-0e9e89b121e0', '3cw25', '2022-08-02 10:36:25');
INSERT INTO `sys_captcha` VALUES ('9ea0fc42-c904-4f2e-8525-003851cc3583', 'c2wnc', '2022-10-12 11:28:13');
INSERT INTO `sys_captcha` VALUES ('a03fe1ff-2ae6-4c4b-89eb-5317942c82f9', 'yynyp', '2022-08-02 13:50:24');
INSERT INTO `sys_captcha` VALUES ('a043212d-a69c-483c-8cca-1ae3d819f638', 'w26g8', '2022-08-06 21:00:18');
INSERT INTO `sys_captcha` VALUES ('a0c9e4d0-38a9-43eb-876e-a95272fce008', 'w2cy2', '2022-08-10 14:21:31');
INSERT INTO `sys_captcha` VALUES ('a3240a10-c5e7-4b2f-8810-42088b66f89e', 'xc275', '2022-08-10 00:06:30');
INSERT INTO `sys_captcha` VALUES ('a53ac942-ff79-42b1-858c-d189d2b750de', 'g5fgd', '2022-08-02 11:27:02');
INSERT INTO `sys_captcha` VALUES ('a549da05-3245-4934-8b29-c667dc46e284', 'ww6c6', '2022-08-11 23:38:53');
INSERT INTO `sys_captcha` VALUES ('a60af9b8-e0d3-483f-8101-59543c8e4a73', 'cnnng', '2022-08-08 12:40:54');
INSERT INTO `sys_captcha` VALUES ('a6d26707-0264-462c-8ce1-14bfcb8f68d8', '4ce8w', '2022-08-02 18:35:36');
INSERT INTO `sys_captcha` VALUES ('a8dc602f-ab34-44cc-810b-03b806d0f7d1', 'xgmfy', '2022-08-02 11:10:10');
INSERT INTO `sys_captcha` VALUES ('aad2533a-b941-40a7-8f44-79ba7c3037a0', '7d7nb', '2022-08-08 12:03:22');
INSERT INTO `sys_captcha` VALUES ('ae7e68ae-509a-450d-8307-5890beebc500', '3fbdx', '2022-08-08 12:48:45');
INSERT INTO `sys_captcha` VALUES ('b018f3fa-1dae-48c9-8eef-10a68a69476e', 'cefbn', '2022-08-06 18:01:48');
INSERT INTO `sys_captcha` VALUES ('b067a0bc-a334-4603-8c46-0eb48ad7521c', 'cdm5a', '2022-08-02 22:15:01');
INSERT INTO `sys_captcha` VALUES ('b0f6358b-3111-4a3b-87fc-606221b5efa0', '5xp6w', '2022-08-09 22:18:49');
INSERT INTO `sys_captcha` VALUES ('b31c5d6c-ce7c-4c32-8c82-41ae9f35b242', '6amc5', '2022-08-04 22:23:42');
INSERT INTO `sys_captcha` VALUES ('b3d3394b-5dc1-4a5b-84de-20dd82c465ac', 'y3y3g', '2022-08-09 15:09:30');
INSERT INTO `sys_captcha` VALUES ('b41bf304-5826-4f2f-8060-5ac9e343ffc0', 'cwy73', '2022-08-10 13:20:06');
INSERT INTO `sys_captcha` VALUES ('b4fa696a-417d-47e3-841d-12372f7802cd', 'fp6wp', '2022-08-04 20:36:31');
INSERT INTO `sys_captcha` VALUES ('b62972ea-90e3-4248-82fe-f9eb11ebee4b', 'yf6y8', '2022-08-11 23:35:54');
INSERT INTO `sys_captcha` VALUES ('b7b6f57e-9afc-4c07-8f7d-9f57c7f9af4f', 'yea4n', '2022-08-09 21:16:48');
INSERT INTO `sys_captcha` VALUES ('b864342b-dfa1-43a5-8859-d9bf3113392a', 'cgyaa', '2022-08-02 18:27:54');
INSERT INTO `sys_captcha` VALUES ('bc496366-ba14-41bc-89ac-8e80a0b53843', 'n76d5', '2022-08-10 14:16:21');
INSERT INTO `sys_captcha` VALUES ('bd32d024-6c8e-44a3-8ef2-0b987a10cf34', '6fm8p', '2022-08-02 10:21:55');
INSERT INTO `sys_captcha` VALUES ('bd7a7310-5d66-437c-8684-4003b9451cbe', 'ndw7c', '2022-08-09 17:44:29');
INSERT INTO `sys_captcha` VALUES ('be0a0fff-6203-4ea4-89ed-e0cfe73d345c', 'pgp63', '2022-08-02 11:09:31');
INSERT INTO `sys_captcha` VALUES ('be18bb46-d7fa-496b-8dcf-32eb311e06bf', 'gwb6f', '2022-08-07 11:14:30');
INSERT INTO `sys_captcha` VALUES ('be9ba815-8a22-4d7c-8105-b8ff1d8bd5fb', 'f87g7', '2022-08-09 16:32:51');
INSERT INTO `sys_captcha` VALUES ('c015c57c-57b5-493e-8055-92fbb95c5e08', 'm37en', '2022-08-09 21:03:57');
INSERT INTO `sys_captcha` VALUES ('c3c817a4-60f3-48c8-81b6-8211db27f3fb', 'gy3cm', '2022-08-09 23:25:40');
INSERT INTO `sys_captcha` VALUES ('c5161b4f-d79a-4fbf-82e2-b73b250620b9', 'd7dxa', '2022-08-09 23:13:48');
INSERT INTO `sys_captcha` VALUES ('c5ee3522-b7b5-44d6-80dc-6345d3eba2a2', '5nygx', '2022-08-09 17:47:35');
INSERT INTO `sys_captcha` VALUES ('c6064733-b31f-4c41-8b65-018dc331e980', 'ap6xf', '2022-08-03 00:10:32');
INSERT INTO `sys_captcha` VALUES ('c7f5a18b-4da4-4a51-8c01-31ecccb83ea5', 'dbm67', '2022-08-08 12:25:02');
INSERT INTO `sys_captcha` VALUES ('ca95f778-c9ea-48a6-8b6f-acd6ff87bab7', '4xnnw', '2022-08-11 23:54:50');
INSERT INTO `sys_captcha` VALUES ('cb33e682-df10-44a0-8526-11fce2dab1d8', '3xe8d', '2022-08-02 18:22:03');
INSERT INTO `sys_captcha` VALUES ('cefe3ab5-8e77-436d-815f-318a6e5e642d', '23w7y', '2022-08-03 10:14:45');
INSERT INTO `sys_captcha` VALUES ('cf3e04dd-81c0-4dfb-8dcc-2baf4eae1996', '2fem8', '2022-08-10 00:13:12');
INSERT INTO `sys_captcha` VALUES ('d01c45cc-65c3-4642-80e4-f58fc96f3f25', 'g7anc', '2022-08-10 00:11:30');
INSERT INTO `sys_captcha` VALUES ('d10d5c28-eb82-4797-8deb-fbdf0a16433c', 'cd3xc', '2022-08-02 09:55:30');
INSERT INTO `sys_captcha` VALUES ('d5a39646-cf4c-45ad-844a-1bae36ccf0b8', 'pppg8', '2022-08-10 13:46:16');
INSERT INTO `sys_captcha` VALUES ('d7c547e6-aebe-4553-80ee-d75695b6cc0a', 'n8ypc', '2022-08-09 18:35:55');
INSERT INTO `sys_captcha` VALUES ('d8541106-df8b-42f5-816a-d311d3ce15ad', 'ag2bc', '2022-08-02 09:39:51');
INSERT INTO `sys_captcha` VALUES ('d8b319ae-8fad-420a-8743-029d65dcaf35', 'fa4gy', '2022-08-11 23:47:29');
INSERT INTO `sys_captcha` VALUES ('da5f6980-412f-4b5f-827a-c350a8532812', 'n22g7', '2022-08-06 16:15:20');
INSERT INTO `sys_captcha` VALUES ('dae7b671-6ed1-46af-87e9-dff376c1de42', '8e4xp', '2022-08-08 17:41:38');
INSERT INTO `sys_captcha` VALUES ('db1a03f1-3437-4518-8f54-6c9c786750d8', 'yxdn8', '2022-08-02 19:29:05');
INSERT INTO `sys_captcha` VALUES ('dc088478-decf-4bf4-887a-c1021a9ab4fa', 'apnx3', '2022-08-08 12:39:57');
INSERT INTO `sys_captcha` VALUES ('dc252f27-a8c7-4364-8c81-0f307ebc833f', 'y2n48', '2022-08-10 00:02:53');
INSERT INTO `sys_captcha` VALUES ('dc991d0a-dce0-4997-83fa-b6f63f5addb8', '26p7g', '2022-08-10 15:16:07');
INSERT INTO `sys_captcha` VALUES ('dfa1ae90-8f83-4800-86d0-622f40b33e62', '3n367', '2022-08-09 23:47:40');
INSERT INTO `sys_captcha` VALUES ('dfc900dd-d5bf-4362-8ec5-5b9f661da716', '6yanp', '2022-08-09 22:18:28');
INSERT INTO `sys_captcha` VALUES ('dff59c3f-4af1-482c-864c-d7cafcfd0b6b', 'ndy6n', '2022-07-20 16:58:47');
INSERT INTO `sys_captcha` VALUES ('e06257c4-ac1e-4ce5-8947-edd7d9c6891c', 'b6ba6', '2022-08-10 13:44:54');
INSERT INTO `sys_captcha` VALUES ('e1529c39-c547-4f04-8191-b994349af92b', '8cfan', '2022-08-06 21:54:36');
INSERT INTO `sys_captcha` VALUES ('e2fcb562-270b-48cd-8fdf-19dc5971e4e0', 'w32nf', '2022-08-09 15:51:08');
INSERT INTO `sys_captcha` VALUES ('e3c66f45-41b1-4747-8f56-89e18c8a6645', 'n3np7', '2022-08-09 14:53:32');
INSERT INTO `sys_captcha` VALUES ('e51a2725-43d7-4414-8b4c-e36ada4487d2', '485an', '2022-08-11 23:31:59');
INSERT INTO `sys_captcha` VALUES ('e6966bb5-a071-4896-89ef-ae8e5c30bcc9', '8b2nn', '2022-08-10 11:07:14');
INSERT INTO `sys_captcha` VALUES ('e6d420fc-db22-474b-8298-3d1bcffcaa89', 'wafpm', '2022-08-10 14:33:47');
INSERT INTO `sys_captcha` VALUES ('e9d3e307-5a73-4146-8300-27ce090fe7d2', 'g8c74', '2022-08-09 23:17:34');
INSERT INTO `sys_captcha` VALUES ('ea0e40e7-9508-40eb-8975-166516144d23', 'db4n6', '2022-08-11 23:40:24');
INSERT INTO `sys_captcha` VALUES ('ebcc1378-a4c3-47eb-8d75-ef1655656cb0', 'eg6w3', '2022-08-07 10:45:37');
INSERT INTO `sys_captcha` VALUES ('ec0a44b0-77ad-4bcd-8036-d35d791377db', 'dwa28', '2022-08-10 10:58:26');
INSERT INTO `sys_captcha` VALUES ('ec587649-dd1c-4fef-8fa0-28537cc6baf9', 'c3c6f', '2022-08-09 23:28:56');
INSERT INTO `sys_captcha` VALUES ('ec688975-c294-42d6-81de-d4921a3c675c', 'a264e', '2022-08-11 21:29:01');
INSERT INTO `sys_captcha` VALUES ('ee4dc5a9-2fac-4ab6-8eec-531be83957ce', 'gnwdb', '2022-08-02 22:15:04');
INSERT INTO `sys_captcha` VALUES ('ef0afc36-2aab-4d2d-80e4-eed0b85e9772', 'n6m5w', '2022-08-02 11:47:37');
INSERT INTO `sys_captcha` VALUES ('f0be1c02-4f7c-41a9-8d58-bb8f88fc4ddf', 'wcgn4', '2022-08-03 00:12:47');
INSERT INTO `sys_captcha` VALUES ('f0e1dcd7-029c-4fb5-8121-bea0729a9f68', 'ffcgb', '2022-08-09 17:46:13');
INSERT INTO `sys_captcha` VALUES ('f49888a3-af59-4bbc-8d71-1e2be4ae4099', 'yd454', '2022-08-09 23:49:16');
INSERT INTO `sys_captcha` VALUES ('f58d2f43-a51b-47d2-8fef-eb528ca2730e', '67838', '2022-08-22 12:28:06');
INSERT INTO `sys_captcha` VALUES ('f5aaa3a6-f786-489d-86cf-c74b2a3a154e', 'ng76w', '2022-08-09 22:14:13');
INSERT INTO `sys_captcha` VALUES ('f5da31b6-5ecc-4c1e-8c8a-c393cc494d27', '4de47', '2022-08-09 18:37:19');
INSERT INTO `sys_captcha` VALUES ('f6a6b623-e76f-4b21-8889-4fed0e593c39', 'bb5xx', '2022-08-09 15:10:52');
INSERT INTO `sys_captcha` VALUES ('f7be4564-486a-4fbc-88c8-1209fbf73cf8', 'wdn63', '2022-08-09 22:57:58');
INSERT INTO `sys_captcha` VALUES ('f90a6009-026d-47f3-8714-892241fbf1ff', 'aawb2', '2022-08-09 16:34:53');
INSERT INTO `sys_captcha` VALUES ('fa0d1f4d-4e3e-4cda-8f69-e02dae4719a6', '3yypn', '2022-08-04 20:30:44');
INSERT INTO `sys_captcha` VALUES ('fb2058ab-ee19-4300-8346-e94b06ff35a9', '5d8xn', '2022-08-09 21:02:47');
INSERT INTO `sys_captcha` VALUES ('fbbc2ebf-705d-4705-817a-6f6964ac6d73', '8bd6g', '2022-08-03 22:37:03');
INSERT INTO `sys_captcha` VALUES ('ff24892e-e951-425b-8635-b32f99fd50b6', 'gdx8e', '2022-08-10 11:01:14');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'value',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `param_key`(`param_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统配置信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, 'CLOUD_STORAGE_CONFIG_KEY', '{\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunEndPoint\":\"\",\"aliyunPrefix\":\"\",\"qcloudBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuBucketName\":\"ios-app\",\"qiniuDomain\":\"http://7xqbwh.dl1.z0.glb.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV\",\"type\":1}', 0, '云存储配置信息');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求参数',
  `time` bigint NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (1, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":31,\"parentId\":0,\"name\":\"商品系统\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"zonghe\",\"orderNum\":0,\"list\":[]}]', 6, '0:0:0:0:0:0:0:1', '2022-07-20 17:03:42');
INSERT INTO `sys_log` VALUES (2, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":32,\"parentId\":31,\"name\":\"分类维护\",\"url\":\"product/category\",\"perms\":\"\",\"type\":1,\"icon\":\"menu\",\"orderNum\":0,\"list\":[]}]', 4, '0:0:0:0:0:0:0:1', '2022-07-20 17:05:26');
INSERT INTO `sys_log` VALUES (3, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":33,\"parentId\":31,\"name\":\"品牌管理\",\"url\":\"product/brand\",\"perms\":\"\",\"type\":1,\"icon\":\"mudedi\",\"orderNum\":0,\"list\":[]}]', 8, '0:0:0:0:0:0:0:1', '2022-07-30 20:20:48');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int NULL DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `order_num` int NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 76 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', NULL, NULL, 0, 'system', 0);
INSERT INTO `sys_menu` VALUES (2, 1, '管理员列表', 'sys/user', NULL, 1, 'admin', 1);
INSERT INTO `sys_menu` VALUES (3, 1, '角色管理', 'sys/role', NULL, 1, 'role', 2);
INSERT INTO `sys_menu` VALUES (4, 1, '菜单管理', 'sys/menu', NULL, 1, 'menu', 3);
INSERT INTO `sys_menu` VALUES (5, 1, 'SQL监控', 'http://localhost:8080/renren-fast/druid/sql.html', NULL, 1, 'sql', 4);
INSERT INTO `sys_menu` VALUES (6, 1, '定时任务', 'job/schedule', NULL, 1, 'job', 5);
INSERT INTO `sys_menu` VALUES (7, 6, '查看', NULL, 'sys:schedule:list,sys:schedule:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (8, 6, '新增', NULL, 'sys:schedule:save', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (9, 6, '修改', NULL, 'sys:schedule:update', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (10, 6, '删除', NULL, 'sys:schedule:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (11, 6, '暂停', NULL, 'sys:schedule:pause', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (12, 6, '恢复', NULL, 'sys:schedule:resume', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (13, 6, '立即执行', NULL, 'sys:schedule:run', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (14, 6, '日志列表', NULL, 'sys:schedule:log', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (15, 2, '查看', NULL, 'sys:user:list,sys:user:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (16, 2, '新增', NULL, 'sys:user:save,sys:role:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (17, 2, '修改', NULL, 'sys:user:update,sys:role:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (18, 2, '删除', NULL, 'sys:user:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (19, 3, '查看', NULL, 'sys:role:list,sys:role:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (20, 3, '新增', NULL, 'sys:role:save,sys:menu:list', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (21, 3, '修改', NULL, 'sys:role:update,sys:menu:list', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (22, 3, '删除', NULL, 'sys:role:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (23, 4, '查看', NULL, 'sys:menu:list,sys:menu:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (24, 4, '新增', NULL, 'sys:menu:save,sys:menu:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (25, 4, '修改', NULL, 'sys:menu:update,sys:menu:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (26, 4, '删除', NULL, 'sys:menu:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (27, 1, '参数管理', 'sys/config', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', 1, 'config', 6);
INSERT INTO `sys_menu` VALUES (29, 1, '系统日志', 'sys/log', 'sys:log:list', 1, 'log', 7);
INSERT INTO `sys_menu` VALUES (30, 1, '文件上传', 'oss/oss', 'sys:oss:all', 1, 'oss', 6);
INSERT INTO `sys_menu` VALUES (31, 0, '商品系统', '', '', 0, 'editor', 0);
INSERT INTO `sys_menu` VALUES (32, 31, '分类维护', 'product/category', '', 1, 'menu', 0);
INSERT INTO `sys_menu` VALUES (34, 31, '品牌管理', 'product/brand', '', 1, 'editor', 0);
INSERT INTO `sys_menu` VALUES (37, 31, '平台属性', '', '', 0, 'system', 0);
INSERT INTO `sys_menu` VALUES (38, 37, '属性分组', 'product/attrgroup', '', 1, 'tubiao', 0);
INSERT INTO `sys_menu` VALUES (39, 37, '规格参数', 'product/baseattr', '', 1, 'log', 0);
INSERT INTO `sys_menu` VALUES (40, 37, '销售属性', 'product/saleattr', '', 1, 'zonghe', 0);
INSERT INTO `sys_menu` VALUES (41, 31, '商品维护', 'product/spu', '', 0, 'zonghe', 0);
INSERT INTO `sys_menu` VALUES (42, 0, '优惠营销', '', '', 0, 'mudedi', 0);
INSERT INTO `sys_menu` VALUES (43, 0, '库存系统', '', '', 0, 'shouye', 0);
INSERT INTO `sys_menu` VALUES (44, 0, '订单系统', '', '', 0, 'config', 0);
INSERT INTO `sys_menu` VALUES (45, 0, '用户系统', '', '', 0, 'admin', 0);
INSERT INTO `sys_menu` VALUES (46, 0, '内容管理', '', '', 0, 'sousuo', 0);
INSERT INTO `sys_menu` VALUES (47, 42, '优惠券管理', 'coupon/coupon', '', 1, 'zhedie', 0);
INSERT INTO `sys_menu` VALUES (48, 42, '发放记录', 'coupon/history', '', 1, 'sql', 0);
INSERT INTO `sys_menu` VALUES (49, 42, '专题活动', 'coupon/subject', '', 1, 'tixing', 0);
INSERT INTO `sys_menu` VALUES (50, 42, '秒杀活动', 'coupon/seckill', '', 1, 'daohang', 0);
INSERT INTO `sys_menu` VALUES (51, 42, '积分维护', 'coupon/bounds', '', 1, 'geren', 0);
INSERT INTO `sys_menu` VALUES (52, 42, '满减折扣', 'coupon/full', '', 1, 'shoucang', 0);
INSERT INTO `sys_menu` VALUES (53, 43, '仓库维护', 'ware/wareinfo', '', 1, 'shouye', 0);
INSERT INTO `sys_menu` VALUES (54, 43, '库存工作单', 'ware/task', '', 1, 'log', 0);
INSERT INTO `sys_menu` VALUES (55, 43, '商品库存', 'ware/sku', '', 1, 'jiesuo', 0);
INSERT INTO `sys_menu` VALUES (56, 44, '订单查询', 'order/order', '', 1, 'zhedie', 0);
INSERT INTO `sys_menu` VALUES (57, 44, '退货单处理', 'order/return', '', 1, 'shanchu', 0);
INSERT INTO `sys_menu` VALUES (58, 44, '等级规则', 'order/settings', '', 1, 'system', 0);
INSERT INTO `sys_menu` VALUES (59, 44, '支付流水查询', 'order/payment', '', 1, 'job', 0);
INSERT INTO `sys_menu` VALUES (60, 44, '退款流水查询', 'order/refund', '', 1, 'mudedi', 0);
INSERT INTO `sys_menu` VALUES (61, 45, '会员列表', 'member/member', '', 1, 'geren', 0);
INSERT INTO `sys_menu` VALUES (62, 45, '会员等级', 'member/level', '', 1, 'tubiao', 0);
INSERT INTO `sys_menu` VALUES (63, 45, '积分变化', 'member/growth', '', 1, 'bianji', 0);
INSERT INTO `sys_menu` VALUES (64, 45, '统计信息', 'member/statistics', '', 1, 'sql', 0);
INSERT INTO `sys_menu` VALUES (65, 46, '首页推荐', 'content/index', '', 1, 'shouye', 0);
INSERT INTO `sys_menu` VALUES (66, 46, '分类热门', 'content/category', '', 1, 'zhedie', 0);
INSERT INTO `sys_menu` VALUES (67, 46, '评论管理', 'content/comments', '', 1, 'pinglun', 0);
INSERT INTO `sys_menu` VALUES (68, 41, 'spu管理', 'product/spu', '', 1, 'config', 0);
INSERT INTO `sys_menu` VALUES (69, 41, '发布商品', 'product/spuadd', '', 1, 'bianji', 0);
INSERT INTO `sys_menu` VALUES (70, 43, '采购单维护', '', '', 0, 'tubiao', 0);
INSERT INTO `sys_menu` VALUES (71, 70, '采购需求', 'ware/purchaseitem', '', 1, 'editor', 0);
INSERT INTO `sys_menu` VALUES (72, 70, '采购单', 'ware/purchase', '', 1, 'menu', 0);
INSERT INTO `sys_menu` VALUES (73, 41, '商品管理', 'product/manager', '', 1, 'zonghe', 0);
INSERT INTO `sys_menu` VALUES (74, 42, '会员价格', 'coupon/memberprice', '', 1, 'admin', 0);
INSERT INTO `sys_menu` VALUES (75, 42, '每日秒杀', 'coupon/seckillsession', '', 1, 'job', 0);

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文件上传' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oss
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint NULL DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色与菜单对应关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '盐',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', 'root@renren.io', '13612345678', 1, 1, '2016-11-11 11:11:11');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户与角色对应关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token`  (
  `user_id` bigint NOT NULL,
  `token` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'token',
  `expire_time` datetime NULL DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `token`(`token`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统用户Token' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------
INSERT INTO `sys_user_token` VALUES (1, '3076aba250c70c89f184166fa99bcec1', '2022-10-12 22:38:19', '2022-10-12 10:38:19');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, 'mark', '13612345678', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '2017-03-23 22:37:41');

SET FOREIGN_KEY_CHECKS = 1;
