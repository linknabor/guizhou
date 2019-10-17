package com.yumu.hexie.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

public class RSAUtil {

	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
	
	public static String signByPrivate(String content, String privateKey, String input_charset) throws Exception {
		if (privateKey == null) {
			throw new Exception("加密私钥为空, 请设置");
		}
		PrivateKey privateKeyInfo = getPrivateKey(privateKey);
		return signByPrivate(content, privateKeyInfo, input_charset);
	}
	
    /**
     * 得到私钥
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    private static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes = buildPKCS8Key(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;        
    }
    
    private static byte[] buildPKCS8Key(String privateKey) throws IOException {
        if (privateKey.contains("-----BEGIN PRIVATE KEY-----")) {
            return Base64.decodeBase64(privateKey.replaceAll("-----\\w+ PRIVATE KEY-----", "").getBytes());
        } else if (privateKey.contains("-----BEGIN RSA PRIVATE KEY-----")) {
            final byte[] innerKey = Base64.decodeBase64(privateKey.replaceAll("-----\\w+ RSA PRIVATE KEY-----", "").getBytes());
            final byte[] result = new byte[innerKey.length + 26];
            System.arraycopy(Base64.decodeBase64("MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKY=".getBytes()), 0, result, 0, 26);
            System.arraycopy(BigInteger.valueOf(result.length - 4).toByteArray(), 0, result, 2, 2);
            System.arraycopy(BigInteger.valueOf(innerKey.length).toByteArray(), 0, result, 24, 2);
            System.arraycopy(innerKey, 0, result, 26, innerKey.length);
            return result;
        } else {
            return Base64.decodeBase64(privateKey.getBytes());
        }
    }
    
    public static String signByPrivate(String content, PrivateKey privateKey, String input_charset) throws Exception {
		if (privateKey == null) {
			throw new Exception("加密私钥为空, 请设置");
		}
		java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
		signature.initSign(privateKey);
		signature.update(content.getBytes(input_charset));
		return new String(Base64.encodeBase64(signature.sign()));
	}
    
    /**
     * 读取证书信息
     * @param filePath
     * @param charSet
     * @return
     * @throws Exception
     */
    public static String readFile(String filePath, String charSet) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        try {
            FileChannel fileChannel = fileInputStream.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) fileChannel.size());
            fileChannel.read(byteBuffer);
            byteBuffer.flip();
            return new String(byteBuffer.array(), charSet);
        } finally {
            fileInputStream.close();
        }
    }
    
    public static boolean verifyByKeyPath(String content, String sign, String publicKeyPath, String input_charset) {
        try {
            return verify(content, sign, getKey(publicKeyPath), input_charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static String getKey(String string) throws Exception {
        String content = readFile(string, "UTF8");
        return content.replaceAll("\\-{5}[\\w\\s]+\\-{5}[\\r\\n|\\n]", "");
    }
    
    /**
     * RSA验签名检查
     * @param content 待签名数据
     * @param sign 签名值
     * @param publicKey 支付宝公钥
     * @param input_charset 编码格式
     * @return 布尔值
     */
    public static boolean verify(String content, String sign, String publicKey, String input_charset) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey =  Base64.decodeBase64(publicKey.getBytes());
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            return verify(content, sign, pubKey, input_charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
	public static boolean verify(String content,String sign,PublicKey publicKey,String inputCharset){
		try {
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(publicKey);
            signature.update(content.getBytes(inputCharset));
            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
