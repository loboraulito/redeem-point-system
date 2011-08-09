/*
MySQL Data Transfer
Source Host: localhost
Source Database: redeempoint
Target Host: localhost
Target Database: redeempoint
Date: 2011/8/9 22:45:29
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
-- Table structure for gift_info
-- ----------------------------
DROP TABLE IF EXISTS `gift_info`;
CREATE TABLE `gift_info` (
  `gift_id` varchar(32) NOT NULL COMMENT '礼品ID',
  `gift_code` varchar(32) default NULL COMMENT '礼品代号',
  `gift_name` varchar(200) default NULL COMMENT '礼品名称',
  `gift_model` varchar(200) default NULL COMMENT '型号',
  `gift_comment` varchar(500) default NULL COMMENT '描述',
  `gift_color` varchar(10) default NULL COMMENT '颜色',
  `gift_size` varchar(10) default NULL COMMENT '尺寸',
  `gift_unit` varchar(10) default NULL COMMENT '单位',
  `gift_type` varchar(10) default NULL COMMENT '类型',
  `gift_image` varchar(500) default NULL COMMENT '图片路径',
  `supplier_id` varchar(32) default NULL COMMENT '供应商ID',
  `stock_no` varchar(11) default NULL COMMENT '库存数量',
  PRIMARY KEY  (`gift_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='礼品的基本信息';

-- ----------------------------
-- Table structure for gift_inventory
-- ----------------------------
DROP TABLE IF EXISTS `gift_inventory`;
CREATE TABLE `gift_inventory` (
  `inventoryId` varchar(32) NOT NULL COMMENT '库存id',
  `giftId` varchar(32) NOT NULL COMMENT '礼品编号',
  `giftcolor` varchar(32) default NULL COMMENT '礼品颜色',
  `giftinventory` int(11) default NULL COMMENT '礼品库存',
  `giftsize` varchar(32) default NULL COMMENT '礼品尺寸',
  `giftimage` varchar(500) default NULL COMMENT '礼品图片',
  PRIMARY KEY  (`inventoryId`)
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
-- Table structure for point_system_codelist
-- ----------------------------
DROP TABLE IF EXISTS `point_system_codelist`;
CREATE TABLE `point_system_codelist` (
  `codeid` varchar(32) NOT NULL COMMENT '代码ID',
  `codename` varchar(500) default NULL COMMENT '代码名称',
  PRIMARY KEY  (`codeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统数据标准表';

-- ----------------------------
-- Table structure for point_system_codelist_data
-- ----------------------------
DROP TABLE IF EXISTS `point_system_codelist_data`;
CREATE TABLE `point_system_codelist_data` (
  `dataid` varchar(32) NOT NULL COMMENT '数据ID',
  `codeid` varchar(32) default NULL COMMENT '数据标准ID',
  `datavalue` varchar(500) default NULL COMMENT '数据值',
  `parentdataid` varchar(32) default NULL COMMENT '父级数据ID',
  `remark` varchar(500) default NULL COMMENT '备注',
  PRIMARY KEY  (`dataid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统数据标准数据表';

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
INSERT INTO `employee_info` VALUES ('2', '2', 'admin', 'ceb4f32325eda6142bd65215f4c0f371', null, null, null, null, null, null, null), ('3', '3', 'user', '47a733d60998c719cf3526ae7d106d13', null, null, null, null, null, null, null), ('8ac388eb310357850131036886720002', '', '11111', '38f41998d5a83fa86c97bf3b64a5f8f1', '1111', '1111', '', '', '', '', ''), ('8ac388eb3103578501310368a10a0003', '', '12222', 'a8658d4e50cccbd88fef6219db147bfd', '2222', '', '', '', '', '', ''), ('8ac388eb310357850131037c313f0007', '', '111', '4fac6bea91e3bafca7e87a8b3dfba7f3', '11', '', '', '', '', '', ''), ('8ac388eb314aae6401314ab031750001', '', 'swpigris81', '67b811e6506d79014211e32cda238696', '1', '', '', '', '', '', ''), ('8ac388eb314aae6401314ab2ddd40003', '', '代超', '628339afcb4b7e79cadb990ffc3d9265', '1', '', '', '', '', '', '');
INSERT INTO `gift_info` VALUES ('402880e431a48b160131a48c23ef0001', '', '111', '', '', '', '', '', '', '/picture/d2399cd3-01f0-4d7e-98bf-57046ee8cedc.gif', '1', '');
INSERT INTO `menu_info` VALUES ('402880e4270e8f7c01270ea42f020004', '权限管理', '/map/map.action?method=begin', '', '402880e430e992d60130e9cab80b0002', '0'), ('402880e4270e8f7c01270ea9ff4c0025', '系统资源管理', '/main.action', null, '402880e4270e8f7c01270ea42f020004', '0'), ('402880e4270e8f7c01270eaaa5730041', '菜单管理', '/menu/beginMenuManage.action?method=begin', null, '402880e4270e8f7c01270ea9ff4c0025', '1'), ('402880e428befea20128bf01b8300003', '用户管理', '/user/userManage.action?method=begin', null, '402880e4270e8f7c01270ea42f020004', '1'), ('402880e428befea20128bf029c1a0005', '角色管理', '/role/roleManage.action?method=begin', null, '402880e4270e8f7c01270ea42f020004', '1'), ('402880e428befea20128bf04ae450021', '授权管理', '/right/authorize.action?method=begin', null, '402880e4270e8f7c01270ea42f020004', '1'), ('402880e430e992d60130e9c8b54d0001', '系统后台管理', '/main.action', '', null, '0'), ('402880e430e992d60130e9cab80b0002', '系统管理', '/mian.action', '', '402880e430e992d60130e9c8b54d0001', '0'), ('402880e430e992d60130e9cca8980003', '数据字典管理', '/main.action', '', '402880e430e992d60130e9cab80b0002', '1'), ('402881eb2452211b012452264ad70049', '按钮管理', '/button/buttonManage.action?method=begin', null, '402880e4270e8f7c01270ea9ff4c0025', '1'), ('8ac388eb311df21701311df3d9190001', '供应商礼品管理', '/gift/giftManage.action?method=begin', '', '8ac388eb314562a601314564ea0f0001', '1'), ('8ac388eb314562a601314564ea0f0001', '供应商后台管理', '/main.action', '', null, '0');
INSERT INTO `menubutton` VALUES ('402880e430e992d60130e9d0267b0039', 'button_addButton', '添加按钮', '402881eb2452211b012452264ad70049', '/button/buttonManageAdd.action?method=addButton', 'yes', 'table_add', 'addButton'), ('402880e430eb58470130eb5d5f880001', 'user_addUser', '添加用户', '402880e428befea20128bf01b8300003', '/user/userManageAdd.action?method=addUser', 'yes', 'table_add', 'addUser'), ('402880e430eb58470130eb5e584e0002', 'user_editUser', '编辑用户', '402880e428befea20128bf01b8300003', '/user/userManageEdit.action?method=editUser', 'yes', 'table_edit', 'editUser'), ('402880e430eb58470130eb5f22870003', 'user_deleteUser', '删除用户', '402880e428befea20128bf01b8300003', '/user/userManageDelete.action?method=deleteUser', 'yes', 'table_delete', 'deleteUser'), ('402880e430eb58470130eb6112ea0004', 'role_addRole', '添加角色', '402880e428befea20128bf029c1a0005', '/role/roleManageAdd.action?method=addRole', 'yes', 'table_add', 'addRole'), ('402880e430eb58470130eb61cf4a0005', 'role_editRole', '修改角色', '402880e428befea20128bf029c1a0005', '/role/roleManageEdit.action?method=editRole', 'yes', 'table_edit', 'editRole'), ('402880e430eb58470130eb62d0f70006', 'role_deleteRole', '删除角色', '402880e428befea20128bf029c1a0005', '/role/roleManageDelete.action?method=deleteRole', 'yes', 'table_delete', 'deleteRole'), ('402881e423cdce810123cdd2c2bc0004', 'authorize_user', '授权用户(勿删)', '402880e428befea20128bf04ae450021', '/right/authorizeUser.action?method=showAuthorizeUser', 'no', 'none', ''), ('402881e82505b3a5012505b666990004', 'authorize_menu', '授权树(勿删)', '402880e428befea20128bf04ae450021', '/right/authorizeMenu.action?method=showAuthorizeMenu', 'no', 'none', ''), ('4af49536275fac9401275faf59c30008', 'button_list', '按钮列表', '402881eb2452211b012452264ad70049', '/button/buttonManageList.action?method=buttonList', 'no', 'none', ''), ('4af49536275fac9401275fb06f00000a', 'button_editButton', '修改按钮', '402881eb2452211b012452264ad70049', '/button/buttonManageEdit.action?method=editButton', 'yes', 'table_edit', 'editButton'), ('4af49536276143d301276145131d0003', 'button_deleteButton', '删除按钮', '402881eb2452211b012452264ad70049', '/button/buttonManageDelete.action?method=deleteButton', 'yes', 'table_delete', 'deleteButton'), ('4af4953627d6f4ff0127d708d95b0019', 'menu_list', '菜单列表', '402880e4270e8f7c01270eaaa5730041', '/menu/menuManage.action?method=menuList', 'no', 'none', ''), ('4af495362813e685012813f3ec120003', 'menu_addMenu', '添加菜单', '402880e4270e8f7c01270eaaa5730041', '/menu/menuManageAdd.action?method=addMenu', 'yes', 'table_add', 'addMenu'), ('4af495362813e685012813f4f53f0005', 'menu_editMenu', '修改菜单', '402880e4270e8f7c01270eaaa5730041', '/menu/menuManageEdit.action?method=editMenu', 'yes', 'table_edit', 'editMenu'), ('4af49536281a5f0301281a6032640003', 'menu_deleteMenu', '删除菜单', '402880e4270e8f7c01270eaaa5730041', '/menu/menuManageDelete.action?method=deleteMenu', 'yes', 'table_delete', 'deleteMenu'), ('8ac388eb314562a601314566b0b10024', 'giftManage_list', '礼品列表', '8ac388eb311df21701311df3d9190001', '/gift/giftList.action?method=giftList', 'no', 'none', ''), ('8ac388eb314562a601314567a5e90025', 'giftManage_add', '添加礼品', '8ac388eb311df21701311df3d9190001', '/gift/giftManageAdd.action?method=addGift', 'yes', 'table_add', 'addGift'), ('8ac388eb314aae6401314bfdb3680035', 'giftManage_edit', '编辑礼品信息', '8ac388eb311df21701311df3d9190001', '/gift/giftManageEdit.action?method=editGift', 'yes', 'table_edit', 'editGift'), ('8ac388eb314aae6401314bfe88a80036', 'giftManage_delete', '删除礼品信息', '8ac388eb311df21701311df3d9190001', '/gift/giftManageDelete.action?method=deleteGift', 'yes', 'table_delete', 'deleteGift'), ('ff808081297247bf0129724a54300004', 'user_list', '用户列表', '402880e428befea20128bf01b8300003', '/user/userList.action?method=userManageList', 'no', 'none', ''), ('ff80808129a30cbc0129a30e45860005', 'role_list', '角色列表', '402880e428befea20128bf029c1a0005', '/role/roleList.action?method=roleManageList', 'no', 'none', ''), ('ff80808129de49ac0129de4b3ecc0005', 'authorizeForUser', '增加授权用户', '402880e428befea20128bf04ae450021', '/right/authorizeUserAdd.action?method=authorizeUserAdd', 'yes', 'table_add', 'addAuthorizeUser'), ('ff80808129e37c8b0129e37dbe0c0005', 'authorize_rught_menu', '保存角色权限菜单', '402880e428befea20128bf04ae450021', '/right/authorizeRoleMenu.action?method=updateAuthorizeRoleMenu', 'yes', 'table_save', null), ('ff8080812c487f3a012c48e306290007', 'authorize_list', '删除授权用户', '402880e428befea20128bf04ae450021', '/right/authorizeUserDelete.action?method=authorizeUserDelete', 'yes', 'table_delete', 'deleteAuthorizeUser'), ('ff8080812c487f3a012c48e3c02c0009', 'authorize_role', '授权角色列表(勿删)', '402880e428befea20128bf04ae450021', '/right/authorizeRole.action?method=showAuthorizeRole', 'no', 'none', '');
INSERT INTO `right_info` VALUES ('8ac388eb31402e2001314059cf0c0006', null, '2', null, 'ff80808129e37c8b0129e37dbe0c0005'), ('8ac388eb314aae6401314bfeee470043', null, '1', null, '4af4953627d6f4ff0127d708d95b0019'), ('8ac388eb314aae6401314bfeee470044', null, '1', null, '4af495362813e685012813f3ec120003'), ('8ac388eb314aae6401314bfeee470045', null, '1', null, '4af495362813e685012813f4f53f0005'), ('8ac388eb314aae6401314bfeee470046', null, '1', null, '4af49536281a5f0301281a6032640003'), ('8ac388eb314aae6401314bfeee470047', null, '1', null, '402880e430e992d60130e9d0267b0039'), ('8ac388eb314aae6401314bfeee470048', null, '1', null, '4af49536275fac9401275faf59c30008'), ('8ac388eb314aae6401314bfeee470049', null, '1', null, '4af49536275fac9401275fb06f00000a'), ('8ac388eb314aae6401314bfeee47004a', null, '1', null, '4af49536276143d301276145131d0003'), ('8ac388eb314aae6401314bfeee47004b', null, '1', null, '402880e430eb58470130eb5d5f880001'), ('8ac388eb314aae6401314bfeee47004c', null, '1', null, '402880e430eb58470130eb5e584e0002'), ('8ac388eb314aae6401314bfeee47004d', null, '1', null, '402880e430eb58470130eb5f22870003'), ('8ac388eb314aae6401314bfeee47004e', null, '1', null, 'ff808081297247bf0129724a54300004'), ('8ac388eb314aae6401314bfeee47004f', null, '1', null, '402880e430eb58470130eb6112ea0004'), ('8ac388eb314aae6401314bfeee470050', null, '1', null, '402880e430eb58470130eb61cf4a0005'), ('8ac388eb314aae6401314bfeee470051', null, '1', null, '402880e430eb58470130eb62d0f70006'), ('8ac388eb314aae6401314bfeee470052', null, '1', null, 'ff80808129a30cbc0129a30e45860005'), ('8ac388eb314aae6401314bfeee470053', null, '1', null, '402881e423cdce810123cdd2c2bc0004'), ('8ac388eb314aae6401314bfeee470054', null, '1', null, '402881e82505b3a5012505b666990004'), ('8ac388eb314aae6401314bfeee470055', null, '1', null, 'ff80808129de49ac0129de4b3ecc0005'), ('8ac388eb314aae6401314bfeee470056', null, '1', null, 'ff80808129e37c8b0129e37dbe0c0005'), ('8ac388eb314aae6401314bfeee470057', null, '1', null, 'ff8080812c487f3a012c48e306290007'), ('8ac388eb314aae6401314bfeee470058', null, '1', null, 'ff8080812c487f3a012c48e3c02c0009'), ('8ac388eb314aae6401314bfeee470059', null, '1', null, '8ac388eb314562a601314566b0b10024'), ('8ac388eb314aae6401314bfeee47005a', null, '1', null, '8ac388eb314562a601314567a5e90025'), ('8ac388eb314aae6401314bfeee47005b', null, '1', null, '8ac388eb314aae6401314bfdb3680035'), ('8ac388eb314aae6401314bfeee47005c', null, '1', null, '8ac388eb314aae6401314bfe88a80036');
INSERT INTO `role_info` VALUES ('1', '系统管理员', null), ('2', '客户', '客户'), ('3', '普通用户', '用户'), ('4', '11', '1');
INSERT INTO `rolemenu` VALUES ('8ac388eb31402e2001314059cf0c0001', '2', '402880e430e992d60130e9c8b54d0001'), ('8ac388eb31402e2001314059cf0c0002', '2', '402880e430e992d60130e9cab80b0002'), ('8ac388eb31402e2001314059cf0c0003', '2', '402880e4270e8f7c01270ea42f020004'), ('8ac388eb31402e2001314059cf0c0004', '2', '402880e428befea20128bf04ae450021'), ('8ac388eb31402e2001314059cf0c0005', '2', '8ac388eb311df21701311df3d9190001'), ('8ac388eb314aae6401314bfeee470037', '1', '402880e430e992d60130e9c8b54d0001'), ('8ac388eb314aae6401314bfeee470038', '1', '402880e430e992d60130e9cab80b0002'), ('8ac388eb314aae6401314bfeee470039', '1', '402880e4270e8f7c01270ea42f020004'), ('8ac388eb314aae6401314bfeee47003a', '1', '402880e4270e8f7c01270ea9ff4c0025'), ('8ac388eb314aae6401314bfeee47003b', '1', '402880e4270e8f7c01270eaaa5730041'), ('8ac388eb314aae6401314bfeee47003c', '1', '402881eb2452211b012452264ad70049'), ('8ac388eb314aae6401314bfeee47003d', '1', '402880e428befea20128bf01b8300003'), ('8ac388eb314aae6401314bfeee47003e', '1', '402880e428befea20128bf029c1a0005'), ('8ac388eb314aae6401314bfeee47003f', '1', '402880e428befea20128bf04ae450021'), ('8ac388eb314aae6401314bfeee470040', '1', '402880e430e992d60130e9cca8980003'), ('8ac388eb314aae6401314bfeee470041', '1', '8ac388eb314562a601314564ea0f0001'), ('8ac388eb314aae6401314bfeee470042', '1', '8ac388eb311df21701311df3d9190001');
INSERT INTO `supplier_role` VALUES ('2', 'admin', '2', '1'), ('8ac388eb314aae6401314ac543df002b', '代超', null, '1'), ('8ac388eb314aae6401314ac611d0002c', 'swpigris81', null, '1'), ('8ac388eb314aae6401314af0eddd002d', 'user', null, '3'), ('8ac388eb314aae6401314af0eddd002e', '11111', null, '3'), ('8ac388eb314aae6401314af0eddd002f', '12222', null, '3'), ('8ac388eb314aae6401314af0eddd0030', '111', null, '3');
