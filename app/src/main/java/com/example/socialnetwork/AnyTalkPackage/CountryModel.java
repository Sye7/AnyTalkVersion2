package com.example.socialnetwork.AnyTalkPackage;


public class CountryModel {
    String name;
    int flag;
    String nameSmall;
    String lanng;

    public CountryModel(String name, int flag, String nameSmall, String lanng) {
        this.name = name;
        this.flag = flag;
        this.nameSmall = nameSmall;
        this.lanng = lanng;
    }

    public CountryModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getNameSmall() {
        return nameSmall;
    }

    public void setNameSmall(String nameSmall) {
        this.nameSmall = nameSmall;
    }

    public String getLanng() {
        return lanng;
    }

    public void setLanng(String lanng) {
        this.lanng = lanng;
    }
}
