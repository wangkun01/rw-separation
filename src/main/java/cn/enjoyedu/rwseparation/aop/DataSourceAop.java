package cn.enjoyedu.rwseparation.aop;

import cn.enjoyedu.rwseparation.dbutils.DBContextHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAop {
    /*从库的切点,没有标注Master注解，并且方法名为select和get开头的方法，走从库*/
    @Pointcut("!@annotation(cn.enjoyedu.rwseparation.annotation.Master) " +
            "&& (execution(* cn.enjoyedu.rwseparation.service..*.select*(..)) " +
            "|| execution(* cn.enjoyedu.rwseparation.service..*.get*(..))" +
            "|| execution(* cn.enjoyedu.rwseparation.service..*.find*(..))" +
            "|| execution(* cn.enjoyedu.rwseparation.service..*.query*(..)))")
    public void slavePointcut() {

    }

    /*主库的切点,或者标注了Master注解或者方法名为insert、update等开头的方法，走主库*/
    @Pointcut("@annotation(cn.enjoyedu.rwseparation.annotation.Master) " +
            "|| execution(* cn.enjoyedu.rwseparation.service..*.insert*(..)) " +
            "|| execution(* cn.enjoyedu.rwseparation.service..*.add*(..)) " +
            "|| execution(* cn.enjoyedu.rwseparation.service..*.update*(..)) " +
            "|| execution(* cn.enjoyedu.rwseparation.service..*.edit*(..)) " +
            "|| execution(* cn.enjoyedu.rwseparation.service..*.delete*(..)) " +
            "|| execution(* cn.enjoyedu.rwseparation.service..*.remove*(..))")
    public void masterPointcut() {
    }

    @Before("slavePointcut()")
    public void slave() {
        DBContextHolder.slave();
    }

    @Before("masterPointcut()")
    public void master() {
        DBContextHolder.master();
    }
}
