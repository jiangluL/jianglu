package com.yc.Tomcat;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Tomcat {
	
	public static void main(String[] args) throws IOException {
		Tomcat tomcat=new Tomcat();
		tomcat.startup();
	}
	
	//��������
	public void startup() throws IOException {
		ServerSocket server=new ServerSocket(8080);
		boolean running=true;
		while(running) {
			Socket socket=server.accept();
			new Thread() {
				public void run() {
					new Processer().process(socket);
		
				}
			}.start();
		}
		server.close();
	}
	//�رշ�����
	public void shutdown() {
		
	}
}
