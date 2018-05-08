package com.gmail.gak.artem;

public class Response {
	private HeaderList headers;
	private Integer statusCode;
	private String body;

	public Response(Integer statusCode) {
		super();
		this.statusCode = statusCode;
		headers = new HeaderList();
		headers.setStatusCode(statusCode);
	}

	public Response(HeaderList headers) {
		super();
		this.headers = headers;
		this.statusCode = headers.getStatusCode();
	}

	public Response() {
		super();
		headers = new HeaderList();
	}

	public void setHeader(String name, String value) {
		if (name == null || value == null) {
			throw new IllegalArgumentException();
		}
		headers.set(new Header(name, value));
	}

	@Override
	public String toString() {
		String result = "";
		result += headers.toString();
		result += body;
		return result;
	}

	public HeaderList getHeaders() {
		return headers;
	}

	public void setHeaders(HeaderList headers) {
		this.headers = headers;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		headers.setStatusCode(statusCode);
		this.statusCode = statusCode;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
