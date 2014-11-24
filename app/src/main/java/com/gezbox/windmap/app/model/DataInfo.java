package com.gezbox.windmap.app.model;

/**
 * Created by zombie on 14-10-29.
 */
public class DataInfo {
    //送达单数
    private String finished_orders;
    //历史最高
    private String highest_orders;
    //送达人数
    private String finished_delivers;
    //新门店送达单数
    private String shop_first_orders;
    //新配送员送达单数
    private String deliver_first_orders;
    //呼叫商圈数
    private String business_district_calls;
    //新呼叫商圈数
    private String first_call_business_district;
    //未呼叫商圈数
    private String no_call_business_district;
    //新呼叫商圈送达单数
    private String first_call_business_district_orders;
    //呼叫门店
    private String shop_calls;
    //呼叫人次
    private String group_calls;
    //应答队伍，接单的队伍数
    private String adopted_teams;
    //应答风先生数
    private String adopted_delivers;
    //投诉人次 异常订单数+投诉次数
    private String deliver_complaints;
    //异常单数
    private String error_orders;
    //最远距离
    private String most_distance;
    //平均距离
    private String average_distance;
    //最长时间
    private String longest_time;
    //平均时间
    private String average_time;
    //新呼叫门店数
    private String first_call_shops;
    //未呼叫门店数
    private String no_call_shops;
    //新配送人员
    private String first_send_deliver;
    //未配送人员
    private String no_send_deliver;
    //新配送队
    private String first_send_team;
    //未配送队
    private String no_send_team;
    //新配送队送达单数
    private String first_send_team_orders;

    public String getAdopted_delivers() {
        return adopted_delivers;
    }

    public void setAdopted_delivers(String adopted_delivers) {
        this.adopted_delivers = adopted_delivers;
    }

    public String getFinished_orders() {
        return finished_orders;
    }

    public void setFinished_orders(String finished_orders) {
        this.finished_orders = finished_orders;
    }

    public String getShop_calls() {
        return shop_calls;
    }

    public void setShop_calls(String shop_calls) {
        this.shop_calls = shop_calls;
    }

    public String getFinished_delivers() {
        return finished_delivers;
    }

    public void setFinished_delivers(String finished_delivers) {
        this.finished_delivers = finished_delivers;
    }

    public String getShop_first_orders() {
        return shop_first_orders;
    }

    public void setShop_first_orders(String shop_first_orders) {
        this.shop_first_orders = shop_first_orders;
    }

    public String getDeliver_first_orders() {
        return deliver_first_orders;
    }

    public void setDeliver_first_orders(String deliver_first_orders) {
        this.deliver_first_orders = deliver_first_orders;
    }

    public String getBusiness_district_calls() {
        return business_district_calls;
    }

    public void setBusiness_district_calls(String business_district_calls) {
        this.business_district_calls = business_district_calls;
    }

    public String getGroup_calls() {
        return group_calls;
    }

    public void setGroup_calls(String group_calls) {
        this.group_calls = group_calls;
    }

    public String getAdopted_teams() {
        return adopted_teams;
    }

    public void setAdopted_teams(String adopted_teams) {
        this.adopted_teams = adopted_teams;
    }

    public String getDeliver_complaints() {
        return deliver_complaints;
    }

    public void setDeliver_complaints(String deliver_complaints) {
        this.deliver_complaints = deliver_complaints;
    }

    public String getError_orders() {
        return error_orders;
    }

    public void setError_orders(String error_orders) {
        this.error_orders = error_orders;
    }

    public String getMost_distance() {
        return most_distance;
    }

    public void setMost_distance(String most_distance) {
        this.most_distance = most_distance;
    }

    public String getAverage_distance() {
        return average_distance;
    }

    public void setAverage_distance(String average_distance) {
        this.average_distance = average_distance;
    }

    public String getLongest_time() {
        return longest_time;
    }

    public void setLongest_time(String longest_time) {
        this.longest_time = longest_time;
    }

    public String getAverage_time() {
        return average_time;
    }

    public void setAverage_time(String average_time) {
        this.average_time = average_time;
    }

    public String getFirst_call_shops() {
        return first_call_shops;
    }

    public void setFirst_call_shops(String first_call_shops) {
        this.first_call_shops = first_call_shops;
    }

    public String getNo_call_shops() {
        return no_call_shops;
    }

    public void setNo_call_shops(String no_call_shops) {
        this.no_call_shops = no_call_shops;
    }

    public String getFirst_send_deliver() {
        return first_send_deliver;
    }

    public void setFirst_send_deliver(String first_send_deliver) {
        this.first_send_deliver = first_send_deliver;
    }

    public String getNo_send_deliver() {
        return no_send_deliver;
    }

    public void setNo_send_deliver(String no_send_deliver) {
        this.no_send_deliver = no_send_deliver;
    }

    public String getFirst_send_team() {
        return first_send_team;
    }

    public void setFirst_send_team(String first_send_team) {
        this.first_send_team = first_send_team;
    }

    public String getNo_send_team() {
        return no_send_team;
    }

    public void setNo_send_team(String no_send_team) {
        this.no_send_team = no_send_team;
    }

    public String getFirst_call_business_district() {
        return first_call_business_district;
    }

    public void setFirst_call_business_district(String first_call_business_district) {
        this.first_call_business_district = first_call_business_district;
    }

    public String getNo_call_business_district() {
        return no_call_business_district;
    }

    public void setNo_call_business_district(String no_call_business_district) {
        this.no_call_business_district = no_call_business_district;
    }

    public String getFirst_call_business_district_orders() {
        return first_call_business_district_orders;
    }

    public void setFirst_call_business_district_orders(String first_call_business_district_orders) {
        this.first_call_business_district_orders = first_call_business_district_orders;
    }

    public String getHighest_orders() {
        return highest_orders;
    }

    public void setHighest_orders(String highest_orders) {
        this.highest_orders = highest_orders;
    }

    public String getFirst_send_team_orders() {
        return first_send_team_orders;
    }

    public void setFirst_send_team_orders(String first_send_team_orders) {
        this.first_send_team_orders = first_send_team_orders;
    }
}
