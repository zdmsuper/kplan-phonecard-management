package com.kplan.phonecard.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.kplan.phonecard.domain.unicom_post_city_code;
import com.kplan.phonecard.repository.MyJpaSpecificationExecutor;
@Repository
public interface unicom_post_city_codeDao extends JpaRepository<unicom_post_city_code, String>, JpaSpecificationExecutor<unicom_post_city_code>,MyJpaSpecificationExecutor<unicom_post_city_code>  {

}
