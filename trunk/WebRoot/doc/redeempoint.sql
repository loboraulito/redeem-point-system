/*
MySQL Data Transfer
Source Host: localhost
Source Database: redeempoint
Target Host: localhost
Target Database: redeempoint
Date: 2011/6/22 22:34:41
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
INSERT INTO `menu_info` VALUES ('1', '账本管理', '/balance/accountbalance.action?method=begin', null, '2', '0');
INSERT INTO `menu_info` VALUES ('10', '系统资源管理', '/main.action', null, '3', '0');
INSERT INTO `menu_info` VALUES ('11', '按钮管理', '/button/buttonManage.action?method=begin', null, '10', '0');
INSERT INTO `menu_info` VALUES ('2', '业务菜单', '/main.action', null, null, '0');
INSERT INTO `menu_info` VALUES ('3', '权限管理', '/map/map.action?method=begin', null, '2', '0');
INSERT INTO `menu_info` VALUES ('4', '测试1', '/test.action', null, '1', '1');
INSERT INTO `menu_info` VALUES ('5', '测试2', '/test1.action', null, '3', '1');
INSERT INTO `menu_info` VALUES ('6', '菜单管理', '/menu/beginMenuManage.action?method=begin', null, '10', '1');
INSERT INTO `menu_info` VALUES ('7', '用户管理', '/user/userManage.action?method=begin', null, '3', '1');
INSERT INTO `menu_info` VALUES ('8', '角色管理', '/role/roleManage.action?method=begin', null, '3', '1');
INSERT INTO `menu_info` VALUES ('9', '授权管理', '/right/authorize.action?method=begin', null, '3', '1');
INSERT INTO `menubutton` VALUES ('1', 'menu_list', '菜单列表', '6', '/menu/menuManage.action?method=menuList');
INSERT INTO `menubutton` VALUES ('10', 'authorize_menu', '授权树', '9', '/right/authorizeMenu.action?method=showAuthorizeMenu');
INSERT INTO `menubutton` VALUES ('11', 'authorize_user', '授权用户', '9', '/right/authorizeUser.action?method=showAuthorizeUser');
INSERT INTO `menubutton` VALUES ('2', 'menu_addMenu', '添加菜单', '6', '/menu/menuManage.action?method=addMenu');
INSERT INTO `menubutton` VALUES ('3', 'menu_editMenu', '修改菜单', '6', '/menu/menuManage.action?method=editMenu');
INSERT INTO `menubutton` VALUES ('4', 'user_list', '用户列表', '7', '/user/userList.action?method=userManageList');
INSERT INTO `menubutton` VALUES ('5', 'role_list', '角色列表', '8', '/role/roleList.action?method=roleManageList');
INSERT INTO `menubutton` VALUES ('6', 'authorizeForUser', '为用户授权', '9', '/right/authorizeUser.action?method=authorizeUser');
INSERT INTO `menubutton` VALUES ('7', 'authorize_rught_menu', '保存角色权限菜单', '9', '/right/authorizeRoleMenu.action?method=updateAuthorizeRoleMenu');
INSERT INTO `menubutton` VALUES ('8', 'authorize_list', '授权列表', '9', '/right/authorizeList.action?method=authorizeList');
INSERT INTO `menubutton` VALUES ('9', 'authorize_role', '授权角色列表', '9', '/right/authorizeRole.action?method=showAuthorizeRole');
INSERT INTO `right_info` VALUES ('402880e430b7ab2c0130b7acbcd6000c', null, '1', null, '1');
INSERT INTO `right_info` VALUES ('402880e430b7ab2c0130b7acbcd6000d', null, '1', null, '2');
INSERT INTO `right_info` VALUES ('402880e430b7ab2c0130b7acbcd6000e', null, '1', null, '3');
INSERT INTO `right_info` VALUES ('402880e430b7ab2c0130b7acbcd6000f', null, '1', null, '4');
INSERT INTO `right_info` VALUES ('402880e430b7ab2c0130b7acbcd60010', null, '1', null, '5');
INSERT INTO `right_info` VALUES ('402880e430b7ab2c0130b7acbcd70011', null, '1', null, '10');
INSERT INTO `right_info` VALUES ('402880e430b7ab2c0130b7acbcd70012', null, '1', null, '11');
INSERT INTO `right_info` VALUES ('402880e430b7ab2c0130b7acbcd70013', null, '1', null, '6');
INSERT INTO `right_info` VALUES ('402880e430b7ab2c0130b7acbcd70014', null, '1', null, '7');
INSERT INTO `right_info` VALUES ('402880e430b7ab2c0130b7acbcd70015', null, '1', null, '8');
INSERT INTO `right_info` VALUES ('402880e430b7ab2c0130b7acbcd70016', null, '1', null, '9');
INSERT INTO `right_info` VALUES ('402880e430b7ab2c0130b7accdd50022', null, '2', null, '1');
INSERT INTO `right_info` VALUES ('402880e430b7ab2c0130b7accdd50023', null, '2', null, '2');
INSERT INTO `right_info` VALUES ('402880e430b7ab2c0130b7accdd50024', null, '2', null, '3');
INSERT INTO `right_info` VALUES ('402880e430b7ab2c0130b7accdd50025', null, '2', null, '4');
INSERT INTO `right_info` VALUES ('402880e430b7ab2c0130b7accdd60026', null, '2', null, '5');
INSERT INTO `right_info` VALUES ('402880e430b7ab2c0130b7accdd60027', null, '2', null, '10');
INSERT INTO `right_info` VALUES ('402880e430b7ab2c0130b7accdd60028', null, '2', null, '11');
INSERT INTO `right_info` VALUES ('402880e430b7ab2c0130b7accdd60029', null, '2', null, '6');
INSERT INTO `right_info` VALUES ('402880e430b7ab2c0130b7accdd6002a', null, '2', null, '7');
INSERT INTO `right_info` VALUES ('402880e430b7ab2c0130b7accdd6002b', null, '2', null, '8');
INSERT INTO `right_info` VALUES ('402880e430b7ab2c0130b7accdd6002c', null, '2', null, '9');
INSERT INTO `role_info` VALUES ('1', '系统管理员', null);
INSERT INTO `role_info` VALUES ('2', '客户', null);
INSERT INTO `role_info` VALUES ('3', '普通用户', null);
INSERT INTO `rolemenu` VALUES ('402880e430b7ab2c0130b7acbcd10001', '1', '2');
INSERT INTO `rolemenu` VALUES ('402880e430b7ab2c0130b7acbcd30002', '1', '1');
INSERT INTO `rolemenu` VALUES ('402880e430b7ab2c0130b7acbcd30003', '1', '4');
INSERT INTO `rolemenu` VALUES ('402880e430b7ab2c0130b7acbcd30004', '1', '3');
INSERT INTO `rolemenu` VALUES ('402880e430b7ab2c0130b7acbcd40005', '1', '10');
INSERT INTO `rolemenu` VALUES ('402880e430b7ab2c0130b7acbcd40006', '1', '11');
INSERT INTO `rolemenu` VALUES ('402880e430b7ab2c0130b7acbcd40007', '1', '6');
INSERT INTO `rolemenu` VALUES ('402880e430b7ab2c0130b7acbcd60008', '1', '5');
INSERT INTO `rolemenu` VALUES ('402880e430b7ab2c0130b7acbcd60009', '1', '7');
INSERT INTO `rolemenu` VALUES ('402880e430b7ab2c0130b7acbcd6000a', '1', '8');
INSERT INTO `rolemenu` VALUES ('402880e430b7ab2c0130b7acbcd6000b', '1', '9');
INSERT INTO `rolemenu` VALUES ('402880e430b7ab2c0130b7accdd20017', '2', '2');
INSERT INTO `rolemenu` VALUES ('402880e430b7ab2c0130b7accdd20018', '2', '1');
INSERT INTO `rolemenu` VALUES ('402880e430b7ab2c0130b7accdd20019', '2', '4');
INSERT INTO `rolemenu` VALUES ('402880e430b7ab2c0130b7accdd2001a', '2', '3');
INSERT INTO `rolemenu` VALUES ('402880e430b7ab2c0130b7accdd3001b', '2', '10');
INSERT INTO `rolemenu` VALUES ('402880e430b7ab2c0130b7accdd3001c', '2', '11');
INSERT INTO `rolemenu` VALUES ('402880e430b7ab2c0130b7accdd4001d', '2', '6');
INSERT INTO `rolemenu` VALUES ('402880e430b7ab2c0130b7accdd4001e', '2', '5');
INSERT INTO `rolemenu` VALUES ('402880e430b7ab2c0130b7accdd5001f', '2', '7');
INSERT INTO `rolemenu` VALUES ('402880e430b7ab2c0130b7accdd50020', '2', '8');
INSERT INTO `rolemenu` VALUES ('402880e430b7ab2c0130b7accdd50021', '2', '9');
INSERT INTO `rolemenu` VALUES ('402880e430b7ab2c0130b7ad0f80002d', '3', '2');
INSERT INTO `rolemenu` VALUES ('402880e430b7ab2c0130b7ad0f81002e', '3', '1');
INSERT INTO `rolemenu` VALUES ('402880e430b7ab2c0130b7ad0f81002f', '3', '4');
INSERT INTO `supplier_role` VALUES ('1', 'swpigris81', '1', '1');
INSERT INTO `supplier_role` VALUES ('2', 'admin', '2', '2');
INSERT INTO `supplier_role` VALUES ('3', 'user', null, '3');
