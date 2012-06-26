package com.securitytools;

import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Encriptor {
	private static String Algorithm ="DES";
    //定义加密算法,可用DES,DESede,Blowfish
	private static String keyStr="0D9145E398436846";
    /**
    *构造子注解.
    */
    public Encriptor ()
    {
    }
    /**
    *生成密钥
    *@returnbyte[]返回生成的密钥
    *@throwsexception扔出异常.
    */
    /*public byte[] getSecretKey() throws Exception 
    {
        KeyGenerator keygen =KeyGenerator.getInstance (Algorithm );
        SecretKey deskey =keygen.generateKey ();
        if (debug )System.out.println ("生成密钥:"+byte2hex (deskey.getEncoded ()));
        return deskey.getEncoded ();
    }*/
    /**
    *将指定的数据根据提供的密钥进行加密
    *@paraminput需要加密的数据
    *@paramkey密钥
    *@returnbyte[]加密后的数据
    *@throwsException
    */
    public static String encrypt(String strInput) throws Exception 
    {
    	byte[] input=strInput.getBytes();
    	byte[] key=hex2byte(keyStr);
        SecretKey deskey =new javax.crypto.spec.SecretKeySpec (key ,Algorithm );
        Cipher c1 =Cipher.getInstance (Algorithm);
        c1.init(Cipher.ENCRYPT_MODE ,deskey);
        byte[] cipherByte =c1.doFinal(input);
        return byte2hex(cipherByte) ;
    }
    /**
    *将给定的已加密的数据通过指定的密钥进行解密
    *@paraminput待解密的数据
    *@paramkey密钥
    *@returnbyte[]解密后的数据
    *@throwsException
    */
    public static String decrypt(String strInput)throws Exception 
    {
    	byte[] key=hex2byte(keyStr);
    	byte[] input=hex2byte(strInput);
        SecretKey deskey =new javax.crypto.spec.SecretKeySpec (key ,Algorithm );
        Cipher c1 =Cipher.getInstance (Algorithm);
        c1.init (Cipher.DECRYPT_MODE ,deskey);
        byte []clearByte =c1.doFinal (input );
        return new String(clearByte) ;
    }
    /**
    *字节码转换成16进制字符串
    *@parambyte[]b输入要转换的字节码
    *@returnString返回转换后的16进制字符串
    */
    private static String byte2hex(byte[] b)
    {
        String hs ="";
        String stmp ="";
        for (int n =0 ;n <b.length ;n++)
        {
            stmp =(java.lang.Integer.toHexString (b[n]&0XFF ));
            if (stmp.length ()==1)hs =hs +"0"+stmp ;
            else hs =hs +stmp ;
        }
        return hs.toUpperCase();
    }

	private static byte[] hex2byte(String hexStr) {
		byte[] b = hexStr.getBytes();
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			// 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}
	public static void main(String[] args) throws Exception{
		System.out.print(encrypt("lan "));
		System.out.print(decrypt("B3AF3B9BF5B3E606"));
		
	}
}