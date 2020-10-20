package com.kplan.phonecard.vo;

import com.kplan.phonecard.dto.BaseDTO;

public class ResultMessageDTO<T> extends BaseDTO {
	private T data;
	private String msg;
	private Boolean success = Boolean.FALSE;

	public <E> ResultMessageDTO<E> withoutData() {
		ResultMessageDTO<E> message = new ResultMessageDTO<>();
		message.setMsg(this.msg);
		message.setSuccess(this.getSuccess());
		return message;
	}

	public T getData() {
		return data;
	}

	public ResultMessageDTO<T> setData(T data) {
		this.data = data;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public ResultMessageDTO<T> setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public Boolean getSuccess() {
		return success;
	}

	public ResultMessageDTO<T> setSuccess(Boolean success) {
		this.success = success;
		return this;
	}

	public static <E> ResultMessageDTO<E> success() {
		return new ResultMessageDTO<E>().setSuccess(true);
	}

	public static <E> ResultMessageDTO<E> success(E data) {
		return new ResultMessageDTO<E>().setData(data).setSuccess(true);
	}

	public static <E> ResultMessageDTO<E> failed() {
		return new ResultMessageDTO<E>().setSuccess(false);
	}

	public static <E> ResultMessageDTO<E> failed(String msg) {
		return new ResultMessageDTO<E>().setMsg(msg).setSuccess(false);
	}
}
