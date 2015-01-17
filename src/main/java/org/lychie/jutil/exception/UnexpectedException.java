package org.lychie.jutil.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * 包装真实抛出的异常的类
 * @author Lychie Fan
 */
public class UnexpectedException extends RuntimeException {
	
	private Throwable throwable;
	private static final long serialVersionUID = 7801146121856082123L;

	public UnexpectedException(Throwable e) {
		this.throwable = e;
	}
	
	@Override
	public String getMessage() {
		return throwable.getMessage();
	}

	@Override
	public String getLocalizedMessage() {
		return throwable.getLocalizedMessage();
	}

	@Override
	public Throwable getCause() {
		return throwable;
	}

	@Override
	public synchronized Throwable initCause(Throwable cause) {
		return throwable.initCause(cause);
	}

	@Override
	public String toString() {
		return throwable.toString();
	}

	@Override
	public void printStackTrace() {
		throwable.printStackTrace();
	}

	@Override
	public void printStackTrace(PrintStream s) {
		throwable.printStackTrace(s);
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		throwable.printStackTrace(s);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return super.fillInStackTrace();
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		return throwable.getStackTrace();
	}

	@Override
	public void setStackTrace(StackTraceElement[] stackTrace) {
		throwable.setStackTrace(stackTrace);
	}
	
}