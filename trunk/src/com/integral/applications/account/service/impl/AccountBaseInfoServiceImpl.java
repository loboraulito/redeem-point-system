package com.integral.applications.account.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.integral.applications.account.bean.AccountBaseInfo;
import com.integral.applications.account.dao.IAccountBaseInfoDAO;
import com.integral.applications.account.service.IAccountBaseInfoService;
import com.integral.common.dao.impl.BaseDao;
import com.integral.util.RequestUtil;
import com.integral.util.Tools;

public class AccountBaseInfoServiceImpl implements IAccountBaseInfoService {
	private IAccountBaseInfoDAO accountDao;
	private BaseDao baseDao;

	public IAccountBaseInfoDAO getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(IAccountBaseInfoDAO accountDao) {
		this.accountDao = accountDao;
	}
	

	public BaseDao getBaseDao() {
        return baseDao;
    }

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
	public void delete(AccountBaseInfo persistentInstance) {
		this.accountDao.delete(persistentInstance);
	}

	@Override
	public void deleteAll(Collection persistentInstance) {
		this.accountDao.deleteAll(persistentInstance);
	}

	@Override
	public List findAll() {
		return this.accountDao.findAll();
	}

	@Override
	public List findByExample(AccountBaseInfo instance) {
		return this.accountDao.findByExample(instance);
	}

	@Override
	public AccountBaseInfo findById(String id) {
		return this.accountDao.findById(id);
	}

	@Override
	public void save(AccountBaseInfo transientInstance) {
		this.accountDao.save(transientInstance);
	}

	@Override
	public void saveOrUpdate(AccountBaseInfo instance) {
		this.accountDao.saveOrUpdate(instance);
	}

	@Override
	public void saveOrUpdateAll(Collection instance) {
		this.accountDao.saveOrUpdateAll(instance);
	}

	@Override
	public Double[] marginAccount(List accounts) {
		if(accounts==null || accounts.size()<1){
			return null;
		}
		//收入
		BigDecimal encount = new BigDecimal("0");
		//支出
		BigDecimal outcount = new BigDecimal("0");
		//结算余额
		BigDecimal margin = new BigDecimal("0");
		//总收入减去总支出即是结算结果
		for(int i=0;i<accounts.size();i++){
			AccountBaseInfo info = (AccountBaseInfo) accounts.get(i);
			encount = encount.add(new BigDecimal(""+info.getAccountenter()));
			outcount = outcount.add(new BigDecimal(""+info.getAccountout()));
		}
		margin = encount.add(outcount.negate()).setScale(2, RoundingMode.HALF_DOWN);
		Double [] returnString = new Double[]{encount.doubleValue(),outcount.doubleValue(),margin.doubleValue()};
		return returnString;
	}
	/**
	 * 根据属性查询记账信息
	 * @author swpigris81@126.com
	 * Description:
	 * @param properties
	 * @return
	 */
	public List findByProperty(Map<String,Object> properties){
		return this.accountDao.findByProperty(properties);
	}

	@Override
	public List queryByProperty(Map<String, Object> properties) {
		return this.accountDao.queryByProperty(properties);
	}
	
	public List queryBySql(String sql){
		return this.accountDao.queryBySql(sql);
	}
	
	/**
	 * 排序查询，findByExample的扩展
	 * @param instance
	 * @return
	 */
	public List findByExampleOrderBy(AccountBaseInfo instance){
		return this.accountDao.findByExampleOrderBy(instance);
	}

	@Override
	public Object[] checkImportData(List list, Map properties, String userId, String userName) {
		if(list==null || list.size()<1){
			return null;
		}
		if(userId == null || "".equals(userId) || userName == null || "".equals(userName)){
			return new Object[]{true,"用户身份验证失败！缺少用户信息！"};
		}
		//Object[] obj = null;
		for(int i=0,j=list.size();i<j;i++){
			Map map = (Map) list.get(i);
			AccountBaseInfo baseInfo = new AccountBaseInfo();
			//校验日期时间是否正常
			if(!Tools.checkDate(map.get("basedate"))){
				//校验失败，日期不正确
				return new Object[]{true,"日期校验失败，您输入了一个不正确的日期："+map.get("basedate")+""};
			}
			
			RequestUtil.replaceNull(map);
			try{
				RequestUtil.repalceAsDate(map, "basedate", "yyyy-MM-dd");
			}catch(Exception e){
				return new Object[]{true,"数据校验失败："+e.toString()};
			}
			
			Set ketSet = map.keySet();
			if(!ketSet.containsAll(properties.values())){
				//校验失败，日期不正确
				return new Object[]{true,"文件内容请按照【导出模板】的格式，导入正确的文件内容！"};
			}
			
			Date basseDate = (Date) map.get("basedate");
			String baseMonth = Tools.dateToString((Date)map.get("basedate"), "yyyy-MM");
			Map queryMap = new HashMap();
			//剔除已经被删掉的账目信息
			queryMap.put("deletetag", "0");
			//当月的账目已经结算，则校验失败
			queryMap.put("margintag", "1");
			queryMap.put("basemonth", baseMonth);
			queryMap.put("userid", userId);
			queryMap.put("username", userName);
			
			List lists = this.accountDao.findByProperty(queryMap);
			if(lists!=null && lists.size()>0){
				//校验失败，数据库中存在当月未被删除的已经结算的记录
				return new Object[]{true,baseMonth+" 月份的账目信息已经结算，您不能再添加该月的账目信息！"};
			}
			//当年的账目已经结算，则校验失败（实际上无需）
			queryMap.clear();
			queryMap.put("deletetag", "0");
			queryMap.put("basedate", basseDate);
			queryMap.put("userid", userId);
			queryMap.put("username", userName);
			
			//当天的账目已经存在则校验失败
			lists = this.accountDao.findByProperty(queryMap);
			if(lists!=null && lists.size()>0){
				//校验失败，数据库中存在当天未被删除的记录
				return new Object[]{true,"已经存在 "+Tools.dateToString(basseDate,"yyyy年MM月dd日")+" 的账目信息，您不能再添加当天的账目信息！"};
			}
		}
		return null;
	}
}
