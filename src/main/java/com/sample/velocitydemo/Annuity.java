package com.sample.velocitydemo;

public class Annuity {

    private String yearMonth;
    private double variablePremium; // variabelePremie
    private double fixedPremium; // vastePremie
    private double insuredAmount; // verzekerdBedrag

    public Annuity(String yearMonth, double variablePremium, double fixedPremium, double insuredAmount) {
        this.yearMonth = yearMonth;
        this.variablePremium = variablePremium;
        this.fixedPremium = fixedPremium;
        this.insuredAmount = insuredAmount;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public double getVariablePremium() {
        return variablePremium;
    }

    public void setVariablePremium(double variablePremium) {
        this.variablePremium = variablePremium;
    }

    public double getFixedPremium() {
        return fixedPremium;
    }

    public void setFixedPremium(double fixedPremium) {
        this.fixedPremium = fixedPremium;
    }

    public double getInsuredAmount() {
        return insuredAmount;
    }

    public void setInsuredAmount(double insuredAmount) {
        this.insuredAmount = insuredAmount;
    }
}
