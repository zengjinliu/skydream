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

 Date: 08/09/2020 17:25:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_captcha
-- ----------------------------
DROP TABLE IF EXISTS `sys_captcha`;
CREATE TABLE `sys_captcha`  (
  `uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'uuid',
  `code` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '验证码',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统验证码' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_captcha
-- ----------------------------
INSERT INTO `sys_captcha` VALUES ('18e63dd5f2274d9182c33c6dfd22c78a', 'ne4g', '2020-09-08 13:54:38');
INSERT INTO `sys_captcha` VALUES ('715e08a0a7c147228a399aefdad81c3d', '33nc', '2020-09-08 13:54:03');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `parent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', '', NULL, '0', 'el-icon-setting', 0);
INSERT INTO `sys_menu` VALUES ('10', '6', '删除', NULL, 'sys:schedule:delete', '2', NULL, 0);
INSERT INTO `sys_menu` VALUES ('11', '6', '暂停', NULL, 'sys:schedule:pause', '2', NULL, 0);
INSERT INTO `sys_menu` VALUES ('12', '6', '恢复', NULL, 'sys:schedule:resume', '2', NULL, 0);
INSERT INTO `sys_menu` VALUES ('13', '6', '立即执行', NULL, 'sys:schedule:run', '2', NULL, 0);
INSERT INTO `sys_menu` VALUES ('14', '6', '日志列表', NULL, 'sys:schedule:log', '2', NULL, 0);
INSERT INTO `sys_menu` VALUES ('15', '2', '查看', NULL, 'sys:user:list,sys:user:info', '2', NULL, 0);
INSERT INTO `sys_menu` VALUES ('16', '2', '新增', NULL, 'sys:user:save,sys:role:select', '2', NULL, 0);
INSERT INTO `sys_menu` VALUES ('17', '2', '修改', NULL, 'sys:user:update,sys:role:select', '2', NULL, 0);
INSERT INTO `sys_menu` VALUES ('18', '2', '删除', NULL, 'sys:user:delete', '2', NULL, 0);
INSERT INTO `sys_menu` VALUES ('19', '3', '查看', NULL, 'sys:role:list,sys:role:info', '2', NULL, 0);
INSERT INTO `sys_menu` VALUES ('2', '1', '管理员列表', 'sys/user', NULL, '1', '', 1);
INSERT INTO `sys_menu` VALUES ('20', '3', '新增', NULL, 'sys:role:save,sys:menu:list', '2', NULL, 0);
INSERT INTO `sys_menu` VALUES ('21', '3', '修改', NULL, 'sys:role:update,sys:menu:list', '2', NULL, 0);
INSERT INTO `sys_menu` VALUES ('22', '3', '删除', NULL, 'sys:role:delete', '2', NULL, 0);
INSERT INTO `sys_menu` VALUES ('23', '4', '查看', NULL, 'sys:menu:list,sys:menu:info', '2', NULL, 0);
INSERT INTO `sys_menu` VALUES ('24', '4', '新增', NULL, 'sys:menu:save,sys:menu:select', '2', NULL, 0);
INSERT INTO `sys_menu` VALUES ('25', '4', '修改', NULL, 'sys:menu:update,sys:menu:select', '2', NULL, 0);
INSERT INTO `sys_menu` VALUES ('26', '4', '删除', NULL, 'sys:menu:delete', '2', NULL, 0);
INSERT INTO `sys_menu` VALUES ('27', '1', '参数管理', 'sys/config', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', '1', 'config', 6);
INSERT INTO `sys_menu` VALUES ('29', '1', '系统日志', 'sys/log', 'sys:log:list', '1', 'log', 7);
INSERT INTO `sys_menu` VALUES ('3', '1', '角色管理', 'sys/role', NULL, '1', 'role', 2);
INSERT INTO `sys_menu` VALUES ('30', '1', '文件上传', 'oss/oss', 'sys:oss:all', '1', 'oss', 6);
INSERT INTO `sys_menu` VALUES ('4', '1', '菜单管理', 'sys/menu', NULL, '1', 'menu', 3);
INSERT INTO `sys_menu` VALUES ('5', '1', 'SQL监控', 'http://localhost:8080/maxvision-fast/druid/sql.html', NULL, '1', 'sql', 4);
INSERT INTO `sys_menu` VALUES ('6', '1', '定时任务', 'job/schedule', NULL, '1', 'job', 5);
INSERT INTO `sys_menu` VALUES ('7', '6', '查看', NULL, 'sys:schedule:list,sys:schedule:info', '2', NULL, 0);
INSERT INTO `sys_menu` VALUES ('8', '6', '新增', NULL, 'sys:schedule:save', '2', NULL, 0);
INSERT INTO `sys_menu` VALUES ('9', '6', '修改', NULL, 'sys:schedule:update', '2', NULL, 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色与菜单对应关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '盐',
  `phone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', '18379254458', '1', '2020-09-07 14:04:00', NULL);

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

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------
INSERT INTO `sys_user_token` VALUES ('1', 'a3169b11e230ca13b9f40e0a9204ca8a', '2020-09-09 01:50:20', '2020-09-08 13:50:20');

SET FOREIGN_KEY_CHECKS = 1;
