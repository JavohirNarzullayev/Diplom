package uz.narzullayev.javohir.aop;/* 
 @author: Javohir
  Date: 1/24/2022
  Time: 1:21 AM*/

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class AnnotationAop {

    @Around("@annotation(javax.validation.constraints.NotBlank)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        System.out.println(proceed.toString());

        long executionTime = System.currentTimeMillis() - start;

        System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");
        return joinPoint.proceed();
    }

}
