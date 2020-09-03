package com.spring.transction;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author yuminjun yuminjun@lexiangbao.com
 * @version 1.00
 * @date 2020/9/3 15:37
 * @record <pre>
 * version  author      date      desc
 * -------------------------------------------------
 * 1.00     yuminjun    2020/9/3   新建
 * -------------------------------------------------
 * </pre>
 */
@Component
@Aspect
public class TxAspect {

    @Pointcut("execution(public * com.mybatis.service..*.*(..))")
    private void pointcut() {
    }

    @Around("pointcut()")
    public Object around(JoinPoint joinPoint) throws Throwable {
        // 在方法执行前开启事务
        TransactionUtil.startTransaction();

        // 执行业务逻辑
        Object proceed = null;
        try {
            ProceedingJoinPoint method = (ProceedingJoinPoint) joinPoint;
            proceed = method.proceed();
        } catch (Throwable throwable) {
            // 出现异常进行回滚
            TransactionUtil.rollback();
            return proceed;
        }
        // 方法执行完成后提交事务
        TransactionUtil.commit();
        return proceed;
    }

}
