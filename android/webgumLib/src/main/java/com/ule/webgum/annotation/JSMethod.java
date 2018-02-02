package com.ule.webgum.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-02-02 14:53
 * @Creator: Liy
 * @Version: 1.0
 */
@Target(ElementType.METHOD)
public @interface JSMethod {

	public String name() default "";

}
