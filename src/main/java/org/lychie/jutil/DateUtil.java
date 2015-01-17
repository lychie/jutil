package org.lychie.jutil;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.lychie.jutil.exception.ParseDateException;
import org.lychie.jutil.exception.UnexpectedException;

/**
 * 日期时间工具类
 * 
 * @author Lychie Fan
 */
public class DateUtil {

	private static final int step = 3;
	private static final int fullCursor = 2;
	private static final int fullIndex = 4 * 5;
	private static final String MATCH_TEMPLATE = "yyyy/MM/dd HH:mm:ss:SSS";
	private static final String PATTERN_TEMPLATE = "0000/00/00 00:00:00:000";
	private static final ThreadLocal<SimpleDateFormat> local = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat();
		}
	};

	/**
	 * 获取SimpleDateFormat实例
	 * 
	 * @param pattern
	 *            模式串
	 * @return
	 */
	public static SimpleDateFormat getSimpleDateFormat(String pattern) {
		SimpleDateFormat format = local.get();
		format.applyPattern(pattern);
		return format;
	}

	/**
	 * 获取表示当前时间的字符串
	 * 
	 * @param pattern
	 *            模式串
	 * @return
	 */
	public static String getCurrentDate(String pattern) {
		return format(new Date(), pattern);
	}

	/**
	 * 日期时间格式化, 自动匹配格式化模式串
	 * 
	 * @param date
	 *            Date
	 * @return
	 */
	public static String format(Date date) {
		SimpleDateFormat format = getSimpleDateFormat(MATCH_TEMPLATE);
		String dateStr = format.format(date);
		String ms = dateStr.substring(dateStr.length() - step);
		if (Short.parseShort(ms) == 0) {
			dateStr = dateStr.substring(0, dateStr.length() - step - 1);
		} else {
			return dateStr;
		}
		String time = dateStr.substring(dateStr.length() - step * 3 + 1);
		time = time.replace(":", "");
		if (Integer.parseInt(time) == 0) {
			dateStr = dateStr.substring(0, dateStr.length() - step * 3);
		} else {
			return dateStr;
		}
		return dateStr;
	}

	/**
	 * 日期时间格式化
	 * 
	 * @param date
	 *            Date
	 * @param pattern
	 *            模式串
	 * @return
	 */
	public static String format(Date date, String pattern) {
		SimpleDateFormat format = getSimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * 解析字符串类型日期, 为参数自动匹配解析模式串
	 * 
	 * @param date
	 *            日期字符串
	 * @return
	 */
	public static Date parse(String date) {
		try {
			String[] mapper = format(date);
			SimpleDateFormat format = getSimpleDateFormat(mapper[0]);
			return format.parse(mapper[1]);
		} catch (ParseException e) {
			throw new ParseDateException("Unparseable date: \"" + date + "\"");
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}

	/**
	 * 解析字符串类型日期
	 * 
	 * @param date
	 *            日期字符串
	 * @param pattern
	 *            模式串
	 * @return
	 */
	public static Date parse(String date, String pattern) {
		try {
			SimpleDateFormat format = getSimpleDateFormat(pattern);
			return format.parse(date);
		} catch (Throwable e) {
			throw new UnexpectedException(e);
		}
	}

	/**
	 * 格式化参数日期字符串, 源日期字符填充到预设模板
	 * 
	 * @param date
	 *            日期字符串
	 * @return
	 */
	private static String[] format(String date) {
		char[] origin = date.toCharArray();
		char[] pattern = PATTERN_TEMPLATE.toCharArray();
		char o, p;
		int cursor = 0, j = 0;
		for (int i = 0; i < origin.length; i++, j++) {
			o = origin[i];
			p = pattern[j];
			if (isCursor(o)) {
				if (!isCursor(p)) {
					moveToNext(pattern, j - 1);
					j++;
				}
				cursor = j;
			}
			if (isCursor(p)) {
				if (!isCursor(o)) {
					cursor = j;
					j++;
				}
			}
			pattern[j] = o;
		}
		j--;
		if (cursor < fullIndex - 1 && j - cursor == 1) {
			moveToNext(pattern, j);
		}
		cursor = pattern.length;
		for (int i = 0; i < fullCursor; i++) {
			if (pattern[cursor - 1] == '0') {
				cursor--;
			} else {
				break;
			}
		}
		if (cursor != pattern.length) {
			char[] target = new char[cursor];
			System.arraycopy(pattern, 0, target, 0, cursor);
			pattern = target;
		}
		char[] match = MATCH_TEMPLATE.toCharArray();
		for (int i = 4; i < fullIndex; i += step) {
			match[i] = pattern[i];
		}
		return new String[] { new String(match), new String(pattern) };
	}

	/**
	 * 字符是否为非数值
	 * 
	 * @param ch
	 *            被测试的字符
	 * @return
	 */
	private static boolean isCursor(char ch) {
		if (ch >= '0' && ch <= '9') {
			return false;
		}
		return true;
	}

	/**
	 * 后移元素
	 * 
	 * @param pattern
	 *            数组
	 * @param i
	 *            被移动的元素的索引值
	 */
	private static void moveToNext(char[] pattern, int i) {
		pattern[i + 1] = pattern[i];
		pattern[i] = '0';
	}

}