package com.casper.guidee;
import org.json.simple.JSONObject;

import lombok.Data;
/*카드 결제 정보*/
@Data
public class CardVO {

    private String purchase_corp, purchase_corp_code;
    private String issuer_corp, issuer_corp_code;
    private String bin, card_type, install_month, approved_id, card_mid;
    private String interest_free_install, card_item_code;

    public  void setCardVO(JSONObject jsonObject) {
        this.purchase_corp = jsonObject.get("purchase_corp").toString();
        this.purchase_corp_code = jsonObject.get("purchase_corp_code").toString();
        this.issuer_corp = jsonObject.get("issuer_corp").toString();
        this.issuer_corp_code = jsonObject.get("issuer_corp_code").toString();
        this.bin = jsonObject.get("bin").toString();
        this.card_type = jsonObject.get("card_type").toString();
        this.install_month = jsonObject.get("install_month").toString();
        this.approved_id = jsonObject.get("approved_id").toString();
        this.card_mid = jsonObject.get("card_mid").toString();
        this.interest_free_install = jsonObject.get("interest_free_install").toString();
        this.card_item_code = jsonObject.get("card_item_code").toString();
    }
}