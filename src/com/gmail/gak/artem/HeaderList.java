package com.gmail.gak.artem;

import java.util.ArrayList;
import java.util.List;

public class HeaderList {
	private List<Header> values;
	private Integer statusCode;

	public HeaderList() {
		super();
		values = new ArrayList<>();
	}

	public void set(Header header) {
		if (header == null) {
			throw new IllegalArgumentException();
		}

		Integer index = values.indexOf(header);
		if (index != -1) {
			values.set(index, header);
		} else {
			values.add(header);
		}
	}

	public Header get(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		for (Header value : values) {
			if (value.getName().equals(name)) {
				return value;
			}
		}

		return null;
	}

	public Header getByIndex(Integer index) {
		Header result;
		try {
			result = values.get(index);
		} catch (IndexOutOfBoundsException e) {
			result = null;
		}

		return result;
	}

	@Override
	public String toString() {
		String result = "";

		for (Header header : values) {
			if (header.getName().equals("HTTP/1.1")) {
				result += header.getName() + " " + header.getValue() + "\n\r";
				continue;
			}

			result += header.getName() + ": " + header.getValue() + "\n\r";
		}

		result += "\n\r";

		return result;
	}

	public List<Header> getValues() {
		return values;
	}

	public void setValues(List<Header> values) {
		this.values = values;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer code) {
		if (code == null) {
			throw new IllegalArgumentException();
		}
		String value = "";

		if (code.equals(200)) {
			value = "200 OK";
		} else if (code.equals(301)) {
			value = "301 Moved Permanently";
		} else if (code.equals(400)) {
			value = "400 Bad Request";
		} else if (code.equals(401)) {
			value = "401 Unauthorized";
		} else if (code.equals(403)) {
			value = "403 Forbidden";
		} else if (code.equals(404)) {
			value = "404 Not Found";
		} else {
			value = "500 Internal Server Error";
		}
		values.set(0, new Header("HTTP/1.1", value));
	}

}
