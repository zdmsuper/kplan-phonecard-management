package com.kplan.phonecard.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.kplan.phonecard.base.BaseDomain;

public class MySimpleJpaRepository<T extends BaseDomain>
        extends SimpleJpaRepository<T,Long>
        implements MyJpaSpecificationExecutor<T> {

    private final EntityManager em;

    public MySimpleJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.em=entityManager;
	}

	public MySimpleJpaRepository(Class<T> entityClass, EntityManager entityManager) {
        super(entityClass, entityManager);
        this.em = entityManager;
    }

//	@Override
//	public Optional<T> findByIdForUpdate(Long id) {
//		T t=this.em.find(super.getDomainClass(), id, LockModeType.PESSIMISTIC_WRITE);
//		return Optional.ofNullable(t);
//	}

	@Override
	public <E> List<E> customerQuery(Function<EntityManager, List<E>> fun) {
		return fun.apply(em);
	}

 
}