package com.yc.Tomcat;

public class RedirectServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		/**
		 * 1��ҳ����ת������ת������Ӧ�ض���
		 * 
		 * 2��ֱ�����html���� ����josn ����
		 */
		response.sendRedirect("index.html");
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

	
	
}
