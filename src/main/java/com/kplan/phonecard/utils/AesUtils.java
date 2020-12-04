package com.kplan.phonecard.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * AES加密解密
 */
public final class AesUtils {

    /**
     * UTF-8
     */
    public static final String UTF_8="UTF-8";

    /**
     * AES模式
     */
    private static final String AES_MODE="AES/ECB/PKCS5Padding";

    /**
     * AES名称
     */
    private static final String AES="AES";

    /**
     * 密匙长度(AES密匙长度：128,192,256)
     */
    private static final int KEY_LEN=128;

    /**
     * 密匙
     */
    private SecretKeySpec m_secretKeySpec=null;

    /**
     * Cipher
     */
    private Cipher m_cipherDecrypt=null;

    /**
     * Cipher
     */
    private Cipher m_cipherEncrypt=null;

    /**
     * 构造函数
     *
     * @param key 密匙
     * @throws Exception 异常
     */
    public AesUtils(String key) throws Exception {
        setSecretKey(key);
    }

    /**
     * 构造密匙
     *
     * @param key 密匙
     * @throws Exception 异常
     */
    public void setSecretKey(String key) throws Exception {
        byte[] keyData=DatatypeConverter.parseHexBinary(key);
        m_secretKeySpec=new SecretKeySpec(keyData, AES);
        m_cipherDecrypt=Cipher.getInstance(AES_MODE);
        m_cipherDecrypt.init(Cipher.DECRYPT_MODE, m_secretKeySpec);
        m_cipherEncrypt=Cipher.getInstance(AES_MODE);
        m_cipherEncrypt.init(Cipher.ENCRYPT_MODE, m_secretKeySpec);
    }

    /**
     * 解密
     *
     * @param content 字符串
     * @param charset 字符集
     * @return 解密后字符串
     * @throws Exception 异常
     */
    public String decrypt(String content, String charset) throws Exception {
        byte[] decrpted=m_cipherDecrypt.doFinal(DatatypeConverter
                .parseHexBinary(content));
        return new String(decrpted, charset);
    }

    /**
     * 解密
     *
     * @param content 字符串
     * @return 解密后字符串
     * @throws Exception 异常
     */
    public String decrypt(String content) throws Exception {
        return decrypt(content, UTF_8);
    }

    /**
     * 加密
     *
     * @param content 字符串
     * @param charset 字符集
     * @return 加密后字符串
     * @throws Exception 异常
     */
    public String encrypt(String content, String charset) throws Exception {
        byte[] byteContent=content.getBytes(charset);
        byte[] decrpted=m_cipherEncrypt.doFinal(byteContent);
        return DatatypeConverter.printHexBinary(decrpted);
    }

    /**
     * 加密
     *
     * @param content 字符串
     * @return 加密后字符串
     * @throws Exception 异常
     */
    public String encrypt(String content) throws Exception {
        return encrypt(content, UTF_8);
    }

    /**
     * 生成AES密匙
     *
     * @return AES密匙
     * @throws Exception 异常
     */
    public static String getAutoCreateAESKey() throws Exception {
        return getAutoCreateAESKey(KEY_LEN);
    }

    /**
     * 生成AES密匙
     *
     * @param len 密匙长度
     * @return AES密匙
     * @throws Exception 异常
     */
    private static String getAutoCreateAESKey(int len) throws Exception {
        KeyGenerator kg=KeyGenerator.getInstance(AES);
        kg.init(len);
        return DatatypeConverter.printHexBinary(kg.generateKey().getEncoded());
    }

    /**
     * 测试用main函数
     *
     * @param args 参数
     * @throws Exception 异常
     */
    public static void main(String[] args) throws Exception {
//		String key = getAutoCreateAESKey();
        String key="2AD262238100D5337FC3D16E73471821";
        System.out.println("key:" + key);
        String org="123";
        AesUtils aes=new AesUtils(key);
        String encrypt=aes.encrypt(org);
        String decrypt=aes.decrypt(encrypt);
        System.out.println("密匙:" + key);
        System.out.println("原始值:" + org);
        System.out.println("加密后:" + encrypt);
        System.out.println("解密后:" + decrypt);
        System.out.println("是否相等:" + org.equals(decrypt));
    }

}
