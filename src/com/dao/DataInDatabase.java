/**
 * 数据库对象类
 */
package com.dao;

public class DataInDatabase {
    String name,tongueColor,coatColor,Large,Moist,Fat,Tooth,Creak,Bruise,Recipe,Health,Cartoon,
            RGB_R,RGB_G,RGB_B,HSV_H,HSV_S,HSV_V,LAB_L,LAB_A,LAB_B;

    public DataInDatabase() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTongueColor() {
        return tongueColor;
    }

    public void setTongueColor(String tongueColor) {
        this.tongueColor = tongueColor;
    }

    public String getCoatColor() {
        return coatColor;
    }

    public void setCoatColor(String coatColor) {
        this.coatColor = coatColor;
    }

    public String getLarge() {
        return Large;
    }

    public void setLarge(String large) {
        Large = large;
    }

    public String getMoist() {
        return Moist;
    }

    public void setMoist(String moist) {
        Moist = moist;
    }

    public String getFat() {
        return Fat;
    }

    public void setFat(String fat) {
        Fat = fat;
    }

    public String getTooth() {
        return Tooth;
    }

    public void setTooth(String tooth) {
        Tooth = tooth;
    }

    public String getCreak() {
        return Creak;
    }

    public void setCreak(String creak) {
        Creak = creak;
    }

    public String getBruise() {
        return Bruise;
    }

    public void setBruise(String bruise) {
        Bruise = bruise;
    }

    public String getRecipe() {
        return Recipe;
    }

    public void setRecipe(String recipe) {
        Recipe = recipe;
    }

    public String getHealth() {
        return Health;
    }

    public void setHealth(String health) {
        Health = health;
    }

    public String getCartoon() {
        return Cartoon;
    }

    public void setCartoon(String cartoon) {
        Cartoon = cartoon;
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
        if (!(object instanceof DataInDatabase)) return false;

        DataInDatabase that = (DataInDatabase) object;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (tongueColor != null ? !tongueColor.equals(that.tongueColor) : that.tongueColor != null) return false;
        if (coatColor != null ? !coatColor.equals(that.coatColor) : that.coatColor != null) return false;
        if (Large != null ? !Large.equals(that.Large) : that.Large != null) return false;
        if (Moist != null ? !Moist.equals(that.Moist) : that.Moist != null) return false;
        if (Fat != null ? !Fat.equals(that.Fat) : that.Fat != null) return false;
        if (Tooth != null ? !Tooth.equals(that.Tooth) : that.Tooth != null) return false;
        if (Creak != null ? !Creak.equals(that.Creak) : that.Creak != null) return false;
        if (Bruise != null ? !Bruise.equals(that.Bruise) : that.Bruise != null) return false;
        if (Recipe != null ? !Recipe.equals(that.Recipe) : that.Recipe != null) return false;
        if (Health != null ? !Health.equals(that.Health) : that.Health != null) return false;
        if (Cartoon != null ? !Cartoon.equals(that.Cartoon) : that.Cartoon != null) return false;
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
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (tongueColor != null ? tongueColor.hashCode() : 0);
        result = 31 * result + (coatColor != null ? coatColor.hashCode() : 0);
        result = 31 * result + (Large != null ? Large.hashCode() : 0);
        result = 31 * result + (Moist != null ? Moist.hashCode() : 0);
        result = 31 * result + (Fat != null ? Fat.hashCode() : 0);
        result = 31 * result + (Tooth != null ? Tooth.hashCode() : 0);
        result = 31 * result + (Creak != null ? Creak.hashCode() : 0);
        result = 31 * result + (Bruise != null ? Bruise.hashCode() : 0);
        result = 31 * result + (Recipe != null ? Recipe.hashCode() : 0);
        result = 31 * result + (Health != null ? Health.hashCode() : 0);
        result = 31 * result + (Cartoon != null ? Cartoon.hashCode() : 0);
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
        return "DataInDatabase{" +
                "name='" + name + '\'' +
                ", tongueColor='" + tongueColor + '\'' +
                ", coatColor='" + coatColor + '\'' +
                ", Large='" + Large + '\'' +
                ", Moist='" + Moist + '\'' +
                ", Fat='" + Fat + '\'' +
                ", Tooth='" + Tooth + '\'' +
                ", Creak='" + Creak + '\'' +
                ", Bruise='" + Bruise + '\'' +
                ", Recipe='" + Recipe + '\'' +
                ", Health='" + Health + '\'' +
                ", Cartoon='" + Cartoon + '\'' +
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
