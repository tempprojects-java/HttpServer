package com.gmail.gak.artem;

public class Main {

	public static void main(String[] args) {

		HttpServer server = new HttpServer(8080, "./public");
		server.start();

		System.out.println("Done");
	}

}
