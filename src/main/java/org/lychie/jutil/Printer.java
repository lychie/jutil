package org.lychie.jutil;

import java.util.Map;
import java.util.Date;
import java.util.Iterator;
import java.lang.reflect.Array;
import org.lychie.beanutil.BeanClass;

/**
 * 打印器
 * 
 * @author Lychie Fan
 */
public class Printer {

	private static final String NEWLINE = "\r\n";
	private StringBuilder queue = new StringBuilder();

	/**
	 * 打印对象
	 * 
	 * @param obj
	 */
	public static void print(Object obj) {
		Printer printer = new Printer();
		printer.print(obj, false, true);
		printer.flush();
	}

	/**
	 * 输出缓冲区数据
	 */
	private void flush() {
		System.out.print(queue);
	}

	/**
	 * 打印对象
	 * 
	 * @param obj
	 *            对象
	 * @param isChild
	 *            是否是子元素
	 * @param isNewLine
	 *            是否换行
	 */
	private void print(Object obj, boolean isChild, boolean isNewLine) {
		if (obj instanceof Iterable) {
			Iterable<?> it = (Iterable<?>) obj;
			printIterator(it.iterator(), isChild, isNewLine);
		} else if (obj instanceof Iterator) {
			printIterator((Iterator<?>) obj, isChild, isNewLine);
		} else if (ArrayUtil.isArray(obj)) {
			printIterator(new ArrayIterator(obj), true, isNewLine);
		} else if (obj instanceof Map) {
			printMap((Map<?, ?>) obj, isNewLine);
		} else if (BeanClass.isFrom(obj, Date.class)) {
			printDate((Date) obj, isNewLine);
		} else {
			printOut(obj, isNewLine);
		}
	}

	/**
	 * 迭代器类型对象打印
	 * 
	 * @param it
	 *            对象
	 * @param isChild
	 *            是否是子元素
	 * @param isNewLine
	 *            是否换行
	 */
	private void printIterator(Iterator<?> it, boolean isChild,
			boolean isNewLine) {
		if (isChild) {
			printOut("[");
		}
		while (it.hasNext()) {
			print(it.next(), true, false);
			if (it.hasNext() && isChild) {
				printOut(", ");
			}
		}
		if (isChild) {
			printOut("]" + NEWLINE);
		}
	}

	/**
	 * 散列表类型对象打印
	 * 
	 * @param map
	 *            对象
	 * @param isNewLine
	 *            是否换行
	 */
	private void printMap(Map<?, ?> map, boolean isNewLine) {
		for (Object key : map.keySet()) {
			print(key, true, false);
			printOut(" : ");
			print(map.get(key), true, true);
		}
	}

	/**
	 * 日期类型对象打印
	 * 
	 * @param date
	 *            对象
	 * @param isNewLine
	 *            是否换行
	 */
	private void printDate(Date date, boolean isNewLine) {
		printOut(DateUtil.format(date), isNewLine);
	}

	/**
	 * 打印输出到缓冲区
	 * 
	 * @param obj
	 *            对象
	 */
	private void printOut(Object obj) {
		queue.append(obj);
	}

	/**
	 * 打印输出到缓冲区
	 * 
	 * @param obj
	 *            对象
	 * @param isNewLine
	 *            是否换行
	 */
	private void printOut(Object obj, boolean isNewLine) {
		printOut(obj);
		if (isNewLine) {
			printOut(NEWLINE);
		}
	}

	/**
	 * 数组迭代器
	 * 
	 * @author Lychie Fan
	 */
	private static class ArrayIterator implements Iterator<Object> {

		private int length;
		private int cursor;
		private Object array;

		public ArrayIterator(Object array) {
			this.array = array;
			this.length = Array.getLength(array);
		}

		@Override
		public boolean hasNext() {
			return length > cursor;
		}

		@Override
		public Object next() {
			return Array.get(array, cursor++);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("unsupported operation");
		}

	}

}