package org.lychie.jutil;

import java.lang.reflect.Array;

/**
 * 类型工具类
 * @author Lychie Fan
 */
public class TypeUtil {

	private static final int integerCursor = 0;
	private static final int integerOffset = 8;
	private static final int decimalCursor = 8;
	private static final int decimalOffset = 4;
	private static final int numbersCursor = 0;
	private static final int numbersOffset = 12;
	private static final int indexNotFound = -1;
	private static final Class<?>[] types  = {
		Byte.TYPE, Byte.class, Short.TYPE, Short.class, Integer.TYPE, Integer.class, 
		Long.TYPE, Long.class, Float.TYPE, Float.class, Double.TYPE,  Double.class, 
		Boolean.TYPE, Boolean.class, Character.TYPE, Character.class
	};
	
	/**
	 * 判断是否是小数类型
	 * 
	 * @param type
	 *            被测试的类型
	 * @return
	 */
	public static boolean isDecimal(Class<?> type) {
		Class<?>[] decimal = subarray(types, decimalCursor, decimalCursor
				+ decimalOffset);
		return ArrayUtil.contains(decimal, type);
	}

	/**
	 * 判断是否是整数类型
	 * 
	 * @param type
	 *            被测试的类型
	 * @return
	 */
	public static boolean isInteger(Class<?> type) {
		Class<?>[] integer = subarray(types, integerCursor, integerCursor
				+ integerOffset);
		return ArrayUtil.contains(integer, type);
	}

	/**
	 * 判断是否是数值类型
	 * 
	 * @param type
	 *            被测试的类型
	 * @return
	 */
	public static boolean isNumber(Class<?> type) {
		Class<?>[] numbers = subarray(types, numbersCursor, numbersCursor
				+ numbersOffset);
		return ArrayUtil.contains(numbers, type);
	}

	/**
	 * 判断是否是基本数据类型或装箱类型
	 * 
	 * @param type
	 *            被测试的类型
	 * @return
	 */
	public static boolean isCompatible(Class<?> type) {
		return ArrayUtil.contains(types, type);
	}

	/**
	 * 获取参数的基本数据类型
	 * 
	 * @param type
	 *            类型
	 * @return
	 */
	public static Class<?> getPrimitive(Class<?> type) {
		if (type.isPrimitive()) {
			return type;
		}
		int index = ArrayUtil.indexOf(types, type);
		if (index != indexNotFound) {
			return types[index - 1];
		}
		throw new IllegalArgumentException(type.getSimpleName()
				+ " is not primitive type");
	}

	/**
	 * 获取参数的装箱类型
	 * 
	 * @param type
	 *            类型
	 * @return
	 */
	public static Class<?> getBoxedPrimitive(Class<?> type) {
		int index = ArrayUtil.indexOf(types, type);
		if (index != indexNotFound) {
			type = types[index];
			if (type.isPrimitive()) {
				type = types[index + 1];
			}
			return type;
		}
		throw new IllegalArgumentException(type.getSimpleName()
				+ " is not primitive type");
	}

	/**
	 * 子数组
	 * 
	 * @param array
	 *            源数组
	 * @param beginIndex
	 *            开始索引
	 * @param endIndex
	 *            结束索引
	 * @return
	 */
	private static <E> E[] subarray(E[] array, int beginIndex, int endIndex) {
		int length = endIndex - beginIndex;
		Class<?> type = ArrayUtil.getElementType(array);
		@SuppressWarnings("unchecked")
		E[] target = (E[]) Array.newInstance(type, length);
		System.arraycopy(array, beginIndex, target, 0, length);
		return target;
	}
	
}