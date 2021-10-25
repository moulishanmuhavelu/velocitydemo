package com.sample.velocitydemo;

import lombok.Data;

@Data
public class AnnuityMasterData {

    private String jaarMaand; // yearMonth
    private double variabelePremie; // variabele Premium
    private double vastePremie; // Fixed premium
    private double verzekerdBedrag;

}
