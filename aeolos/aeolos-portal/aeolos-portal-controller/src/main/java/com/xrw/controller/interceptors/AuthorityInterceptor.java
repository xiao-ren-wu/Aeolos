package com.xrw.controller.interceptors;

import com.xrw.common.utils.JsonUtil;
import com.xrw.controller.utils.Check;
import com.xrw.portal.pojo.vo.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/7/25 0:58
 */
@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {

    /**
     * controller处理之前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //请求中的方法名称
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        //解析hanglerMethod
        String methodName=handlerMethod.getMethod().getName();
        String className=handlerMethod.getBean().getClass().getSimpleName();

        //解析参数,具体的key和value
        StringBuilder requestParamBuffer;
        requestParamBuffer = new StringBuilder();
        Map paramMap=request.getParameterMap();
        for (Object o : paramMap.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            String mapKey = (String) entry.getKey();
            String mapValue = StringUtils.EMPTY;
            //request这个参数的map,里面的value返回的是一个String[]
            Object value = entry.getValue();
            if (value instanceof String[]) {
                String[] strs = (String[]) value;
                mapValue = Arrays.toString(strs);
            }
            requestParamBuffer.append(mapKey).append("=").append(mapValue);
        }


        /*
         * 如果用户没有登录，或者当前用户的身份不是管理员，不在执行controller中的方法
         */
        if(!Check.checkLogin()||!Check.checkLogin()){
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter printWriter = response.getWriter();
            if(!Check.checkLogin()){
                printWriter.write(JsonUtil.objToStringPretty(ServerResponse.createByErrorMessage("拦截器拦截，用户没有登录，请登录！！！")));
            }else{
                printWriter.write(JsonUtil.objToStringPretty(ServerResponse.createByErrorMessage("拦截器拦截，当前用户没有权限访问！！！")));
            }
            printWriter.flush();
            printWriter.close();
            return false;
        }
        return true;
    }

    /**
     * controller处理之后执行
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        log.info("postHandle");
    }

    /**
     * 在返回modelAndView之前执行
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        log.info("afterCompletion");
    }

}
