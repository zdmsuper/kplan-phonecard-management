package com.kplan.phonecard.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.kplan.phonecard.domain.KplanPhoneNumber;
import com.kplan.phonecard.repository.MyJpaSpecificationExecutor;
@Repository
public interface kplan_phone_numberDao extends JpaRepository<KplanPhoneNumber, String>, JpaSpecificationExecutor<KplanPhoneNumber>,MyJpaSpecificationExecutor<KplanPhoneNumber> {

}
