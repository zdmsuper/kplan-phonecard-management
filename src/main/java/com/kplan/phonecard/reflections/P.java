package com.kplan.phonecard.reflections;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class P {
	private PropertyDescriptor propertyDescriptor;

	P(PropertyDescriptor propertyDescriptor) {
		this.propertyDescriptor = propertyDescriptor;
	}

	public String name() {
		return propertyDescriptor.getName();
	}

	public Method method() {
		return propertyDescriptor.getReadMethod();
	}

	public Class<?> type() {
		return propertyDescriptor.getPropertyType();
	}
}
