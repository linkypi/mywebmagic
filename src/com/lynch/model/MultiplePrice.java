package com.lynch.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: JL
 * @version: v1.0
 * @date: 2016/9/3/0003 20:29
 */
public class MultiplePrice {
    private String id;

    public String getId(){
       return id;
    }
    public void setId(String value){
       id = value;
    }
    private String productCode;

    public String getProductCode(){
       return productCode;
    }
    public void setProductCode(String value){
       productCode = value;
    }
    private String price;

    public String getPrice(){
       return price;
    }
    @JSONField(name="priceA")
    public void setPrice(String value){
       price = value;
    }
    private String quantity;

    public String getQuantity(){
       return quantity;
    }
    @JSONField(name="quantityA")
    public void setQuantity(String value){
       quantity = value;
    }
    private String quantityStr;

    public String getQuantityStr(){
       return quantityStr;
    }
    @JSONField(name="quantityAStr")
    public void setQuantityStr(String value){
       quantityStr = value;
    }
    private String isAnonymity;

    public String getIsAnonymity(){
       return isAnonymity;
    }
    public void setIsAnonymity(String value){
       isAnonymity = value;
    }
    private String metalMarket;

    public String getMetalMarket(){
       return metalMarket;
    }
    public void setMetalMarket(String value){
       metalMarket = value;
    }
    private String customerCode;

    public String getCustomerCode(){
       return customerCode;
    }
    public void setCustomerCode(String value){
       customerCode = value;
    }
    private String customerName;

    public String getCustomerName(){
       return customerName;
    }
    public void setCustomerName(String value){
       customerName = value;
    }
    private String provinceCode;

    public String getProvinceCode(){
       return provinceCode;
    }
    public void setProvinceCode(String value){
       provinceCode = value;
    }

}
