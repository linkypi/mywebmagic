package com.lynch.main;

import java.util.List;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import com.lynch.dao.MultiplePriceDao;
import com.lynch.model.MultiplePrice;
import com.lynch.model.Pagination;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author code4crafter@gmail.com <br>
 */
public class SinaBlogProcessor implements PageProcessor {
    private static final String DOMAIN =  "www.lessomall.com";
    private static final String SCHEMA =  "http";
    private static final String URL_CATEGORY = DOMAIN + "/rest/api/v1/home/categoryList";
    private static final String URL_SINGLE_CATEGORY = DOMAIN + "/rest/api/v1/product/productListForCategory";
    private static final String URL_MULTIPRICE = DOMAIN + "/rest/api/v1/innershops/multiPrice";
    private static final String URL_DETAIL = DOMAIN + "/p";

    private Site site = Site.me()
            .setCharset("UTF-8")
            .setDomain(DOMAIN)
            .setSleepTime(3000)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.73 Safari/537.36");
                   // "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/config/applicationContext.xml");// 没有classpath表示当前目录

        MultiplePriceDao dao = context.getBean(MultiplePriceDao.class);
        try
        {
            dao.insert(new MultiplePrice(UUID.randomUUID().toString().replace("-",""),
                    "234231","90","1200","40032",false,"customcode","lessmall"));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }



        Spider.create(new SinaBlogProcessor())
        //.addUrl(SCHEMA+"://"+URL_CATEGORY) //必须加上前缀http
         .addUrl("http://www.lessomall.com/medias/?context=bWFzdGVyfGltYWdlc3wxMDIwOTl8aW1hZ2UvanBlZ3xpbWFnZXMvaDJkL2hhNi84Nzk4Njk0OTY1Mjc4LmpwZ3w3ODc4NjAxNDg4Y2FkMmQ0ZDMwOWYzODhhNmRhNmNlOTM2OTc0YmFiODM4Mjc1YmI4ZmZmN2RkMDQzMTIzY2Ux")
        .addPipeline(new MyPipeline()).addPipeline(new ConsolePipeline())
        .setDownloader(new ImageDownloader())
        .thread(1)
        .run();

    }
    
    @Override
    public void process(Page page) {
    	String raw = page.getRawText();
        if (page.getUrl().regex(URL_CATEGORY).match())
        {
        	getAllCategory(page);
        }
        else if(page.getUrl().regex(URL_SINGLE_CATEGORY).match())
        {
            getPerCategoryProducts(page);
        }
        else if(page.getUrl().regex(URL_MULTIPRICE).match())
        {
            getProductMultiPrice(page);
        }
        else if(page.getUrl().regex(URL_DETAIL).match())
        {
            getProductDetail(page);
        }
        
    }

    /**
     * 获取商品详情
     * @param page
     */
    private void getProductDetail(Page page)
    {
        List<String> list = page.getHtml().$("div.productsPresentation div.detailedInformation p").all();

        System.out.println("detail:" + list );

        getImageLinks(page);
    }

    private void getImageLinks(Page page)
    {
        List<String> list = page.getHtml().$("p.detailImg img").links().all();
    }

    /**
     * 抓取商品更多价格优惠
     * @param page
     */
    private void getProductMultiPrice(Page page){
        String multistr = page.getJson().jsonPath("$.dealerProductDTOs[*]").get();
        System.out.println("multiple price: " + multistr);
        //List<MultiplePrice> multiples = JSONObject.parseObject(multistr,new TypeReference<List<MultiplePrice>>(){});

    }

    /**
     * 抓取每个类别的所有商品
     * @param page
     */
    private void getPerCategoryProducts(Page page) {
        List<String> codes = page.getJson().jsonPath("$.searchPageData.results[*].code").all();
        if(codes!=null){
            /*抓取商品更多价格优惠*/
            for(String item : codes){
                long timestamp =  System.currentTimeMillis();
                String multiple_target = String.format("%s://%s?_r=%s&productCode=%s",SCHEMA,URL_MULTIPRICE,timestamp,item);
                page.addTargetRequest(multiple_target);

                //抓取详情页面
                String detail_target = String.format("%s://%s/%s",SCHEMA,URL_DETAIL,item);
                page.addTargetRequest(detail_target);
                break;
            }
            //抓取该类别下一页商品
            //getNextPage(page);

        }
    }

    private void getNextPage(Page page) {
        String paginationstr = page.getJson().jsonPath("$.searchPageData.pagination").get();
        Pagination pagination = JSONObject.parseObject(paginationstr,new TypeReference<Pagination>(){});
        if(pagination!=null && pagination.getCurrentPage()<pagination.getTotalPages()){
            long timestamp =  System.currentTimeMillis();
            String categoryCode = page.getJson().jsonPath("$.searchPageData.categoryCode").get();
            String target = String.format("%s://%s?_r=%s&categoryCode=%s&isFirstLoaded=true&page=%s",
                    SCHEMA,URL_SINGLE_CATEGORY,timestamp,categoryCode,pagination.getCurrentPage()+1);
            page.addTargetRequest(target);
        }
    }

    /**
     * 获取所有类别
     * @param page
     */
    private void getAllCategory(Page page) {
		List<String> codes = page.getJson().jsonPath("$.subCategoris[*].subCategoris.subCategoris.code").all();
		if(codes!=null)
		{
			for(String item : codes)
			{
			    /*抓取各个分类所有品种*/
                if(!item.equals("")){
			       long timestamp =  System.currentTimeMillis();
				   String target = String.format("%s://%s?_r=%s&categoryCode=%s&isFirstLoaded=true&page=0",
                           SCHEMA,URL_SINGLE_CATEGORY,timestamp,item);
				   page.addTargetRequest(target);
			   }
			   break;
			}
		}
		page.putField("json_category",page.getJson());
	}


}
