package com.yc.Tomcat;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.DocumentException;

public class HttpServletResponse {
	private static Dom4j dom4j=new Dom4j();
	private HttpServletRequest request;
	private OutputStream out;
	
	private int status=200;
	private String message="OK";
	private HashMap<String ,String > headerMap=new HashMap<>();
	
	public HttpServletResponse(HttpServletRequest request,OutputStream out) {
		super();
		this.request=request;
		this.out=out;
	}
	/**
	 * response.setContentType("");  //������Ӧ����
	 * response.setStatus(400,"");   //���ý����
	 * response.setHeader("��","ֵ");
	 */
	
	public void commit() throws IOException, DocumentException {
		Map<String, String> contentType=dom4j.parseWebxml();
		String suffix=request.getRequestURL().substring(request.getRequestURL().lastIndexOf(".")+1);
		if(headerMap.containsKey("Content-Type")== false) {
			String type=contentType.get(suffix);
			//������Ӧ����
			setContentType(type);
			
		}
		
		//������Ӧ���ĵ�����
		String responseStr = "HTTP/1.1 "+status+" "+message+"\r\n";
		//responseStr += "Content-Type: "+contentType+"\r\n";
		//дͷ����Ϣ
		for(Entry<String,String> entry:headerMap.entrySet()) {
			responseStr+=entry.getKey()+":"+entry.getValue()+"\r\n";
		}
		
		//д��cookie����
		for(Cookie cookie : cookies) {
			responseStr+=cookie+"\r\n";
		}
		
		
		responseStr +="\r\n";//CRLF ����
		out.write(responseStr.getBytes());
		
		//��Ӧ�ض�����Ҫдbody
		if(status<300 || status>399) {
			
			if(caw.toString().isEmpty()) {
				String rootPath="E:\\java study\\s3\\10.21_HTTP\\photo";
				String filePath=request.getRequestURL();
				//�жϽ���Ƿ����
				String diskPath=rootPath+filePath;
				if(new File(diskPath).exists()==false) {
					diskPath=rootPath+"/404.html";
				}

				FileInputStream fis=new FileInputStream(rootPath+filePath);
				
				int count;
				byte[] buf=new byte[1024];
				//����������ͱ���
				while((count =fis.read(buf))>0) {
					out.write(buf,0,count);
				}
				fis.close();
			}else {
				out.write(caw.toString().getBytes());
			}
		}
	}
	public void setStatus(int status,String message) {
		this.status=status;
		this.message=message;
	}
	public void addHeader(String key,String value) {
		this.headerMap.put(key, value);
	}
	public void setContentType(String contentType) {
		this.headerMap.put("Content-Type",contentType);
	}

	public void sendRedirect(String webPath) {
		/**
		 * ��Ӧ����룺
		 * 1XX�������󣬼�������
		 * 2XX������Ӧ200
		 * 3XX��Ӧ�ض���301 302
		 * 4XX������˴���404 405
		 * 5XX�������˴���
		 */
		this.setStatus(301, "Redirect");
		this.addHeader("Location", webPath);
	}
	/**
	 * ��ζ��� PrintWrite����commitҪ���� �� �ļ�������������
	 * 
	 */
	CharArrayWriter caw=new CharArrayWriter();
	PrintWriter pw=new PrintWriter(caw);
	public PrintWriter getWriter() {
		return pw;
	}
	
	private List<Cookie> cookies=new ArrayList<Cookie>();
	public void addCookie(Cookie cookie) {
		cookies.add(cookie);
	}
	
	
}
