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
	 * response.setContentType("");  //设置响应类型
	 * response.setStatus(400,"");   //设置结果码
	 * response.setHeader("键","值");
	 */
	
	public void commit() throws IOException, DocumentException {
		Map<String, String> contentType=dom4j.parseWebxml();
		String suffix=request.getRequestURL().substring(request.getRequestURL().lastIndexOf(".")+1);
		if(headerMap.containsKey("Content-Type")== false) {
			String type=contentType.get(suffix);
			//设置响应类型
			setContentType(type);
			
		}
		
		//定义响应报文的内容
		String responseStr = "HTTP/1.1 "+status+" "+message+"\r\n";
		//responseStr += "Content-Type: "+contentType+"\r\n";
		//写头域信息
		for(Entry<String,String> entry:headerMap.entrySet()) {
			responseStr+=entry.getKey()+":"+entry.getValue()+"\r\n";
		}
		
		//写入cookie数据
		for(Cookie cookie : cookies) {
			responseStr+=cookie+"\r\n";
		}
		
		
		responseStr +="\r\n";//CRLF 空行
		out.write(responseStr.getBytes());
		
		//响应重定向不需要写body
		if(status<300 || status>399) {
			
			if(caw.toString().isEmpty()) {
				String rootPath="E:\\java study\\s3\\10.21_HTTP\\photo";
				String filePath=request.getRequestURL();
				//判断结果是否存在
				String diskPath=rootPath+filePath;
				if(new File(diskPath).exists()==false) {
					diskPath=rootPath+"/404.html";
				}

				FileInputStream fis=new FileInputStream(rootPath+filePath);
				
				int count;
				byte[] buf=new byte[1024];
				//向浏览器发送报文
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
		 * 响应结果码：
		 * 1XX接受请求，继续处理
		 * 2XX正常响应200
		 * 3XX相应重定向301 302
		 * 4XX浏览器端错误404 405
		 * 5XX服务器端错误
		 */
		this.setStatus(301, "Redirect");
		this.addHeader("Location", webPath);
	}
	/**
	 * 如何定义 PrintWrite，在commit要考虑 和 文件输出的配合问题
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
