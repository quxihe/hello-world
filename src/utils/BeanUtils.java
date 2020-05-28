package com.sinolife.lem.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.ognl.NoSuchPropertyException;
import org.apache.ibatis.ognl.Ognl;

public class BeanUtils {
	/** 获得所有包含set方法 的属性名 */
	public static Set<String> getSetFields(Class clazz) {
		Set<String> fields = new HashSet();
		for (Method method : clazz.getMethods()) {
			String methodName = method.getName();
			if (method.getParameterTypes().length != 1) {
				continue;
			}
			if (methodName.startsWith("set") && !methodName.equals("set")) {
				String name = StringUtil.getFieldName(method.getName());
				fields.add(name);
			}
		}
		return fields;
	}
	/** 获得一个字段的所有属性 */
	public static List<String> getFileds(Class clazz) {
		Set<String> setFields = getSetFields(clazz);
		List<String> fields = new ArrayList();
		for (Method method : clazz.getMethods()) {
			String methodName = method.getName();
			if (method.getParameterTypes().length != 0) {
				continue;
			}
			if (methodName.startsWith("get") && !methodName.equals("get")) {
				String name = StringUtil.getFieldName(method.getName());
				if (setFields.contains(name)) {
					fields.add(name);
				}
			}
		}
		return fields;
	}
	/** 将一个对象转换为map 
	 * @throws Exception */
	public static Map<String, Object> toMap(Object obj,	Map<String, Object> map ) throws Exception {
		if(map==null){
			map=new HashMap();
		}
		List<String> fields = getFileds(obj.getClass());
		try {
			for (String expression : fields) {				
				Object value = null;
				try {
					value = Ognl.getValue(expression, obj);
				} catch (NoSuchPropertyException ex) {
				}
				if(value==null||value instanceof String){
					map.put(expression.toUpperCase(),  value==null?"":value);
				}else if(value instanceof Collection){
					if(value !=null){
						List<Map<String, Object>> list = new ArrayList();
						map.put(expression.toUpperCase(),list);
						
						Collection col = (Collection)value;
						Iterator it=col.iterator();
						
						while(it.hasNext()){
							Object innerObj=it.next();
							if(value==null||value instanceof String){
								Map map3=new HashMap();
								map3.put(expression.toUpperCase(), value==null?"":value);
								list.add(map3);
								
							}else if(value instanceof Collection){
								
								list.add(toMap(innerObj,null));
							}else{
								Map map2 = new HashMap();
								map.put(expression.toUpperCase(), map2);
								list.add(toMap( value,map2));
							}							
						}
					}
				}else{
					Map map2 = new HashMap();
					map.put(expression.toUpperCase(), map2);
					toMap( value,map2);
				}			
			}
		} catch (Exception ex) {
			throw new Exception(ex);
		}
		return map;
	}
	
	
}
