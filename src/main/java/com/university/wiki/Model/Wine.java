package com.university.wiki.Model;

public class Wine {
    private double fixedAcidity;
    private double volatileAcidity;
    private double citricAcid;
    private double residualSugar;
    private double chlorides;
    private double freeSulfurDioxide;
    private double totalSulfurDioxide;
    private double density;
    private double pH;
    private double sulphates;
    private double alcohol;
    private int quality;


    public Wine(double fixedAcidity, double volatileAcidity, double citricAcid, double residualSugar,
                double chlorides, double freeSulfurDioxide, double totalSulfurDioxide,
                double density, double pH, double sulphates, double alcohol, int quality) {
        this.fixedAcidity = fixedAcidity;
        this.volatileAcidity = volatileAcidity;
        this.citricAcid = citricAcid;
        this.residualSugar = residualSugar;
        this.chlorides = chlorides;
        this.freeSulfurDioxide = freeSulfurDioxide;
        this.totalSulfurDioxide = totalSulfurDioxide;
        this.density = density;
        this.pH = pH;
        this.sulphates = sulphates;
        this.alcohol = alcohol;
        this.quality = quality;
    }


    public double getFixedAcidity() {
        return fixedAcidity;
    }

    public void setFixedAcidity(double fixedAcidity) {
        this.fixedAcidity = fixedAcidity;
    }

    public double getVolatileAcidity() {
        return volatileAcidity;
    }

    public void setVolatileAcidity(double volatileAcidity) {
        this.volatileAcidity = volatileAcidity;
    }

    public double getCitricAcid() {
        return citricAcid;
    }

    public void setCitricAcid(double citricAcid) {
        this.citricAcid = citricAcid;
    }

    public double getResidualSugar() {
        return residualSugar;
    }

    public void setResidualSugar(double residualSugar) {
        this.residualSugar = residualSugar;
    }

    public double getChlorides() {
        return chlorides;
    }

    public void setChlorides(double chlorides) {
        this.chlorides = chlorides;
    }

    public double getFreeSulfurDioxide() {
        return freeSulfurDioxide;
    }

    public void setFreeSulfurDioxide(double freeSulfurDioxide) {
        this.freeSulfurDioxide = freeSulfurDioxide;
    }

    public double getTotalSulfurDioxide() {
        return totalSulfurDioxide;
    }

    public void setTotalSulfurDioxide(double totalSulfurDioxide) {
        this.totalSulfurDioxide = totalSulfurDioxide;
    }

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public double getPH() {
        return pH;
    }

    public void setPH(double pH) {
        this.pH = pH;
    }

    public double getSulphates() {
        return sulphates;
    }

    public void setSulphates(double sulphates) {
        this.sulphates = sulphates;
    }

    public double getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(double alcohol) {
        this.alcohol = alcohol;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }


    @Override
    public String toString() {
        return "Wine{" +
                "fixedAcidity=" + fixedAcidity +
                ", volatileAcidity=" + volatileAcidity +
                ", citricAcid=" + citricAcid +
                ", residualSugar=" + residualSugar +
                ", chlorides=" + chlorides +
                ", freeSulfurDioxide=" + freeSulfurDioxide +
                ", totalSulfurDioxide=" + totalSulfurDioxide +
                ", density=" + density +
                ", pH=" + pH +
                ", sulphates=" + sulphates +
                ", alcohol=" + alcohol +
                ", quality=" + quality +
                '}';
    }
}