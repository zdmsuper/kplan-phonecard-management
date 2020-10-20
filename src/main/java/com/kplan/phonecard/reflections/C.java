package com.kplan.phonecard.reflections;

import de.cronn.reflection.util.PropertyUtils;
import de.cronn.reflection.util.TypedPropertyGetter;

public class C<T> {
	private Class<T> beanClass;

	private C(Class<T> beanClass) {
		this.beanClass = beanClass;
	}

	public P p(TypedPropertyGetter<T, ?> propertyGetter) {

		return new P(PropertyUtils.getPropertyDescriptor(this.beanClass, propertyGetter));
	}

	public static <E> C<E> of(Class<E> clz) {
		return new C<>(clz);
	}

	public static interface Test {

		public String getName();

	}

	public static void main(String[] args) {
		System.out.println(C.of(Test.class).p(Test::getName).name());
		System.out.println(C.of(Test.class).p(Test::getName).type());
	}
}
