package org.lychie.jutil;

import java.util.Date;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.lang.reflect.Array;
import org.lychie.beanutil.BeanMethod;
import org.lychie.jutil.exception.UnexpectedException;
import org.lychie.jutil.exception.ConvertCastException;

/**
 * 转换工具类
 * 
 * @author Lychie Fan
 */
public class ConvertUtil {

	/**
	 * 字符串转换成参数类型的实例
	 * 
	 * @param value
	 *            字符串类型值
	 * @param type
	 *            期望得到的类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> E convert(String value, Class<E> type) {
		if (TypeUtil.isCompatible(type)) {
			return (E) compatibleValue(value, type);
		}
		if (type == Date.class) {
			return (E) DateUtil.parse(value);
		}
		if (type == byte[].class) {
			try {
				return (E) value.getBytes("UTF-8");
			} catch (Throwable e) {
				throw new UnexpectedException(e);
			}
		}
		if (type == BigInteger.class) {
			return (E) new BigInteger(value);
		}
		if (type == BigDecimal.class) {
			return (E) new BigDecimal(value);
		}
		if (type == String.class) {
			return (E) value;
		}
		throw new ConvertCastException(value
				+ " can not be automatically converted into "
				+ type.getSimpleName() + " type");
	}

	/**
	 * 对象转换成参数类型的实例, 支持数组
	 * 
	 * @param value
	 *            字符串类型值
	 * @param type
	 *            期望得到的类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> E convert(Object value, Class<E> type) {
		Class<?> valueType = value.getClass();
		if (valueType.isArray() && type.isArray()) {
			return (E) arrayConvertor(value, type);
		}
		if (valueType.isArray() && valueType.getComponentType() == byte.class
				&& type == String.class) {
			try {
				return (E) new String((byte[]) value, "UTF-8");
			} catch (Throwable e) {
				throw new UnexpectedException(e);
			}
		}
		if ((valueType == BigInteger.class || valueType == BigDecimal.class)
				&& TypeUtil.isNumber(type)) {
			String name = TypeUtil.getPrimitive(type) + "Value";
			return BeanMethod.invoke(value, name);
		}
		return convert(value.toString(), type);
	}

	/**
	 * Byte转换
	 * 
	 * @param value
	 *            字符串类型值
	 * @return
	 */
	static Byte byteConvertor(String value) {
		return Byte.valueOf(value);
	}

	/**
	 * Long转换
	 * 
	 * @param value
	 *            字符串类型值
	 * @return
	 */
	static Long longConvertor(String value) {
		return Long.valueOf(value);
	}

	/**
	 * Short转换
	 * 
	 * @param value
	 *            字符串类型值
	 * @return
	 */
	static Short shortConvertor(String value) {
		return Short.valueOf(value);
	}

	/**
	 * Float转换
	 * 
	 * @param value
	 *            字符串类型值
	 * @return
	 */
	static Float floatConvertor(String value) {
		return Float.valueOf(value);
	}

	/**
	 * Double转换
	 * 
	 * @param value
	 *            字符串类型值
	 * @return
	 */
	static Double doubleConvertor(String value) {
		return Double.valueOf(value);
	}

	/**
	 * Integer转换
	 * 
	 * @param value
	 *            字符串类型值
	 * @return
	 */
	static Integer intConvertor(String value) {
		return Integer.valueOf(value);
	}

	/**
	 * Character转换
	 * 
	 * @param value
	 *            字符串类型值
	 * @return
	 */
	static Character charConvertor(String value) {
		return value.charAt(0);
	}

	/**
	 * Boolean转换
	 * 
	 * @param value
	 *            字符串类型值
	 * @return
	 */
	static Boolean booleanConvertor(String value) {
		if (value.equals("1") || value.equalsIgnoreCase("yes")
				|| value.equalsIgnoreCase("true")) {
			return true;
		} else if (value.equals("0") || value.equalsIgnoreCase("no")
				|| value.equalsIgnoreCase("false")) {
			return false;
		}
		throw new IllegalArgumentException(value
				+ " can not be parsed into boolean");
	}

	/**
	 * 数组转换
	 * 
	 * @param array
	 *            数组对象
	 * @param type
	 *            期望的类型
	 * @return
	 */
	static Object arrayConvertor(Object array, Class<?> type) {
		int length = Array.getLength(array);
		Object target = Array.newInstance(type.getComponentType(), length);
		for (int i = 0; i < length; i++) {
			Array.set(target, i, Array.get(array, i));
		}
		return target;
	}

	/**
	 * 字符串与基本数据类型、装箱类型之间的转换
	 * 
	 * @param value
	 *            字符串类型值
	 * @param type
	 *            期望得到的类型
	 * @return
	 */
	private static Object compatibleValue(String value, Class<?> type) {
		try {
			String name = TypeUtil.getPrimitive(type) + "Convertor";
			return BeanMethod.invoke(ConvertUtil.class, name, value);
		} catch (Throwable e) {
			throw new ConvertCastException(value
					+ " can not be automatically converted into "
					+ type.getSimpleName() + " type");
		}
	}

}