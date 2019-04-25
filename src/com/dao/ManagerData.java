/**
 * 用户对象类
 */
package com.dao;

public class ManagerData{
    String ManagerName,tongue,coat,large,moist,fat,tooth,creak,bruise,
            RGB_R,RGB_G,RGB_B,HSV_H,HSV_S,HSV_V,LAB_L,LAB_A,LAB_B;

    public ManagerData() {
    }

    public String getManagerName() {
        return ManagerName;
    }

    public void setManagerName(String managerName) {
        ManagerName = managerName;
    }

    public String getTongue() {
        return tongue;
    }

    public void setTongue(String tongue) {
        this.tongue = tongue;
    }

    public String getCoat() {
        return coat;
    }

    public void setCoat(String coat) {
        this.coat = coat;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMoist() {
        return moist;
    }

    public void setMoist(String moist) {
        this.moist = moist;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getTooth() {
        return tooth;
    }

    public void setTooth(String tooth) {
        this.tooth = tooth;
    }

    public String getCreak() {
        return creak;
    }

    public void setCreak(String creak) {
        this.creak = creak;
    }

    public String getBruise() {
        return bruise;
    }

    public void setBruise(String bruise) {
        this.bruise = bruise;
    }

    public String getRGB_R() {
        return RGB_R;
    }

    public void setRGB_R(String RGB_R) {
        this.RGB_R = RGB_R;
    }

    public String getRGB_G() {
        return RGB_G;
    }

    public void setRGB_G(String RGB_G) {
        this.RGB_G = RGB_G;
    }

    public String getRGB_B() {
        return RGB_B;
    }

    public void setRGB_B(String RGB_B) {
        this.RGB_B = RGB_B;
    }

    public String getHSV_H() {
        return HSV_H;
    }

    public void setHSV_H(String HSV_H) {
        this.HSV_H = HSV_H;
    }

    public String getHSV_S() {
        return HSV_S;
    }

    public void setHSV_S(String HSV_S) {
        this.HSV_S = HSV_S;
    }

    public String getHSV_V() {
        return HSV_V;
    }

    public void setHSV_V(String HSV_V) {
        this.HSV_V = HSV_V;
    }

    public String getLAB_L() {
        return LAB_L;
    }

    public void setLAB_L(String LAB_L) {
        this.LAB_L = LAB_L;
    }

    public String getLAB_A() {
        return LAB_A;
    }

    public void setLAB_A(String LAB_A) {
        this.LAB_A = LAB_A;
    }

    public String getLAB_B() {
        return LAB_B;
    }

    public void setLAB_B(String LAB_B) {
        this.LAB_B = LAB_B;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof ManagerData)) return false;

        ManagerData that = (ManagerData) object;

        if (ManagerName != null ? !ManagerName.equals(that.ManagerName) : that.ManagerName != null) return false;
        if (tongue != null ? !tongue.equals(that.tongue) : that.tongue != null) return false;
        if (coat != null ? !coat.equals(that.coat) : that.coat != null) return false;
        if (large != null ? !large.equals(that.large) : that.large != null) return false;
        if (moist != null ? !moist.equals(that.moist) : that.moist != null) return false;
        if (fat != null ? !fat.equals(that.fat) : that.fat != null) return false;
        if (tooth != null ? !tooth.equals(that.tooth) : that.tooth != null) return false;
        if (creak != null ? !creak.equals(that.creak) : that.creak != null) return false;
        if (bruise != null ? !bruise.equals(that.bruise) : that.bruise != null) return false;
        if (RGB_R != null ? !RGB_R.equals(that.RGB_R) : that.RGB_R != null) return false;
        if (RGB_G != null ? !RGB_G.equals(that.RGB_G) : that.RGB_G != null) return false;
        if (RGB_B != null ? !RGB_B.equals(that.RGB_B) : that.RGB_B != null) return false;
        if (HSV_H != null ? !HSV_H.equals(that.HSV_H) : that.HSV_H != null) return false;
        if (HSV_S != null ? !HSV_S.equals(that.HSV_S) : that.HSV_S != null) return false;
        if (HSV_V != null ? !HSV_V.equals(that.HSV_V) : that.HSV_V != null) return false;
        if (LAB_L != null ? !LAB_L.equals(that.LAB_L) : that.LAB_L != null) return false;
        if (LAB_A != null ? !LAB_A.equals(that.LAB_A) : that.LAB_A != null) return false;
        return LAB_B != null ? LAB_B.equals(that.LAB_B) : that.LAB_B == null;
    }

    @Override
    public int hashCode() {
        int result = ManagerName != null ? ManagerName.hashCode() : 0;
        result = 31 * result + (tongue != null ? tongue.hashCode() : 0);
        result = 31 * result + (coat != null ? coat.hashCode() : 0);
        result = 31 * result + (large != null ? large.hashCode() : 0);
        result = 31 * result + (moist != null ? moist.hashCode() : 0);
        result = 31 * result + (fat != null ? fat.hashCode() : 0);
        result = 31 * result + (tooth != null ? tooth.hashCode() : 0);
        result = 31 * result + (creak != null ? creak.hashCode() : 0);
        result = 31 * result + (bruise != null ? bruise.hashCode() : 0);
        result = 31 * result + (RGB_R != null ? RGB_R.hashCode() : 0);
        result = 31 * result + (RGB_G != null ? RGB_G.hashCode() : 0);
        result = 31 * result + (RGB_B != null ? RGB_B.hashCode() : 0);
        result = 31 * result + (HSV_H != null ? HSV_H.hashCode() : 0);
        result = 31 * result + (HSV_S != null ? HSV_S.hashCode() : 0);
        result = 31 * result + (HSV_V != null ? HSV_V.hashCode() : 0);
        result = 31 * result + (LAB_L != null ? LAB_L.hashCode() : 0);
        result = 31 * result + (LAB_A != null ? LAB_A.hashCode() : 0);
        result = 31 * result + (LAB_B != null ? LAB_B.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ManagerData{" +
                "ManagerName='" + ManagerName + '\'' +
                ", tongue='" + tongue + '\'' +
                ", coat='" + coat + '\'' +
                ", large='" + large + '\'' +
                ", moist='" + moist + '\'' +
                ", fat='" + fat + '\'' +
                ", tooth='" + tooth + '\'' +
                ", creak='" + creak + '\'' +
                ", bruise='" + bruise + '\'' +
                ", RGB_R='" + RGB_R + '\'' +
                ", RGB_G='" + RGB_G + '\'' +
                ", RGB_B='" + RGB_B + '\'' +
                ", HSV_H='" + HSV_H + '\'' +
                ", HSV_S='" + HSV_S + '\'' +
                ", HSV_V='" + HSV_V + '\'' +
                ", LAB_L='" + LAB_L + '\'' +
                ", LAB_A='" + LAB_A + '\'' +
                ", LAB_B='" + LAB_B + '\'' +
                '}';
    }
}
