package com.yc.Tomcat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class parseXml {
	public static  void parse() throws DocumentException {
		SAXReader reader=new SAXReader();
		Document document=reader.read("E:\\apache-tomcat-9.0.24\\conf\\web.xml");
		//获取根节点
		Element rootEle=document.getRootElement();
		System.out.println(rootEle);
		
		List<Element> elements=rootEle.elements("mime-mapping");
		System.out.println(elements);
		Map<String,String> eleMap=new HashMap<String ,String>();
		for(Iterator<?> it=elements.iterator();it.hasNext();) {
			Element e=(Element) it.next();
			String childEle1=e.element("extension").getText();
			String childEle2=e.element("mime-type").getText();
			eleMap.put(childEle1, childEle2);
			System.out.println(childEle1+"  "+childEle2);
		}
		
	}
	
	public static void main(String[] args) throws DocumentException {
		parse();
		
	}
	
}
