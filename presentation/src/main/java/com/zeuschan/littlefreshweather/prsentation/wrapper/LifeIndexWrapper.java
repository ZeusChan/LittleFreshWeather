package com.zeuschan.littlefreshweather.prsentation.wrapper;

/**
 * Created by chenxiong on 2016/6/14.
 */
public class LifeIndexWrapper {
    private String lifeIndexName;
    private String lifeIndexBrief;
    private String lifeIndexDesc;

    public LifeIndexWrapper(String lifeIndexName, String lifeIndexBrief, String lifeIndexDesc) {
        this.lifeIndexBrief = lifeIndexBrief;
        this.lifeIndexDesc = lifeIndexDesc;
        this.lifeIndexName = lifeIndexName;
    }

    public String getLifeIndexBrief() {
        return lifeIndexBrief;
    }

    public void setLifeIndexBrief(String lifeIndexBrief) {
        this.lifeIndexBrief = lifeIndexBrief;
    }

    public String getLifeIndexDesc() {
        return lifeIndexDesc;
    }

    public void setLifeIndexDesc(String lifeIndexDesc) {
        this.lifeIndexDesc = lifeIndexDesc;
    }

    public String getLifeIndexName() {
        return lifeIndexName;
    }

    public void setLifeIndexName(String lifeIndexName) {
        this.lifeIndexName = lifeIndexName;
    }

    @Override
    public String toString() {
        return "LifeIndexWrapper{" +
                "lifeIndexBrief='" + lifeIndexBrief + '\'' +
                ", lifeIndexName='" + lifeIndexName + '\'' +
                ", lifeIndexDesc='" + lifeIndexDesc + '\'' +
                '}';
    }
}
