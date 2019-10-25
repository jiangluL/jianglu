package com.yc.Tomcat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Dom4j {

	public static Map<String,String> parseWebxml() throws DocumentException {
		//建立一个sax解析器
		SAXReader reader = new SAXReader();
		//读取文件  ps：文件路径需要自己修改
		Document document = reader.read("E:\\apache-tomcat-9.0.24\\conf\\web.xml");
		//获取根节点
		Element rootElement = document.getRootElement();
		//获取指定名称元素的所有子节点
		List mime_mapping = rootElement.elements("mime-mapping");
		//建立一个HashMap用于存放数据
		Map<String,String> map=new HashMap<String,String>();
		//迭代器迭代这个所有子节点
		for (Iterator<?> it = mime_mapping.iterator(); it.hasNext();) {
			Element e = (Element) it.next();
			//获取子节点下的子节点元素内容并put到map集合中
			String extension=e.element("extension").getText();
			String mime_type=e.element("mime-type").getText();
			map.put(extension, mime_type);
			System.out.println(mime_type);
		}
		return map;
	}

}
