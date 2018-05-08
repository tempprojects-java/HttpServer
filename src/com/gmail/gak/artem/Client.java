package com.gmail.gak.artem;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {
	private Socket socket;
	private Thread thread;
	private HttpServer server;
	private InputStream inStream;
	private OutputStream outStream;

	public Client(HttpServer server, Socket socket) {
		super();
		this.server = server;
		this.socket = socket;
		thread = new Thread(this);
		thread.start();
	}

	public Client() {
		super();
	}

	public void run() {
		try {
			inStream = socket.getInputStream();
			outStream = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(outStream);

			byte[] buf = new byte[8192];
			int rlen = inStream.read(buf, 0, buf.length);
			if (rlen <= 0) {
				socket.close();
				return;
			}

			ByteArrayInputStream hbis = new ByteArrayInputStream(buf, 0, rlen);
			BufferedReader hin = new BufferedReader(new InputStreamReader(hbis));

			Request request = Request.parseRequest(hin);
			String response = server.getResponse(request).toString();
			pw.print(parseResponse(response));
			pw.flush();

			closeAll();
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private String parseResponse(String response) {

		/* TODO create REAL Response parser with blackjack and hookers! */

		String os = System.getProperty("os.name");
		response = response.replaceAll("\\{\\$SysInfo\\.os\\}", os);
		String cores = "" + Runtime.getRuntime().availableProcessors();
		response = response.replaceAll("\\{\\$SysInfo\\.cores\\}", cores);
		return response;
	}

	private void closeAll() {
		try {
			inStream.close();
			outStream.close();
			socket.close();
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
