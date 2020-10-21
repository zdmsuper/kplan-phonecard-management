package com.kplan.phonecard.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kplan.phonecard.domain.KplanChannelNumber;
import com.kplan.phonecard.domain.KplanChannelNumberDetail;
import com.kplan.phonecard.domain.ManagerInfo;
import com.kplan.phonecard.manager.KplanChannelNumberDetailManager;
import com.kplan.phonecard.manager.KplanChannelNumberManager;
import com.kplan.phonecard.query.KplanChannelNumberDetailQuery;
import com.kplan.phonecard.query.KplanChannelNumberQuery;

@Controller
@RequestMapping("/channel")
public class ChannelManageController  extends AbstractBaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ManagerInfoController.class);
	@Autowired
	KplanChannelNumberDetailManager kplanChannelNumberDetailManager;
	@Autowired
	KplanChannelNumberManager kplanChannelNumberManager;
	/**工号处理明细列表
	 * @param map
	 * @param query
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Map<String, Object> map, KplanChannelNumberDetailQuery query) {
		Page<KplanChannelNumberDetail> page = this.kplanChannelNumberDetailManager.findChannelInfos(query, this.getPageRequest());
		map.put("query", query);
		map.put("page", page);
		
		return "channel/list";
	}
	
	/** 查看工号列表
	 * @param map
	 * @param query
	 * @return
	 */
	@RequestMapping("/channellist")
	public String channelList(Map<String, Object> map, KplanChannelNumberQuery query) {
		Page<KplanChannelNumber> page=this.kplanChannelNumberManager.findChannelInfos(query, this.getPageRequest());
		map.put("query", query);
		map.put("page", page);
		return "channel/channellist";
	}
}
