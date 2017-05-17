package com.bombelab.lakaz.octo_voca;

/**
 * Created by Pomiring on 2016-06-14.
 */
public class V_CustomRow {
    private String Mean;
    private String Word;
    private String Pro;
    private int v_cal;
    private int v_re;

    public V_CustomRow(String Mean, String Word, String Pro, int v_cal, int v_re){
        this.Mean = Mean;
        this.Word = Word;
        this.Pro = Pro;
        this.v_cal = v_cal;
        this.v_re = v_re;
    }

    public String getMean(){ return Mean;}
    public String getWord(){ return Word;}
    public String getPro(){ return Pro;}
    public int getV_cal(){ return v_cal;}
    public int getV_re(){ return v_re;}
}
