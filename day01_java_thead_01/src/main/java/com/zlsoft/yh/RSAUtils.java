//package com.zlsoft.yh;
//
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.security.KeyPair;
//import java.security.PrivateKey;
//import java.security.Security;
//import java.security.Signature;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//import com.yinhai.cdsics.exceptions.ClientException;
//import org.apache.commons.codec.binary.Hex;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.bouncycastle.openssl.PEMReader;
//
//
//public class RSAUtils {
//	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
//	public static final String DEFAULT_KEY_FILE = "bcp_ww.keystore";
//	private static final Map<String,PrivateKey> pks = new ConcurrentHashMap<String, PrivateKey>();
//	static {
//		try {
//			loadPrivateKey(DEFAULT_KEY_FILE);
//		}catch (ClientException e) {
//
//		}
//	}
//
//	public static void loadPrivateKey(String keyFile) throws ClientException{
//		if(!pks.containsKey(keyFile)){
//			pks.put(keyFile, getPrivateKey(keyFile));
//		}
//	}
//
//	private static InputStream getResourceAsStream(String resource) throws ClientException {
//		InputStream in = null;
//		ClassLoader loader = RSAUtils.class.getClassLoader();
//		if (loader != null) {
//			in = loader.getResourceAsStream(resource);
//		}
//		if (in == null) {
//			in = ClassLoader.getSystemResourceAsStream(resource);
//		}
//		if (in == null) {
//			throw new ClientException("请将密钥文件"+resource+"放到工程classpath目录！");
//		}
//		return in;
//	}
//
//	private static PrivateKey getPrivateKey(String key) throws ClientException {
//		PrivateKey pk = null;
//		try {
//			InputStream in = getResourceAsStream(key);
//			if(in == null) {
//				return null;
//			}
//			Security.addProvider(new BouncyCastleProvider());
//			PEMReader reader = new PEMReader(new InputStreamReader(in));
//	 		KeyPair kp = (KeyPair)reader.readObject();
//	 		pk = kp.getPrivate();
//	 		reader.close();
//		} catch (Exception e) {
//             throw new  ClientException(e.getMessage());
//		}
//		return pk;
//	}
//
//
//	/***
//	 * 使用私钥对数据签名
//	 *
//	 * @param content
//	 *            待签名数据
//	 * @param privateKey
//	 *            私钥
//	 * @return 签名数据
//	 * @throws ClientException
//	 */
//	public static String sign(String content, PrivateKey privateKey) throws ClientException {
//		Security.addProvider(new BouncyCastleProvider());
//		try {
//			Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
//			signature.initSign(privateKey);
//			signature.update(content.getBytes("UTF-8"));
//			byte[] signed = signature.sign();
//			return Hex.encodeHexString(signed);
//		} catch (Exception e) {
//			throw new ClientException("数据签名失败!");
//		}
//	}
//
//	/***
//	 * 使用私钥对数据签名
//	 *
//	 * @param content
//	 *            待签名数据
//	 *@param key
//	 *            私钥文件名称
//	 * @return 签名数据
//	 * @throws ClientException
//	 */
//	public static String sign(String content,String key) throws ClientException {
//		if(key == null || key.trim().length() == 0) {
//			throw new  ClientException("加载密钥文件失败！");
//		}
//		PrivateKey pk = pks.get(key);
//		if(pk == null){
//			loadPrivateKey(key);
//			pk = pks.get(key);
//		}
//		if(pk == null) {
//			throw new  ClientException("加载密钥文件失败！");
//		}
//		return sign(content, pk);
//	}
//
//	/***
//	 * 使用私钥对数据签名
//	 *
//	 * @param content
//	 *            待签名数据
//	 * @return 签名数据
//	 * @throws ClientException
//	 */
//	public static String sign(String content) throws ClientException {
//		PrivateKey pk = pks.get(DEFAULT_KEY_FILE);
//		if(pk == null) {
//			throw new  ClientException("加载密钥文件失败！");
//		}
//		return sign(content, pk);
//	}
//
//}
