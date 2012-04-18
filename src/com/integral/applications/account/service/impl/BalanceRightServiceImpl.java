package com.integral.applications.account.service.impl;

import java.util.Collection;
import java.util.List;

import com.integral.applications.account.bean.BalanceRight;
import com.integral.applications.account.dao.IBalanceRightDAO;
import com.integral.applications.account.service.IBalanceRightService;

public class BalanceRightServiceImpl implements IBalanceRightService {

	private IBalanceRightDAO rightDao;

	public IBalanceRightDAO getRightDao() {
		return rightDao;
	}

	public void setRightDao(IBalanceRightDAO rightDao) {
		this.rightDao = rightDao;
	}

	@Override
	public void delete(BalanceRight persistentInstance) {
		this.rightDao.delete(persistentInstance);
	}

	@Override
	public void deleteAll(Collection<BalanceRight> persistentInstance) {
		this.rightDao.deleteAll(persistentInstance);
	}

	@Override
	public List findByExample(BalanceRight instance) {
		return this.rightDao.findByExample(instance);
	}

	@Override
	public BalanceRight findById(String id) {
		return this.rightDao.findById(id);
	}

	@Override
	public void save(BalanceRight transientInstance) {
		this.rightDao.save(transientInstance);
	}

	@Override
	public void saveOrUpdate(BalanceRight instance) {
		this.rightDao.saveOrUpdate(instance);
	}

	@Override
	public List findBalanceRightByPage(int start, int limit, String[] params) {
		return this.rightDao.findBalanceRightByPage(start, limit, params);
	}

}
