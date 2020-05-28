package com.sinolife.activity.util.web;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import net.sf.json.xml.XMLSerializer;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.CollectionUtils;

import com.sinolife.activity.util.web.processor.JsonDateValueProcessor;

/**
 * 活动-Json工具类
 * @author renjia.wang001
 *
 */
public class JsonUtil {
	
	public static ObjectMapper mapper;

	static {

		mapper = new ObjectMapper();

		mapper.configure(
				org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		mapper.configure(
				org.codehaus.jackson.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,
				true);
		mapper.getSerializationConfig().setDateFormat(
				new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		mapper.getDeserializationConfig().setDateFormat(
				new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

	}
      
    /** 
     * 从一个JSON 对象字符格式中得到一个java对象，形如： 
     * {"id" : idValue, "name" : nameValue, "aBean" : {"aBeanId" : aBeanIdValue, ...}} 
     * @param object 
     * @param clazz 
     * @return 
     */  
    @SuppressWarnings("rawtypes")
	public static Object getDTO(String jsonString, Class clazz){  
        JSONObject jsonObject = null;  
        try{  
            setDataFormat2JAVA();   
            jsonObject = JSONObject.fromObject(jsonString);  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return JSONObject.toBean(jsonObject, clazz);  
    }  
      
    /** 
     * 从一个JSON 对象字符格式中得到一个java对象，其中beansList是一类的集合，形如： 
     * {"id" : idValue, "name" : nameValue, "aBean" : {"aBeanId" : aBeanIdValue, ...}, 
     * beansList:[{}, {}, ...]} 
     * @param jsonString 
     * @param clazz 
     * @param map 集合属性的类型 (key : 集合属性名, value : 集合属性类型class) eg: ("beansList" : Bean.class) 
     * @return 
     */  
    @SuppressWarnings("rawtypes")
	public static Object getDTO(String jsonString, Class clazz, Map map){  
        JSONObject jsonObject = null;  
        try{  
            setDataFormat2JAVA();   
            jsonObject = JSONObject.fromObject(jsonString);  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return JSONObject.toBean(jsonObject, clazz, map);  
    }  
      
    /** 
     * 从一个JSON数组得到一个java对象数组，形如： 
     * [{"id" : idValue, "name" : nameValue}, {"id" : idValue, "name" : nameValue}, ...] 
     * @param object 
     * @param clazz 
     * @return 
     */  
    @SuppressWarnings("rawtypes")
    public static Object[] getDTOArray(String jsonString, Class clazz){  
        setDataFormat2JAVA();  
        JSONArray array = JSONArray.fromObject(jsonString);  
        Object[] obj = new Object[array.size()];  
        for(int i = 0; i < array.size(); i++){  
            JSONObject jsonObject = array.getJSONObject(i);  
            obj[i] = JSONObject.toBean(jsonObject, clazz);  
        }  
        return obj;  
    }  
      
    /** 
     * 从一个JSON数组得到一个java对象数组，形如： 
     * [{"id" : idValue, "name" : nameValue}, {"id" : idValue, "name" : nameValue}, ...] 
     * @param object 
     * @param clazz 
     * @param map 
     * @return 
     */  
    @SuppressWarnings("rawtypes")
    public static Object[] getDTOArray(String jsonString, Class clazz, Map map){  
        setDataFormat2JAVA();  
        JSONArray array = JSONArray.fromObject(jsonString);  
        Object[] obj = new Object[array.size()];  
        for(int i = 0; i < array.size(); i++){  
            JSONObject jsonObject = array.getJSONObject(i);  
            obj[i] = JSONObject.toBean(jsonObject, clazz, map);  
        }  
        return obj;  
    }  
      
    /** 
     * 从一个JSON数组得到一个java对象集合 
     * @param object 
     * @param clazz 
     * @return 
     */  
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List getDTOList(String jsonString, Class clazz){  
        setDataFormat2JAVA();  
        JSONArray array = JSONArray.fromObject(jsonString);  
        List list = new ArrayList();  
        for(Iterator iter = array.iterator(); iter.hasNext();){  
            JSONObject jsonObject = (JSONObject)iter.next();  
            list.add(JSONObject.toBean(jsonObject, clazz));  
        }  
        return list;  
    }  
    
    /** 
     * 从一个JSON数组得到一个java对象集合 
     * @param object 
     * @param clazz 
     * @return 
     */  
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getDTOObjectList(String jsonString){  
        setDataFormat2JAVA();  
        JSONArray array = JSONArray.fromObject(jsonString);  
        List list = new ArrayList();  
        for(Iterator iter = array.iterator(); iter.hasNext();){  
            list.add(iter.next());  
        }  
        return list;  
    } 
      
    /** 
     * 从一个JSON数组得到一个java对象集合，其中对象中包含有集合属性 
     * @param object 
     * @param clazz 
     * @param map 集合属性的类型 
     * @return 
     */  
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List getDTOList(String jsonString, Class clazz, Map map){  
        setDataFormat2JAVA();  
        JSONArray array = JSONArray.fromObject(jsonString);  
        List list = new ArrayList();  
        for(Iterator iter = array.iterator(); iter.hasNext();){  
            JSONObject jsonObject = (JSONObject)iter.next();  
            list.add(JSONObject.toBean(jsonObject, clazz, map));  
        }  
        return list;  
    }  
      
    /** 
     * 从json HASH表达式中获取一个map，该map支持嵌套功能 
     * 形如：{"id" : "johncon", "name" : "小强"} 
     * 注意commons-collections版本，必须包含org.apache.commons.collections.map.MultiKeyMap 
     * @param object 
     * @return 
     */  
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map getMapFromJson(String jsonString) {  
    	setDataFormat2JAVA();
        JSONObject jsonObject = JSONObject.fromObject(jsonString);  
        Map map = new HashMap(jsonObject.size());
        Object value;
        for(Iterator iter = jsonObject.keys(); iter.hasNext();){  
            String key = (String)iter.next();
            value = jsonObject.get(key);
            if (value instanceof JSONNull) {
            	map.put(key, null);
            	continue;
            }
			map.put(key, value);  
        }  
        return map;
    }  
    
    /** 
     * 从json HASH表达式中获取一个TreeMap，该TreeMap支持嵌套功能 
     * 形如：{"id" : "johncon", "name" : "小强"} 
     * 注意commons-collections版本，必须包含org.apache.commons.collections.map.MultiKeyMap 
     * @param object 
     * @return 
     */  
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static TreeMap getTreeMapFromJson(String jsonString) {  
        setDataFormat2JAVA();  
        JSONObject jsonObject = JSONObject.fromObject(jsonString);  
        TreeMap map = new TreeMap();  
        for(Iterator iter = jsonObject.keys(); iter.hasNext();){  
            String key = (String)iter.next();  
            map.put(key, jsonObject.get(key));  
        }  
        return map;  
    }  
      
    /** 
     * 从json数组中得到相应java数组 
     * json形如：["123", "456"] 
     * @param jsonString 
     * @return 
     */  
    public static Object[] getObjectArrayFromJson(String jsonString) {  
        JSONArray jsonArray = JSONArray.fromObject(jsonString);  
        return jsonArray.toArray();  
    }  
  
  
    /** 
     * 把数据对象转换成json字符串 
     * DTO对象形如：{"id" : idValue, "name" : nameValue, ...} 
     * 数组对象形如：[{}, {}, {}, ...] 
     * map对象形如：{key1 : {"id" : idValue, "name" : nameValue, ...}, key2 : {}, ...} 
     * @param object 
     * @return 
     */  
    public static String getJSONString(Object object){  
        String jsonString = null;  
        // 日期值处理器  
        JsonConfig jsonConfig = new JsonConfig();  
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());  
        jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonDateValueProcessor());  
        if(object != null){  
            if(object instanceof Collection || object instanceof Object[]){  
                jsonString = JSONArray.fromObject(object, jsonConfig).toString();  
            }else{  
                jsonString = JSONObject.fromObject(object, jsonConfig).toString();  
            }  
        }  
        return jsonString == null ? "{}" : jsonString;  
    } 
    
    public static String json2XML(String json){
        JSONObject jobj = JSONObject.fromObject(json);
        String xml =  new XMLSerializer().write(jobj);
        return xml;
    }
      
    private static void setDataFormat2JAVA(){  
        // 设定日期转换格式  
        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"}));  
    }
    
    /**
     * json类型字符串转换为xml类型字符串
     * @param jsonStr json类型字符串
     * @return
     */
    public static String parseJsonStrToXml(String jsonStr){
    	StringBuffer s = new StringBuffer();
    	JSONObject jsonObject = JSONObject.fromObject(jsonStr);
	    return parseObjectToXml(jsonObject,s);
    }
    
    @SuppressWarnings("rawtypes")
	static String parseArrayToXml (Object o,StringBuffer ss){
    		JSONArray jo = JSONArray.fromObject(o);
    		Iterator keyIter = jo.iterator();
    		 while(keyIter.hasNext()){
    		    	Object o1 = keyIter.next();
    		    	if(o1 instanceof JSONArray){
    		    		parseArrayToXml(o1,ss);
    		    	}else{
    		    		parseObjectToXml(o1,ss);
    		    	}
    		    	
    		    }
    		return ss.toString();
    	}
    	
    @SuppressWarnings("rawtypes")
	static String parseObjectToXml(Object o,StringBuffer s){
    		JSONObject jo = JSONObject.fromObject(o);
    		Iterator keyIter = jo.keys();
    		while(keyIter.hasNext()){
    	    	String key =(String)keyIter.next();
    	    	Object o1 = jo.get(key);
    	    	if(o1 instanceof JSONArray){
    	    		s.append("<" + key + ">");
    	    		parseArrayToXml(o1,s);
    	    		s.append("</" + key + ">");
    	    	}else{
    	    		if(jo.getString(key)!=null && jo.getString(key).startsWith("{")){
    	    			s.append("<" + key + ">");
    	    			parseObjectToXml(o1,s);
    	    			s.append("</" + key + ">");
    	    		}else{
	    	    		s.append("<" + key + ">");
	    	    		s.append(jo.getString(key));
	    	    		s.append("</" + key + ">");
    	    		}
    	    	}
    	    	
    	    }
    		return s.toString();
    	}
    
    @SuppressWarnings("unchecked")
	public static List<Map<String, Object>> parseJSON2List(String jsonStr){
        JSONArray jsonArr = JSONArray.fromObject(jsonStr);
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        Iterator<JSONObject> it = jsonArr.iterator();
        while(it.hasNext()){
            JSONObject json2 = it.next();
            list.add(parseJSON2Map(json2.toString()));
        }
        return list;
    }
    
    @SuppressWarnings("unchecked")
	public static Map<String, Object> parseJSON2Map(String jsonStr){
        Map<String, Object> map = new HashMap<String, Object>();
        // 最外层解析
        JSONObject json = JSONObject.fromObject(jsonStr);
        for(Object k : json.keySet()){
            Object v = json.get(k); 
            // 如果内层还是数组的话，继续解析
            if(v instanceof JSONArray){
                List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
                Iterator<JSONObject> it = ((JSONArray)v).iterator();
                while(it.hasNext()){
                    JSONObject json2 = it.next();
                    list.add(parseJSON2Map(json2.toString()));
                }
                map.put(k.toString(), list);
            } else {
                map.put(k.toString(), v);
            }
        }
        return map;
    }

    /**
     * 递归来将所有key值换成小写
     * @author renjia.wang001
     * @param jsonObject
     * @return
     */
	@SuppressWarnings("rawtypes")
	public static JSONObject transObject(JSONObject jsonObject) {
		JSONObject obj = new JSONObject();
		Iterator it = jsonObject.keys();
		while (it.hasNext()) {
			String key = (String) it.next();
			Object object = jsonObject.get(key);
			if (object.getClass().toString().endsWith("String")) {
				obj.accumulate(key.toLowerCase(), object);
			} else if (object.getClass().toString().endsWith("JSONObject")) {
				obj.accumulate(key.toLowerCase(),
						JsonUtil.transObject((JSONObject) object));
			} else if (object.getClass().toString().endsWith("JSONArray")) {
				obj.accumulate(key.toLowerCase(),
						JsonUtil.transArray(jsonObject.getJSONArray(key)));
			}
		}
		return obj;
	}
	private static JSONArray transArray(JSONArray objArr){
        JSONArray jsonObj = new JSONArray();
        for (int i = 0; i < objArr.size(); i++) {
            Object jArray=objArr.getJSONObject(i);
            if(jArray.getClass().toString().endsWith("JSONObject")){
            	jsonObj.add(JsonUtil.transObject((JSONObject)jArray));
            }else if(jArray.getClass().toString().endsWith("JSONArray")){
            	jsonObj.add(JsonUtil.transArray((JSONArray)jArray));
            }
        }
        return jsonObj;
    }
	
    /**
     * Map字符串转换为Map，形如：{activityName=2019签到999活动, activityDesc=2019签到999活动,……}转成{"activityName"="2019签到999活动", "activityDesc"="2019签到999活动",……}
     * @author renjia.wang001
     * @param str
     * @return
     */
    public static Map<String, Object> mapStringToMap(String str){  
        str=str.substring(1, str.length()-1);  
        String[] strs=str.split(",");  
        Map<String, Object> map = new HashMap<String, Object>();  
        for (String string : strs) {
        	String value=null;
        	String[] strStr = string.split("=");
        	String key = string.split("=")[0];
        	if(strStr.length>1) {
        		value = string.split("=")[1];
        	}
        	if(StringUtil.isDataEmpty(key)) {
        		continue;
        	}
        	map.put(key, value);
        }  
        return map;  
    }
    
	/**
	 * 实体对象转成Map
	 * @param obj 实体对象
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> object2Map(Object obj) {
	    Map<String, Object> map = new HashMap<String, Object>();
	    if (obj == null) {
	        return map;
	    }
	    Class clazz = obj.getClass();
	    Field[] fields = clazz.getDeclaredFields();
	    try {
	        for (Field field : fields) {
	            field.setAccessible(true);
	            map.put(field.getName(), field.get(obj));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return map;
	}
	
    /**
     * Map转成实体对象
     * @param map map实体对象包含属性
     * @param clazz 实体对象类型
     * @return
     */
    public static <T> T map2Object(Map<String, Object> map, Class<T> clazz) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (map == null) {
            return null;
        }
        T obj = null;
        try {
            // 使用newInstance来创建对象
            obj = clazz.newInstance();
            // 获取类中的所有字段
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                // 判断是拥有某个修饰符
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                // 当字段使用private修饰时，需要加上
                field.setAccessible(true);
                // 获取参数类型名字
                String filedTypeName = field.getType().getName();
                // 判断是否为时间类型，使用equalsIgnoreCase比较字符串，不区分大小写
                // 给obj的属性赋值
                if (filedTypeName.equalsIgnoreCase("java.util.date")) {
                    String datetimestamp = ""+map.get(field.getName());
                    if (StringUtil.isDataEmpty(datetimestamp) || datetimestamp.equalsIgnoreCase("null")) {
                        field.set(obj, null);
                    } else {
                        field.set(obj, sdf.parse(datetimestamp));
                    }
                } else {
                    field.set(obj, map.get(field.getName()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
    
    /**
     * List<String>转换为List<Map<String,Object>
     * Stirng-->Map
     * String为json串
     * @param sourceList
     * @return
     */
    public static List<Map<String,Object>> transferList(List<String> sourceList){
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		if(!CollectionUtils.isEmpty(sourceList)) {
			for(String jsonStr:sourceList) {
				resultList.add(JsonUtil.parseJSON2Map(jsonStr));
			}
		}
		return resultList;
	}
}  