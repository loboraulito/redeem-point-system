package com.integral.applications.account.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.integral.applications.account.bean.AccountAlert;
import com.integral.applications.account.bean.AccountBaseInfo;
import com.integral.applications.account.bean.BalanceInfo;
import com.integral.applications.account.dao.IAccountAlertDAO;
import com.integral.applications.account.service.IAccountAlertService;

public class AccountAlertServiceImpl implements IAccountAlertService {
	private static final String SHOWDATEALERTVALUE = "from AccountAlert sm where sm.begindate<=? and sm.enddate>=? and sm.userid=? and sm.username=? and sm.alerttype=? ";
	private IAccountAlertDAO alertDao;

	public IAccountAlertDAO getAlertDao() {
		return alertDao;
	}

	public void setAlertDao(IAccountAlertDAO alertDao) {
		this.alertDao = alertDao;
	}

	@Override
	public void delete(AccountAlert transientInstance) {
		this.alertDao.delete(transientInstance);
	}

	@Override
	public void deleteAll(Collection transientInstance) {
		this.alertDao.deleteAll(transientInstance);
	}

	@Override
	public List findByExample(AccountAlert instance) {
		return this.alertDao.findByExample(instance);
	}

	@Override
	public AccountAlert findById(String id) {
		return this.alertDao.findById(id);
	}

	@Override
	public void save(AccountAlert transientInstance) {
		this.alertDao.save(transientInstance);
	}

	@Override
	public void saveOrUpdate(AccountAlert instance) {
		this.alertDao.saveOrUpdate(instance);
	}

	@Override
	public void saveOrUpdateAll(Collection instance) {
		this.alertDao.saveOrUpdateAll(instance);
	}

	@Override
	public List findByCustom(Object[] params) {
		return this.alertDao.findByCustom(params);
	}

	@Override
	public List findByCustom(String sql, Object[] params) {
		if(sql == null || "".equals(sql)){
			sql = SHOWDATEALERTVALUE;
		}
		return this.alertDao.findByCustom(sql, params);
	}

	@Override
	public List doWithAccountBaseInfo(List baseInfo) {
		List infos = new ArrayList();
		if(baseInfo!=null){
			for(int i=0,j=baseInfo.size();i<j;i++){
				AccountBaseInfo info = (AccountBaseInfo) baseInfo.get(i);
				//1:日警告类型
				Object []param = new Object[]{info.getBasedate(),info.getUserid(),info.getUsername(),"1"};
				//2:月经过类型
				Object []paramMon = new Object[]{info.getBasedate(),info.getUserid(),info.getUsername(),"2"};
				List list = this.findByCustom("", param);
				List listMon = this.findByCustom("", paramMon);
				if(list!=null && list.size()>0){
					AccountAlert alert = (AccountAlert) list.get(0);
					info.setAccountalert(alert.getAlertvalue());
				}
				if(listMon!=null && listMon.size()>0){
					AccountAlert alert = (AccountAlert) listMon.get(0);
					info.setAccountalertmon(alert.getAlertvalue());
				}
				infos.add(info);
			}
		}
		return infos;
	}
	
	public List balanceAlertInfo(List list) {
		List infos = new ArrayList();
		if(list==null){
			return null;
		}
		for(int i=0,j=list.size();i<j;i++){
			BalanceInfo balance = (BalanceInfo) list.get(i);
			//年度警告。日警告以及月警告请参考com.systemsoft.application.accountbalance.service.impl.AccountAlertServiceImpl.doWithAccountBaseInfo(list)方法
			Object []param = new Object[]{balance.getBegindate(),balance.getUserid(),balance.getUsername(),"4"};
			List listyear = this.findByCustom("", param);
			if(listyear!=null && listyear.size()>0){
				AccountAlert alert = (AccountAlert) listyear.get(0);
				balance.setAlertvalue(alert.getAlertvalue());
			}
			infos.add(balance);
		}
		return infos;
	}

	public List findByProperty(Map<String,Object> properties) {
		return this.alertDao.findByProperty(properties);
	}

}
