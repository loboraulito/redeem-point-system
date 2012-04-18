package com.integral.applications.account.service.impl;

import java.util.Collection;
import java.util.List;

import com.integral.applications.account.bean.BalanceInfo;
import com.integral.applications.account.dao.IBalanceInfoDAO;
import com.integral.applications.account.service.IBalanceInfoService;

public class BalanceInfoServiceImpl implements IBalanceInfoService {
	private IBalanceInfoDAO balanceDao;

	public IBalanceInfoDAO getBalanceDao() {
		return balanceDao;
	}

	public void setBalanceDao(IBalanceInfoDAO balanceDao) {
		this.balanceDao = balanceDao;
	}

	@Override
	public void save(BalanceInfo transientInstance) {
		this.balanceDao.save(transientInstance);
	}

	@Override
	public void delete(BalanceInfo persistentInstance) {
		this.balanceDao.delete(persistentInstance);
	}

	@Override
	public void deleteAll(Collection persistentInstance) {
		this.balanceDao.deleteAll(persistentInstance);
	}

	@Override
	public List findByExample(BalanceInfo instance) {
		return this.balanceDao.findByExample(instance);
	}

	@Override
	public BalanceInfo findById(String id) {
		return this.balanceDao.findById(id);
	}

	@Override
	public void saveOrUpdate(BalanceInfo instance) {
		this.balanceDao.saveOrUpdate(instance);
	}

	@Override
	public void saveOrUpdateAll(Collection instance) {
		this.balanceDao.saveOrUpdateAll(instance);
	}

	public List findByExampleOrderBy(BalanceInfo instance) {
		return this.balanceDao.findByExampleOrderBy(instance);
	}
}
