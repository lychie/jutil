package org.lychie.jutil;

import java.io.File;
import java.io.InputStream;
import java.security.MessageDigest;
import org.lychie.jutil.exception.UnexpectedException;

/**
 * MD5 信息摘要
 * 
 * @author Lychie Fan
 */
public final class MD5Util {

	private static final int BUFFER_SIZE = 1024 * 1024 / 4;
	private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * 获取MD5密文
	 * 
	 * @param source
	 *            字符串
	 * @return
	 */
	public static String getMD5Str(String source) {
		if (source == null) {
			throw new IllegalArgumentException("argument can not be null");
		}
		MessageDigest md5 = getMD5();
		md5.update(source.getBytes());
		return byteArrayToHex(md5.digest());
	}

	/**
	 * 获取文件MD5值
	 * 
	 * @param file
	 *            文件
	 * @return
	 */
	public static String getFileMD5(File file) {
		return getInputStreamMD5(IOUtil.openFileInputStream(file));
	}

	/**
	 * 获取输入流MD5值
	 * 
	 * @param in
	 *            输入流
	 * @return
	 */
	public static String getInputStreamMD5(InputStream in) {
		try {
			if (in == null) {
				throw new IllegalArgumentException("argument can not be null");
			}
			MessageDigest md5 = getMD5();
			int read;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((read = in.read(buffer)) != -1) {
				md5.update(buffer, 0, read);
			}
			return byteArrayToHex(md5.digest());
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		} finally {
			IOUtil.close(in);
		}
	}

	private static String byteArrayToHex(byte[] bytes) {
		char[] chars = new char[bytes.length * 2];
		int index = 0;
		for (byte by : bytes) {
			chars[index++] = hexDigits[by >>> 4 & 0xf];
			chars[index++] = hexDigits[by & 0xf];
		}
		return new String(chars);
	}

	private static MessageDigest getMD5() {
		try {
			return MessageDigest.getInstance("MD5");
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}

}