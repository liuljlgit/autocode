package com.gen.config;

import com.cloud.common.base.BaseController;
import com.cloud.common.exception.BusiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 全局异常处理类
 */

@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //系统异常
    @ExceptionHandler(BusiException.class)
    @ResponseBody
    public String busiExcepitonHandler(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        if(Objects.nonNull(e) && Objects.nonNull(e.getMessage())){
            return formatResponseParams(EXEC_ERROR,null,e.getMessage());
        }
        return formatResponseParams(EXEC_ERROR,null);
    }

    //系统异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String defultExcepitonHandler(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        if(Objects.nonNull(e) && Objects.nonNull(e.getMessage())){
            return formatResponseParams(EXEC_ERROR,null,e.getMessage());
        }
        return formatResponseParams(EXEC_ERROR,null);
    }

}
