package com.yc.Tomcat;

import java.io.PrintWriter;

public class CookieServlet extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		response.setContentType("text/html;charset=gbk");
		PrintWriter pw=response.getWriter();
		pw.print("<h1>≤‚ ‘cookie</h1>");
		
		Cookie cookie=new Cookie("username","jack");
		response.addCookie(cookie);
		
		cookie=new Cookie("level","100");
		cookie.setMaxAge(60*60);
		response.addCookie(cookie);
		
		cookie=new Cookie("page","1");
		cookie.setPath("/page");
		response.addCookie(cookie);
		
		cookie=new Cookie("user","test");
		cookie.setPath("/user");
		response.addCookie(cookie);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request,response);
	}
}
