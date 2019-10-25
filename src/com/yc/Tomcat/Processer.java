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
		//���һ��Servlet
		
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
			//��ȡ����������
			byte[] buf=new byte[1024];
			int count;
			count=in.read(buf);
			String content = new String (buf,0,count);
			//����������
			HttpServletRequest request=parseRequest(content);
			HttpServletResponse response=new HttpServletResponse(request, out);
			/**
			 * �������󣺶�Ӧ��һ��html��js��css
			 * ��̬����hello.s
			 * �Ƿ�404���� ��û�������ļ�Ҳû������ĵ�ַ
			 */
			//�ж������ļ��Ƿ����
			String rootPath="E:\\java study\\s3\\10.21_HTTP\\photo";
			String webPath=request.getRequestURL();
			System.out.println(webPath);
			//�жϷ����ļ��Ƿ����
			String diskPath=rootPath+webPath;
			if(new File(diskPath).exists()==true) {
				//��̬����ֱ��commit
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
