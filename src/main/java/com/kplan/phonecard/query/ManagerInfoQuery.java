package com.kplan.phonecard.query;

import java.util.ArrayList;
import java.util.List;

import com.kplan.phonecard.domain.ManagerInfo;

public class ManagerInfoQuery extends BaseQuery<ManagerInfo> {
	private List<Long> notinIdList = new ArrayList<>();

	public List<Long> getNotinIdList() {
		return notinIdList;
	}

	public void setNotinIdList(List<Long> notinIdList) {
		this.notinIdList = notinIdList;
	}

}
