package com.kplan.phonecard.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kplan.phonecard.base.BaseDomain;
import com.kplan.phonecard.dao.BaseDao;
import com.kplan.phonecard.exceptions.BusinessException;
import com.kplan.phonecard.reflections.C;
import com.kplan.phonecard.reflections.P;

import de.cronn.reflection.util.TypedPropertyGetter;

/**
 *
 */

public abstract class BaseService<T extends BaseDomain> {
	@Autowired
	private BaseDao<T> dao;
	@Autowired
	EntityManager em;
	
	private String errorMsg;

	public BaseService(String errorMsg) {
		this.c = C.of(this.genericType());
		this.errorMsg = errorMsg;
	}

	public BaseDao<T> getDao() {
		return this.dao;
	}

	private final C<T> c;

	private Class<T> genericType() {
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		Class<T> beanClass = (Class<T>) type.getActualTypeArguments()[0];
		return beanClass;

	}

	public <E> List<E> customerQuery(Function<EntityManager, List<E>> fun) {

		return this.getDao().customerQuery(fun);

	}
	public T findByIdOrException(Long id) {
		return this.findById(id).orElseThrow(() -> new BusinessException(this.errorMsg));
	};

	protected P p(TypedPropertyGetter<T, ?> propertyGetter) {
		return this.c.p(propertyGetter);

	}

	@Transactional
	public T insert(T entity) {
		if (entity == null) {
			throw new BusinessException("参数不能为空");
		}
//		if (entity.getId() != null) {
//			throw new BusinessException("不能设置ID");
//		}
//		entity.setCreatedDate(new Date());
//		entity.setUpdatedDate(new Date());
		return this.getDao().save(entity);

	}

	@Transactional
	public T update(T entity) {
		if (entity == null) {
			throw new BusinessException("参数不能为空");
		}
//		if (entity.getId() == null) {
//			throw new BusinessException("ID不能为空");
//		}
//		entity.setUpdatedDate(new Date());
		return this.getDao().save(entity);
	}

//	public Optional<T> findByIdForUpdate(Long id) {
//		return this.getDao().findByIdForUpdate(id);
//	}

	public Optional<T> findById(Long id) {
		if (Objects.isNull(id)) {
			return Optional.empty();
		}
		return this.getDao().findById(id);
	}

	public T findByIdOrNew(Long id) {
		return this.findById(id).orElseGet(() -> {
			try {
				return this.genericType().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new BusinessException(e);
			}
		});
	}

	public List<T> findAll() {

		return this.getDao().findAll();

	}

	public long count(Specification<T> spec) {
		return this.getDao().count(spec);
	}

//	@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	public Optional<T> findByIdWithLock(Long id) {
		if (Objects.isNull(id)) {
			return Optional.empty();
		}
		T t=this.em.find(this.genericType(), id, LockModeType.PESSIMISTIC_WRITE);
		return Optional.of(t);
	}

	public List<T> findAll(Specification<T> spec) {
		return this.getDao().findAll(spec);
	}

	public List<T> findAll(Specification<T> spec, Sort sort) {
		return this.getDao().findAll(spec, sort);
	}

	public Page<T> findAll(Specification<T> spec, Pageable pageable) {
		return this.getDao().findAll(spec, pageable);
	}

	public Page<T> findAll(T example, Pageable pageable) {
		if (example == null) {
			return Page.empty();
		}
		Example<T> e = Example.of(example);
		return this.getDao().findAll(e, pageable);
	}

	public void delete(T entity) {
		this.getDao().delete(entity);
	}

	public void deleteById(Long id) {
		this.getDao().deleteById(id);
	}

	
	public  List getNativeResultList(String sql, Object... paras) {
		Query query = em.createNativeQuery(sql);
		int parameterIndex = 1;
		if (paras != null && paras.length > 0) {
			for (Object obj : paras) {
				query.setParameter(parameterIndex++, obj);
			}
		}
		return query.getResultList();
	}
	
	public  List getResultList( String sql, Object... paras) {
		Query query = em.createQuery(sql);
		int parameterIndex = 1;
		if (paras != null && paras.length > 0) {
			for (Object obj : paras) {
				query.setParameter(parameterIndex++, obj);
			}
		}
		return query.getResultList();
	}
	
	public  Object getNative(String sql, Object... paras) {
		Query query = em.createNativeQuery(sql);
		int parameterIndex = 1;
		if (paras != null && paras.length > 0) {
			for (Object obj : paras) {
				query.setParameter(parameterIndex++, obj);
			}
		}
		return query.getSingleResult();
	}
	
	public  void add(  Object arg0) {
		em.persist(arg0);
	}
	
	public  Object getById(Object arg1, Class arg0) {
		Object o = em.find(arg0, arg1);
		return o;
	}
	
	public  int exeNative(String sql,Object... paras){
		Query query=em.createNativeQuery(sql);
		int parameterIndex = 1;
		if (paras != null && paras.length > 0) {
			for (Object obj : paras) {
				query.setParameter(parameterIndex++, obj);
			}
		}
		return query.executeUpdate();
	}
	
	public  void removeById(Serializable arg1, Class arg0) {
		em.remove(em.getReference(arg0, arg1));
	}
	public  void modify( Object arg0) {
		em.merge(arg0);
	}
}
