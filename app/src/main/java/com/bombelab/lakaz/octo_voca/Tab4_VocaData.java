package com.bombelab.lakaz.octo_voca;

/**
 * Created by Pomiring on 2016-06-03.
 */

public class Tab4_VocaData {

    private String Word_Text;
    private String Mean_Text;
    private String Table_Text;
    private int imgId;

    public Tab4_VocaData(String Word_Text,String Mean_Text, int imgId, String Table_Text){
        this.Word_Text = Word_Text;
        this.Mean_Text = Mean_Text;
        this.Table_Text = Table_Text;
        this.imgId = imgId;
    }

    public String getKanji_Text(){
        return  Word_Text;
    }

    public void setKanji_Text(String Word_Text){ this.Word_Text = Word_Text; }

    public String getKor_Text(){
        return Mean_Text;
    }

    public void setKor_Text(String Mean_Text){
        this.Mean_Text = Mean_Text;
    }

    public String getTable_Text(){
        return Table_Text;
    }

    public void setTable_Text(String Table_Text){
        this.Table_Text=Table_Text;
    }

    public int getImgId(){
        return imgId;
    }

    public void setImgId(int imgId){
        this.imgId=imgId;
    }

}
