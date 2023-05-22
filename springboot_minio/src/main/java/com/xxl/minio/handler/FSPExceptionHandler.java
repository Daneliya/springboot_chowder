package com.xxl.minio.handler;

import com.xxl.minio.result.ApiResult;
import com.xxl.minio.result.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;


/**
 * @Description: 文件上传全局异常拦截器
 * @Author: xxl
 * @Date: 2023/05/22 0:53
 * @Version: 1.0
 */

@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class FSPExceptionHandler {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    public ApiResult handleValidException(MethodArgumentNotValidException exception) {
//        BindingResult result = exception.getBindingResult();
//        String errorMessage = StringUtils.EMPTY;
//        if (result.hasErrors()) {
//            errorMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
//            log.error("请求对象[{}]验证错误信息{}", result.getFieldErrors().get(0).getObjectName(), errorMessage);
//        }
//        return ApiResult.error(HttpStatus.BAD_REQUEST.value(), errorMessage);
//    }
//
//    @ExceptionHandler(value = BindException.class)
//    public ApiResult handleValidException(BindException exception) {
//        BindingResult result = exception.getBindingResult();
//        String errorMessage = StringUtils.EMPTY;
//        if (result.hasErrors()) {
//            errorMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
//            log.error("请求对象[{}]验证错误信息{}", result.getFieldErrors().get(0).getObjectName(), errorMessage);
//        }
//        return ApiResult.error(HttpStatus.BAD_REQUEST.value(), errorMessage);
//    }


    @ExceptionHandler(value = {MaxUploadSizeExceededException.class})
    public ApiResult handleValidException(MaxUploadSizeExceededException exception) {
        long maxUploadSize = exception.getMaxUploadSize();
        log.error("允许的最大上传字节为:{}", maxUploadSize);
        String errorMessage = "文件大于最大限制" + maxFileSize + ",请重新上传";
        log.error(errorMessage);
        return ApiResult.error(HttpStatus.BAD_REQUEST.value(), errorMessage);
    }

//    @Override
//    @ExceptionHandler({ConstraintViolationException.class})
//    public ApiResult handleConstraintValidationException(final ConstraintViolationException ex) {
//        String errorMessage = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
//        return ApiResult.error(HttpStatus.BAD_REQUEST.value(), errorMessage);
//
//    }
//
//
//    @ExceptionHandler({EnterpriseBusinessException.class})
//    public ApiResult handEnterpriseBusinessException(EnterpriseBusinessException e) {
//        log.error("当前请求出现业务异常！", e);
//        return ApiResult.error(e.getCode(), e.getMessage());
//    }
//
//
    @ExceptionHandler({Exception.class})
    public ApiResult handleUnknownException(final Exception ex) {
        if (ex instanceof MaxUploadSizeExceededException || ex instanceof FileUploadBase.FileSizeLimitExceededException || ex instanceof FileUploadBase.SizeLimitExceededException) {
            String errorMessage = "文件超出限制，请重新上传";
            return ApiResult.error(errorMessage);
        }
        log.error("==> Error {} detected when request", ex.getMessage(), ex);
        return ApiResult.error(ErrorCode.INTERNAL_SERVER_ERROR.ordinal());
    }


}