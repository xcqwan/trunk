package com.gezbox.windmap.app.model;

/**
 * Created by zombie on 14-11-10.
 */
public class RecommendDeliver {
    private String name;
    private String tel;
    private String type = "windmap";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
