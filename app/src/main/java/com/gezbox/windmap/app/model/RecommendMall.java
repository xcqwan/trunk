package com.gezbox.windmap.app.model;

/**
 * Created by zombie on 14-11-10.
 */
public class RecommendMall {
    private String shop_name;
    private String phone;
    private String type = "windmap";

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
