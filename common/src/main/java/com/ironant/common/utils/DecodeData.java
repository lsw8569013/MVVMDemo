package com.ironant.common.utils;



import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.util.Base64;
import java.util.zip.GZIPInputStream;


/**
 * Author: lsw
 * Created by lsw on 2017/11/15.
 */

public class DecodeData {

//    private String testStr = "H4sIAAAAAAAAAw3O24JCQAAA0H/Z1x66kHjoAYkZ1IjMaNsH0iTWZeQyfP3u+YLz/RWwpqQ7R5NdB6+bJve8/HHF4dxGCW2bTDo+VSBOtEV5VoOiGgap3Ai6crVAJjSQwMkKa1eLE38etl7Di9sntHckyr1fBvQ1KZguwAF3B4/Mbynb3JfnIF3AuGFBrHNEw0cWqGgisMrUD8ydY6CISnd078vN4UZWun152YAtzAQ8SYg6W2YpKtpao7JOTVPO8bSm3lhETt+biEGNrBJ6SW/jyFU3o6Pld5yfogEl1PD0q3vocekeyxvg/3tD8dco1N49fyb3pQjO+IXZqEk7obIqHus4lS3TMPC006Jp250us+GJvmuicwvOYjFnMe8fqgrfRIS4hOz6qbr0vjworJtqSd3vv37+AEnOs7dgAQAA";

    /**
     * 解密方法 base64+zip+64
     * @param beanStr
     */
    public static String decode(String beanStr) {

        String s = zipDecodeStream(decodeBase64(beanStr));
//        byte[] bytes = unZip(decodeBase64(beanStr));

//        LogUtil.e("--zip"+s);
        StringBuffer sb = new StringBuffer();
        try {
            JSONArray jsonArray = new JSONArray(s);

            for (int i = 0; i <jsonArray.length() ; i++) {
                sb.append( rsaDecode(jsonArray.getString(i)));
//                LogUtil.e("rsA-array--"+rsaDecode(jsonArray.getString(i)));
            }

//            LogUtil.e("rsa 之后数据"+sb.toString());
            return new String(decodeBase64(sb.toString()));
        } catch (JSONException e) {
            LogUtil.e("不是有效的josn 数组格式");
            e.printStackTrace();
        }
        return s;
    }

    /**
     * base 解密
     * @param codeStr
     */
    public static byte[] decodeBase64(String codeStr) {
        byte[] bytes = null;

//        try {
//            byte[] byteData = codeStr.getBytes("UTF-8");
//             bytes = (new Base64Utils()).decode(byteData);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

//        LogUtil.e("base64解密后- "+s);

        return bytes;
    }





    public static byte[] unGZip(byte[] bContent)
    {

//        byte[] data = new byte[1024];
//        try
//        {
//            ByteArrayInputStream in = new ByteArrayInputStream(bContent);
//            GZIPInputStream pIn = new GZIPInputStream(in);
//            DataInputStream objIn = new DataInputStream(pIn);
//
//            int len = 0;
//            int count = 0;
//            while ((count = objIn.read(data, len, len + BUFFERSIZE)) != -1)
//            {
//                len = len + count;
//            }
//
//            byte[] trueData = new byte[len];
//            System.arraycopy(data, 0, trueData, 0, len);
//
//            objIn.close();
//            pIn.close();
//            in.close();
//
//            return trueData;
//
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
        return null;
    }

    /**
     * zip 解压
     * @param str
     */
    public static String zipDecode(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = null;
        try {
            in = new ByteArrayInputStream(str.getBytes("utf-8"));
//            in = new ByteArrayInputStream(str.getBytes("GBK"));

            GZIPInputStream gunzip = new GZIPInputStream(in);
            byte[] buffer = new byte[1024 *3];
            int n;
            while ((n = gunzip.read(buffer))>= 0) {
                out.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // toString()使用平台默认编码，也可以显式的指定如toString(&quot;GBK&quot;)
        return out.toString();
    }

    public static String zipDecodeStream(byte[] str) {
        if (str == null || str.length == 0) {
            return null;
        }

        String zipStr = null;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = null;
        try {

            in = new ByteArrayInputStream(str);
//            in = new ByteArrayInputStream(str.getBytes("GBK"));

            GZIPInputStream gunzip = new GZIPInputStream(in);
            zipStr = convertStreamToString(gunzip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // toString()使用平台默认编码，也可以显式的指定如toString(&quot;GBK&quot;)
        return zipStr;
    }


    public static String convertStreamToString(InputStream is) throws IOException {
        try {
            if (is != null) {
                StringBuilder sb = new StringBuilder();
                String line;
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
                    // BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    while ((line = reader.readLine()) != null) {
                        // sb.append(line);
                        sb.append(line).append("\n");
                    }
                } finally {
                    is.close();
                }
                return sb.toString();
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * rsa decode
     * @param codeStr
     */
    public static String rsaDecode(String codeStr){
//        String key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0SVurBKmHwQpSgvaL8u0ogmau5z3+hiGzR42nk5o0bG6RQ6dBNfhvVHFgXeGQKIsNTEaZDRbfGE9JWOvoWiF4zVaxb7G4YjtH1+n2vC4XYPaUhlZjTzvu2LZ3NSx3o/SAAtTpAlv+pNymHCrrQoeaBYSuM695cKiigzqaIypuCWPRcDnJIX55vuohGCv/HJTLS3ua03LymsPtZo1JYAJ4j2vAnqmc914gpSeXydVBDFynWx8wo0nAKQoQbp0LsT4xN3OYNFD0jSMso2Jb8jyY7/04qVNpMqYGzuKkTEhyLOGHh40FS3R7NQz6D16hEyJ0nZGL0UeaWm0QhBve0237QIDAQAB";
        String rsaStr = null;
//        PublicKey publicKey = null;
//        try {
//            publicKey = RSAUtils.loadPublicKey(MyApplication.getRsaInputStream());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
////             bytes = RSAUtils.decryptByRSA1(key.getBytes(),codeStr.getBytes());
////             bytes = RSAUtils.decryptByRSAKey(publicKey,codeStr.getBytes());
//
//            rsaStr = RsaUtil.decryptWithRSAK(codeStr,publicKey);
////             LogUtil.e(rsaStr);
//        } catch (Exception e) {
//            LogUtil.e("key异常"+e.getMessage().toString());
//            e.printStackTrace();
//        }

        return rsaStr;
    }
}
