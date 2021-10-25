package com.sample.velocitydemo;

import lombok.Data;

@Data
public class Annuity {

    private String yearMonth;
    private Double variablePremium; // variabelePremie
    private Double fixedPremium; // vastePremie
    private Double insuredAmount; // verzekerdBedrag

}
