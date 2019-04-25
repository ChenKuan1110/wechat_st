/**
 * 权重参数对象类
 */
package com.dao;

public class ParaData {
    double paraTongueColor,paraCoatColor,paraLarge,paraMoist,paraFat,paraTooth,paraCreake,paraBruise;

    public ParaData() {
    }

    public double getParaTongueColor() {
        return paraTongueColor;
    }

    public void setParaTongueColor(double paraTongueColor) {
        this.paraTongueColor = paraTongueColor;
    }

    public double getParaCoatColor() {
        return paraCoatColor;
    }

    public void setParaCoatColor(double paraCoatColor) {
        this.paraCoatColor = paraCoatColor;
    }

    public double getParaLarge() {
        return paraLarge;
    }

    public void setParaLarge(double paraLarge) {
        this.paraLarge = paraLarge;
    }

    public double getParaMoist() {
        return paraMoist;
    }

    public void setParaMoist(double paraMoist) {
        this.paraMoist = paraMoist;
    }

    public double getParaFat() {
        return paraFat;
    }

    public void setParaFat(double paraFat) {
        this.paraFat = paraFat;
    }

    public double getParaTooth() {
        return paraTooth;
    }

    public void setParaTooth(double paraTooth) {
        this.paraTooth = paraTooth;
    }

    public double getParaCreake() {
        return paraCreake;
    }

    public void setParaCreake(double paraCreake) {
        this.paraCreake = paraCreake;
    }

    public double getParaBruise() {
        return paraBruise;
    }

    public void setParaBruise(double paraBruise) {
        this.paraBruise = paraBruise;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof ParaData)) return false;

        ParaData paraData = (ParaData) object;

        if (Double.compare(paraData.paraTongueColor, paraTongueColor) != 0) return false;
        if (Double.compare(paraData.paraCoatColor, paraCoatColor) != 0) return false;
        if (Double.compare(paraData.paraLarge, paraLarge) != 0) return false;
        if (Double.compare(paraData.paraMoist, paraMoist) != 0) return false;
        if (Double.compare(paraData.paraFat, paraFat) != 0) return false;
        if (Double.compare(paraData.paraTooth, paraTooth) != 0) return false;
        if (Double.compare(paraData.paraCreake, paraCreake) != 0) return false;
        return Double.compare(paraData.paraBruise, paraBruise) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(paraTongueColor);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(paraCoatColor);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(paraLarge);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(paraMoist);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(paraFat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(paraTooth);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(paraCreake);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(paraBruise);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "ParaData{" +
                "paraTongueColor=" + paraTongueColor +
                ", paraCoatColor=" + paraCoatColor +
                ", paraLarge=" + paraLarge +
                ", paraMoist=" + paraMoist +
                ", paraFat=" + paraFat +
                ", paraTooth=" + paraTooth +
                ", paraCreake=" + paraCreake +
                ", paraBruise=" + paraBruise +
                '}';
    }
}
