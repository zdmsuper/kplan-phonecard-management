package com.kplan.phonecard.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.kplan.phonecard.domain.kplan_phone_number;
import com.kplan.phonecard.repository.MyJpaSpecificationExecutor;
@Repository
public interface kplan_phone_numberDao extends JpaRepository<kplan_phone_number, String>, JpaSpecificationExecutor<kplan_phone_number>,MyJpaSpecificationExecutor<kplan_phone_number> {

}
