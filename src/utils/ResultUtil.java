package com.sinolife.activity.util.result;

/**
 * 返回结果
 * @author wupingyan.wb
 *
 * @param <T>
 */
public class ResultUtil<T> {
	private String errorCode; //返回状态码
	private String message;   //返回描述
	private T data;           //返回业务数据
	
	/**
	 * 返回成功时调用
	 * @param <T>
	 * @param data
	 * @return
	 */
	public static <T> ResultUtil<T> success(T data){
		return new ResultUtil<T>(data,CallResultUtil.SUCCESSED_CODE,CallResultUtil.SUCCESSED_MESSAGE);
	}
	
	/**
	 * 返回失败时调用
	 * @param <T>
	 * @param errorCode
	 * @param message
	 * @return
	 */
	public static <T> ResultUtil<T> fail(String message){
		return new ResultUtil<T>(null,CallResultUtil.FAILED_CODE,message);
	}
	/**
	 * 返回失败时调用
	 * @param <T>
	 * @param errorCode
	 * @param message
	 * @return
	 */
	public static <T> ResultUtil<T> fail(String errorCode,String message){
		return new ResultUtil<T>(null,errorCode,message);
	}
	
	
	private ResultUtil(T data,String errorCode,String message) {
		this.data=data;
		this.errorCode=errorCode;
		this.message=message;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

}
