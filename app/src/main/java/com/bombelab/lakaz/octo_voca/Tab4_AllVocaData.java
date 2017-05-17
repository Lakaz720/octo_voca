package com.bombelab.lakaz.octo_voca;

/**
 * Created by Pomiring on 2016-07-16.
 */
public class Tab4_AllVocaData {

    private String word_Text;
    private String pro_Text;
    private String mean_Text;
    private String cal_Text;
    private String table_Text;

    public Tab4_AllVocaData (String word_Text,String pro_Text, String mean_Text, String cal_Text, String table_Text){

        this.word_Text = word_Text;
        this.pro_Text = pro_Text;
        this.mean_Text = mean_Text;
        this.cal_Text = cal_Text;
        this.table_Text = table_Text;

    }

    public String getWord_Text(){
        return  word_Text;
    }
    public void setWord_Text(String word_Text){
        this.word_Text = word_Text;
    }

    public String getPro_Text(){return  pro_Text;}
    public void  setPro_Text(String pro_Text){ this.pro_Text = pro_Text;}

    public String getMean_Text(){return  mean_Text;}
    public void  setMean_Text(String mean_Text){ this.mean_Text = mean_Text;}

    public String getCal_Text(){return  cal_Text;}
    public void  setCal_Text(String cal_Text){ this.cal_Text = cal_Text;}

    public String getTable_Text(){return  table_Text;}
    public void  setTable_Text(String table_Text){ this.table_Text = table_Text;}


}
