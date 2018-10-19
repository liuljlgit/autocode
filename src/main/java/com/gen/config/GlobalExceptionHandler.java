package com.gen.config;

import com.cloud.common.base.BaseController;
import com.cloud.common.exception.BusiException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理类
 */

@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    //系统异常
    @ExceptionHandler(BusiException.class)
    @ResponseBody
    public String busiExcepitonHandler(HttpServletRequest request, Exception e) {
        return formatResponseParams(EXEC_ERROR,null);
    }

    //系统异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String defultExcepitonHandler(HttpServletRequest request, Exception e) {
        return formatResponseParams(EXEC_ERROR,null);
    }

}
