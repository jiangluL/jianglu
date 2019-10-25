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
		//����һ��sax������
		SAXReader reader = new SAXReader();
		//��ȡ�ļ�  ps���ļ�·����Ҫ�Լ��޸�
		Document document = reader.read("E:\\apache-tomcat-9.0.24\\conf\\web.xml");
		//��ȡ���ڵ�
		Element rootElement = document.getRootElement();
		//��ȡָ������Ԫ�ص������ӽڵ�
		List mime_mapping = rootElement.elements("mime-mapping");
		//����һ��HashMap���ڴ������
		Map<String,String> map=new HashMap<String,String>();
		//������������������ӽڵ�
		for (Iterator<?> it = mime_mapping.iterator(); it.hasNext();) {
			Element e = (Element) it.next();
			//��ȡ�ӽڵ��µ��ӽڵ�Ԫ�����ݲ�put��map������
			String extension=e.element("extension").getText();
			String mime_type=e.element("mime-type").getText();
			map.put(extension, mime_type);
			System.out.println(mime_type);
		}
		return map;
	}

}
