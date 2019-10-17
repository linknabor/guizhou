package com.yumu.hexie.integration.wuye.resp;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yumu.hexie.integration.wuye.resp.BaseResponseDTO;

public class BaseResponse {
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	/**
	 * 返回成功, void无参
	 * @param requestId
	 * @param t
	 * @return
	 */
	public static <T> BaseResponseDTO<T> success(String requestId){
		
		BaseResponseDTO<T> dto = new BaseResponseDTO<>();
		dto.setRequestId(requestId);
		dto.setReturnCode(SUCCESS);
		return dto;
	}

	/**
	 * 返回成功
	 * @param requestId
	 * @param t
	 * @return
	 */
	public static <T> BaseResponseDTO<T> success(String requestId, T t){
		
		BaseResponseDTO<T> dto = new BaseResponseDTO<>();
		dto.setRequestId(requestId);
		dto.setData(t);
		dto.setReturnCode(SUCCESS);
		return dto;
	}
	
	/**
	 * 返回失败
	 * @param requestId
	 * @param message
	 * @return
	 */
	public static BaseResponseDTO<String> fail(String requestId, String message){
		
		BaseResponseDTO<String> dto = new BaseResponseDTO<>();
		dto.setRequestId(requestId);
		dto.setReturnCode(FAIL);
		dto.setReturnMessage(message);
		return dto;
		
	}
	
	/**
	 *  带有分页多条的返回
	 * @param requestId
	 * @param page
	 * @return
	 */
	public static <T> BaseResponseDTO<List<T>> success(String requestId, Page<T> page) {
		
		BaseResponseDTO<List<T>> dto = new BaseResponseDTO<>();
		dto.setRequestId(requestId);
		dto.setReturnCode(SUCCESS);
		dto.setData(page.getContent());
		dto.setTotal_size((int)page.getTotalElements());
		return dto;
		
	}
	
	/**
	 *  带有分页多条的返回
	 * @param requestId
	 * @param page
	 * @return
	 */
	public static <T> BaseResponseDTO<T> success(String requestId, T t, List<String> sectIds) {
		
		BaseResponseDTO<T> dto = new BaseResponseDTO<>();
		dto.setRequestId(requestId);
		dto.setReturnCode(SUCCESS);
		dto.setData(t);
		dto.setSectList(sectIds);
		return dto;
		
	}
	
	/**
	 *  带有分页多条的返回
	 * @param requestId
	 * @param page
	 * @return
	 */
	public static <T> BaseResponseDTO<T> success(BaseResponseDTO<T> dto) {
		
		dto.setReturnCode(SUCCESS);
		return dto;
		
	}
}
