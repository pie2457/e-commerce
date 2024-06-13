package dev.practice.ecommerce.common.response;

import java.util.List;

import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.MDC;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.collect.Lists;

import dev.practice.ecommerce.common.exception.BaseException;
import dev.practice.ecommerce.common.interceptor.CommonHttpRequestInterceptor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class CommonControllerAdvice {

	private static final List<ErrorCode> SPECIFIC_ALERT_TARGET_ERROR_CODE_LIST = Lists.newArrayList();

	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public CommonResponse onException(Exception e) {
		String eventId = MDC.get(CommonHttpRequestInterceptor.HEADER_REQUEST_UUID_KEY);
		log.error("eventId = {} ", eventId, e);
		return CommonResponse.fail(ErrorCode.COMMON_SYSTEM_ERROR);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(value = BaseException.class)
	public CommonResponse onBaseException(BaseException e) {
		String eventId = MDC.get(CommonHttpRequestInterceptor.HEADER_REQUEST_UUID_KEY);
		if (SPECIFIC_ALERT_TARGET_ERROR_CODE_LIST.contains(e.getErrorCode())) {
			log.error("[BaseException] eventId = {}, cause = {}, errorMsg = {}", eventId,
				NestedExceptionUtils.getMostSpecificCause(e),
				NestedExceptionUtils.getMostSpecificCause(e).getMessage());
		} else {
			log.warn("[BaseException] eventId = {}, cause = {}, errorMsg = {}", eventId,
				NestedExceptionUtils.getMostSpecificCause(e),
				NestedExceptionUtils.getMostSpecificCause(e).getMessage());
		}
		return CommonResponse.fail(e.getMessage(), e.getErrorCode().name());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(value = {ClientAbortException.class})
	public CommonResponse skipException(Exception e) {
		String eventId = MDC.get(CommonHttpRequestInterceptor.HEADER_REQUEST_UUID_KEY);
		log.warn("[skipException] eventId = {}, cause = {}, errorMsg = {}", eventId,
			NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
		return CommonResponse.fail(ErrorCode.COMMON_SYSTEM_ERROR);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public CommonResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
		String eventId = MDC.get(CommonHttpRequestInterceptor.HEADER_REQUEST_UUID_KEY);
		log.warn("[BaseException] eventId = {}, errorMsg = {}", eventId,
			NestedExceptionUtils.getMostSpecificCause(e).getMessage());
		BindingResult bindingResult = e.getBindingResult();
		FieldError fe = bindingResult.getFieldError();
		if (fe != null) {
			String message =
				"Request Error" + " " + fe.getField() + "=" + fe.getRejectedValue() + " (" + fe.getDefaultMessage()
					+ ")";
			return CommonResponse.fail(message, ErrorCode.COMMON_INVALID_PARAMETER.name());
		} else {
			return CommonResponse.fail(ErrorCode.COMMON_INVALID_PARAMETER.getErrorMsg(),
				ErrorCode.COMMON_INVALID_PARAMETER.name());
		}
	}
}
