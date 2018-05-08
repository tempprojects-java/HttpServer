package com.gmail.gak.artem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;

public class HttpServer {
	private Integer port = 8080;
	private ServerSocket socket;
	private String publicDir = "./public";

	public HttpServer(Integer port, String dir) {
		super();
		if (port == null || dir == null) {
			throw new IllegalArgumentException();
		}
		this.port = port;
		this.publicDir = dir;
	}

	public HttpServer() {
		super();
	}

	public void start() {
		openServerSocket();
		listen();
	}

	private void openServerSocket() {
		try {
			socket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Cannot open port " + port);
		}
	}

	private void listen() {
		for (;;) {
			try {
				new Client(this, socket.accept());
			} catch (IOException e) {
				System.out.println("Error: Server Socket is open");
			}
		}
	}

	public Response getResponse(Request request) {
		Response response = new Response();
		response.setHeader("Server", "JAVA TestServer");
		response.setHeader("Content-Type", "text/html");
		response.setHeader("Content-Length:", "");
		response.setHeader("Connection:", "close");

		if (request.getUrl().equals("/")) {
			response.setStatusCode(200);

			response.setBody(getFileContent("/index.html"));
			if (response.getBody().isEmpty()) {
				response.setBody("<h1>JAVA TEST SERVER</h1>");
			}
		} else if (!request.getUrl().isEmpty()) {
			File file = new File(publicDir + request.getUrl());
			if (!file.exists()) {
				response.setStatusCode(404);
				response.setBody(getFileContent("/404.html"));
				if (response.getBody().isEmpty()) {
					response.setBody("<h1>404 FILE NOT FOUND</h1>");
				}
			} else {
				response.setStatusCode(200);
				response.setBody(getFileContent(request.getUrl()));
			}
		} else {
			response.setStatusCode(500);
			response.setBody(getFileContent("/500.html"));
			if (response.getBody().isEmpty()) {
				response.setBody("<h1>500 Internal Server Error</h1>");
			}
		}

		return response;
	}

	private String getFileContent(String path) {
		String result = "";
		try (BufferedReader br = new BufferedReader(new FileReader(publicDir + path))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			for (; line != null;) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			result = sb.toString();
		} catch (IOException e) {

		}

		return result;
	}
}
