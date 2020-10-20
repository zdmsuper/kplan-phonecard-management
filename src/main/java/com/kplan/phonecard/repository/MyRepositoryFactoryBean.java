package com.kplan.phonecard.repository;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import com.kplan.phonecard.base.BaseDomain;

public class MyRepositoryFactoryBean<T extends BaseDomain, REPO extends JpaRepository<T, Long>>
		extends JpaRepositoryFactoryBean<REPO, T, Long> {



	public MyRepositoryFactoryBean(Class<? extends REPO> repositoryInterface) {
		super(repositoryInterface);
	}

	protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
		return new MyRepositoryFactory<T>(em);
	}

	private static class MyRepositoryFactory<T extends BaseDomain> extends JpaRepositoryFactory {

		private final EntityManager em;

		public MyRepositoryFactory(EntityManager em) {
			super(em);
			this.em = em;
		}

		protected Object getTargetRepository(RepositoryMetadata metadata) {
			return new MySimpleJpaRepository<T>((Class<T>) metadata.getDomainType(), em);
		}

		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return MySimpleJpaRepository.class;
		}
	}

}