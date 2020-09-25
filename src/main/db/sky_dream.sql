/*
 Navicat Premium Data Transfer

 Source Server         : native
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : sky_dream

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 25/09/2020 18:06:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_captcha
-- ----------------------------
DROP TABLE IF EXISTS `sys_captcha`;
CREATE TABLE `sys_captcha`  (
  `uuid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
  `code` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '验证码',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统验证码' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('0dc0b13f49c949a0bd3ebe2d1d8e2483', '390760ee60444e5c828553fb90cc7f24', '删除', 'user/del', 'user:del', '2', '', 3);
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', '', NULL, '0', 'el-icon-setting', 0);
INSERT INTO `sys_menu` VALUES ('203ff1439c854fbe9375db89e8eedb2b', '390760ee60444e5c828553fb90cc7f24', '修改', 'user/update', 'user:edit', '2', '', 2);
INSERT INTO `sys_menu` VALUES ('2962fcf6af10482ca2fda5f61b1abc44', 'f0d3342aaf554528995cf222f966924a', '新增', 'menu/add', 'menu:add', '2', '', 1);
INSERT INTO `sys_menu` VALUES ('390760ee60444e5c828553fb90cc7f24', '1', '用户管理', 'user', '', '1', 'el-icon-user', 1);
INSERT INTO `sys_menu` VALUES ('531b4ec25d674483972ed2d77f02b301', '390760ee60444e5c828553fb90cc7f24', '新增', 'user/add', 'user:add', '2', '', 1);
INSERT INTO `sys_menu` VALUES ('5d662561c86f48be8ea8d182998f8360', '1', '角色管理', 'role', '', '1', 'el-icon-help', 2);
INSERT INTO `sys_menu` VALUES ('76068386386741f7926ba9dd0ecb1814', 'f0d3342aaf554528995cf222f966924a', '修改', 'menu/update', 'menu:edit', '2', '', 3);
INSERT INTO `sys_menu` VALUES ('77f6c6a9494e4a48a477ee9822f4a24a', '5d662561c86f48be8ea8d182998f8360', '修改', 'role/update', 'role:edit', '2', '', 3);
INSERT INTO `sys_menu` VALUES ('780859efd2c343d4bbc66792ec9920eb', '5d662561c86f48be8ea8d182998f8360', '删除', 'role/del', 'role:del', '2', '', 2);
INSERT INTO `sys_menu` VALUES ('afe28b984cdc41e3ab5eb6a793b0ac6e', 'e3d04d2f546646599d069411de62c51f', 'echart', 'echart', '', '0', 'el-icon-s-data', 1);
INSERT INTO `sys_menu` VALUES ('b5778bd2bc9d414c81d4ef71d4550ccb', '5d662561c86f48be8ea8d182998f8360', '新增', 'role/add', 'role:add', '2', '', 1);
INSERT INTO `sys_menu` VALUES ('daf58c5ea0784a328b18c2238f8515fc', 'f0d3342aaf554528995cf222f966924a', '删除', 'menu/del', 'menu:del', '2', '', 2);
INSERT INTO `sys_menu` VALUES ('e3d04d2f546646599d069411de62c51f', '0', 'demo', '', '', '0', 'el-icon-document', 1);
INSERT INTO `sys_menu` VALUES ('f0d3342aaf554528995cf222f966924a', '1', '菜单管理', 'menu', '', '1', 'el-icon-document', 3);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'admin', 'admin', '1', '2020-09-15 16:00:11');
INSERT INTO `sys_role` VALUES ('a1d6c79ff20847cf8036d116952e2db2', 'test', 'test', '1', '2020-09-17 10:44:02');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色与菜单对应关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('32e894821e7b4f67a4585868379f27de', '1', '1');
INSERT INTO `sys_role_menu` VALUES ('8dc8347e961c4347b48a0b33fccb6f6a', 'a1d6c79ff20847cf8036d116952e2db2', '1');
INSERT INTO `sys_role_menu` VALUES ('8ffb7f711162496e8c792e3bb7c7a94c', '1', 'f0d3342aaf554528995cf222f966924a');
INSERT INTO `sys_role_menu` VALUES ('98362b13475a44278cb676968c7c179c', '1', '203ff1439c854fbe9375db89e8eedb2b');
INSERT INTO `sys_role_menu` VALUES ('aa5375fef1184722b21e17dfc6241863', 'a1d6c79ff20847cf8036d116952e2db2', '531b4ec25d674483972ed2d77f02b301');
INSERT INTO `sys_role_menu` VALUES ('b37509e2259e41f3988807a33353d7ae', 'a1d6c79ff20847cf8036d116952e2db2', '390760ee60444e5c828553fb90cc7f24');
INSERT INTO `sys_role_menu` VALUES ('b6ee4b92077d4bee9b128753814d5080', '1', '0dc0b13f49c949a0bd3ebe2d1d8e2483');
INSERT INTO `sys_role_menu` VALUES ('b835b973f5cb4c14a16abf7927c71443', '1', '390760ee60444e5c828553fb90cc7f24');
INSERT INTO `sys_role_menu` VALUES ('b94defe3d55f4ed08e14e2e727aa1e89', 'a1d6c79ff20847cf8036d116952e2db2', '5d662561c86f48be8ea8d182998f8360');
INSERT INTO `sys_role_menu` VALUES ('ce7f8c5977f74ffba2623efd8e6de30b', '1', '5d662561c86f48be8ea8d182998f8360');
INSERT INTO `sys_role_menu` VALUES ('d81bb316d3384062b17260a6ff6f53fd', '1', '531b4ec25d674483972ed2d77f02b301');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片',
  `salt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐',
  `phone` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `phone_index`(`phone`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', NULL, 'YzcmCZNvbXocrsz9dm8e', '18379254458', '1', '2020-09-07 14:04:00', NULL);
INSERT INTO `sys_user` VALUES ('7504bd6e61f94b018927938b0525da51', 'jayson', '37feb509a00c295a4d57cc83900fcd2887844d2b63557f6a75726969e16c37d3', NULL, 'vxOQyBFksw0spfKZwH2Y', '18770098554', '1', '2020-09-15 11:06:30', NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('9dfa9a9da495446a9a8d4b1316b7a764', '1', '1');
INSERT INTO `sys_user_role` VALUES ('9edef0d811d349b296b4f6b691de9f70', '7504bd6e61f94b018927938b0525da51', 'a1d6c79ff20847cf8036d116952e2db2');

-- ----------------------------
-- Table structure for sys_user_third
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_third`;
CREATE TABLE `sys_user_third`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `uid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `access_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '访问令牌',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '三方名字',
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '照片',
  `expires_in` bigint(255) NOT NULL COMMENT '过期时间s',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token`  (
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `token` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `expire_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
