package com.firefly.service.framework;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StringUtils;

import lombok.extern.log4j.Log4j;

@Aspect
@Log4j
public class SoaServiceAspect {

    // 服务启动信息，格式：{token}:start:{className}.{methodName}:{argsStr}
    private final static String SERVICE_START = "%s:start:%s.%s:%s";
    // 服务结束信息，格式：{token}:end
    private final static String SERVICE_END = "%s:end";
    // 服务异常中止信息，格式：{token}:exception
    private final static String SERVICE_EXCEPTION = "%s:exception";

    @Pointcut("execution(@com.firefly.service.framework.SoaService * com.hrd800.soa..*.*(..))")
    private void pointCutMethod() {
    }

    @Pointcut("@annotation(soaService)")
    private void soaAnnotation(SoaService soaService) {
    }

    @Before("pointCutMethod()")
    public void doBefore(JoinPoint joinPoint) {

    }

    @SuppressWarnings("unchecked")
    @Around("pointCutMethod() && soaAnnotation(soaService)")
    public ServiceResult<Object> atomicHandler(ProceedingJoinPoint pjp, SoaService soaService) throws Throwable {
        if (!soaService.isAtomic()) {
            return (ServiceResult<Object>) pjp.proceed();
        }

        // 不能记录服务日志信息，则抛出异常
        if (!log.isInfoEnabled()) {
            throw new RuntimeException("cant log info for atomic service");
        }

        // 清除token
        AtomicServiceTokenContainer.clear();
        // 生成token
        String token = UUID.randomUUID().toString();
        AtomicServiceTokenContainer.setToken(token);
        Object[] args = pjp.getArgs();
        StringBuffer argsSb = new StringBuffer();
        for (Object object : args) {
            if (!StringUtils.isEmpty(object)) {
                argsSb.append(object.toString());
            }
        }
        // 服务启动
        log.info(String.format(SERVICE_START, token, pjp.getSignature().getDeclaringTypeName(),
                pjp.getSignature().getName(), argsSb.toString()));
        ServiceResult<Object> result;
        try {
            result = (ServiceResult<Object>) pjp.proceed();
            log.info(String.format(SERVICE_END, token));
        } catch (Exception e) {
            log.info(String.format(SERVICE_EXCEPTION, token));
            throw e;
        }
        return result;
    }

    protected static Logger getLogger() {
        return log;
    }
}
