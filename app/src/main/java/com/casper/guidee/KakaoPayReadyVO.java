package com.casper.guidee;

import android.icu.text.SimpleDateFormat;

import org.json.simple.JSONObject;

import java.util.Date;

public class KakaoPayReadyVO {

    //response
    private String tid;
    private String next_redirect_app_url,next_redirect_mobile_url,next_redirect_pc_url;
    private Date created_at;

    public  void setKakaoPayReadyVO(JSONObject jsonObject) {
        try {
            this.tid = jsonObject.get("tid").toString();
            this.next_redirect_app_url = jsonObject.get("next_redirect_app_url").toString();
            this.next_redirect_mobile_url = jsonObject.get("next_redirect_mobile_url").toString();
            this.next_redirect_pc_url = jsonObject.get("next_redirect_pc_url").toString();
            SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            this.created_at = fm.parse(jsonObject.get("created_at").toString());
        }
        catch (java.text.ParseException e) { e.printStackTrace(); }
    }

    public String getTid() { return this.tid; }
    public String getNext_redirect_app_url() { return this.next_redirect_app_url; }
    public String getNext_redirect_mobile_url() { return this.next_redirect_mobile_url; }
    public String getNext_redirect_pc_url() { return this.next_redirect_pc_url; }
    public Date getCreated_at(){ return this.created_at;}


}