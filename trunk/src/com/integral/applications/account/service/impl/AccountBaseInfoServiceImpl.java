package com.integral.applications.account.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.math.NumberUtils;

import com.integral.applications.account.bean.AccountBaseInfo;
import com.integral.applications.account.dao.IAccountBaseInfoDAO;
import com.integral.applications.account.service.IAccountBaseInfoService;
import com.integral.common.dao.impl.BaseDao;
import com.integral.util.RequestUtil;
import com.integral.util.StringUtils;
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
    public List<AccountBaseInfo> queryPage(int start, int limit, Map<String, Object> params) {
        String hql = "from AccountBaseInfo acctinfo where acctinfo.username = :userName";
        return this.baseDao.queryPageByHQL(hql, params, start, limit);
    }
    
    @Override
    public int queryPageSize(Map<String, Object> params) {
        String sql = "select count(baseinfoid) from accountbaseinfo where username = :userName";
        List list = this.baseDao.queryPageBySQL(sql, params, -1, -1);
        if(list != null && !list.isEmpty()){
            return NumberUtils.toInt(String.valueOf(list.get(0)), 0);
        }
        return 0;
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

    @Override
    public void chargeAccount(double outAmount, double inAmount, String comment, String cardId, String userName) {
        AccountBaseInfo baseInfo = new AccountBaseInfo();
        baseInfo.setUsername(userName);
        baseInfo.setAccountenter(inAmount);
        baseInfo.setAccountout(outAmount);
        baseInfo.setRemark(comment);
        baseInfo.setAccountcard(cardId);
        baseInfo.setAccountmargin(new BigDecimal(""+inAmount).add(new BigDecimal(""+outAmount).negate()).doubleValue());
        
        Date today = new Date();
        baseInfo.setBasedate(today);
        baseInfo.setBasemonth(Tools.dateToString(today, "yyyy-MM"));
        baseInfo.setBaseyear(Tools.dateToString(today, "yyyy"));
        
        baseInfo.setDeletetag("0");//未删除
        baseInfo.setMargintag("0");//未结算
        
        this.accountDao.save(baseInfo);
    }

    @Override
    public void update(AccountBaseInfo transientInstance) {
        this.accountDao.update(transientInstance);
    }

    @Override
    public List<AccountBaseInfo> queryListByIds(String[] baseIds) {
        if(baseIds == null || baseIds.length <1){
            return null;
        }
        StringBuilder hql = new StringBuilder("from AccountBaseInfo model where 1=1 and model.baseinfoid in ( ");
        for(int i=0; i< baseIds.length; i++){
            if(i == baseIds.length -1){
                hql.append(" ? )");
            }else{
                hql.append(" ? , ");
            }
        }
        List list = this.baseDao.queryByHQL(hql.toString(), baseIds);
        return list;
    }

    @Override
    public List<AccountBaseInfo> queryAccountBaseInfoBudgetPage(int start, int limit, Map<String, Object> paramMap) {
        String sql = "select model.baseinfoid, model.basedate, model.baseyear, model.basemonth, model.accountenter," +
        		" model.accountout, model.accountmargin, model.remark, model.deletetag, model.accountcard," +
        		" model.margintag, model.userid, model.username, model.maintype, model.setype, IFNULL(alert.alertvalue, -1) alertvalue" +
        		" from accountbaseinfo model left join accountalert alert on( " +
        		" model.basemonth = DATE_FORMAT(alert.begindate, '%Y-%m') " +
        		" and model.username = alert.username ) where model.username=?";
        //List list = this.accountDao.queryAccountBaseInfoBudgetPage(sql, start, limit, paramMap);
        List<Object []> list = this.baseDao.queryListByPageByJDBC(sql, start, limit, paramMap.values().toArray());
        List<AccountBaseInfo> resutList = null;
        if(list != null && !list.isEmpty()){
            resutList = new ArrayList<AccountBaseInfo>(list.size());
            for(int i=0, j = list.size(); i<j; i++){
                Object []obj = list.get(i);
                AccountBaseInfo info = new AccountBaseInfo();
                info.setBaseinfoid(StringUtils.convertToString(obj[0], ""));
                info.setBasedate((Date) obj[1]);
                info.setBaseyear(StringUtils.convertToString(obj[2], ""));
                info.setBasemonth(StringUtils.convertToString(obj[3], ""));
                info.setAccountenter(((BigDecimal)obj[4]).doubleValue());
                info.setAccountout(((BigDecimal)obj[5]).doubleValue());
                info.setAccountmargin(((BigDecimal)obj[6]).doubleValue());
                info.setRemark(StringUtils.convertToString(obj[7], ""));
                info.setDeletetag(StringUtils.convertToString(obj[8], ""));
                info.setAccountcard(StringUtils.convertToString(obj[9], ""));
                info.setMargintag(StringUtils.convertToString(obj[10], ""));
                info.setUserid(StringUtils.convertToString(obj[11], ""));
                info.setUsername(StringUtils.convertToString(obj[12], ""));
                info.setMaintype(StringUtils.convertToString(obj[13], ""));
                info.setSetype(StringUtils.convertToString(obj[14], ""));
                info.setAccountalertmon(((BigDecimal)obj[15]).doubleValue());
                resutList.add(info);
            }
        }
        return resutList;
    }
}
