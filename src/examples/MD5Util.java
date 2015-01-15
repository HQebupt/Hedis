package examples;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	private static final String UTF8_ENCODING = "UTF-8";

	public static String md5(String text, int type) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			md.update(toBytes(text));
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i)); // ������String,y��Ϊ�漰��λ���㡣
			}

			return (type == 32) ? buf.toString().toUpperCase() : buf.toString()
					.substring(8, 24).toUpperCase();// 32λ�ļ���,����ֱ�Ӽ���.

		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	// �õ�һ��������MD5ֵ,ʹ��BigInteger�洢��
	public static BigInteger md5Value(String text) {
		String value = md5(text, 32);
		return new BigInteger(value, 16);
	}

	// String to byte[]
	private static byte[] toBytes(String s) {
		try {
			return s.getBytes(UTF8_ENCODING);
		} catch (UnsupportedEncodingException e) {
			System.err.println("UTF-8 not supported?" + e);
			return null;
		}
	}
}