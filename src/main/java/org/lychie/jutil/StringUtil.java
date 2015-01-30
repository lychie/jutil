package org.lychie.jutil;

/**
 * 字符串工具类
 * 
 * @author Lychie Fan
 */
public class StringUtil {

	private static final int INDEX_NOT_FOUND = -1;

	private static final String NULL = "null";

	private static final String PLACEHOLDER = "\\?";

	/**
	 * 判断字符串是否为空
	 * 
	 * @param source
	 *            字符串
	 * @return 若为null或长度为0, 则返回true, 否则返回false
	 */
	public static boolean isEmpty(String source) {
		return source == null || source.length() == 0;
	}

	/**
	 * 判断字符串是否不为空
	 * 
	 * @param source
	 *            字符串
	 * @return 若不为null而且长度不为0, 则返回true, 否则返回false
	 */
	public static boolean isNotEmpty(String source) {
		return !isEmpty(source);
	}

	/**
	 * 分割字符串得到子串
	 * 
	 * @param source
	 *            源字符串
	 * @param beginIndex
	 *            开始索引, 允许负数值, 表示从后往前
	 * @return
	 */
	public static String substring(String source, int beginIndex) {
		return substring(source, beginIndex, source.length());
	}

	/**
	 * 分割字符串得到子串
	 * 
	 * @param source
	 *            源字符串
	 * @param beginIndex
	 *            开始索引, 允许负数值, 表示从后往前
	 * @param endIndex
	 *            结束索引, 允许负数值, 表示从后往前
	 * @return
	 */
	public static String substring(String source, int beginIndex, int endIndex) {
		int length = source.length();
		if (beginIndex < 0) {
			beginIndex += length;
		}
		if (endIndex < 0) {
			endIndex += length;
		}
		if (beginIndex > endIndex) {
			throw new StringIndexOutOfBoundsException(
					"String index out of range: " + (endIndex - beginIndex));
		}
		return source.substring(beginIndex, endIndex);
	}

	/**
	 * 获取子串在源串中第一次出现的位置结束, 索引从0开始的串
	 * 
	 * @param source
	 *            源串
	 * @param substring
	 *            子串
	 * @return 若源串中不含有子串, 则返回一个空串
	 */
	public static String beforeString(String source, String substring) {
		int index = source.indexOf(substring);
		if (index != INDEX_NOT_FOUND) {
			return source.substring(0, index);
		}
		return "";
	}

	/**
	 * 获取子串在源串中最后一次出现的位置结束, 索引从0开始的串
	 * 
	 * @param source
	 *            源串
	 * @param substring
	 *            子串
	 * @return 若源串中不含有子串, 则返回一个空串
	 */
	public static String beforeLastString(String source, String substring) {
		int index = source.lastIndexOf(substring);
		if (index != INDEX_NOT_FOUND) {
			return source.substring(0, index);
		}
		return "";
	}

	/**
	 * 获取子串在源串中第一次出现的位置开始, 至源串末尾的串
	 * 
	 * @param source
	 *            源串
	 * @param substring
	 *            子串
	 * @return 若源串中不含有子串, 则返回一个空串
	 */
	public static String afterString(String source, String substring) {
		int index = source.indexOf(substring);
		if (index != INDEX_NOT_FOUND) {
			return source.substring(index + substring.length());
		}
		return "";
	}

	/**
	 * 获取子串在源串中最后一次出现的位置开始, 至源串末尾的串
	 * 
	 * @param source
	 *            源串
	 * @param substring
	 *            子串
	 * @return 若源串中不含有子串, 则返回一个空串
	 */
	public static String afterLastString(String source, String substring) {
		int index = source.lastIndexOf(substring);
		if (index != INDEX_NOT_FOUND) {
			return source.substring(index + substring.length());
		}
		return "";
	}

	/**
	 * 获取源串中, 两个子串之间的串
	 * 
	 * @param source
	 *            源串
	 * @param begin
	 *            子串
	 * @param end
	 *            子串
	 * @return 若源串中不含有其中任一子串, 则返回一个空串
	 */
	public static String betweenString(String source, String begin, String end) {
		int endIndex = source.indexOf(end);
		int beginIndex = source.indexOf(begin);
		if (beginIndex != INDEX_NOT_FOUND && endIndex != INDEX_NOT_FOUND) {
			return source.substring(beginIndex + begin.length(), endIndex);
		}
		return "";
	}

	/**
	 * 首字母大写
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String toCapitalize(String str) {
		byte[] bytes = str.getBytes();
		byte e = bytes[0];
		if(e >= 'a' && e <= 'z'){
			bytes[0] -= 32;
		}
		return new String(bytes);
	}

	/**
	 * 首字母小写
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String toUncapitalize(String str) {
		byte[] bytes = str.getBytes();
		byte e = bytes[0];
		if(e >= 'A' && e <= 'Z'){
			bytes[0] += 32;
		}
		return new String(bytes);
	}

	/**
	 * 以参数替换占位符[?]的形式格式化字符串
	 * 
	 * @param origin
	 *            字符串
	 * @param args
	 *            参数
	 * @return
	 */
	public static String format(String origin, Object... args) {
		for (Object arg : args) {
			origin = origin.replaceFirst(PLACEHOLDER,
					arg == null ? NULL : arg.toString());
		}
		return origin;
	}

}