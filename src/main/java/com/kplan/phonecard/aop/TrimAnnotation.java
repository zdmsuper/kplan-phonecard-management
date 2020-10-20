package com.kplan.phonecard.aop;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//@Component()
//@Aspect
public class TrimAnnotation {
	// 定义切点
	@Around("@within(org.springframework.stereotype.Component) || @within(org.springframework.stereotype.Service)")
	public Object trim(ProceedingJoinPoint pjp) throws Throwable {
		// Object[] args = this.trim(pjp.getArgs());
		// return pjp.proceed(args);// 执行方法
		return pjp.proceed();
	}

	private static final Map<Class<?>, List<String>> cache = new ConcurrentHashMap<>();

	private Object[] trim(Object[] args) {
		if (args == null || args.length == 0) {
			return args;
		}
		List<Object> list = new ArrayList<>();
		for (Object obj : args) {
			Object result = this.trim(obj);
			list.add(result);
		}
		return list.toArray(new Object[0]);

	}

	private Object trim(Object obj) {
		Class<?> clz = obj.getClass();
		if (!cache.containsKey(clz)) {
			List<String> propList = new ArrayList<>();
			Field[] fields = clz.getDeclaredFields();
			for (Field f : fields) {
				if (f.getType() == String.class) {
					propList.add(f.getName());
				}
			}
			cache.put(clz, propList);

		}
		try {
			for (String name : cache.get(clz)) {
				Field f = clz.getDeclaredField(name);
				String v = (String) f.get(obj);
				f.setAccessible(true);
				f.set(obj, StringUtils.trim(v));

			}
		} catch (Exception e) {

		}

		return obj;

	}
	/**
	 * 前置通知(注解中的sayings()方法，其实就是上面定义pointcut切点注解所修饰的方法名，那只是个代理对象,不需要写具体方法，
	 * 相当于xml声明切面的id名，如下，相当于id="embark",用于供其他通知类型引用) <aop:config>
	 * <aop:aspect ref="mistrel"> <!-- 定义切点 -->
	 * <aop:pointcut expression="execution(* *.saying(..))" id="embark"/> <!--
	 * 声明前置通知 (在切点方法被执行前调用) -->
	 * <aop:before method="beforSay" pointcut-ref="embark"/> <!-- 声明后置通知
	 * (在切点方法被执行后调用) --> <aop:after method="afterSay" pointcut-ref="embark"/>
	 * </aop:aspect> </aop:config>
	 */
//     @Before("sayings()")
//     public void sayHello(){
//         System.out.println("注解类型前置通知");
//     }
//     //后置通知
//     @After("sayings()")
//     public void sayGoodbey(){
//         System.out.println("注解类型后置通知");
//     }
//     //环绕通知。注意要有ProceedingJoinPoint参数传入。
//     @Around("sayings()")
//     public void sayAround(ProceedingJoinPoint pjp) throws Throwable{
//         System.out.println("注解类型环绕通知..环绕前");
//         pjp.proceed();//执行方法
//         System.out.println("注解类型环绕通知..环绕后");
//     }
}