/*
MySQL Data Transfer
Source Host: localhost
Source Database: redeempoint
Target Host: localhost
Target Database: redeempoint
Date: 2011/7/2 23:44:35
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
  `button_show` varchar(10) default NULL COMMENT '是否显示',
  `button_css` varchar(20) default NULL COMMENT '按钮样式',
  `handler` varchar(100) default NULL COMMENT '要处理的事件',
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
INSERT INTO `menu_info` VALUES ('402880e4270e8f7c01270ea42f020004', '权限管理', '/map/map.action?method=begin', '', '402880e430e992d60130e9cab80b0002', '0');
INSERT INTO `menu_info` VALUES ('402880e4270e8f7c01270ea9ff4c0025', '系统资源管理', '/main.action', null, '402880e4270e8f7c01270ea42f020004', '0');
INSERT INTO `menu_info` VALUES ('402880e4270e8f7c01270eaaa5730041', '菜单管理', '/menu/beginMenuManage.action?method=begin', null, '402880e4270e8f7c01270ea9ff4c0025', '1');
INSERT INTO `menu_info` VALUES ('402880e428befea20128bf01b8300003', '用户管理', '/user/userManage.action?method=begin', null, '402880e4270e8f7c01270ea42f020004', '1');
INSERT INTO `menu_info` VALUES ('402880e428befea20128bf029c1a0005', '角色管理', '/role/roleManage.action?method=begin', null, '402880e4270e8f7c01270ea42f020004', '1');
INSERT INTO `menu_info` VALUES ('402880e428befea20128bf04ae450021', '授权管理', '/right/authorize.action?method=begin', null, '402880e4270e8f7c01270ea42f020004', '1');
INSERT INTO `menu_info` VALUES ('402880e430e992d60130e9c8b54d0001', '系统后台管理', '/main.action', '', null, '0');
INSERT INTO `menu_info` VALUES ('402880e430e992d60130e9cab80b0002', '系统管理', '/mian.action', '', '402880e430e992d60130e9c8b54d0001', '0');
INSERT INTO `menu_info` VALUES ('402880e430e992d60130e9cca8980003', '数据字典管理', '/main.action', '', '402880e430e992d60130e9cab80b0002', '1');
INSERT INTO `menu_info` VALUES ('402881eb2452211b012452264ad70049', '按钮管理', '/button/buttonManage.action?method=begin', null, '402880e4270e8f7c01270ea9ff4c0025', '1');
INSERT INTO `menubutton` VALUES ('402880e430e992d60130e9d0267b0039', 'button_addButton', '添加按钮', '402881eb2452211b012452264ad70049', '/button/buttonManageAdd.action?method=addButton', 'yes', 'table_add', 'addButton');
INSERT INTO `menubutton` VALUES ('402880e430eb58470130eb5d5f880001', 'user_addUser', '添加用户', '402880e428befea20128bf01b8300003', '/user/userManageAdd.action?method=addUser', 'yes', 'table_add', 'addUser');
INSERT INTO `menubutton` VALUES ('402880e430eb58470130eb5e584e0002', 'user_editUser', '编辑用户', '402880e428befea20128bf01b8300003', '/user/userManageEdit.action?method=editUser', 'yes', 'table_edit', 'editUser');
INSERT INTO `menubutton` VALUES ('402880e430eb58470130eb5f22870003', 'user_deleteUser', '删除用户', '402880e428befea20128bf01b8300003', '/user/userManageDelete.action?method=deleteUser', 'yes', 'table_delete', 'deleteUser');
INSERT INTO `menubutton` VALUES ('402880e430eb58470130eb6112ea0004', 'role_addRole', '添加角色', '402880e428befea20128bf029c1a0005', '/role/roleManageAdd.action?method=addRole', 'yes', 'table_add', 'addRole');
INSERT INTO `menubutton` VALUES ('402880e430eb58470130eb61cf4a0005', 'role_editRole', '修改角色', '402880e428befea20128bf029c1a0005', '/role/roleManageEdit.action?method=editRole', 'yes', 'table_edit', 'editRole');
INSERT INTO `menubutton` VALUES ('402880e430eb58470130eb62d0f70006', 'role_deleteRole', '删除角色', '402880e428befea20128bf029c1a0005', '/role/roleManageDelete.action?method=deleteRole', 'yes', 'table_delete', 'deleteRole');
INSERT INTO `menubutton` VALUES ('402881e423cdce810123cdd2c2bc0004', 'authorize_user', '授权用户(勿删)', '402880e428befea20128bf04ae450021', '/right/authorizeUser.action?method=showAuthorizeUser', 'no', 'none', '');
INSERT INTO `menubutton` VALUES ('402881e82505b3a5012505b666990004', 'authorize_menu', '授权树(勿删)', '402880e428befea20128bf04ae450021', '/right/authorizeMenu.action?method=showAuthorizeMenu', 'no', 'none', '');
INSERT INTO `menubutton` VALUES ('4af49536275fac9401275faf59c30008', 'button_list', '按钮列表', '402881eb2452211b012452264ad70049', '/button/buttonManageList.action?method=buttonList', 'no', 'none', '');
INSERT INTO `menubutton` VALUES ('4af49536275fac9401275fb06f00000a', 'button_editButton', '修改按钮', '402881eb2452211b012452264ad70049', '/button/buttonManageEdit.action?method=editButton', 'yes', 'table_edit', 'editButton');
INSERT INTO `menubutton` VALUES ('4af49536276143d301276145131d0003', 'button_deleteButton', '删除按钮', '402881eb2452211b012452264ad70049', '/button/buttonManageDelete.action?method=deleteButton', 'yes', 'table_delete', 'deleteButton');
INSERT INTO `menubutton` VALUES ('4af4953627d6f4ff0127d708d95b0019', 'menu_list', '菜单列表', '402880e4270e8f7c01270eaaa5730041', '/menu/menuManage.action?method=menuList', 'no', 'none', '');
INSERT INTO `menubutton` VALUES ('4af495362813e685012813f3ec120003', 'menu_addMenu', '添加菜单', '402880e4270e8f7c01270eaaa5730041', '/menu/menuManageAdd.action?method=addMenu', 'yes', 'table_add', 'addMenu');
INSERT INTO `menubutton` VALUES ('4af495362813e685012813f4f53f0005', 'menu_editMenu', '修改菜单', '402880e4270e8f7c01270eaaa5730041', '/menu/menuManageEdit.action?method=editMenu', 'yes', 'table_edit', 'editMenu');
INSERT INTO `menubutton` VALUES ('4af49536281a5f0301281a6032640003', 'menu_deleteMenu', '删除菜单', '402880e4270e8f7c01270eaaa5730041', '/menu/menuManageDelete.action?method=deleteMenu', 'yes', 'table_delete', 'deleteMenu');
INSERT INTO `menubutton` VALUES ('ff808081297247bf0129724a54300004', 'user_list', '用户列表', '402880e428befea20128bf01b8300003', '/user/userList.action?method=userManageList', 'no', 'none', '');
INSERT INTO `menubutton` VALUES ('ff80808129a30cbc0129a30e45860005', 'role_list', '角色列表', '402880e428befea20128bf029c1a0005', '/role/roleList.action?method=roleManageList', 'no', 'none', '');
INSERT INTO `menubutton` VALUES ('ff80808129de49ac0129de4b3ecc0005', 'authorizeForUser', '为用户授权(未使用)', '402880e428befea20128bf04ae450021', '/right/authorizeUser.action?method=authorizeUser', 'no', 'none', '');
INSERT INTO `menubutton` VALUES ('ff80808129e37c8b0129e37dbe0c0005', 'authorize_rught_menu', '保存角色权限菜单', '402880e428befea20128bf04ae450021', '/right/authorizeRoleMenu.action?method=updateAuthorizeRoleMenu', 'yes', 'table_save', null);
INSERT INTO `menubutton` VALUES ('ff8080812c487f3a012c48e306290007', 'authorize_list', '授权列表(未使用)', '402880e428befea20128bf04ae450021', '/right/authorizeList.action?method=authorizeList', 'no', 'none', '');
INSERT INTO `menubutton` VALUES ('ff8080812c487f3a012c48e3c02c0009', 'authorize_role', '授权角色列表(勿删)', '402880e428befea20128bf04ae450021', '/right/authorizeRole.action?method=showAuthorizeRole', 'no', 'none', '');
INSERT INTO `right_info` VALUES ('402880e430e992d60130e9e4e9390073', null, '2', null, 'ff80808129e37c8b0129e37dbe0c0005');
INSERT INTO `right_info` VALUES ('402880e430eb58470130eb64484f0011', null, '1', null, '4af4953627d6f4ff0127d708d95b0019');
INSERT INTO `right_info` VALUES ('402880e430eb58470130eb64484f0012', null, '1', null, '4af495362813e685012813f3ec120003');
INSERT INTO `right_info` VALUES ('402880e430eb58470130eb64484f0013', null, '1', null, '4af495362813e685012813f4f53f0005');
INSERT INTO `right_info` VALUES ('402880e430eb58470130eb64484f0014', null, '1', null, '4af49536281a5f0301281a6032640003');
INSERT INTO `right_info` VALUES ('402880e430eb58470130eb6448500015', null, '1', null, '402880e430e992d60130e9d0267b0039');
INSERT INTO `right_info` VALUES ('402880e430eb58470130eb6448500016', null, '1', null, '4af49536275fac9401275faf59c30008');
INSERT INTO `right_info` VALUES ('402880e430eb58470130eb6448500017', null, '1', null, '4af49536275fac9401275fb06f00000a');
INSERT INTO `right_info` VALUES ('402880e430eb58470130eb6448500018', null, '1', null, '4af49536276143d301276145131d0003');
INSERT INTO `right_info` VALUES ('402880e430eb58470130eb6448500019', null, '1', null, '402880e430eb58470130eb5d5f880001');
INSERT INTO `right_info` VALUES ('402880e430eb58470130eb644850001a', null, '1', null, '402880e430eb58470130eb5e584e0002');
INSERT INTO `right_info` VALUES ('402880e430eb58470130eb644850001b', null, '1', null, '402880e430eb58470130eb5f22870003');
INSERT INTO `right_info` VALUES ('402880e430eb58470130eb644850001c', null, '1', null, 'ff808081297247bf0129724a54300004');
INSERT INTO `right_info` VALUES ('402880e430eb58470130eb644850001d', null, '1', null, '402880e430eb58470130eb6112ea0004');
INSERT INTO `right_info` VALUES ('402880e430eb58470130eb644850001e', null, '1', null, '402880e430eb58470130eb61cf4a0005');
INSERT INTO `right_info` VALUES ('402880e430eb58470130eb644850001f', null, '1', null, '402880e430eb58470130eb62d0f70006');
INSERT INTO `right_info` VALUES ('402880e430eb58470130eb6448500020', null, '1', null, 'ff80808129a30cbc0129a30e45860005');
INSERT INTO `right_info` VALUES ('402880e430eb58470130eb6448500021', null, '1', null, '402881e423cdce810123cdd2c2bc0004');
INSERT INTO `right_info` VALUES ('402880e430eb58470130eb6448500022', null, '1', null, '402881e82505b3a5012505b666990004');
INSERT INTO `right_info` VALUES ('402880e430eb58470130eb6448510023', null, '1', null, 'ff80808129de49ac0129de4b3ecc0005');
INSERT INTO `right_info` VALUES ('402880e430eb58470130eb6448510024', null, '1', null, 'ff80808129e37c8b0129e37dbe0c0005');
INSERT INTO `right_info` VALUES ('402880e430eb58470130eb6448510025', null, '1', null, 'ff8080812c487f3a012c48e306290007');
INSERT INTO `right_info` VALUES ('402880e430eb58470130eb6448510026', null, '1', null, 'ff8080812c487f3a012c48e3c02c0009');
INSERT INTO `role_info` VALUES ('1', '系统管理员', null);
INSERT INTO `role_info` VALUES ('2', '客户', null);
INSERT INTO `role_info` VALUES ('3', '普通用户', null);
INSERT INTO `rolemenu` VALUES ('402880e430e992d60130e9e4e938006f', '2', '402880e430e992d60130e9c8b54d0001');
INSERT INTO `rolemenu` VALUES ('402880e430e992d60130e9e4e9380070', '2', '402880e430e992d60130e9cab80b0002');
INSERT INTO `rolemenu` VALUES ('402880e430e992d60130e9e4e9380071', '2', '402880e4270e8f7c01270ea42f020004');
INSERT INTO `rolemenu` VALUES ('402880e430e992d60130e9e4e9380072', '2', '402880e428befea20128bf04ae450021');
INSERT INTO `rolemenu` VALUES ('402880e430eb58470130eb64484e0007', '1', '402880e430e992d60130e9c8b54d0001');
INSERT INTO `rolemenu` VALUES ('402880e430eb58470130eb64484e0008', '1', '402880e430e992d60130e9cab80b0002');
INSERT INTO `rolemenu` VALUES ('402880e430eb58470130eb64484e0009', '1', '402880e4270e8f7c01270ea42f020004');
INSERT INTO `rolemenu` VALUES ('402880e430eb58470130eb64484f000a', '1', '402880e4270e8f7c01270ea9ff4c0025');
INSERT INTO `rolemenu` VALUES ('402880e430eb58470130eb64484f000b', '1', '402880e4270e8f7c01270eaaa5730041');
INSERT INTO `rolemenu` VALUES ('402880e430eb58470130eb64484f000c', '1', '402881eb2452211b012452264ad70049');
INSERT INTO `rolemenu` VALUES ('402880e430eb58470130eb64484f000d', '1', '402880e428befea20128bf01b8300003');
INSERT INTO `rolemenu` VALUES ('402880e430eb58470130eb64484f000e', '1', '402880e428befea20128bf029c1a0005');
INSERT INTO `rolemenu` VALUES ('402880e430eb58470130eb64484f000f', '1', '402880e428befea20128bf04ae450021');
INSERT INTO `rolemenu` VALUES ('402880e430eb58470130eb64484f0010', '1', '402880e430e992d60130e9cca8980003');
INSERT INTO `supplier_role` VALUES ('1', 'swpigris81', '1', '1');
INSERT INTO `supplier_role` VALUES ('2', 'admin', '2', '2');
INSERT INTO `supplier_role` VALUES ('3', 'user', null, '3');
