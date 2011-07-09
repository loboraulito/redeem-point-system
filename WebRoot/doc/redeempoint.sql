/*
MySQL Data Transfer
Source Host: localhost
Source Database: redeempoint
Target Host: localhost
Target Database: redeempoint
Date: 2011/7/9 14:37:45
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for employee_info
-- ----------------------------
DROP TABLE IF EXISTS `employee_info`;
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
DROP TABLE IF EXISTS `menu_info`;
CREATE TABLE `menu_info` (
  `menu_id` varchar(32) NOT NULL,
  `menu_name` varchar(100) default NULL,
  `page_path` varchar(500) default NULL,
  `menu_level` varchar(32) default NULL,
  `parent_menu` varchar(32) default NULL,
  `is_leave` varchar(2) default NULL,
  PRIMARY KEY  (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for menubutton
-- ----------------------------
DROP TABLE IF EXISTS `menubutton`;
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
DROP TABLE IF EXISTS `right_info`;
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
DROP TABLE IF EXISTS `role_info`;
CREATE TABLE `role_info` (
  `role_id` varchar(32) NOT NULL,
  `role_name` varchar(256) default NULL,
  `comment` varchar(500) default NULL,
  PRIMARY KEY  (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rolemenu
-- ----------------------------
DROP TABLE IF EXISTS `rolemenu`;
CREATE TABLE `rolemenu` (
  `ID` varchar(32) character set utf8 collate utf8_bin NOT NULL,
  `roleId` varchar(32) character set utf8 collate utf8_bin default NULL,
  `menuId` varchar(32) character set utf8 collate utf8_bin default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for supplier_role
-- ----------------------------
DROP TABLE IF EXISTS `supplier_role`;
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
INSERT INTO `employee_info` VALUES ('1', '1', 'swpigris81', '67b811e6506d79014211e32cda238696', null, null, null, null, null, null, null);
INSERT INTO `employee_info` VALUES ('2', '2', 'admin', 'ceb4f32325eda6142bd65215f4c0f371', '11', '111', '', '', '', '', ''), ('3', '3', 'user', '47a733d60998c719cf3526ae7d106d13', null, null, null, null, null, null, null), ('402880e5310d8e6201310d96cb7e0001', '', 'user1', '026f93a32862a2cdd419fb7b8f71183f', '1111111', '', '', '', '', '', ''), ('402880e5310d8e6201310d978cdd0003', '', 'user11', 'dda4b53dfc9bb16f264b3708d6a44a76', '1212', '', '', '', '', '', '');
INSERT INTO `employee_info` VALUES ('402880e5310d8e6201310d99b22d0005', '', 'zhucd1', 'f05685f47c35703f9652a67c615261f7', '1111', '', '', '', '', '', '');
INSERT INTO `menu_info` VALUES ('402880e4270e8f7c01270ea42f020004', '权限管理', '/map/map.action?method=begin', '', '402880e430e992d60130e9cab80b0002', '0'), ('402880e4270e8f7c01270ea9ff4c0025', '系统资源管理', '/main.action', null, '402880e4270e8f7c01270ea42f020004', '0'), ('402880e4270e8f7c01270eaaa5730041', '菜单管理', '/menu/beginMenuManage.action?method=begin', null, '402880e4270e8f7c01270ea9ff4c0025', '1'), ('402880e428befea20128bf01b8300003', '用户管理', '/user/userManage.action?method=begin', null, '402880e4270e8f7c01270ea42f020004', '1'), ('402880e428befea20128bf029c1a0005', '角色管理', '/role/roleManage.action?method=begin', null, '402880e4270e8f7c01270ea42f020004', '1'), ('402880e428befea20128bf04ae450021', '授权管理', '/right/authorize.action?method=begin', null, '402880e4270e8f7c01270ea42f020004', '1'), ('402880e430e992d60130e9c8b54d0001', '系统后台管理', '/main.action', '', null, '0'), ('402880e430e992d60130e9cab80b0002', '系统管理', '/mian.action', '', '402880e430e992d60130e9c8b54d0001', '0'), ('402880e430e992d60130e9cca8980003', '数据字典管理', '/main.action', '', '402880e430e992d60130e9cab80b0002', '1'), ('402880e43109f66101310a31c1250004', '系统前台管理', '/main.action', '', null, '0'), ('402880e43109f66101310a3215210005', '系统前台管理子菜单', '/main.action', '', '402880e43109f66101310a31c1250004', '1'), ('402881eb2452211b012452264ad70049', '按钮管理', '/button/buttonManage.action?method=begin', null, '402880e4270e8f7c01270ea9ff4c0025', '1');
INSERT INTO `menubutton` VALUES ('402880e430e992d60130e9d0267b0039', 'button_addButton', '添加按钮', '402881eb2452211b012452264ad70049', '/button/buttonManageAdd.action?method=addButton', 'yes', 'table_add', 'addButton'), ('402880e430eb58470130eb5d5f880001', 'user_addUser', '添加用户', '402880e428befea20128bf01b8300003', '/user/userManageAdd.action?method=addUser', 'yes', 'table_add', 'addUser'), ('402880e430eb58470130eb5e584e0002', 'user_editUser', '编辑用户', '402880e428befea20128bf01b8300003', '/user/userManageEdit.action?method=editUser', 'yes', 'table_edit', 'editUser'), ('402880e430eb58470130eb5f22870003', 'user_deleteUser', '删除用户', '402880e428befea20128bf01b8300003', '/user/userManageDelete.action?method=deleteUser', 'yes', 'table_delete', 'deleteUser'), ('402880e430eb58470130eb6112ea0004', 'role_addRole', '添加角色', '402880e428befea20128bf029c1a0005', '/role/roleManageAdd.action?method=addRole', 'yes', 'table_add', 'addRole'), ('402880e430eb58470130eb61cf4a0005', 'role_editRole', '修改角色', '402880e428befea20128bf029c1a0005', '/role/roleManageEdit.action?method=editRole', 'yes', 'table_edit', 'editRole'), ('402880e430eb58470130eb62d0f70006', 'role_deleteRole', '删除角色', '402880e428befea20128bf029c1a0005', '/role/roleManageDelete.action?method=deleteRole', 'yes', 'table_delete', 'deleteRole'), ('402881e423cdce810123cdd2c2bc0004', 'authorize_user', '授权用户(勿删)', '402880e428befea20128bf04ae450021', '/right/authorizeUser.action?method=showAuthorizeUser', 'no', 'none', ''), ('402881e82505b3a5012505b666990004', 'authorize_menu', '授权树(勿删)', '402880e428befea20128bf04ae450021', '/right/authorizeMenu.action?method=showAuthorizeMenu', 'no', 'none', ''), ('4af49536275fac9401275faf59c30008', 'button_list', '按钮列表', '402881eb2452211b012452264ad70049', '/button/buttonManageList.action?method=buttonList', 'no', 'none', ''), ('4af49536275fac9401275fb06f00000a', 'button_editButton', '修改按钮', '402881eb2452211b012452264ad70049', '/button/buttonManageEdit.action?method=editButton', 'yes', 'table_edit', 'editButton'), ('4af49536276143d301276145131d0003', 'button_deleteButton', '删除按钮', '402881eb2452211b012452264ad70049', '/button/buttonManageDelete.action?method=deleteButton', 'yes', 'table_delete', 'deleteButton'), ('4af4953627d6f4ff0127d708d95b0019', 'menu_list', '菜单列表', '402880e4270e8f7c01270eaaa5730041', '/menu/menuManage.action?method=menuList', 'no', 'none', ''), ('4af495362813e685012813f3ec120003', 'menu_addMenu', '添加菜单', '402880e4270e8f7c01270eaaa5730041', '/menu/menuManageAdd.action?method=addMenu', 'yes', 'table_add', 'addMenu'), ('4af495362813e685012813f4f53f0005', 'menu_editMenu', '修改菜单', '402880e4270e8f7c01270eaaa5730041', '/menu/menuManageEdit.action?method=editMenu', 'yes', 'table_edit', 'editMenu'), ('4af49536281a5f0301281a6032640003', 'menu_deleteMenu', '删除菜单', '402880e4270e8f7c01270eaaa5730041', '/menu/menuManageDelete.action?method=deleteMenu', 'yes', 'table_delete', 'deleteMenu'), ('ff808081297247bf0129724a54300004', 'user_list', '用户列表', '402880e428befea20128bf01b8300003', '/user/userList.action?method=userManageList', 'no', 'none', ''), ('ff80808129a30cbc0129a30e45860005', 'role_list', '角色列表', '402880e428befea20128bf029c1a0005', '/role/roleList.action?method=roleManageList', 'no', 'none', ''), ('ff80808129de49ac0129de4b3ecc0005', 'authorizeForUser', '添加授权用户', '402880e428befea20128bf04ae450021', '/right/authorizeUserAdd.action?method=authorizeUserAdd', 'yes', 'table_add', 'addAuthorizeUser'), ('ff80808129e37c8b0129e37dbe0c0005', 'authorize_rught_menu', '保存角色权限菜单', '402880e428befea20128bf04ae450021', '/right/authorizeRoleMenu.action?method=updateAuthorizeRoleMenu', 'yes', 'table_save', null), ('ff8080812c487f3a012c48e306290007', 'authorize_list', '删除授权用户', '402880e428befea20128bf04ae450021', '/right/authorizeUserDelete.action?method=authorizeUserDelete', 'yes', 'table_delete', 'deleteAuthorizeUser'), ('ff8080812c487f3a012c48e3c02c0009', 'authorize_role', '授权角色列表(勿删)', '402880e428befea20128bf04ae450021', '/right/authorizeRole.action?method=showAuthorizeRole', 'no', 'none', '');
INSERT INTO `right_info` VALUES ('402880e430e992d60130e9e4e9390073', null, '2', null, 'ff80808129e37c8b0129e37dbe0c0005'), ('402880e5310c78c301310c7cc678006e', null, '1', null, '4af4953627d6f4ff0127d708d95b0019'), ('402880e5310c78c301310c7cc678006f', null, '1', null, '4af495362813e685012813f3ec120003'), ('402880e5310c78c301310c7cc6780070', null, '1', null, '4af495362813e685012813f4f53f0005'), ('402880e5310c78c301310c7cc6780071', null, '1', null, '4af49536281a5f0301281a6032640003'), ('402880e5310c78c301310c7cc6780072', null, '1', null, '402880e430e992d60130e9d0267b0039'), ('402880e5310c78c301310c7cc6780073', null, '1', null, '4af49536275fac9401275faf59c30008'), ('402880e5310c78c301310c7cc6780074', null, '1', null, '4af49536275fac9401275fb06f00000a'), ('402880e5310c78c301310c7cc6780075', null, '1', null, '4af49536276143d301276145131d0003'), ('402880e5310c78c301310c7cc6780076', null, '1', null, '402880e430eb58470130eb5d5f880001'), ('402880e5310c78c301310c7cc6780077', null, '1', null, '402880e430eb58470130eb5e584e0002'), ('402880e5310c78c301310c7cc6780078', null, '1', null, '402880e430eb58470130eb5f22870003'), ('402880e5310c78c301310c7cc6780079', null, '1', null, 'ff808081297247bf0129724a54300004'), ('402880e5310c78c301310c7cc679007a', null, '1', null, '402880e430eb58470130eb6112ea0004'), ('402880e5310c78c301310c7cc679007b', null, '1', null, '402880e430eb58470130eb61cf4a0005'), ('402880e5310c78c301310c7cc679007c', null, '1', null, '402880e430eb58470130eb62d0f70006'), ('402880e5310c78c301310c7cc679007d', null, '1', null, 'ff80808129a30cbc0129a30e45860005'), ('402880e5310c78c301310c7cc679007e', null, '1', null, '402881e423cdce810123cdd2c2bc0004'), ('402880e5310c78c301310c7cc679007f', null, '1', null, '402881e82505b3a5012505b666990004'), ('402880e5310c78c301310c7cc6790080', null, '1', null, 'ff80808129de49ac0129de4b3ecc0005'), ('402880e5310c78c301310c7cc6790081', null, '1', null, 'ff80808129e37c8b0129e37dbe0c0005'), ('402880e5310c78c301310c7cc6790082', null, '1', null, 'ff8080812c487f3a012c48e306290007'), ('402880e5310c78c301310c7cc6790083', null, '1', null, 'ff8080812c487f3a012c48e3c02c0009');
INSERT INTO `role_info` VALUES ('1', '系统管理员', null), ('2', '客户', '客户'), ('3', '普通用户', '用户'), ('402880e5310c95c901310ca03c8b0001', '系统注册用户', '系统注册用户');
INSERT INTO `rolemenu` VALUES ('402880e430e992d60130e9e4e938006f', '2', '402880e430e992d60130e9c8b54d0001'), ('402880e430e992d60130e9e4e9380070', '2', '402880e430e992d60130e9cab80b0002'), ('402880e430e992d60130e9e4e9380071', '2', '402880e4270e8f7c01270ea42f020004'), ('402880e430e992d60130e9e4e9380072', '2', '402880e428befea20128bf04ae450021'), ('402880e5310c78c301310c7cc6770062', '1', '402880e430e992d60130e9c8b54d0001'), ('402880e5310c78c301310c7cc6770063', '1', '402880e430e992d60130e9cab80b0002'), ('402880e5310c78c301310c7cc6770064', '1', '402880e4270e8f7c01270ea42f020004'), ('402880e5310c78c301310c7cc6770065', '1', '402880e4270e8f7c01270ea9ff4c0025'), ('402880e5310c78c301310c7cc6770066', '1', '402880e4270e8f7c01270eaaa5730041'), ('402880e5310c78c301310c7cc6770067', '1', '402881eb2452211b012452264ad70049'), ('402880e5310c78c301310c7cc6770068', '1', '402880e428befea20128bf01b8300003'), ('402880e5310c78c301310c7cc6770069', '1', '402880e428befea20128bf029c1a0005'), ('402880e5310c78c301310c7cc677006a', '1', '402880e428befea20128bf04ae450021'), ('402880e5310c78c301310c7cc677006b', '1', '402880e430e992d60130e9cca8980003'), ('402880e5310c78c301310c7cc677006c', '1', '402880e43109f66101310a31c1250004'), ('402880e5310c78c301310c7cc677006d', '1', '402880e43109f66101310a3215210005'), ('402880e5310d8e6201310d9bbb2b0021', '402880e5310c95c901310ca03c8b0001', '402880e43109f66101310a31c1250004'), ('402880e5310d8e6201310d9bbb2b0022', '402880e5310c95c901310ca03c8b0001', '402880e43109f66101310a3215210005');
INSERT INTO `supplier_role` VALUES ('1', 'swpigris81', '1', '1'), ('2', 'admin', '2', '2'), ('3', 'user', null, '3'), ('402880e5310d8e6201310d96cb860002', 'user1', null, '402880e5310c95c901310ca03c8b0001'), ('402880e5310d8e6201310d978cdd0004', 'user11', null, '402880e5310c95c901310ca03c8b0001'), ('402880e5310d8e6201310d99b22d0006', 'zhucd1', null, '402880e5310c95c901310ca03c8b0001');
