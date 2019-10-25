package com.yc.Tomcat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;



public class Processer {
	private HashMap<String,HttpServlet> servletContainer=new HashMap<>();
	{
		//添加一个Servlet
		
		servletContainer.put("/redirect.s",new RedirectServlet());

		servletContainer.put("/forward.s",new ForwardServlet());

		servletContainer.put("/hello.s",new HelloServlet());
		
		servletContainer.put("/cookie.s",new CookieServlet());
		
		servletContainer.put("/page/getcookie.s", new GetCookieServlet());
	}
	public void process(Socket socket) {
		InputStream in;
		OutputStream out;
		try {
			in=socket.getInputStream();
			out=socket.getOutputStream();
			//读取请求报文内容
			byte[] buf=new byte[1024];
			int count;
			count=in.read(buf);
			String content = new String (buf,0,count);
			//解析请求报文
			HttpServletRequest request=parseRequest(content);
			HttpServletResponse response=new HttpServletResponse(request, out);
			/**
			 * 解析请求：对应着一个html，js，css
			 * 动态请求：hello.s
			 * 非法404请求 即没有物理文件也没有虚拟的地址
			 */
			//判断物理文件是否存在
			String rootPath="E:\\java study\\s3\\10.21_HTTP\\photo";
			String webPath=request.getRequestURL();
			System.out.println(webPath);
			//判断访问文件是否存在
			String diskPath=rootPath+webPath;
			if(new File(diskPath).exists()==true) {
				//静态请求直接commit
			}else if(servletContainer.containsKey(webPath)){
				HttpServlet servlet=servletContainer.get(webPath);
				servlet.servise(request, response);
			}else {
				response.setStatus(404, "Not Found");
				request.setRequestURL("/404.html");
			}
			
			response.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private HttpServletRequest parseRequest(String content) {
		HttpServletRequest request=new HttpServletRequest(content);
		System.out.println(content);
		return request;
	}
}
