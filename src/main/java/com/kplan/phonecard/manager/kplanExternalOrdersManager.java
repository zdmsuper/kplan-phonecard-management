package com.kplan.phonecard.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.kplan.phonecard.domain.KplanMolicioustag;
import com.kplan.phonecard.domain.ManagerInfo;
import com.kplan.phonecard.domain.UnicomPostCityCode;
import com.kplan.phonecard.domain.kplanExternalOrders;
import com.kplan.phonecard.domain.msgRes;
import com.kplan.phonecard.query.KplanMolicioustagQuery;
import com.kplan.phonecard.query.kplanExternalOrdersQuery;
import com.kplan.phonecard.service.*;
import com.kplan.phonecard.utils.SqeUtils;

@Component
@Transactional
public class kplanExternalOrdersManager extends BaseManager{
	@Autowired
	kplanExternalOrdersService kplanExternalOrdersService;
	@Autowired
	UnicomPostcityCodeService unicomPostcityCodeService;
	
	public Page<kplanExternalOrders> list(@NotNull kplanExternalOrdersQuery query, Pageable pageable,ManagerInfo managerInfo){
		Specification<kplanExternalOrders> spec = new Specification<kplanExternalOrders>() {
			public Predicate toPredicate(Root<kplanExternalOrders> r, CriteriaQuery<?> qr, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if(managerInfo.getBasicUserInfo().getJurisdiction()!=9) {
					list.add(cb.equal(r.get("comPany"), managerInfo.getBasicUserInfo().getCardId()));
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		return this.kplanExternalOrdersService.findAll(spec, pageable);
	}
	
	 /**订单上传
	 * @param managerInfo
	 * @param data
	 * @return
	 */
	public Object uploadFile(ManagerInfo managerInfo,List<Object> data) {
		 msgRes msg = new msgRes();
		 if(data!=null&&data.size()>0) {
				List<String> l;
				kplanExternalOrders o;
			 for(Object b:data) {
				 o=new kplanExternalOrders();
				 l=(List<String>)b;
				 UnicomPostCityCode c=(UnicomPostCityCode) this.unicomPostcityCodeService.getById(l.get(2), UnicomPostCityCode.class);
				 if(c!=null) {
					 o.setProvinceCode(c.getProvince_code());
					 o.setProvinceName(c.getProvince_name());
					 o.setCityCode(c.getCity_code());
					 o.setCityName(c.getCity_name());
					 o.setDistrictCode(c.getDistrict_code());
					 o.setDistrictName(c.getDistrict_name());
					 o.setOrderStatus(0);
				 }else {
					 o.setOrderStatus(-1);
				 }
				 o.setProcductCode(l.get(6));
				 o.setPhoneNum(l.get(1));
				 o.setUserName(l.get(0));
				 o.setAddress(l.get(3));
				 o.setUserId(l.get(5));
				 o.setPersonCode(l.get(9));
				 o.setPhoneprovincecode(l.get(7));
				 o.setPhoneCityCode(l.get(8));
				 o.setComPany(managerInfo.getBasicUserInfo().getCardId());
				 o.setOperator(managerInfo.getBasicUserInfo().getUserRealName());
				 o.setCreadDate(new Date());
				 o.setLastDate(new Date());
				 o.setOrderNo(SqeUtils.getExterNal());
				 this.kplanExternalOrdersService.add(o);
			 }
			 msg.setCode("200");
			 msg.setStatus("200");
			 msg.setMsg("文件上传成功");
		 }else {
			 msg.setCode("200");
			 msg.setStatus("200");
			 msg.setMsg("请检查文件内容");
		 }
		 
		return JSON.toJSON(msg);
		 
	 }
	
	/**数据回导
	 * @param managerInfo
	 * @param data
	 * @param status
	 * @return
	 */
	public Object reUpLoadFile(ManagerInfo managerInfo,List<Object> data,String status) {
		 msgRes msg = new msgRes();
		 if(data!=null&&data.size()>0) {
			 if("suc".equals(status)) {
				 List<String> l;
				 for(Object o:data) {
					 l=(List<String>)o;
					 kplanExternalOrders ex=this.qryExterNalOrder(l.get(2));
					 if(ex!=null) {
						ex.setOrderStatus(2);
						ex.setPhone(l.get(12));
						this.kplanExternalOrdersService.modify(ex);
					 }
				 }
			 }else if("fai".equals(status)) {
				 List<String> l;
				 for(Object o:data) {
					 l=(List<String>)o;
					 kplanExternalOrders ex=this.qryExterNalOrder(l.get(0));
					 if(ex!=null) {
						ex.setOrderStatus(3);
						ex.setRemarks(l.get(25));
						this.kplanExternalOrdersService.modify(ex);
					 }
				 }
				 }
			 msg.setCode("200");
			 msg.setStatus("200");
			 msg.setMsg("数据回导成功");
		 }else {
			 msg.setCode("200");
			 msg.setStatus("200");
			 msg.setMsg("请检查文件内容");
		 }
		 return JSON.toJSON(msg);
	}
	/**查询需要下载的数据
	 * @return
	 */
	public List<kplanExternalOrders> qryDownData(){
		String sql="from kplanExternalOrders where orderStatus=0";
		List<kplanExternalOrders> l=this.kplanExternalOrdersService.getResultList(sql);
		return l;
	}
	
	
	/**更新订单
	 * @param o
	 */
	public void modiy(kplanExternalOrders o) {
		this.kplanExternalOrdersService.modify(o);
	}
	public kplanExternalOrders qryExterNalOrder(String orderNo) {
		String sql="from kplanExternalOrders where orderNo='"+orderNo+"'";
		List<kplanExternalOrders> l=this.kplanExternalOrdersService.getResultList(sql);
		if(l!=null&&l.size()>0) {
			return l.get(0);
		}
		return null;
	}
}
