package com.kplan.phonecard.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.kplan.phonecard.domain.CoreOrdersMarketk;
import com.kplan.phonecard.domain.ManagerInfo;
import com.kplan.phonecard.domain.kplanExternalOrders;
import com.kplan.phonecard.domain.entity.ExterNalDataExcel;
import com.kplan.phonecard.domain.entity.excelOrder;
import com.kplan.phonecard.enums.kplanExternalOrderStatusEnum;
import com.kplan.phonecard.query.CoreOrdersMarketkQuery;
import com.kplan.phonecard.query.kplanExternalOrdersQuery;
import com.kplan.phonecard.query.kplanscordersQuery;
import com.kplan.phonecard.manager.*;
@Controller
@RequestMapping("/external")
public class ExterNalOrderController extends AbstractBaseController{
	@Autowired
	kplanExternalOrdersManager kplanExternalOrdersManager;
	@RequestMapping("/list")
	public String list(Map<String, Object> map, kplanExternalOrdersQuery query) {
		ManagerInfo managerInfo = super.getCurrentUserDetails().orElse(null);
		Page<kplanExternalOrders> page = this.kplanExternalOrdersManager.list(query, this.getPageRequest(),managerInfo) ;
		map.put("query",query);
		map.put("page", page);
		map.put("basicUserInfo",managerInfo.getBasicUserInfo());
	return "externalOrders/list";
	}
	@RequestMapping("/upedit")
	public String upedit(Map<String, Object> map, kplanExternalOrdersQuery query) {
		return "externalOrders/upedit";
	}
	
	@RequestMapping("/reupedit")
	public String reupedit(Map<String, Object> map, kplanExternalOrdersQuery query) {
		return "externalOrders/reupedit";
	}
	/**外围订单数据上传
	 * @param file
	 * @param query
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public Object uploadFile(@RequestParam("file") MultipartFile file, kplanExternalOrdersQuery query) throws IOException {
		ManagerInfo managerInfo = super.getCurrentUserDetails().orElse(null);
		List<Object> data = EasyExcelFactory.read(file.getInputStream(), new Sheet(1, 1));
		return this.kplanExternalOrdersManager.uploadFile(managerInfo, data);
	}
	
	/**数据回导
	 * @param file
	 * @param query
	 * @param status
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/reUpLoadFile", method = RequestMethod.POST)
	@ResponseBody
	public Object reUpLoadFile(@RequestParam("file") MultipartFile file, kplanExternalOrdersQuery query,String status) throws IOException {
		ManagerInfo managerInfo = super.getCurrentUserDetails().orElse(null);
		List<Object> data = EasyExcelFactory.read(file.getInputStream(), new Sheet(1, 2));
		return this.kplanExternalOrdersManager.reUpLoadFile(managerInfo, data, status);
	}
	@RequestMapping("/downExcel")
	public void downExcel( HttpServletResponse response,kplanExternalOrdersQuery query) {
		 String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		    String fileName = date + "，" + "订单数据表";
		       try {
		    	   response.setCharacterEncoding("UTF-8");
			        response.setContentType("application/vnd.ms-excel");
			        fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
			        List<kplanExternalOrders> l=this.kplanExternalOrdersManager.qryDownData();
			        List<ExterNalDataExcel> ex = new ArrayList<ExterNalDataExcel>();
			        if(l!=null) {
			        	ExterNalDataExcel e;
			        	for(kplanExternalOrders k:l) {
			        		 e = new ExterNalDataExcel();
			        		 e.setAddress(k.getAddress());
			        		 e.setOrderNo(k.getOrderNo());
			        		 e.setCityCode(k.getCityCode());
			        		 e.setContCode("08-2278-2948-a4o2");
			        		 e.setDirsCode(k.getDistrictCode());
			        		 e.setPersonCode(k.getPersonCode());
			        		 e.setPhoneNum(k.getPhoneNum());
			        		 e.setProcDuctCode(k.getProcductCode());
			        		 e.setProvinceCode(k.getProvinceCode());
			        		 e.setReUserName(k.getUserName());
			        		 e.setUserId(k.getUserId());
			        		 e.setUserName(k.getUserName());
			        		 ex.add(e);
			        		 k.setOrderStatus(kplanExternalOrderStatusEnum.EXPSTATUS);
			        		 this.kplanExternalOrdersManager.modiy(k);
			        	}
			        }
			        OutputStream outputStream = response.getOutputStream();
			        EasyExcel.write(outputStream, ExterNalDataExcel.class) 
			                .excelType(ExcelTypeEnum.XLSX)
			                .sheet("订单数据")
			                .doWrite(ex);
			        outputStream.flush();
			        outputStream.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
	}
	
}
