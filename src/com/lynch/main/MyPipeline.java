package com.lynch.main;
import java.util.List;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import net.minidev.json.JSONStyle;
import net.minidev.json.JSONValue;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.*;
import us.codecraft.xsoup.Xsoup;

public class MyPipeline implements Pipeline {

	@Override
	public void process(ResultItems resultItems, Task task) {
		// TODO Auto-generated method stub
	   System.out.println("get page: " + resultItems.getRequest().getUrl());
	
        for (Entry<String, Object> entry : resultItems.getAll().entrySet()) {
        	String key = entry.getKey();
        	//Extract(entry, key);
        	if(key.equals("level1")){
        		System.out.println(String.format("һ��: %s",
        				JSONValue.toJSONString(entry.getValue(), JSONStyle.MAX_COMPRESS)));
        	}	
        	if(key.equals("level2")){
        		System.out.println(String.format("����: %s",
        				JSONValue.toJSONString(entry.getValue(), JSONStyle.MAX_COMPRESS)));
        	}	
        }
	}

	private void Extract(Entry<String, Object> entry, String key) {
		if(key.equals("items")){
			  Document document = Jsoup.parse((String) entry.getValue());

		      List<String> imgUrls = Xsoup.compile("/li/div/div/div[2]/div/ul/li").evaluate(document).list();
		      
		      String price = Xsoup.compile("/li/div/div/div[2]/strong/i").evaluate(document).get();
		      String comments = Xsoup.compile("/html/body/li/div/div/i/div[1]/strong/a").evaluate(document).get();
		      String name =  Xsoup.compile("/li/div/div/div[3]/a/em").evaluate(document).get();
		      
		      System.out.println(String.format("���ƣ�%s \n �۸�: %s \n ��������%s \n ͼƬ��ַ: %s.",
		      		name,price,comments, JSONValue.toJSONString(imgUrls, JSONStyle.MAX_COMPRESS)));
		}
		else if(key.equals("tags")){
		    System.out.println(String.format("���ࣺ%s ",JSONValue.toJSONString(entry.getValue(), JSONStyle.MAX_COMPRESS)));
		}
		else if(key.equals("like")){
			 System.out.println(String.format("ϲ��������%s ",entry.getValue()));
		}
		else if(key.equals("read_comment_collect")){
			List<String> results =  (List<String>)entry.getValue();
			 System.out.println(String.format("read_comment_collect��%s ",JSONValue.toJSONString(entry.getValue(), JSONStyle.MAX_COMPRESS)));
		}
	}

}
