package com.yc.Tomcat;

import java.io.PrintWriter;

public class GetCookieServlet extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		response.setContentType("text/html;charset=gbk");
		PrintWriter pw=response.getWriter();
		pw.print("<h1>≤‚ ‘ ªÒ»°cookie÷µ</h1>");
		Cookie[] cookies=request.getCookie();
		
		if(cookies != null) {
			for(Cookie c: cookies) {
				pw.print(c.getName() + " = " + c.getValue() + "<br>");
			}
		}
		
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request,response);
	}
}
