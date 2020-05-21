package com.ironant.common.utils;

import android.util.Base64;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * Author: lsw
 * Created by lsw on 2017/11/16.
 */

public class RsaUtil {




        /**************************** RSA 公钥加密解密**************************************/
        /**
         * 从字符串中加载公钥,从服务端获取
         *
         * @param pubKey
         *            公钥数据字符串
         * @throws Exception
         *             加载公钥时产生的异常
         */
        public static PublicKey loadPublicKey(String pubKey) {
            PublicKey publicKey = null;
            try {
//                byte[] buffer = Base64.decode(pubKey, Base64.DEFAULT);
                byte[] buffer = pubKey.getBytes();
                KeyFactory keyFactory = KeyFactory.getInstance("RSA","BC");
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
                publicKey =  keyFactory.generatePublic(keySpec);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return publicKey ;
        }

        /**
         * 公钥加密过程
         *
         *            公钥
         * @param plainData
         *            明文数据
         * @return
         * @throws Exception
         *             加密过程中的异常信息
         */
        public static String encryptWithRSA(String plainData, String publicKey) throws Exception {

            PublicKey rsaPublicKey = loadPublicKey(publicKey);

            Cipher cipher = null;
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");// 此处如果写成"RSA"加密出来的信息JAVA服务器无法解析

            cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
            byte[] output = cipher.doFinal(plainData.getBytes("utf-8"));
            // 必须先encode成 byte[]，再转成encodeToString，否则服务器解密会失败
            byte[] encode = Base64.encode(output, Base64.DEFAULT);
            return Base64.encodeToString(encode, Base64.DEFAULT);
        }

        /**
         * 公钥解密过程
         *
         *            公钥
         * @param encryedData
         *            明文数据
         * @return
         * @throws Exception
         *             加密过程中的异常信息
         */
        public static String decryptWithRSA(String encryedData, String publicKey) throws Exception {
            PublicKey rsaPublicKey = loadPublicKey(publicKey);

            Cipher cipher = null;
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");// 此处如果写成"RSA"解析的数据前多出来些乱码
            cipher.init(Cipher.DECRYPT_MODE, rsaPublicKey);
            byte[] output = cipher.doFinal(Base64.decode(encryedData, Base64.DEFAULT));
//            byte[] output = cipher.doFinal(encryedData.getBytes());
            return new String(output);
        }
        /**************************** RSA 公钥加密解密**************************************/


        public static String decryptWithRSAK(String encryedData, PublicKey rsaPublicKey) throws Exception {

            Cipher cipher = null;
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");// 此处如果写成"RSA"解析的数据前多出来些乱码
            cipher.init(Cipher.DECRYPT_MODE, rsaPublicKey);
            byte[] output = cipher.doFinal(Base64.decode(encryedData, Base64.DEFAULT));
//            byte[] output = cipher.doFinal(encryedData.getBytes());
            return new String(output);
        }

}
