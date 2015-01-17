package org.lychie.jutil;

import java.util.Set;
import java.util.List;
import java.util.Date;
import java.util.Arrays;
import java.util.Locale;
import java.util.HashSet;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Collections;
import org.lychie.beanutil.ClassWrapper;

/**
 * 集合工具类
 * 
 * @author Lychie Fan
 */
public class CollectionUtil {

	/**
	 * 集合是否为空
	 * 
	 * @param collection
	 *            集合
	 * @return 若集合为null, 或集合大小为0, 则返回true, 否则返回false
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.size() == 0;
	}

	/**
	 * 集合是否不为空
	 * 
	 * @param collection
	 *            集合
	 * @return 若集合不为null, 而且集合大小不为0, 则返回true, 否则返回false
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	/**
	 * 参数转换为List集合
	 * 
	 * @param objs
	 *            参数对象
	 * @return
	 */
	public static <E> List<E> asList(E... objs) {
		return Arrays.asList(objs);
	}

	/**
	 * 参数集合转换为List集合
	 * 
	 * @param collection
	 *            集合参数
	 * @return
	 */
	public static <E> List<E> asList(Collection<E> collection) {
		if (collection == null) {
			return null;
		}
		if (collection instanceof List) {
			return (List<E>) collection;
		}
		return new ArrayList<E>(collection);
	}

	/**
	 * 参数转换为Set集合
	 * 
	 * @param objs
	 *            参数对象
	 * @return
	 */
	public static <E> Set<E> asSet(E... objs) {
		return new HashSet<E>(Arrays.asList(objs));
	}

	/**
	 * 参数转换为Set集合
	 * 
	 * @param collection
	 *            集合参数
	 * @return
	 */
	public static <E> Set<E> asSet(Collection<E> collection) {
		if (collection == null) {
			return null;
		}
		if (collection instanceof Set) {
			return (Set<E>) collection;
		}
		return new HashSet<E>(collection);
	}

	/**
	 * 将集合转换为数组
	 * 
	 * @param collection
	 *            集合
	 * @return
	 */
	public static <E> E[] toArray(Collection<E> collection) {
		return ArrayUtil.asArray(collection);
	}

	/**
	 * 创建ArrayList实例
	 * 
	 * @return
	 */
	public static <E> List<E> newList() {
		return new ArrayList<E>();
	}

	/**
	 * 创建一个指定容量的ArrayList实例
	 * 
	 * @param size
	 *            大小
	 * @return
	 */
	public static <E> List<E> newList(int size) {
		return new ArrayList<E>(size);
	}

	/**
	 * 创建一个HashSet实例
	 * 
	 * @return
	 */
	public static <E> Set<E> newSet() {
		return new HashSet<E>(16, .75f);
	}

	/**
	 * 创建一个指定容量大小的HashSet实例
	 * 
	 * @param initialCapacity
	 *            初始容量
	 * @param loadFactor
	 *            加载因子
	 * @return
	 */
	public static <E> Set<E> newSet(int initialCapacity, double loadFactor) {
		return new HashSet<E>(initialCapacity, (float) loadFactor);
	}

	/**
	 * 根据给定的Bean属性名称, 按升序顺序排序集合里面的对象
	 * 
	 * @param collection
	 *            集合
	 * @param propertyName
	 *            属性名称
	 */
	public static <E> void sort(Collection<E> collection, String propertyName) {
		sort(collection, propertyName, true);
	}

	/**
	 * 根据给定的Bean属性名称, 按降序顺序排序集合里面的对象
	 * 
	 * @param collection
	 *            集合
	 * @param propertyName
	 *            属性名称
	 */
	public static <E> void sortByDesc(Collection<E> collection,
			String propertyName) {
		sort(collection, propertyName, false);
	}

	/**
	 * 根据给定的Bean属性名称, 排序集合里面的对象
	 * 
	 * @param collection
	 *            集合
	 * @param key
	 *            排序关键字
	 * @param asc
	 *            是否升序
	 */
	private static <E> void sort(Collection<E> collection, String key,
			boolean asc) {
		List<E> list = new ArrayList<E>(collection);
		Class<?> type = collection.iterator().next().getClass();
		CommonComparator comparator = new CommonComparator(key, type, asc);
		Collections.sort(list, comparator);
		collection.clear();
		collection.addAll(list);
	}

	/**
	 * 常用类型比较器
	 */
	private static class CommonComparator implements Comparator<Object> {

		private Type type;
		private String name;
		private boolean asc;
		private ClassWrapper wrapper;

		public CommonComparator(String name, Class<?> type, boolean asc) {
			this.asc = asc;
			this.name = name;
			this.wrapper = ClassWrapper.wrap(type);
			this.type = Type.from(wrapper.getPropertyType(name));
		}

		@Override
		public int compare(Object o1, Object o2) {
			int result;
			Object v1 = wrapper.getPropertyValue(o1, name);
			Object v2 = wrapper.getPropertyValue(o2, name);
			switch (type) {
			case DATE:
				result = dateCompare(v1, v2);
				break;
			case CHAR:
				result = charCompare(v1, v2);
				break;
			case NUMBER:
				result = numberCompare(v1, v2);
				break;
			case STRING:
				result = stringCompare(v1, v2);
				break;
			default:
				return 0;
			}
			return asc ? result : -result;
		}

		/**
		 * 数值类型比较
		 */
		private int numberCompare(Object o1, Object o2) {
			BigDecimal n1 = new BigDecimal(o1.toString());
			BigDecimal n2 = new BigDecimal(o2.toString());
			return n1.compareTo(n2);
		}

		/**
		 * 字符串类型比较
		 */
		private int stringCompare(Object o1, Object o2) {
			String s1 = o1.toString();
			String s2 = o2.toString();
			return Collator.getInstance(Locale.CHINESE).compare(s1, s2);
		}

		/**
		 * 字符类型比较
		 */
		private int charCompare(Object o1, Object o2) {
			Character c1 = (Character) o1;
			Character c2 = (Character) o2;
			return c1.compareTo(c2);
		}

		/**
		 * 日期类型比较
		 */
		private int dateCompare(Object o1, Object o2) {
			Date d1 = (Date) o1;
			Date d2 = (Date) o2;
			return d1.compareTo(d2);
		}

		private enum Type {

			NUMBER, STRING, CHAR, DATE, UNDEFINED;

			public static Type from(Class<?> type) {
				if (TypeUtil.isNumber(type)) {
					return NUMBER;
				}
				if (String.class.isAssignableFrom(type)) {
					return STRING;
				}
				if (char.class.isAssignableFrom(type)) {
					return CHAR;
				}
				if (Character.class.isAssignableFrom(type)) {
					return CHAR;
				}
				if (Date.class.isAssignableFrom(type)) {
					return DATE;
				}
				return UNDEFINED;
			}

		}

	}

}