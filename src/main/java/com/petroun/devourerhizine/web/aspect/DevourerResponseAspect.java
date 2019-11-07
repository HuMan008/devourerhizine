/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.web.aspect.DolphinApiResponseAspect
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 1/4/17 10:14 AM
 *
 */

package com.petroun.devourerhizine.web.aspect;


import cn.gotoil.bill.aspect.ApiResponseAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DevourerResponseAspect extends ApiResponseAspect {

    @Around("execution(* com.petroun.devourerhizine.web.controller.api.v1..*.*Action(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        return aroundCall(point);
    }

}
