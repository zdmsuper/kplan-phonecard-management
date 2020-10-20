package com.kplan.phonecard.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.kplan.phonecard.base.BaseDomain;

@NoRepositoryBean
public interface MyJpaSpecificationExecutor<T extends BaseDomain> extends JpaSpecificationExecutor<T> {

//	Optional<T> findByIdForUpdate(Long id);

	public <E> List<E> customerQuery(Function<EntityManager, List<E>> fun);
}