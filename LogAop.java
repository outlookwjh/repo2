package com.itheima.controller;

import com.itheima.domain.Syslog;
import com.itheima.service.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {

    @Autowired
    HttpServletRequest request;

    @Autowired
    SysLogService sysLogService;

    private Date starttime;
    private Class clazz;
    private Method method;

    @Before("execution(* com.itheima.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {
        //访问开始时间
        starttime= new Date();
        //访问的类
        clazz = jp.getTarget().getClass();
        //访问的方法
        String methodname = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        if (args==null || args.length==0){
            method=clazz.getMethod(methodname);
        }else {
            Class[] classargs=new Class[args.length];
            for (int i=0;i<args.length;i++){
                classargs[i]=args[i].getClass();
            }
            method = clazz.getMethod(methodname, classargs);
        }
    }

    @After("execution(* com.itheima.controller.*.*(..))")
    public void dafter(JoinPoint jp) throws NoSuchMethodException {
        //访问时长
        long time=new Date().getTime()-starttime.getTime();
        //获取url,controller上的注解
        String url="";
        if (clazz!=null&&method!=null&&clazz!=LogAop.class){
            //获取类上的注解
            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if (classAnnotation!=null){
                String[] classValue = classAnnotation.value();
                //获取方法上的注解
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                if (methodAnnotation!=null){
                    String[] methodValue = methodAnnotation.value();
                    url=classValue[0]+methodValue[0];


                    //获取ip
                    String ip=request.getRemoteAddr();
                    //获取当前操作用户
                    SecurityContext context= SecurityContextHolder.getContext();
                    User user = (User) context.getAuthentication().getPrincipal();
                    String username = user.getUsername();


                    //封装对象
                    Syslog syslog = new Syslog();
                    syslog.setExecutionTime(time);
                    syslog.setIp(ip);
                    syslog.setMethod("[类名] "+clazz.getName()+"[方法名] "+method.getName());
                    syslog.setUrl(url);
                    syslog.setUsername(username);
                    syslog.setVisitTime(starttime);

                    //保存日志
                    sysLogService.savelog(syslog);


					//第一次修改
					system.out.println("第一次修改")
					//第二次修改
					system.out.println("第二次修改")
                }
            }
        }


    }
}
