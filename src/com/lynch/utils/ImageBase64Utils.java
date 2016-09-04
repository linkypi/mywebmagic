package com.lynch.utils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * @author: JL
 * @version: v1.0
 * @date: 2016/9/4/0004 10:02
 */
public class ImageBase64Utils {
    public static String GetImageStr(byte[] data) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }

    public static boolean GenerateImage(String path, String filename, String imgStr) {
        //对字节数组字符串进行Base64解码并生成图片
        System.out.println("imgStr == null:" + imgStr == null);
        if (imgStr == null) //图像数据为空
        {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] arr = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < arr.length; ++i) {
                if (arr[i] < 0) {//调整异常数据
                    arr[i] += 256;
                }
            }
            //生成jpeg图片
            String imgFilePath = path + filename;//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(arr);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            System.out.println("保存图片发生异常:" + e.getMessage());
            return false;
        }
    }
}
