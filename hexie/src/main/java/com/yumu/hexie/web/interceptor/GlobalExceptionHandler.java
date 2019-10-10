package com.yumu.hexie.web.interceptor;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.yumu.hexie.integration.wuye.resp.BaseResponse;
import com.yumu.hexie.integration.wuye.resp.BaseResponseDTO;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.exception.IntegrationBizException;
import com.yumu.hexie.web.BaseResult;
import com.yumu.hexie.web.common.WechatController;

@ControllerAdvice
public class GlobalExceptionHandler<T> {
    @Inject
    private MessageSource messageSource;

	private static final Logger LOGGER = LoggerFactory.getLogger(WechatController.class);

    @SuppressWarnings("rawtypes")
	@ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResult defaultErrorHandler(HttpServletRequest req, HttpServletResponse res, Exception e) throws Exception {
        if(e instanceof HttpSessionRequiredException) {
            LOGGER.error("用户未登录！");
            return BaseResult.fail(BaseResult.NEED_LOGIN);
        }
        LOGGER.error("内部处理异常", e);
    	if(e instanceof BizValidateException) {
    		return BaseResult.fail(e.getMessage());
    	}
        if (e instanceof HttpRequestMethodNotSupportedException)
            return BaseResult.fail(localizeErrorMessage("http.method.notsupport"));
        if (e instanceof TypeMismatchException)
            return BaseResult.fail(localizeErrorMessage("http.type.mismatch"));
        return BaseResult.fail(localizeErrorMessage(StringUtils.isEmpty(e.getMessage()) ? "server.internal.error" : e.getMessage()));
    }

    @SuppressWarnings("rawtypes")
	@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResult handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        return BaseResult.fail(localizeErrorMessage(bindingResult.getFieldError().getDefaultMessage()));
    }

    private String localizeErrorMessage(String errorCode) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(errorCode, null, locale);
    }
    
    @ExceptionHandler(value = IntegrationBizException.class)
    @ResponseBody
    public BaseResponseDTO<?> integrationExceptionHandler(HttpServletRequest request, HttpServletResponse response, IntegrationBizException e) {

    	if (e instanceof IntegrationBizException) {
			return BaseResponse.fail(e.getRequestId(), e.getMessage());
		}
    	return BaseResponse.fail("", e.getMessage());
    }
    
    
}
