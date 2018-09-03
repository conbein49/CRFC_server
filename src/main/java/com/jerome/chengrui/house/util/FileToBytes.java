package com.jerome.chengrui.house.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;

public class FileToBytes {

	public static byte[] getFileBytes(String resourcePath) {
		ByteArrayOutputStream cb = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		try(InputStream stream = Resources.getUrlAsStream(resourcePath)) {
			int len = 0;
			while((len = stream.read(b)) != -1) {
				cb.write(b, 0, len);
			}
		} catch (IOException e) {
			throw new InternalError("Failed to get bytes from file " + resourcePath);
		}
		return cb.toByteArray();
	}
}
