package com.kplan.phonecard.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseDomain {


//	@Id
//	public abstract Long getId() ;
//
//	public abstract void setId(Long id);
//	
//	public static List<Long> ids(Collection<BaseDomain> collection) {
//		if (Objects.isNull(collection)) {
//			return new ArrayList<>();
//		}
//		return collection.stream().filter(Objects::nonNull).map(BaseDomain::getId)
//				.collect(Collectors.toList());
//
//	}

}
