/*
MySQL Data Transfer
Source Host: localhost
Source Database: redeempoint
Target Host: localhost
Target Database: redeempoint
Date: 2011/6/6 17:07:16
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for employee_info
-- ----------------------------
CREATE TABLE `employee_info` (
  `operater_id` varchar(32) NOT NULL,
  `operater_code` varchar(32) default NULL,
  `operater_name` varchar(32) default NULL,
  `password` varchar(32) default NULL,
  `tel_no` varchar(20) default NULL,
  `phon_no` varchar(20) default NULL,
  `privence` varchar(256) default NULL,
  `city` varchar(256) default NULL,
  `address` varchar(256) default NULL,
  `zip` varchar(10) default NULL,
  `email` varchar(200) default NULL,
  PRIMARY KEY  (`operater_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for menu_info
-- ----------------------------
CREATE TABLE `menu_info` (
  `menu_id` varchar(32) NOT NULL,
  `menu_name` varchar(100) default NULL,
  `page_path` varchar(256) default NULL,
  `menu_level` varchar(32) default NULL,
  `parent_menu` varchar(32) default NULL,
  `is_leave` varchar(2) default NULL,
  PRIMARY KEY  (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for menubutton
-- ----------------------------
CREATE TABLE `menubutton` (
  `button_id` varchar(32) NOT NULL COMMENT '按钮ID',
  `button_name` varchar(500) character set utf8 collate utf8_bin default NULL COMMENT '按钮名称',
  `button_text` varchar(500) default NULL,
  `menu_id` varchar(32) character set utf8 collate utf8_bin default NULL COMMENT '所属菜单ID',
  `button_url` varchar(500) default NULL COMMENT '按钮路径',
  PRIMARY KEY  (`button_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for right_info
-- ----------------------------
CREATE TABLE `right_info` (
  `right_id` varchar(32) NOT NULL COMMENT '权限ID',
  `right_name` varchar(256) default NULL COMMENT '权限名称',
  `role_id` varchar(32) character set utf8 collate utf8_bin default NULL COMMENT '角色ID',
  `menu_id` varchar(32) character set utf8 collate utf8_bin default NULL COMMENT '菜单ID',
  `button_id` varchar(32) character set utf8 collate utf8_bin default NULL COMMENT '按钮ID',
  PRIMARY KEY  (`right_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role_info
-- ----------------------------
CREATE TABLE `role_info` (
  `role_id` varchar(32) NOT NULL,
  `role_name` varchar(256) default NULL,
  `comment` varchar(500) default NULL,
  PRIMARY KEY  (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rolemenu
-- ----------------------------
CREATE TABLE `rolemenu` (
  `ID` varchar(32) character set utf8 collate utf8_bin NOT NULL,
  `roleId` varchar(32) character set utf8 collate utf8_bin default NULL,
  `menuId` varchar(32) character set utf8 collate utf8_bin default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for supplier_role
-- ----------------------------
CREATE TABLE `supplier_role` (
  `ID` varchar(32) NOT NULL,
  `operater_id` varchar(32) default NULL,
  `operate_type` varchar(32) default NULL,
  `role_id` varchar(32) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `employee_info` VALUES ('1', '1', 'swpigris81', '812877', null, null, null, null, null, null, null);
INSERT INTO `employee_info` VALUES ('2', '2', 'admin', 'admin', null, null, null, null, null, null, null);
INSERT INTO `employee_info` VALUES ('3', '3', 'user', 'user', null, null, null, null, null, null, null);
INSERT INTO `menu_info` VALUES ('1', '账本管理', '/balance/accountbalance.action?method=begin', null, '2', '1');
INSERT INTO `menu_info` VALUES ('2', '业务菜单', '/main.action', null, null, '0');
INSERT INTO `menu_info` VALUES ('3', '电子地图', '/map/map.action?method=begin', null, '2', '0');
INSERT INTO `menu_info` VALUES ('4', '测试1', '/test.action', null, '1', '1');
INSERT INTO `menu_info` VALUES ('5', '测试2', '/test1.action', null, '3', '1');
INSERT INTO `menu_info` VALUES ('6', '菜单管理', '/menu/beginMenuManage.action?method=begin', null, '3', '1');
INSERT INTO `menubutton` VALUES ('1', 'menu_list', '菜单列表', '6', '/menu/menuManage.action?method=menuList');
INSERT INTO `menubutton` VALUES ('2', 'menu_addMenu', '添加菜单', '6', '/menu/menuManage.action?method=addMenu');
INSERT INTO `menubutton` VALUES ('3', 'menu_editMenu', '修改菜单', '6', '/menu/menuManage.action?method=editMenu');
INSERT INTO `right_info` VALUES ('1', '测试权限1', '2', '6', '1');
INSERT INTO `right_info` VALUES ('2', '测试权限2', '2', '6', '2');
INSERT INTO `right_info` VALUES ('3', '测试权限3', '2', '6', '3');
INSERT INTO `role_info` VALUES ('1', '系统管理员', null);
INSERT INTO `role_info` VALUES ('2', '客户', null);
INSERT INTO `role_info` VALUES ('3', '普通用户', null);
INSERT INTO `rolemenu` VALUES ('1', '1', '1');
INSERT INTO `rolemenu` VALUES ('10', '2', '7');
INSERT INTO `rolemenu` VALUES ('11', '2', '6');
INSERT INTO `rolemenu` VALUES ('2', '1', '2');
INSERT INTO `rolemenu` VALUES ('3', '1', '3');
INSERT INTO `rolemenu` VALUES ('4', '2', '2');
INSERT INTO `rolemenu` VALUES ('5', '2', '3');
INSERT INTO `rolemenu` VALUES ('6', '3', '1');
INSERT INTO `rolemenu` VALUES ('7', '2', '4');
INSERT INTO `rolemenu` VALUES ('8', '2', '5');
INSERT INTO `rolemenu` VALUES ('9', '2', '1');
INSERT INTO `supplier_role` VALUES ('1', 'swpigris81', '1', '1');
INSERT INTO `supplier_role` VALUES ('2', 'admin', '2', '2');
INSERT INTO `supplier_role` VALUES ('3', 'user', null, '3');
