package com.nowcoder.community.controller.advice;

import com.nowcoder.community.util.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: Dai
 * @Date: 2024/04/22 21:36
 * @Description: ExceptionAdvice
 * @Version: 1.0
 */
@ControllerAdvice(annotations = Controller.class) // 定义一个全局的异常处理类，适用于所有标注了Controller的异常
public class ExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class); // 日志记录器

    /**
     * 处理所有捕获到的异常。
     *
     * @param e 捕获到的异常对象
     * @param request 客户端的HTTP请求对象
     * @param response 用于向客户端发送响应的对象
     * @throws IOException 如果在处理过程中发生IO错误
     */
    @ExceptionHandler({Exception.class})
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.error("服务器发生异常：" + e.getMessage()); // 记录异常信息

        // 遍历并记录异常的堆栈信息
        for(StackTraceElement element:e.getStackTrace()){
            logger.error(element.toString());
        }

        // 检查请求是否来自异步请求（如Ajax）
        String xRequestedWith = request.getHeader("x-requested-with");
        if("XMLHttpRequest".equals(xRequestedWith)){
            // 对于异步请求，返回JSON格式的错误信息
            response.setContentType("application/plain;charset=utf-8");
            PrintWriter writer =response.getWriter();
            writer.write(CommunityUtil.getJsonString(1,"服务器异常")); // 返回定制的错误信息
        }else {
            // 对于普通请求，重定向到错误页面
            response.sendRedirect(request.getContextPath()+"/error");
        }
    }
}

