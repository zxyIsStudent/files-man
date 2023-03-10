package com.files.filesman.err;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局api异常处理
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(value = ApiException.class)
    public CommonResult handle(ApiException e) {





        //return CommonResult.failed(e.getCode(), MessageUtils.get(e.getCode().toString(), e.getMessage(), null));
        return CommonResult.failed(e.getMessage());
    }
}
