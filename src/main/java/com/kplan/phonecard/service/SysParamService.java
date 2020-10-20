package com.kplan.phonecard.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kplan.phonecard.domain.SysParam;

@Service
public class SysParamService extends BaseService<SysParam> {

	public SysParamService() {
		super("未能找到系统信息");
	}
	
	/**
	 * 获取平台佣金
	 * @param money
	 * @return
	 */
	public List<SysParam> getCommissionList() {
	 	return findAll((root, query, cb) -> cb.equal(root.get("type"), 1));
	}
	
	/**
	 * 获取平台佣金
	 * @param money 订单金额
	 * @return
	 */
	public BigDecimal getCommission(BigDecimal money) {
		if(money == null) {
			return new BigDecimal(0);
		}
		BigDecimal xm = null;
		List<SysParam> list = findAll((root, query, cb) -> cb.equal(root.get("type"), 1));
		for(SysParam x : list) {
			if(x.getX() == null && money.compareTo(x.getY()) <= 0) {
				xm = x.getZ();
				break;
			}
			if(x.getY() == null && money.compareTo(x.getX()) == 1) {
				xm = x.getZ();
				break;
			}
			if(x.getX() != null && x.getY() != null && money.compareTo(x.getX()) == 1 && money.compareTo(x.getY()) <= 0) {
				xm = x.getZ();
				break;
			}
		}
		if(xm == null) {
			xm = new BigDecimal(0);
		}
		return xm;
	}
	
	/**
	 * 获取群主补贴
	 * @return
	 */
	public BigDecimal getGroupSubsidy() {
		SysParam x = findAll((root, query, cb) -> cb.equal(root.get("type"), 2)).stream().findFirst().orElse(null);
		BigDecimal xm = x == null || x.getX() == null ? new BigDecimal(0) : x.getX();
		return xm;
	}
}
