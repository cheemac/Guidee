package com.casper.guidee;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.extern.java.Log
class Controller {

    private KakaoPay kakaopay;
    private PlanAllInfo planAllInfo;
    public Controller(PlanAllInfo planAllInfo){
        this.kakaopay = new KakaoPay();
        this.planAllInfo = planAllInfo;
    }

    public void readyToKakao(){
        kakaopay.kakaoPayReadyByHTTP(this.planAllInfo);

    }
    public void approveToKakao(){
        kakaopay.kakaoPayInfoByHTTP("pgtoken123asdasd");
    }

    public KakaoPay getKakaopay() {
        return this.kakaopay;
    }


    @JsonProperty("/kakaoPaySuccess")
    public void kakaoPaySuccess(@JsonProperty("pg_token") String pg_token) {
        log.info("kakaoPaySuccess get............................................");
        log.info("kakaoPaySuccess pg_token : " + pg_token);

    }


}