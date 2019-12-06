package com.casper.guidee;


import org.json.simple.JSONObject;
import lombok.Data;

/*결제정보*/
@Data
public class AmountVO {

    private Integer total, tax_free, vat, point, discount;

    public  void setAmountVO(JSONObject jsonObject) {
        this.total = Integer.parseInt(jsonObject.get("total").toString());
        this.tax_free = Integer.parseInt(jsonObject.get("tax_free_amount").toString());
        this.vat = Integer.parseInt(jsonObject.get("vat").toString());
        this.point = Integer.parseInt(jsonObject.get("point").toString());
        this.discount = Integer.parseInt(jsonObject.get("discount").toString());
    }
}
