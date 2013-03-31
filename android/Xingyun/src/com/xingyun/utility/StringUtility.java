package com.xingyun.utility;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StringUtility {
	public static String inputstreamToString(InputStream in) throws Exception {
		int BUFFER_SIZE = 1024;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);

		data = null;
		return new String(outStream.toByteArray(), "UTF-8");
	}
}
