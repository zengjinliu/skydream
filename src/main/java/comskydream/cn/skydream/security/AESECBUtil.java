package comskydream.cn.skydream.security;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Consts;
import org.apache.logging.log4j.Logger;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * ECB方式
 *
 * @author Jayson
 * @date 2020/11/20 16:29
 */
@Slf4j
public class AESECBUtil {

    private static final String METHOD = "AES";
    /**
     * "算法/模式/补码方式"
     */
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * AES加密
     *
     * @param content 加密内容
     * @param key     加密密码，由字母或数字组成
     *                此方法使用AES-128-ECB加密模式，key需要为16位
     *                加密解密key必须相同，如：abcd1234abcd1234
     * @return 加密密文
     */

    public static String enCode(String content, String key) throws Exception {
        //获得密码的字节数组
        byte[] raw = key.getBytes();
        //根据指定算法ALGORITHM自成密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        //初始化密码器，第一个参数为加密(ENCRYPT_MODE)或者解密(DECRYPT_MODE)操作，第二个参数为生成的AES密钥
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(raw));
        //获取加密内容的字节数组(设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
        byte[] byte_content = content.getBytes(Consts.UTF_8);
        //密码器加密数据
        byte[] encode_content = cipher.doFinal(byte_content);
        //将加密后的数据转换为字符串返回
        return Base64.encodeBase64String(encode_content);
    }

    /**
     * AES解密
     *
     * @param content 加密密文
     * @param key     加密密码,由字母或数字组成
     *                此方法使用AES-128-ECB加密模式，key需要为16位
     *                加密解密key必须相同
     * @return 解密明文
     */
    public static String decrypt(String content, String key) throws Exception {
        //获得密码的字节数组
        byte[] raw = key.getBytes();
        //根据指定算法ALGORITHM自成密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        //初始化密码器，第一个参数为加密(ENCRYPT_MODE)或者解密(DECRYPT_MODE)操作，第二个参数为生成的AES密钥
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(raw));
        //把密文字符串转回密文字节数组
        byte[] encode_content = Base64.decodeBase64(content);
        //密码器解密数据
        byte[] byte_content = cipher.doFinal(encode_content);
        //将解密后的数据转换为字符串返回
        return new String(byte_content, Consts.UTF_8);

    }

    private static SecretKeySpec getSecretKey(final byte[] raw) throws NoSuchAlgorithmException {
        //转换成AES的密钥
        return new SecretKeySpec(raw, METHOD);
    }


}