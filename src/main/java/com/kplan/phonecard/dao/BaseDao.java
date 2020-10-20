package com.kplan.phonecard.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.kplan.phonecard.base.BaseDomain;
import com.kplan.phonecard.repository.MyJpaSpecificationExecutor;

@NoRepositoryBean
public interface BaseDao<T extends BaseDomain> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T>,MyJpaSpecificationExecutor<T>  {

	
}