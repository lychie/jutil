package org.lychie.jutil.exception;

/**
 * 转换异常
 * @author Lychie Fan
 */
public class ConvertCastException extends RuntimeException {

	private static final long serialVersionUID = 2827279003760752507L;

	public ConvertCastException(String message) {
		super(message);
	}

}