package com.yc.Tomcat;

public class ForwardServlet extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher rd=new RequestDispatcher("/index.html");
		rd.forward(request, response);
		
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request,response);
	}
	
}
