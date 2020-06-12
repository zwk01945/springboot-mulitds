/*
 Navicat Premium Data Transfer

 Source Server         : 本机mysql
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : icp

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 12/06/2020 13:24:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for icp_code
-- ----------------------------
DROP TABLE IF EXISTS `icp_code`;
CREATE TABLE `icp_code`  (
  `id` int(0) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of icp_code
-- ----------------------------
INSERT INTO `icp_code` VALUES (3, 'aaa');
INSERT INTO `icp_code` VALUES (4, 'asdad');

SET FOREIGN_KEY_CHECKS = 1;
