package com.gmail.gak.artem;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {
	private HeaderList headers = new HeaderList();
	private String type;
	private String url;

	public Request(HeaderList headers) {
		super();
		this.headers = headers;
	}

	public Request() {
		super();
	}

	public static Request parseRequest(BufferedReader bfr) {
		Request result = new Request();

		try {
			String line;
			for (int i = 0; (line = bfr.readLine()) != null; ++i) {
				if (i == 0) {
					result.setUrl(getUrl(line));
					result.setType(getType(line));
				}
			}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}

		return result;
	}

	private static String getUrl(String str) {
		return parse(str, Pattern.compile("(?:^GET\\s)(.*)(?:\\sHTTP.*)"));
	}

	private static String getType(String str) {
		return parse(str, Pattern.compile("^(GET|POST)\\s.*(?:\\sHTTP.*)"));
	}

	private static String parse(String str, Pattern pattern) {
		Matcher matcher = pattern.matcher(str);
		if (matcher.find() && matcher.groupCount() > 0) {
			return matcher.group(1);
		}
		return null;
	}

	public void setHeader(String name, String value) {
		if (name == null || value == null) {
			throw new IllegalArgumentException();
		}
		headers.set(new Header(name, value));
	}

	public HeaderList getHeaders() {
		return headers;
	}

	public void setHeaders(HeaderList headers) {
		this.headers = headers;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
