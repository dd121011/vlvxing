package com.qunar.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by stanley on 15-11-20 At 11:42
 */
public class BookingPriceInfo implements Serializable{
    private static final long serialVersionUID = 7789533672050161132L;
    /**
     * 票面价
     */
    private String ticketPrice;

    /**
     * 机票价格
     */
    private String price;

    /**
     * 裸票价
     */
    private String barePrice;

    /**
     * TTS原票价
     */
    private String originalBarePrice;

    /**
     * 加价
     */
    private String addPrice ;

    /**
     * Y舱价格
     */
    private String basePrice;

    /**
     * 折扣
     */
    private String discount;

    private String childPrice;

    private String childTicketPrice;

    /**
     * TTS原票价
     */
    private String originalChildBarePrice;

    /**
     * 加价
     */
    private String childAddPrice ;

    private String babyPrice;

    private String babyTicketPrice;

    /**
     * TTS原票价
     */
    private String originalBabyBarePrice;

    /**
     * 加价
     */
    private String babyAddPrice ;

    private String returnMoney;

    private String cutMoney;

    private String babyServiceFee;

    private Inventory inventory;

    private String tof;

    private String arf;

    private String childtof;

    private String prdTag;

    private String dtTag;

    Map<String, List<PackageInfo>> priceTag;

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBarePrice() {
        return barePrice;
    }

    public void setBarePrice(String barePrice) {
        this.barePrice = barePrice;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(String childPrice) {
        this.childPrice = childPrice;
    }

    public String getChildTicketPrice() {
        return childTicketPrice;
    }

    public void setChildTicketPrice(String childTicketPrice) {
        this.childTicketPrice = childTicketPrice;
    }

    public String getBabyPrice() {
        return babyPrice;
    }

    public void setBabyPrice(String babyPrice) {
        this.babyPrice = babyPrice;
    }

    public String getBabyTicketPrice() {
        return babyTicketPrice;
    }

    public void setBabyTicketPrice(String babyTicketPrice) {
        this.babyTicketPrice = babyTicketPrice;
    }

    public String getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(String returnMoney) {
        this.returnMoney = returnMoney;
    }

    public String getCutMoney() {
        return cutMoney;
    }

    public void setCutMoney(String cutMoney) {
        this.cutMoney = cutMoney;
    }

    public String getBabyServiceFee() {
        return babyServiceFee;
    }

    public void setBabyServiceFee(String babyServiceFee) {
        this.babyServiceFee = babyServiceFee;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public String getTof() {
        return tof;
    }

    public void setTof(String tof) {
        this.tof = tof;
    }

    public String getArf() {
        return arf;
    }

    public void setArf(String arf) {
        this.arf = arf;
    }

    public String getChildtof() {
        return childtof;
    }

    public void setChildtof(String childtof) {
        this.childtof = childtof;
    }

    public String getPrdTag() {
        return prdTag;
    }

    public void setPrdTag(String prdTag) {
        this.prdTag = prdTag;
    }

    public String getDtTag() {
        return dtTag;
    }

    public void setDtTag(String dtTag) {
        this.dtTag = dtTag;
    }

    public Map<String, List<PackageInfo>> getPriceTag() {
        return priceTag;
    }

    public void setPriceTag(Map<String, List<PackageInfo>> priceTag) {
        this.priceTag = priceTag;
    }


    public String getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(String addPrice) {
        this.addPrice = addPrice;
    }

    public String getOriginalBarePrice() {
        return originalBarePrice;
    }

    public void setOriginalBarePrice(String originalBarePrice) {
        this.originalBarePrice = originalBarePrice;
    }

    public String getOriginalChildBarePrice() {
        return originalChildBarePrice;
    }

    public void setOriginalChildBarePrice(String originalChildBarePrice) {
        this.originalChildBarePrice = originalChildBarePrice;
    }

    public String getChildAddPrice() {
        return childAddPrice;
    }

    public void setChildAddPrice(String childAddPrice) {
        this.childAddPrice = childAddPrice;
    }

    public String getOriginalBabyBarePrice() {
        return originalBabyBarePrice;
    }

    public void setOriginalBabyBarePrice(String originalBabyBarePrice) {
        this.originalBabyBarePrice = originalBabyBarePrice;
    }

    public String getBabyAddPrice() {
        return babyAddPrice;
    }

    public void setBabyAddPrice(String babyAddPrice) {
        this.babyAddPrice = babyAddPrice;
    }

    @Override
    public String toString() {
        return "BookingPriceInfo{" +
                "ticketPrice='" + ticketPrice + '\'' +
                ", price='" + price + '\'' +
                ", barePrice='" + barePrice + '\'' +
                ", basePrice='" + basePrice + '\'' +
                ", discount='" + discount + '\'' +
                ", childPrice='" + childPrice + '\'' +
                ", childTicketPrice='" + childTicketPrice + '\'' +
                ", babyPrice='" + babyPrice + '\'' +
                ", babyTicketPrice='" + babyTicketPrice + '\'' +
                ", returnMoney='" + returnMoney + '\'' +
                ", cutMoney='" + cutMoney + '\'' +
                ", babyServiceFee='" + babyServiceFee + '\'' +
                ", inventory=" + inventory +
                ", tof='" + tof + '\'' +
                ", arf='" + arf + '\'' +
                ", childtof='" + childtof + '\'' +
                ", prdTag='" + prdTag + '\'' +
                ", dtTag='" + dtTag + '\'' +
                ", priceTag=" + priceTag +
                '}';
    }
}
