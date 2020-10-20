package com.kplan.phonecard.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.kplan.phonecard.domain.CoreOrdersMarketk;
import com.kplan.phonecard.repository.MyJpaSpecificationExecutor;
@Repository
public interface Core_orders_market_kDao extends  JpaRepository<CoreOrdersMarketk, String>, JpaSpecificationExecutor<CoreOrdersMarketk>,MyJpaSpecificationExecutor<CoreOrdersMarketk>  {

}
