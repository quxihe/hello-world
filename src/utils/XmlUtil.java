package com.sinolife.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import com.sinolife.efs.grown.domain.Question;
import com.sinolife.lem.util.StringUtil;


public class XmlUtil{
	
    /** 
     * 将xml格式的字符串转换成Map对象 
     *  
     * @param xmlStr xml格式的字符串 
     * @return Map对象 
     * @throws Exception 异常 
     */  
    @SuppressWarnings("rawtypes")
    public static Map<String, Object> xmlStrToMap(String path) throws Exception {  
        if(StringUtil.isNull(path)) {  
            return null;  
        }  
        Map<String, Object> map = new HashMap<String, Object>();  
        //将xml格式的字符串转换成Document对象  
        SAXReader saxReader = new SAXReader();
        Document doc = saxReader.read(new File(path));
        //获取根节点  
        Element root = doc.getRootElement();  
        //获取根节点下的所有元素  
        List children = root.elements();  
        //循环所有子元素  
        if(children != null && children.size() > 0) {  
            for(int i = 0; i < children.size(); i++) {  
            	Map<String, Object> objMap = new HashMap<String, Object>();  
                Element child = (Element)children.get(i);  
                for (Iterator it = child.nodeIterator(); it.hasNext();) {  
                	Node node = (Node) it.next();  
                    if(StringUtil.isNotNull(node.getName())){
                    	objMap.put(node.getName(),node.getText());  
                    }
                } 
                map.put("key"+i, objMap);
            }  
        }  
        return map;  
    } 
    
    /** 
     * 将Map对象通过反射机制转换成Bean对象 
     *  
     * @param map 存放数据的map对象 
     * @param clazz 待转换的class 
     * @return 转换后的Bean对象 
     * @throws Exception 异常 
     */  
	public static Object mapToBean(Map<String, Object> map, Class<?> clazz) throws Exception {  
        Object obj = clazz.newInstance();  
        if(map != null && map.size() > 0) {  
            for(Map.Entry<String, Object> entry : map.entrySet()) {  
                String propertyName = entry.getKey();  
                Object value = entry.getValue();  
                String setMethodName = "set"  
                        + propertyName.substring(0, 1).toUpperCase()  
                        + propertyName.substring(1);  
                Field field = getClassField(clazz, propertyName);  
                Class<?> fieldTypeClass = field.getType();  
                value = convertValType(value, fieldTypeClass);  
                clazz.getMethod(setMethodName, field.getType()).invoke(obj, value);  
            }  
        }  
        return obj;  
    }  
      
    /** 
     * 将Object类型的值，转换成bean对象属性里对应的类型值 
     *  
     * @param value Object对象值 
     * @param fieldTypeClass 属性的类型 
     * @return 转换后的值 
     */  
    private static Object convertValType(Object value, Class<?> fieldTypeClass) {  
        Object retVal = null;  
        if(Long.class.getName().equals(fieldTypeClass.getName())  
                || long.class.getName().equals(fieldTypeClass.getName())) {  
            retVal = Long.parseLong(value.toString());  
        } else if(Integer.class.getName().equals(fieldTypeClass.getName())  
                || int.class.getName().equals(fieldTypeClass.getName())) {  
            retVal = Integer.parseInt(value.toString());  
        } else if(Float.class.getName().equals(fieldTypeClass.getName())  
                || float.class.getName().equals(fieldTypeClass.getName())) {  
            retVal = Float.parseFloat(value.toString());  
        } else if(Double.class.getName().equals(fieldTypeClass.getName())  
                || double.class.getName().equals(fieldTypeClass.getName())) {  
            retVal = Double.parseDouble(value.toString());  
        } else {  
            retVal = value;  
        }  
        return retVal;  
    }  
  
    /** 
     * 获取指定字段名称查找在class中的对应的Field对象(包括查找父类) 
     *  
     * @param clazz 指定的class 
     * @param fieldName 字段名称 
     * @return Field对象 
     */  
    private static Field getClassField(Class<?> clazz, String fieldName) {  
        if( Object.class.getName().equals(clazz.getName())) {  
            return null;  
        }  
        Field []declaredFields = clazz.getDeclaredFields();  
        for (Field field : declaredFields) {  
            if (field.getName().equals(fieldName)) {  
                return field;  
            }  
        }  
        Class<?> superClass = clazz.getSuperclass();  
        if(superClass != null) {// 简单的递归一下  
            return getClassField(superClass, fieldName);  
        }  
        return null;  
    }   
    
    /** 
     * xml字符串转换成bean对象 
     *  
     * @param xmlStr xml字符串 
     * @param clazz 待转换的class 
     * @return 转换后的对象 
     */  
    @SuppressWarnings("unchecked")
	public static List<Question> xmlStrToBeanList(String path, Class<?> clazz) {  
    	List<Question> questionList = new ArrayList<Question>();;  
        try {  
            // 将xml格式的数据转换成Map对象  
            Map<String, Object> map = xmlStrToMap(path);  
            //将map对象的数据转换成Bean对象  
    		for (Map.Entry<String, Object> entry : map.entrySet()) {
    			Map<String, Object> objMap = (Map<String, Object>) entry.getValue();
    			Question object = (Question)mapToBean(objMap, clazz);  
    			questionList.add(object);
    	    }
        } catch(Exception e) {  
            e.printStackTrace();  
        }  
        return questionList;  
    }  
    
    public static void main(String[] args) {
		XmlUtil.xmlStrToBeanList("E://question.xml",Question.class);
	}
	
}
