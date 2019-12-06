package com.casper.guidee;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.extern.java.Log
class Controller {

    private KakaoPay kakaopay;

    public Controller(){
        this.kakaopay = new KakaoPay();
    }

    public void readyToKakao(){
        kakaopay.kakaoPayReadyByHTTP( );

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