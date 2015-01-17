package org.lychie.jutil;

import java.io.File;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FilenameFilter;
import java.io.FileInputStream;
import java.io.LineNumberReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.FileNotFoundException;
import org.lychie.jutil.exception.FileCastException;
import org.lychie.jutil.exception.UnexpectedException;

/**
 * 文件工具类
 * 
 * @author Lychie Fan
 */
public class FileUtil {

	private static final int EOF = -1;
	private static final int INDEX_NOT_FOUND = -1;
	private static final int BUFFER_SIZE = 1024 * 1024 / 2;
	private static final String DEFAULT_CHARSET = "UTF-8";

	/**
	 * 写出
	 * 
	 * @param src
	 *            源文件
	 * @param out
	 *            输出流
	 */
	public static void write(File src, OutputStream out) {
		try {
			InputStream in = new FileInputStream(src);
			write(in, out, BUFFER_SIZE);
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}

	/**
	 * 写出
	 * 
	 * @param in
	 *            输入流
	 * @param dest
	 *            目标文件
	 */
	public static void write(InputStream in, File dest) {
		try {
			OutputStream out = new FileOutputStream(dest);
			write(in, out, BUFFER_SIZE);
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}

	/**
	 * 写出
	 * 
	 * @param in
	 *            输入流
	 * @param out
	 *            输出流
	 */
	public static void write(InputStream in, OutputStream out) {
		write(in, out, BUFFER_SIZE);
	}

	/**
	 * 写出
	 * 
	 * @param in
	 *            输入流
	 * @param out
	 *            输出流
	 * @param bufferSize
	 *            缓冲字节数大小
	 */
	public static void write(InputStream in, OutputStream out, int bufferSize) {
		try {
			int read;
			byte[] buffer = new byte[bufferSize];
			while ((read = in.read(buffer)) != EOF) {
				out.write(buffer, 0, read);
			}
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		} finally {
			IOUtil.close(in, out);
		}
	}

	/**
	 * 写出文本内容
	 * 
	 * @param text
	 *            文本内容
	 * @param out
	 *            输出流
	 */
	public static void write(String text, OutputStream out) {
		Writer writer = new OutputStreamWriter(out);
		write(text, new BufferedWriter(writer));
	}

	/**
	 * 写出文本内容
	 * 
	 * @param text
	 *            文本内容
	 * @param file
	 *            目标文件
	 */
	public static void write(String text, File file) {
		try {
			Writer writer = new FileWriter(file);
			write(text, new BufferedWriter(writer));
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}

	/**
	 * 读取文件内容, 使用UTF-8字符集编码
	 * 
	 * @param file
	 *            文件
	 * @return
	 */
	public static String readAsString(File file) {
		try {
			return readAsString(new FileInputStream(file), DEFAULT_CHARSET);
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}

	/**
	 * 读取文件内容
	 * 
	 * @param file
	 *            文件
	 * @param charset
	 *            使用的字符集编码
	 * @return
	 */
	public static String readAsString(File file, String charset) {
		try {
			return readAsString(new FileInputStream(file), charset);
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}

	/**
	 * 读取输入流内容, 使用UTF-8字符集编码
	 * 
	 * @param in
	 *            输入流
	 * @return
	 */
	public static String readAsString(InputStream in) {
		return readAsString(in, DEFAULT_CHARSET);
	}

	/**
	 * 读取输入流内容
	 * 
	 * @param in
	 *            输入流
	 * @param charset
	 *            使用的字符集编码
	 * @return
	 */
	public static String readAsString(InputStream in, String charset) {
		try {
			return readAsString(new InputStreamReader(in, charset));
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}

	/**
	 * 逐行读取整个文件内容, 使用UTF-8字符集编码
	 * 
	 * @param file
	 *            文件
	 * @return
	 */
	public static String[] read(File file) {
		try {
			return read(new FileInputStream(file), DEFAULT_CHARSET);
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}

	/**
	 * 逐行读取整个文件内容
	 * 
	 * @param file
	 *            文件
	 * @param charset
	 *            使用的字符集编码
	 * @return
	 */
	public static String[] read(File file, String charset) {
		try {
			return read(new FileInputStream(file), charset);
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}

	/**
	 * 逐行读取输入流内容, 使用UTF-8字符集编码
	 * 
	 * @param in
	 *            输入流
	 * @return
	 */
	public static String[] read(InputStream in) {
		return read(in, DEFAULT_CHARSET);
	}

	/**
	 * 逐行读取输入流内容
	 * 
	 * @param in
	 *            输入流
	 * @param charset
	 *            使用的字符集编码
	 * @return
	 */
	public static String[] read(InputStream in, String charset) {
		try {
			return read(new InputStreamReader(in, charset));
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}

	/**
	 * 获取文件总行数
	 * 
	 * @param file
	 *            文件
	 * @return
	 */
	public static int getLineNumber(File file) {
		LineNumberReader reader = null;
		try {
			reader = new LineNumberReader(new FileReader(file));
			reader.skip(Long.MAX_VALUE);
			int lines = reader.getLineNumber() + 1;
			return lines;
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		} finally {
			IOUtil.close(reader);
		}
	}

	/**
	 * 获取目录下含参数指定的文件扩展名的文件列表
	 * 
	 * @param dir
	 *            目录
	 * @param extensions
	 *            文件扩展名
	 * @return
	 */
	public static List<File> list(File dir, String... extensions) {
		return list(dir, new ExtensionFilenameFilter(extensions));
	}

	/**
	 * 删除目录或文件
	 * 
	 * @param src
	 *            文件或目录
	 */
	public static void delete(File src) {
		if (!src.exists()) {
			throw new UnexpectedException(new FileNotFoundException(src
					+ " file dose not exist"));
		}
		if (src.isFile()) {
			src.delete();
		} else if (src.isDirectory()) {
			File[] files = src.listFiles();
			for (File item : files) {
				delete(item);
			}
		}
		src.delete();
	}

	/**
	 * 重命名目录名或文件名
	 * 
	 * @param src
	 *            文件或目录
	 * @param newName
	 *            新的名称
	 * @return
	 */
	public static File rename(File src, String newName) {
		if (!src.exists()) {
			throw new UnexpectedException(new FileNotFoundException(src
					+ " file dose not exist"));
		}
		String oldName = src.getName();
		int oldIndex = oldName.lastIndexOf(".");
		if (oldIndex != INDEX_NOT_FOUND) {
			int newIndex = newName.lastIndexOf(".");
			if (newIndex == INDEX_NOT_FOUND) {
				newName += oldName.substring(oldIndex);
			}
		}
		String pathname = src.toString();
		pathname = pathname.substring(0, pathname.length() - oldName.length());
		pathname += newName;
		File newFile = new File(pathname);
		if (newFile.exists()) {
			throw new FileCastException(newFile + " file already exists");
		}
		src.renameTo(newFile);
		return newFile;
	}

	/**
	 * 创建目录
	 * 
	 * @param dir
	 *            目录
	 * @return
	 */
	public static File mkdir(File dir) {
		String empty = "";
		return mkdir(dir, empty);
	}

	/**
	 * 创建目录
	 * 
	 * @param parent
	 *            父目录
	 * @param child
	 *            子目录名称
	 * @return
	 */
	public static File mkdir(File parent, String child) {
		File dir = new File(parent, child);
		if (dir.exists()) {
			throw new FileCastException(dir + " directory already exists");
		}
		dir.mkdirs();
		return dir;
	}

	/**
	 * 拷贝文件或目录
	 * 
	 * @param src
	 *            源文件或目录
	 * @param destDir
	 *            目的目录
	 */
	public static void copy(File src, File destDir) {
		List<File> record = new ArrayList<File>();
		try {
			if (src.isFile()) {
				copyFileToDirectory(src, destDir, record);
			} else if (src.isDirectory()) {
				copyDirectorToDirector(src, destDir, record);
			}
		} catch (Throwable e) {
			rollback(record);
			throw new UnexpectedException(e);
		}
	}

	/**
	 * 强行拷贝文件或目录
	 * 
	 * @param src
	 *            源文件或目录
	 * @param destDir
	 *            目的目录
	 * @param overwrite
	 *            存在是否覆盖
	 */
	public static void forceCopy(File src, File destDir, boolean overwrite) {
		try {
			if (src.isFile()) {
				forceCopyFileToDirectory(src, destDir, overwrite);
			} else if (src.isDirectory()) {
				forceCopyDirectorToDirector(src, destDir, overwrite);
			}
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}

	/**
	 * 剪切文件或目录
	 * 
	 * @param src
	 *            源文件或目录
	 * @param destDir
	 *            目的目录
	 */
	public static void move(File src, File destDir) {
		copy(src, destDir);
		delete(src);
	}

	/**
	 * 强行剪切文件或目录
	 * 
	 * @param src
	 *            源文件或目录
	 * @param destDir
	 *            目的目录
	 * @param overwrite
	 *            存在是否覆盖
	 */
	public static void forceMove(File src, File destDir, boolean overwrite) {
		forceCopy(src, destDir, overwrite);
		delete(src);
	}

	/**
	 * 清空目录
	 * 
	 * @param dir
	 *            目录
	 */
	public static void cleanDirectory(File dir) {
		if (!dir.exists()) {
			throw new FileCastException(dir + " does not exist");
		}
		if (!dir.isDirectory()) {
			throw new FileCastException(dir + " is not a directory");
		}
		File[] list = dir.listFiles();
		if (list != null && list.length > 0) {
			for (File item : list) {
				delete(item);
			}
		}
	}

	/**
	 * 写出文本内容
	 * 
	 * @param text
	 *            文本内容
	 * @param writer
	 *            Writer
	 */
	private static void write(String text, Writer writer) {
		BufferedReader reader = null;
		try {
			int read;
			char[] buffer = new char[BUFFER_SIZE];
			reader = new BufferedReader(new StringReader(text));
			while ((read = reader.read(buffer)) != EOF) {
				writer.write(buffer, 0, read);
			}
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		} finally {
			IOUtil.close(reader, writer);
		}
	}

	/**
	 * 逐行读取
	 * 
	 * @param r
	 *            Reader
	 * @return
	 */
	private static String[] read(Reader r) {
		BufferedReader reader = null;
		try {
			String read;
			reader = new BufferedReader(r);
			List<String> contents = new ArrayList<String>();
			while ((read = reader.readLine()) != null) {
				contents.add(read);
			}
			return ArrayUtil.asArray(contents);
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		} finally {
			IOUtil.close(reader);
		}
	}

	/**
	 * 读取内容
	 * 
	 * @param r
	 *            Reader
	 * @return
	 */
	private static String readAsString(Reader r) {
		String[] result = read(r);
		int size = result.length - 1;
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < size; i++) {
			builder.append(result[i]).append("\r\n");
		}
		builder.append(result[size]);
		return builder.toString();
	}

	/**
	 * 目录下的文件列表
	 * 
	 * @param dir
	 *            目录
	 * @param filters
	 *            FilenameFilter
	 * @return
	 */
	private static List<File> list(File dir, FilenameFilter filters) {
		if (dir.isFile()) {
			throw new FileCastException(dir + " is not a directory");
		}
		File[] files = dir.listFiles(filters);
		List<File> list = new ArrayList<File>();
		for (File file : files) {
			if (file.isFile()) {
				list.add(file);
			} else if (file.isDirectory()) {
				list.addAll(list(file, filters));
			}
		}
		return list;
	}

	/**
	 * 拷贝文件到目录
	 * 
	 * @param srcFile
	 *            源文件
	 * @param destDir
	 *            目的目录
	 * @param record
	 *            记录列表
	 * @throws Throwable
	 */
	private static void copyFileToDirectory(File srcFile, File destDir,
			List<File> record) throws Throwable {

		File destFile = new File(destDir, srcFile.getName());
		if (destFile.exists()) {
			throw new FileCastException(destFile + " file already exists");
		}
		record.add(destFile);
		write(new FileInputStream(srcFile), new FileOutputStream(destFile));
	}

	/**
	 * 拷贝目录到目录
	 * 
	 * @param srcDir
	 *            源目录
	 * @param destDir
	 *            目的目录
	 * @param record
	 *            记录列表
	 * @throws Throwable
	 */
	private static void copyDirectorToDirector(File srcDir, File destDir,
			List<File> record) throws Throwable {

		destDir = new File(destDir, srcDir.getName());
		if (!destDir.exists()) {
			destDir.mkdir();
			record.add(destDir);
		}
		File[] files = srcDir.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				copyFileToDirectory(file, destDir, record);
			} else if (file.isDirectory()) {
				copyDirectorToDirector(file, destDir, record);
			}
		}
	}

	/**
	 * 强行拷贝文件到目录
	 * 
	 * @param srcFile
	 *            源文件
	 * @param destDir
	 *            目的目录
	 * @param overwrite
	 *            存在是否覆盖
	 * @throws Throwable
	 */
	private static void forceCopyFileToDirectory(File srcFile, File destDir,
			boolean overwrite) throws Throwable {

		File destFile = new File(destDir, srcFile.getName());
		if (!destFile.exists() || (destFile.exists() && overwrite)) {
			write(new FileInputStream(srcFile), new FileOutputStream(destFile));
		}
	}

	/**
	 * 强行拷贝目录到目录
	 * 
	 * @param srcDir
	 *            源目录
	 * @param destDir
	 *            目的目录
	 * @param overwrite
	 *            存在是否覆盖
	 * @throws Throwable
	 */
	private static void forceCopyDirectorToDirector(File srcDir, File destDir,
			boolean overwrite) throws Throwable {

		destDir = new File(destDir, srcDir.getName());
		if (!destDir.exists()) {
			destDir.mkdir();
		}
		File[] files = srcDir.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				forceCopyFileToDirectory(file, destDir, overwrite);
			} else if (file.isDirectory()) {
				forceCopyDirectorToDirector(file, destDir, overwrite);
			}
		}
	}

	/**
	 * 回滚
	 * 
	 * @param record
	 *            记录列表
	 */
	private static void rollback(List<File> record) {
		try {
			for (File item : record) {
				delete(item);
			}
		} catch (Throwable e) {
			/* ignore */
		}
	}

	/**
	 * 文件扩展名过滤器
	 */
	private static class ExtensionFilenameFilter implements FilenameFilter {

		private String[] extensions;

		public ExtensionFilenameFilter(String[] extensions) {
			this.extensions = extensions;
		}

		@Override
		public boolean accept(File dir, String name) {
			if (new File(dir, name).isDirectory()) {
				return true;
			}
			if (extensions.length == 0) {
				return true;
			}
			int index = name.lastIndexOf(".");
			if (index != INDEX_NOT_FOUND) {
				name = name.substring(index + 1);
				if (ArrayUtil.contains(extensions, name)) {
					return true;
				}
			}
			return false;
		}

	}

}