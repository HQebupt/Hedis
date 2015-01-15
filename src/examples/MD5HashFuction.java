package examples;

import java.math.BigInteger;

public class MD5HashFuction implements HashFunction {

	@Override
	public BigInteger hash(String key) {
		return MD5Util.md5Value(key);
	}

}
