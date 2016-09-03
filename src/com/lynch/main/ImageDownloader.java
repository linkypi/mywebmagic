package com.lynch.main;

import org.apache.http.HttpResponse;
import org.apache.http.Header;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

import java.io.IOException;

/**
 * @author: JL
 * @version: v1.0
 * @date: 2016/9/3/0003 21:34
 */
public class ImageDownloader extends HttpClientDownloader
{
    @Override
    protected String getContent(String charset, HttpResponse httpResponse) throws IOException {
        //String a = httpResponse.toString();
       Header[] arr = httpResponse.getHeaders("Content-Type");
        System.out.println(arr[0].getValue());

        return super.getContent(charset, httpResponse);
    }

//    @Override
//    protected String getContent(String charset, HttpResponse httpResponse) throws IOException {
//
//        byte[] imageByte = EntityUtils.toByteArray(httpResponse.getEntity());
//        String iageStr = ImageBase64Utils.GetImageStr(imageByte);
//        return iageStr;
//    }
}
