package com.kplan.phonecard.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kplan.phonecard.domain.entity.OrderStatistics;
import com.kplan.phonecard.manager.KplanSecondaryOrdersManager;
import com.kplan.phonecard.query.KplanSecondaryOrdersQuery;
import com.kplan.phonecard.utils.DateUtils;

@Controller
@RequestMapping("/order")
public class OrderStatusticsController extends AbstractBaseController{
	@Autowired
	KplanSecondaryOrdersManager kplanSecondaryOrdersManager;
	@RequestMapping("/KplanSecondaryStatustics")
	public String KplanSecondaryStatustics(Map<String, Object> map, KplanSecondaryOrdersQuery query) throws ParseException {
		if (query.getCreatedDateEnd() == null) {
			query.setCreatedDateEnd(DateUtils.getDayNum(0));
			query.setCreatedDateStart(DateUtils.getDayNum(3));
		}
		List<OrderStatistics> l=kplanSecondaryOrdersManager.KplanSecondaryStatustics(query);
		map.put("l", l);
		map.put("query", query);
		return "statistics/Backstatistics";
	}
}
