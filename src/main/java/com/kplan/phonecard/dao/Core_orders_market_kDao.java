package com.kplan.phonecard.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.kplan.phonecard.domain.core_orders_market_k;
import com.kplan.phonecard.repository.MyJpaSpecificationExecutor;
@Repository
public interface Core_orders_market_kDao extends  JpaRepository<core_orders_market_k, String>, JpaSpecificationExecutor<core_orders_market_k>,MyJpaSpecificationExecutor<core_orders_market_k>  {

}
