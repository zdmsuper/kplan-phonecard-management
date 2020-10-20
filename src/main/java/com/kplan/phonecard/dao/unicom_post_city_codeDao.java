package com.kplan.phonecard.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.kplan.phonecard.domain.UnicomPostCityCode;
import com.kplan.phonecard.repository.MyJpaSpecificationExecutor;
@Repository
public interface unicom_post_city_codeDao extends JpaRepository<UnicomPostCityCode, String>, JpaSpecificationExecutor<UnicomPostCityCode>,MyJpaSpecificationExecutor<UnicomPostCityCode>  {

}
