package com.casper.guidee;


import android.icu.text.SimpleDateFormat;

import org.json.simple.JSONObject;


import java.util.Date;




public class KakaoPayApprovalVO {

    //response
    private String aid, tid, cid, sid;
    private String partner_order_id, partner_user_id, payment_method_type;
    private AmountVO amount;
    private CardVO card_info;
    private String item_name, item_code, payload;
    private Integer quantity;
    private Date created_at, approved_at;

    public void setKakaoPayApprovalVO(JSONObject jsonObject){
        try {
            this.aid = jsonObject.get("aid").toString();
            this.tid = jsonObject.get("tid").toString();
            this.cid = jsonObject.get("cid").toString();
            this.sid = jsonObject.get("sid").toString();
            this.partner_order_id = jsonObject.get("partner_order_id").toString();
            this.partner_user_id = jsonObject.get("partner_user_id").toString();
            this.payment_method_type = jsonObject.get("payment_method_type").toString();
            this.amount = new AmountVO();
            this.amount.setAmountVO((JSONObject) jsonObject.get("amount"));
            this.card_info = new CardVO();
            this.card_info.setCardVO((JSONObject) jsonObject.get("card_info"));
            this.item_name = jsonObject.get("item_name").toString();
            this.item_code = jsonObject.get("item_code").toString();
            this.payload = jsonObject.get("payload").toString();
            this.quantity = Integer.parseInt(jsonObject.get("quantity").toString());

            SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            this.created_at = fm.parse(jsonObject.get("created_at").toString());
            this.approved_at = fm.parse(jsonObject.get("approved_at").toString());
        }
        catch (java.text.ParseException e) { e.printStackTrace(); }
    }
}

