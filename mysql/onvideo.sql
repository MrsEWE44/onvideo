/*
 Navicat Premium Data Transfer

 Source Server         : dockervm
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Host           : 192.168.136.129:3307
 Source Schema         : ovideo

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 04/03/2022 17:29:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ov_comment
-- ----------------------------
DROP TABLE IF EXISTS `ov_comment`;
CREATE TABLE `ov_comment`  (
  `cdate` datetime(0) NOT NULL COMMENT '评论发布时间',
  `uid` int(11) NOT NULL COMMENT '发布者id',
  `msg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论内容',
  `vid` int(11) NOT NULL COMMENT '评论的视频id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ov_menu
-- ----------------------------
DROP TABLE IF EXISTS `ov_menu`;
CREATE TABLE `ov_menu`  (
  `mid` int(11) NOT NULL COMMENT '菜单id',
  `mtitle` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜刀名称',
  `fid` int(11) NOT NULL COMMENT '菜单上级id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ov_menu
-- ----------------------------
INSERT INTO `ov_menu` VALUES (1, '番剧', 0);
INSERT INTO `ov_menu` VALUES (0, '连载', 1);
INSERT INTO `ov_menu` VALUES (1, '完结', 1);
INSERT INTO `ov_menu` VALUES (2, '科技', 0);
INSERT INTO `ov_menu` VALUES (1, '知识', 2);
INSERT INTO `ov_menu` VALUES (2, '科普', 2);
INSERT INTO `ov_menu` VALUES (3, '计算机技术', 2);
INSERT INTO `ov_menu` VALUES (4, '软件应用', 2);
INSERT INTO `ov_menu` VALUES (5, '极客diy', 2);
INSERT INTO `ov_menu` VALUES (3, '数码', 0);
INSERT INTO `ov_menu` VALUES (2, '手机', 3);
INSERT INTO `ov_menu` VALUES (3, '平板', 3);
INSERT INTO `ov_menu` VALUES (4, '相机', 3);
INSERT INTO `ov_menu` VALUES (5, '刷机', 3);
INSERT INTO `ov_menu` VALUES (6, '新机', 3);
INSERT INTO `ov_menu` VALUES (4, '电视剧', 0);
INSERT INTO `ov_menu` VALUES (3, '搞笑', 4);
INSERT INTO `ov_menu` VALUES (4, '战争', 4);
INSERT INTO `ov_menu` VALUES (5, '青春', 4);
INSERT INTO `ov_menu` VALUES (6, '经典', 4);
INSERT INTO `ov_menu` VALUES (7, '古装', 4);
INSERT INTO `ov_menu` VALUES (8, '奇幻', 4);
INSERT INTO `ov_menu` VALUES (5, '音乐', 0);
INSERT INTO `ov_menu` VALUES (4, '原创', 5);
INSERT INTO `ov_menu` VALUES (5, '翻唱', 5);
INSERT INTO `ov_menu` VALUES (6, 'mv', 5);
INSERT INTO `ov_menu` VALUES (7, '现场', 5);
INSERT INTO `ov_menu` VALUES (8, '说唱', 5);
INSERT INTO `ov_menu` VALUES (9, '电音', 5);
INSERT INTO `ov_menu` VALUES (6, '游戏', 0);
INSERT INTO `ov_menu` VALUES (5, '单机', 6);
INSERT INTO `ov_menu` VALUES (6, '端游', 6);
INSERT INTO `ov_menu` VALUES (7, '手游', 6);
INSERT INTO `ov_menu` VALUES (8, '电子竞技', 6);
INSERT INTO `ov_menu` VALUES (7, '生活', 0);
INSERT INTO `ov_menu` VALUES (6, '搞笑', 7);
INSERT INTO `ov_menu` VALUES (7, '日常', 7);
INSERT INTO `ov_menu` VALUES (8, '家居', 7);

-- ----------------------------
-- Table structure for ov_role
-- ----------------------------
DROP TABLE IF EXISTS `ov_role`;
CREATE TABLE `ov_role`  (
  `rid` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `rname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`rid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ov_role
-- ----------------------------
INSERT INTO `ov_role` VALUES (1, 'ROOT');
INSERT INTO `ov_role` VALUES (2, 'ADMIN');
INSERT INTO `ov_role` VALUES (3, 'USER');
INSERT INTO `ov_role` VALUES (4, 'TMP');

-- ----------------------------
-- Table structure for ov_user
-- ----------------------------
DROP TABLE IF EXISTS `ov_user`;
CREATE TABLE `ov_user`  (
  `uid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `uname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `uicon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `upwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密码',
  `rid` int(11) NOT NULL COMMENT '用户角色id',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ov_user
-- ----------------------------
INSERT INTO `ov_user` VALUES (1, 'root', 'icon/uicon.jpg', '123', 1);
INSERT INTO `ov_user` VALUES (2, 'gf', 'icon/ffdk.png', '123', 3);

-- ----------------------------
-- Table structure for ov_video
-- ----------------------------
DROP TABLE IF EXISTS `ov_video`;
CREATE TABLE `ov_video`  (
  `vid` int(11) NOT NULL AUTO_INCREMENT COMMENT '视频id',
  `vtitle` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '视频标题',
  `vicon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '视频封面',
  `vdate` datetime(0) NOT NULL COMMENT '视频发布时间',
  `uid` int(11) NOT NULL COMMENT '用户发布id',
  `vpath` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '视频链接/存放位置',
  `vmid` int(11) NOT NULL COMMENT '视频小分区id',
  `vfid` int(11) NOT NULL COMMENT '视频大分区id',
  `vmsg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '视频介绍',
  PRIMARY KEY (`vid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
