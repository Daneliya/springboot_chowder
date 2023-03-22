package com.xxl.handler;

import com.xxl.request.BaseRequest;
import com.xxl.request.QueryRequest;
import com.xxl.util.UserUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 定义拦截器类，实现HandlerInterceptor接口
 *
 * @author xxl
 * @date 2022/12/22 21:33
 */
//注意当前类必须受Spring容器控制
@Component
public class MyInterceptor implements HandlerInterceptor {

    @Override
    // 原始方法调用前执行的内容
    // 返回值类型可以拦截控制的执行，true放行，false终止
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("preHandle..." + contentType);
//        //获取session中的数据
//        Object employee = request.getSession().getAttribute("employee");
//
//        //若为空,不放行,且写回页面指定数据
//        if (employee == null) {
//            R r = R.error("NOTLOGIN");
//            response.getWriter().print(JSON.toJSON(r));
//            return false;
//        }
        System.out.println("preHandle...");
        String hospitalId = request.getHeader("hospitalId");
        UserUtils.setLoginUser(Integer.valueOf(hospitalId));
//        BaseRequest.hospitalId = 320;
        new QueryRequest().setId(320);
//        new QueryRequest(320);
        return true;
    }

    @Override
    // 原始方法调用后执行的内容
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle...");
    }

    @Override
    // 原始方法调用完成后执行的内容
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion...");
    }
}