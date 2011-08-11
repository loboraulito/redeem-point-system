# --------------------------------------------------------
# Host:                         127.0.0.1
# Server version:               5.0.45-community-nt
# Server OS:                    Win32
# HeidiSQL version:             6.0.0.3603
# Date/time:                    2011-08-11 10:31:15
# --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

# Dumping structure for table redeempoint.employee_info
DROP TABLE IF EXISTS `employee_info`;
CREATE TABLE IF NOT EXISTS `employee_info` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=2340;

# Dumping data for table redeempoint.employee_info: ~7 rows (approximately)
/*!40000 ALTER TABLE `employee_info` DISABLE KEYS */;
INSERT INTO `employee_info` (`operater_id`, `operater_code`, `operater_name`, `password`, `tel_no`, `phon_no`, `privence`, `city`, `address`, `zip`, `email`) VALUES
	('2', '2', 'admin', 'ceb4f32325eda6142bd65215f4c0f371', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	('3', '3', 'user', '47a733d60998c719cf3526ae7d106d13', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	('8ac388eb310357850131036886720002', '', '11111', '38f41998d5a83fa86c97bf3b64a5f8f1', '1111', '1111', '', '', '', '', ''),
	('8ac388eb3103578501310368a10a0003', '', '12222', 'a8658d4e50cccbd88fef6219db147bfd', '2222', '', '', '', '', '', ''),
	('8ac388eb310357850131037c313f0007', '', '111', '4fac6bea91e3bafca7e87a8b3dfba7f3', '11', '', '', '', '', '', ''),
	('8ac388eb314aae6401314ab031750001', '', 'swpigris81', '67b811e6506d79014211e32cda238696', '1', '', '', '', '', '', ''),
	('8ac388eb314aae6401314ab2ddd40003', '', '代超', '628339afcb4b7e79cadb990ffc3d9265', '1', '', '', '', '', '', '');
/*!40000 ALTER TABLE `employee_info` ENABLE KEYS */;


# Dumping structure for table redeempoint.gift_info
DROP TABLE IF EXISTS `gift_info`;
CREATE TABLE IF NOT EXISTS `gift_info` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=16384 COMMENT='礼品的基本信息';

# Dumping data for table redeempoint.gift_info: ~1 rows (approximately)
/*!40000 ALTER TABLE `gift_info` DISABLE KEYS */;
INSERT INTO `gift_info` (`gift_id`, `gift_code`, `gift_name`, `gift_model`, `gift_comment`, `gift_color`, `gift_size`, `gift_unit`, `gift_type`, `gift_image`, `supplier_id`, `stock_no`) VALUES
	('402880e431a48b160131a48c23ef0001', '', '111', '', '', '', '', '', '', '/picture/d2399cd3-01f0-4d7e-98bf-57046ee8cedc.gif', '1', '');
/*!40000 ALTER TABLE `gift_info` ENABLE KEYS */;


# Dumping structure for table redeempoint.gift_inventory
DROP TABLE IF EXISTS `gift_inventory`;
CREATE TABLE IF NOT EXISTS `gift_inventory` (
  `inventoryId` varchar(32) NOT NULL COMMENT '库存id',
  `giftId` varchar(32) NOT NULL COMMENT '礼品编号',
  `giftcolor` varchar(32) default NULL COMMENT '礼品颜色',
  `giftinventory` int(11) default NULL COMMENT '礼品库存',
  `giftsize` varchar(32) default NULL COMMENT '礼品尺寸',
  `giftimage` varchar(500) default NULL COMMENT '礼品图片',
  PRIMARY KEY  (`inventoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# Dumping data for table redeempoint.gift_inventory: ~0 rows (approximately)
/*!40000 ALTER TABLE `gift_inventory` DISABLE KEYS */;
/*!40000 ALTER TABLE `gift_inventory` ENABLE KEYS */;


# Dumping structure for table redeempoint.menubutton
DROP TABLE IF EXISTS `menubutton`;
CREATE TABLE IF NOT EXISTS `menubutton` (
  `button_id` varchar(32) NOT NULL COMMENT '按钮ID',
  `button_name` varchar(500) character set utf8 collate utf8_bin default NULL COMMENT '按钮名称',
  `button_text` varchar(500) default NULL,
  `menu_id` varchar(32) character set utf8 collate utf8_bin default NULL COMMENT '所属菜单ID',
  `button_url` varchar(500) default NULL COMMENT '按钮路径',
  `button_show` varchar(10) default NULL COMMENT '是否显示',
  `button_css` varchar(20) default NULL COMMENT '按钮样式',
  `handler` varchar(100) default NULL COMMENT '要处理的事件',
  PRIMARY KEY  (`button_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=528;

# Dumping data for table redeempoint.menubutton: ~31 rows (approximately)
/*!40000 ALTER TABLE `menubutton` DISABLE KEYS */;
INSERT INTO `menubutton` (`button_id`, `button_name`, `button_text`, `menu_id`, `button_url`, `button_show`, `button_css`, `handler`) VALUES
	('402880e430e992d60130e9d0267b0039', 'button_addButton', '添加按钮', '402881eb2452211b012452264ad70049', '/button/buttonManageAdd.action?method=addButton', 'yes', 'table_add', 'addButton'),
	('402880e430eb58470130eb5d5f880001', 'user_addUser', '添加用户', '402880e428befea20128bf01b8300003', '/user/userManageAdd.action?method=addUser', 'yes', 'table_add', 'addUser'),
	('402880e430eb58470130eb5e584e0002', 'user_editUser', '编辑用户', '402880e428befea20128bf01b8300003', '/user/userManageEdit.action?method=editUser', 'yes', 'table_edit', 'editUser'),
	('402880e430eb58470130eb5f22870003', 'user_deleteUser', '删除用户', '402880e428befea20128bf01b8300003', '/user/userManageDelete.action?method=deleteUser', 'yes', 'table_delete', 'deleteUser'),
	('402880e430eb58470130eb6112ea0004', 'role_addRole', '添加角色', '402880e428befea20128bf029c1a0005', '/role/roleManageAdd.action?method=addRole', 'yes', 'table_add', 'addRole'),
	('402880e430eb58470130eb61cf4a0005', 'role_editRole', '修改角色', '402880e428befea20128bf029c1a0005', '/role/roleManageEdit.action?method=editRole', 'yes', 'table_edit', 'editRole'),
	('402880e430eb58470130eb62d0f70006', 'role_deleteRole', '删除角色', '402880e428befea20128bf029c1a0005', '/role/roleManageDelete.action?method=deleteRole', 'yes', 'table_delete', 'deleteRole'),
	('402880e431b3e2c40131b3e59b960001', 'list_codelistdata', '数据标准值列表', '402880e430e992d60130e9cca8980003', '/codelist/codeDataList.action?method=codeDataList', 'no', 'none', ''),
	('402880e431b3e2c40131b3e63d700002', 'list_codelist', '数据标准管理', '402880e430e992d60130e9cca8980003', '/codelist/codeList.action?method=codeList', 'yes', 'table_gear', 'listCodeList'),
	('402880e431b3e2c40131b3ea37b7002b', 'add_codedatalist', '添加数据标准', '402880e430e992d60130e9cca8980003', '/codelist/codeDataManageAdd.action?method=codeDataManageAdd', 'yes', 'table_add', 'addCodeData'),
	('402880e431b3e2c40131b3eaff23002c', 'edit_codedatalist', '修改数据标准', '402880e430e992d60130e9cca8980003', '/codelist/codeDataManageEdit.action?method=codeDataManageEdit', 'yes', 'table_edit', 'editCodeData'),
	('402880e431b3e2c40131b3ebadc8002d', 'delete_codedatalist', '删除数据标准', '402880e430e992d60130e9cca8980003', '/codelist/codeDataManageDel.action?method=codeDataManageDelete', 'yes', 'table_delete', 'deleteCodeData'),
	('402881e423cdce810123cdd2c2bc0004', 'authorize_user', '授权用户(勿删)', '402880e428befea20128bf04ae450021', '/right/authorizeUser.action?method=showAuthorizeUser', 'no', 'none', ''),
	('402881e82505b3a5012505b666990004', 'authorize_menu', '授权树(勿删)', '402880e428befea20128bf04ae450021', '/right/authorizeMenu.action?method=showAuthorizeMenu', 'no', 'none', ''),
	('4af49536275fac9401275faf59c30008', 'button_list', '按钮列表', '402881eb2452211b012452264ad70049', '/button/buttonManageList.action?method=buttonList', 'no', 'none', ''),
	('4af49536275fac9401275fb06f00000a', 'button_editButton', '修改按钮', '402881eb2452211b012452264ad70049', '/button/buttonManageEdit.action?method=editButton', 'yes', 'table_edit', 'editButton'),
	('4af49536276143d301276145131d0003', 'button_deleteButton', '删除按钮', '402881eb2452211b012452264ad70049', '/button/buttonManageDelete.action?method=deleteButton', 'yes', 'table_delete', 'deleteButton'),
	('4af4953627d6f4ff0127d708d95b0019', 'menu_list', '菜单列表', '402880e4270e8f7c01270eaaa5730041', '/menu/menuManage.action?method=menuList', 'no', 'none', ''),
	('4af495362813e685012813f3ec120003', 'menu_addMenu', '添加菜单', '402880e4270e8f7c01270eaaa5730041', '/menu/menuManageAdd.action?method=addMenu', 'yes', 'table_add', 'addMenu'),
	('4af495362813e685012813f4f53f0005', 'menu_editMenu', '修改菜单', '402880e4270e8f7c01270eaaa5730041', '/menu/menuManageEdit.action?method=editMenu', 'yes', 'table_edit', 'editMenu'),
	('4af49536281a5f0301281a6032640003', 'menu_deleteMenu', '删除菜单', '402880e4270e8f7c01270eaaa5730041', '/menu/menuManageDelete.action?method=deleteMenu', 'yes', 'table_delete', 'deleteMenu'),
	('8ac388eb314562a601314566b0b10024', 'giftManage_list', '礼品列表', '8ac388eb311df21701311df3d9190001', '/gift/giftList.action?method=giftList', 'no', 'none', ''),
	('8ac388eb314562a601314567a5e90025', 'giftManage_add', '添加礼品', '8ac388eb311df21701311df3d9190001', '/gift/giftManageAdd.action?method=addGift', 'yes', 'table_add', 'addGift'),
	('8ac388eb314aae6401314bfdb3680035', 'giftManage_edit', '编辑礼品信息', '8ac388eb311df21701311df3d9190001', '/gift/giftManageEdit.action?method=editGift', 'yes', 'table_edit', 'editGift'),
	('8ac388eb314aae6401314bfe88a80036', 'giftManage_delete', '删除礼品信息', '8ac388eb311df21701311df3d9190001', '/gift/giftManageDelete.action?method=deleteGift', 'yes', 'table_delete', 'deleteGift'),
	('ff808081297247bf0129724a54300004', 'user_list', '用户列表', '402880e428befea20128bf01b8300003', '/user/userList.action?method=userManageList', 'no', 'none', ''),
	('ff80808129a30cbc0129a30e45860005', 'role_list', '角色列表', '402880e428befea20128bf029c1a0005', '/role/roleList.action?method=roleManageList', 'no', 'none', ''),
	('ff80808129de49ac0129de4b3ecc0005', 'authorizeForUser', '增加授权用户', '402880e428befea20128bf04ae450021', '/right/authorizeUserAdd.action?method=authorizeUserAdd', 'yes', 'table_add', 'addAuthorizeUser'),
	('ff80808129e37c8b0129e37dbe0c0005', 'authorize_rught_menu', '保存角色权限菜单', '402880e428befea20128bf04ae450021', '/right/authorizeRoleMenu.action?method=updateAuthorizeRoleMenu', 'yes', 'table_save', NULL),
	('ff8080812c487f3a012c48e306290007', 'authorize_list', '删除授权用户', '402880e428befea20128bf04ae450021', '/right/authorizeUserDelete.action?method=authorizeUserDelete', 'yes', 'table_delete', 'deleteAuthorizeUser'),
	('ff8080812c487f3a012c48e3c02c0009', 'authorize_role', '授权角色列表(勿删)', '402880e428befea20128bf04ae450021', '/right/authorizeRole.action?method=showAuthorizeRole', 'no', 'none', '');
/*!40000 ALTER TABLE `menubutton` ENABLE KEYS */;


# Dumping structure for table redeempoint.menu_info
DROP TABLE IF EXISTS `menu_info`;
CREATE TABLE IF NOT EXISTS `menu_info` (
  `menu_id` varchar(32) NOT NULL,
  `menu_name` varchar(100) default NULL,
  `page_path` varchar(500) default NULL,
  `menu_level` varchar(32) default NULL,
  `parent_menu` varchar(32) default NULL,
  `is_leave` varchar(2) default NULL,
  PRIMARY KEY  (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=1365;

# Dumping data for table redeempoint.menu_info: ~12 rows (approximately)
/*!40000 ALTER TABLE `menu_info` DISABLE KEYS */;
INSERT INTO `menu_info` (`menu_id`, `menu_name`, `page_path`, `menu_level`, `parent_menu`, `is_leave`) VALUES
	('402880e4270e8f7c01270ea42f020004', '权限管理', '/map/map.action?method=begin', '', '402880e430e992d60130e9cab80b0002', '0'),
	('402880e4270e8f7c01270ea9ff4c0025', '系统资源管理', '/main.action', NULL, '402880e4270e8f7c01270ea42f020004', '0'),
	('402880e4270e8f7c01270eaaa5730041', '菜单管理', '/menu/beginMenuManage.action?method=begin', NULL, '402880e4270e8f7c01270ea9ff4c0025', '1'),
	('402880e428befea20128bf01b8300003', '用户管理', '/user/userManage.action?method=begin', NULL, '402880e4270e8f7c01270ea42f020004', '1'),
	('402880e428befea20128bf029c1a0005', '角色管理', '/role/roleManage.action?method=begin', NULL, '402880e4270e8f7c01270ea42f020004', '1'),
	('402880e428befea20128bf04ae450021', '授权管理', '/right/authorize.action?method=begin', NULL, '402880e4270e8f7c01270ea42f020004', '1'),
	('402880e430e992d60130e9c8b54d0001', '系统后台管理', '/main.action', '', NULL, '0'),
	('402880e430e992d60130e9cab80b0002', '系统管理', '/mian.action', '', '402880e430e992d60130e9c8b54d0001', '0'),
	('402880e430e992d60130e9cca8980003', '数据字典管理', '/codelist/codelist.action?method=begin', '', '402880e430e992d60130e9cab80b0002', '1'),
	('402881eb2452211b012452264ad70049', '按钮管理', '/button/buttonManage.action?method=begin', NULL, '402880e4270e8f7c01270ea9ff4c0025', '1'),
	('8ac388eb311df21701311df3d9190001', '供应商礼品管理', '/gift/giftManage.action?method=begin', '', '8ac388eb314562a601314564ea0f0001', '1'),
	('8ac388eb314562a601314564ea0f0001', '供应商后台管理', '/main.action', '', NULL, '0');
/*!40000 ALTER TABLE `menu_info` ENABLE KEYS */;


# Dumping structure for table redeempoint.point_system_codelist
DROP TABLE IF EXISTS `point_system_codelist`;
CREATE TABLE IF NOT EXISTS `point_system_codelist` (
  `codeid` varchar(32) NOT NULL COMMENT '代码ID',
  `codename` varchar(500) default NULL COMMENT '代码名称',
  PRIMARY KEY  (`codeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=8192 COMMENT='系统数据标准表';

# Dumping data for table redeempoint.point_system_codelist: ~2 rows (approximately)
/*!40000 ALTER TABLE `point_system_codelist` DISABLE KEYS */;
INSERT INTO `point_system_codelist` (`codeid`, `codename`) VALUES
	('1', '性别'),
	('2', '地区');
/*!40000 ALTER TABLE `point_system_codelist` ENABLE KEYS */;


# Dumping structure for table redeempoint.point_system_codelist_data
DROP TABLE IF EXISTS `point_system_codelist_data`;
CREATE TABLE IF NOT EXISTS `point_system_codelist_data` (
  `dataid` varchar(32) NOT NULL COMMENT '数据ID',
  `codeid` varchar(32) default NULL COMMENT '数据标准ID',
  `datakey` varchar(32) default NULL COMMENT '数据key',
  `datavalue` varchar(500) default NULL COMMENT '数据值',
  `parentdatakey` varchar(32) default NULL COMMENT '父级数据ID',
  `remark` varchar(500) default NULL COMMENT '备注',
  PRIMARY KEY  (`dataid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=3276 COMMENT='系统数据标准数据表';

# Dumping data for table redeempoint.point_system_codelist_data: ~5 rows (approximately)
/*!40000 ALTER TABLE `point_system_codelist_data` DISABLE KEYS */;
INSERT INTO `point_system_codelist_data` (`dataid`, `codeid`, `datakey`, `datavalue`, `parentdatakey`, `remark`) VALUES
	('1', '1', '1', '男', NULL, NULL),
	('2', '1', '2', '女', NULL, NULL),
	('3', '2', '10000', '四川', NULL, NULL),
	('4', '2', '10001', '达州', '10000', NULL),
	('5', '2', '10002', '大竹', '10001', NULL);
/*!40000 ALTER TABLE `point_system_codelist_data` ENABLE KEYS */;


# Dumping structure for table redeempoint.right_info
DROP TABLE IF EXISTS `right_info`;
CREATE TABLE IF NOT EXISTS `right_info` (
  `right_id` varchar(32) NOT NULL COMMENT '权限ID',
  `right_name` varchar(256) default NULL COMMENT '权限名称',
  `role_id` varchar(32) character set utf8 collate utf8_bin default NULL COMMENT '角色ID',
  `menu_id` varchar(32) character set utf8 collate utf8_bin default NULL COMMENT '菜单ID',
  `button_id` varchar(32) character set utf8 collate utf8_bin default NULL COMMENT '按钮ID',
  PRIMARY KEY  (`right_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=512;

# Dumping data for table redeempoint.right_info: ~32 rows (approximately)
/*!40000 ALTER TABLE `right_info` DISABLE KEYS */;
INSERT INTO `right_info` (`right_id`, `right_name`, `role_id`, `menu_id`, `button_id`) VALUES
	('402880e431b3e2c40131b3ee859b003a', NULL, '1', NULL, '4af4953627d6f4ff0127d708d95b0019'),
	('402880e431b3e2c40131b3ee859b003b', NULL, '1', NULL, '4af495362813e685012813f3ec120003'),
	('402880e431b3e2c40131b3ee859b003c', NULL, '1', NULL, '4af495362813e685012813f4f53f0005'),
	('402880e431b3e2c40131b3ee859b003d', NULL, '1', NULL, '4af49536281a5f0301281a6032640003'),
	('402880e431b3e2c40131b3ee859b003e', NULL, '1', NULL, '402880e430e992d60130e9d0267b0039'),
	('402880e431b3e2c40131b3ee859b003f', NULL, '1', NULL, '4af49536275fac9401275faf59c30008'),
	('402880e431b3e2c40131b3ee859b0040', NULL, '1', NULL, '4af49536275fac9401275fb06f00000a'),
	('402880e431b3e2c40131b3ee859b0041', NULL, '1', NULL, '4af49536276143d301276145131d0003'),
	('402880e431b3e2c40131b3ee859b0042', NULL, '1', NULL, '402880e430eb58470130eb5d5f880001'),
	('402880e431b3e2c40131b3ee859b0043', NULL, '1', NULL, '402880e430eb58470130eb5e584e0002'),
	('402880e431b3e2c40131b3ee859b0044', NULL, '1', NULL, '402880e430eb58470130eb5f22870003'),
	('402880e431b3e2c40131b3ee859b0045', NULL, '1', NULL, 'ff808081297247bf0129724a54300004'),
	('402880e431b3e2c40131b3ee859b0046', NULL, '1', NULL, '402880e430eb58470130eb6112ea0004'),
	('402880e431b3e2c40131b3ee859c0047', NULL, '1', NULL, '402880e430eb58470130eb61cf4a0005'),
	('402880e431b3e2c40131b3ee859c0048', NULL, '1', NULL, '402880e430eb58470130eb62d0f70006'),
	('402880e431b3e2c40131b3ee859c0049', NULL, '1', NULL, 'ff80808129a30cbc0129a30e45860005'),
	('402880e431b3e2c40131b3ee859c004a', NULL, '1', NULL, '402881e423cdce810123cdd2c2bc0004'),
	('402880e431b3e2c40131b3ee859c004b', NULL, '1', NULL, '402881e82505b3a5012505b666990004'),
	('402880e431b3e2c40131b3ee859c004c', NULL, '1', NULL, 'ff80808129de49ac0129de4b3ecc0005'),
	('402880e431b3e2c40131b3ee859c004d', NULL, '1', NULL, 'ff80808129e37c8b0129e37dbe0c0005'),
	('402880e431b3e2c40131b3ee859c004e', NULL, '1', NULL, 'ff8080812c487f3a012c48e306290007'),
	('402880e431b3e2c40131b3ee859c004f', NULL, '1', NULL, 'ff8080812c487f3a012c48e3c02c0009'),
	('402880e431b3e2c40131b3ee85c10050', NULL, '1', NULL, '402880e431b3e2c40131b3e59b960001'),
	('402880e431b3e2c40131b3ee85c10051', NULL, '1', NULL, '402880e431b3e2c40131b3e63d700002'),
	('402880e431b3e2c40131b3ee85c20052', NULL, '1', NULL, '402880e431b3e2c40131b3ea37b7002b'),
	('402880e431b3e2c40131b3ee85c20053', NULL, '1', NULL, '402880e431b3e2c40131b3eaff23002c'),
	('402880e431b3e2c40131b3ee85c20054', NULL, '1', NULL, '402880e431b3e2c40131b3ebadc8002d'),
	('402880e431b3e2c40131b3ee85c20055', NULL, '1', NULL, '8ac388eb314562a601314566b0b10024'),
	('402880e431b3e2c40131b3ee85c20056', NULL, '1', NULL, '8ac388eb314562a601314567a5e90025'),
	('402880e431b3e2c40131b3ee85c20057', NULL, '1', NULL, '8ac388eb314aae6401314bfdb3680035'),
	('402880e431b3e2c40131b3ee85c20058', NULL, '1', NULL, '8ac388eb314aae6401314bfe88a80036'),
	('8ac388eb31402e2001314059cf0c0006', NULL, '2', NULL, 'ff80808129e37c8b0129e37dbe0c0005');
/*!40000 ALTER TABLE `right_info` ENABLE KEYS */;


# Dumping structure for table redeempoint.rolemenu
DROP TABLE IF EXISTS `rolemenu`;
CREATE TABLE IF NOT EXISTS `rolemenu` (
  `ID` varchar(32) character set utf8 collate utf8_bin NOT NULL,
  `roleId` varchar(32) character set utf8 collate utf8_bin default NULL,
  `menuId` varchar(32) character set utf8 collate utf8_bin default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=963;

# Dumping data for table redeempoint.rolemenu: ~17 rows (approximately)
/*!40000 ALTER TABLE `rolemenu` DISABLE KEYS */;
INSERT INTO `rolemenu` (`ID`, `roleId`, `menuId`) VALUES
	('402880e431b3e2c40131b3ee8599002e', '1', '402880e430e992d60130e9c8b54d0001'),
	('402880e431b3e2c40131b3ee8599002f', '1', '402880e430e992d60130e9cab80b0002'),
	('402880e431b3e2c40131b3ee85990030', '1', '402880e4270e8f7c01270ea42f020004'),
	('402880e431b3e2c40131b3ee85990031', '1', '402880e4270e8f7c01270ea9ff4c0025'),
	('402880e431b3e2c40131b3ee85990032', '1', '402880e4270e8f7c01270eaaa5730041'),
	('402880e431b3e2c40131b3ee85990033', '1', '402881eb2452211b012452264ad70049'),
	('402880e431b3e2c40131b3ee85990034', '1', '402880e428befea20128bf01b8300003'),
	('402880e431b3e2c40131b3ee859a0035', '1', '402880e428befea20128bf029c1a0005'),
	('402880e431b3e2c40131b3ee859a0036', '1', '402880e428befea20128bf04ae450021'),
	('402880e431b3e2c40131b3ee859a0037', '1', '402880e430e992d60130e9cca8980003'),
	('402880e431b3e2c40131b3ee859a0038', '1', '8ac388eb314562a601314564ea0f0001'),
	('402880e431b3e2c40131b3ee859a0039', '1', '8ac388eb311df21701311df3d9190001'),
	('8ac388eb31402e2001314059cf0c0001', '2', '402880e430e992d60130e9c8b54d0001'),
	('8ac388eb31402e2001314059cf0c0002', '2', '402880e430e992d60130e9cab80b0002'),
	('8ac388eb31402e2001314059cf0c0003', '2', '402880e4270e8f7c01270ea42f020004'),
	('8ac388eb31402e2001314059cf0c0004', '2', '402880e428befea20128bf04ae450021'),
	('8ac388eb31402e2001314059cf0c0005', '2', '8ac388eb311df21701311df3d9190001');
/*!40000 ALTER TABLE `rolemenu` ENABLE KEYS */;


# Dumping structure for table redeempoint.role_info
DROP TABLE IF EXISTS `role_info`;
CREATE TABLE IF NOT EXISTS `role_info` (
  `role_id` varchar(32) NOT NULL,
  `role_name` varchar(256) default NULL,
  `comment` varchar(500) default NULL,
  PRIMARY KEY  (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=4096;

# Dumping data for table redeempoint.role_info: ~4 rows (approximately)
/*!40000 ALTER TABLE `role_info` DISABLE KEYS */;
INSERT INTO `role_info` (`role_id`, `role_name`, `comment`) VALUES
	('1', '系统管理员', NULL),
	('2', '客户', '客户'),
	('3', '普通用户', '用户'),
	('4', '11', '1');
/*!40000 ALTER TABLE `role_info` ENABLE KEYS */;


# Dumping structure for table redeempoint.supplier_role
DROP TABLE IF EXISTS `supplier_role`;
CREATE TABLE IF NOT EXISTS `supplier_role` (
  `ID` varchar(32) NOT NULL,
  `operater_id` varchar(32) default NULL,
  `operate_type` varchar(32) default NULL,
  `role_id` varchar(32) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=2340;

# Dumping data for table redeempoint.supplier_role: ~7 rows (approximately)
/*!40000 ALTER TABLE `supplier_role` DISABLE KEYS */;
INSERT INTO `supplier_role` (`ID`, `operater_id`, `operate_type`, `role_id`) VALUES
	('2', 'admin', '2', '1'),
	('8ac388eb314aae6401314ac543df002b', '代超', NULL, '1'),
	('8ac388eb314aae6401314ac611d0002c', 'swpigris81', NULL, '1'),
	('8ac388eb314aae6401314af0eddd002d', 'user', NULL, '3'),
	('8ac388eb314aae6401314af0eddd002e', '11111', NULL, '3'),
	('8ac388eb314aae6401314af0eddd002f', '12222', NULL, '3'),
	('8ac388eb314aae6401314af0eddd0030', '111', NULL, '3');
/*!40000 ALTER TABLE `supplier_role` ENABLE KEYS */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
