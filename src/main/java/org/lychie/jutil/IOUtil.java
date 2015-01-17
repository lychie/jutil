package org.lychie.jutil;

import java.io.File;
import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.lychie.beanutil.BeanMethod;
import org.lychie.jutil.exception.UnexpectedException;

/**
 * IO工具类
 * 
 * @author Lychie Fan
 */
public class IOUtil {

	/**
	 * 获取CLASSPATH类路径下的文件输入流
	 * 
	 * @param pathname
	 *            相对于类路径下的文件路径名称
	 * @return
	 */
	public static InputStream getResourceAsStream(String pathname) {
		return ClassLoader.getSystemClassLoader().getResourceAsStream(pathname);
	}

	/**
	 * 打开文件输入流
	 * 
	 * @param file
	 *            文件
	 * @return
	 */
	public static InputStream openFileInputStream(File file) {
		try {
			return new FileInputStream(file);
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}

	/**
	 * 打开文件输出流
	 * 
	 * @param file
	 *            文件
	 * @return
	 */
	public static OutputStream openFileOutputStream(File file) {
		try {
			return new FileOutputStream(file);
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}

	/**
	 * 尝试刷新缓冲区并关闭流
	 * 
	 * @param streams
	 *            流对象
	 */
	public static void close(Closeable... streams) {
		for (Closeable stream : streams) {
			try {
				BeanMethod.invoke(stream, "flush");
			} catch (Throwable e) {
				/* ignore */
			} finally {
				try {
					stream.close();
				} catch (Throwable e) {
					/* ignore */
				}
			}
		}
	}

}