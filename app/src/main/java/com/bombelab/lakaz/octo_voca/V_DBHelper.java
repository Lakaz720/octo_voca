package com.bombelab.lakaz.octo_voca;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Pomiring on 2016-06-14.
 */

public class V_DBHelper extends SQLiteOpenHelper{

    @SuppressLint("SdCardPath")
    private static String DB_PATH;
    private static String DB_NAME = "Voca_DB.db";
    private SQLiteDatabase v_DataBase;
    private final Context v_Context;

    /*Query*/
    public String lev1="";
    public String lev2="";
    public String lev3="";
    public int check=0;


    /*Setting*/
    S_SetVal t = new S_SetVal();

    public V_DBHelper(Context context){
        super(context, DB_NAME, null, 1);
        this.v_Context = context;
    }

    public void createDataBase() throws IOException{
        SQLiteDatabase checkDB = null;

        try{
            DB_PATH = v_Context.getFilesDir().getAbsolutePath().replace("files","");
            String v_Path = DB_PATH+DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(v_Path, null, SQLiteDatabase.OPEN_READWRITE);
        }catch (SQLException e){
        }

        if(checkDB != null){
            checkDB.close();
        }
        else {

            this.getWritableDatabase();

            try{

                InputStream V_input = v_Context.getAssets().open(DB_NAME);
                String outFileName = DB_PATH+DB_NAME;
                OutputStream V_output = new FileOutputStream(outFileName);
                byte[] buffer = new byte[1024];

                int length;
                while ((length=V_input.read(buffer))>0){
                    V_output.write(buffer,0,length);
                }
                V_output.flush();
                V_output.close();
                V_input.close();

            }catch (IOException e){

            }
        }

    } /*end of createDataBase*/


    public void tableCreate(String table_name){

        String q_create = "CREATE TABLE `" + table_name +
                "` (`Mean` TEXT, `Voca` TEXT, " +
                "`Pro` TEXT, `Class_V` INTEGER, " +
                "`Check_lev` INTEGER); ";

        v_DataBase.execSQL(q_create);

    }/*tableCreate end*/


    @Override
    public void onCreate(SQLiteDatabase db){

    }


    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion) {

    }


    public void openDataBase(){
        String v_PATH = DB_PATH+DB_NAME;
        v_DataBase = SQLiteDatabase.openDatabase(v_PATH,null,
                SQLiteDatabase.NO_LOCALIZED_COLLATORS);
    }/*openDataBase*/


    public void close(){
        if(v_DataBase != null){
            v_DataBase.close();
        }
        super.close();
    }/*closeDataBase*/

    public ArrayList<V_CustomRow> getInfo_selectlevel(boolean lev1, boolean lev2, boolean lev3){

        ArrayList<V_CustomRow> dbinfo = new ArrayList<V_CustomRow>();

        String q_table_select = "SELECT * FROM " + t.table_name;

        if(lev1 == true){
            q_table_select = q_table_select+ " WHERE Check_lev = 0";
            check = 1;

        }
        if(lev2 == true){
            if(check == 0) {q_table_select = q_table_select+ " WHERE";}
            else {q_table_select = q_table_select+ " OR";}
            q_table_select = q_table_select+ " Check_lev = 1";
            check = 2;

        }
        if(lev3==true){
            if(check == 0) {q_table_select = q_table_select+ " WHERE";}
            else {q_table_select = q_table_select+ " OR";}
            q_table_select = q_table_select+ " Check_lev = 2";
            check = 0;

        }

        Cursor c = v_DataBase.rawQuery(q_table_select, null);

        while(c.moveToNext()){
            String kor = c.getString(0);
            String kanji = c.getString(1);
            String hiragana = c.getString(2);
            int v_cla = c.getInt(3);
            int v_re = c.getInt(4);

            V_CustomRow temp = new V_CustomRow(kor, kanji, hiragana, v_cla, v_re);
            dbinfo.add(temp);
        }

        return dbinfo;
    }/*getInfo_selectlevel*/

    public ArrayList<V_CustomRow> getInfo(){

        ArrayList<V_CustomRow> dbinfo = new ArrayList<V_CustomRow>();

        String q_table_select = "SELECT * FROM " + t.table_name ;
        Cursor c = v_DataBase.rawQuery(q_table_select, null);

        while(c.moveToNext()){
            String kor = c.getString(0);
            String kanji = c.getString(1);
            String hiragana = c.getString(2);
            int v_cla = c.getInt(3);
            int v_re = c.getInt(4);

            V_CustomRow temp = new V_CustomRow(kor, kanji, hiragana, v_cla, v_re);
            dbinfo.add(temp);
        }

        return dbinfo;
    }/*getInfo*/


    public ArrayList<S_CustomRow> maingetInfo(){

        ArrayList<S_CustomRow> maindbinfo = new ArrayList<S_CustomRow>();

        String q_table_select =  "SELECT * FROM VOCA_SET";
        Cursor c = v_DataBase.rawQuery(q_table_select, null);

        while(c.moveToNext()){
            String mean = c.getString(0);
            String values = c.getString(1);

            S_CustomRow temp = new S_CustomRow(mean, values);
            maindbinfo.add(temp);
        }

        return maindbinfo;

    }/*maingetInfo*/



    public ArrayList<S_CustomRow> listgetInfo(){

        ArrayList<S_CustomRow> listdbinfo = new ArrayList<S_CustomRow>();

        String q_table_select =  "SELECT * FROM VOCA_LIST";
        Cursor c = v_DataBase.rawQuery(q_table_select, null);

        while(c.moveToNext()){
            String mean = c.getString(0);
            String values = c.getString(1);

            S_CustomRow temp = new S_CustomRow(mean,values);
            listdbinfo.add(temp);
        }

        return listdbinfo;

    }/*listgetInfo end*/

    public void newDB(String word, String pro, String mean, int v_cla, String list_name){

        String n_word = "'" + word + "',";
        String n_pro = "'" + pro + "',";
        String n_mean = "'" + mean +"',";

        String newq = "INSERT INTO " + list_name+" VALUES ";
        newq = newq + "( " + n_mean + n_word + n_pro + String.valueOf(v_cla) +", " + "0);";

        v_DataBase.execSQL(newq);

    }/*newDB end*/


    public void Updatelevelcheck(String word, String mean, int level){

        String update = "UPDATE "+ t.table_name +
                " SET Check_lev=" + level +
                " WHERE Voca='" + word +
                "' AND Mean='" + mean + "';";

        v_DataBase.execSQL(update);

    }/*Updatelevelcheck end*/


    public void Updatetablename(String table_name){
        String Update_table = "UPDATE VOCA_SET"+
                " SET VALUE='" + table_name+
                "' WHERE MEAN='SEL_TABLE';";
        v_DataBase.execSQL(Update_table);
    }/*Updatetablename end*/


    public void Updatetablespeed(String speed){
        String Update_speed = "UPDATE VOCA_SET"+
                " SET VALUE='" + speed+
                "' WHERE MEAN='VOCA_SPEED';";
        v_DataBase.execSQL(Update_speed);

    }/*Updatetablespeed end*/


    public void Updatelevelcheck_setting(boolean lev1, boolean lev2, boolean lev3){

        String Update_table = "UPDATE VOCA_SET"+
                " SET VALUE='" + lev1 +
                "' WHERE MEAN='LEVEL_1';";

        v_DataBase.execSQL(Update_table);

        Update_table = "UPDATE VOCA_SET"+
                " SET VALUE='" + lev2 +
                "' WHERE MEAN='LEVEL_2';";

        v_DataBase.execSQL(Update_table);

        Update_table = "UPDATE VOCA_SET"+
                " SET VALUE='" + lev3 +
                "' WHERE MEAN='LEVEL_3';";

        v_DataBase.execSQL(Update_table);

    }/*Updatetablename end*/

    public void DeleteVocabulary(String voca, String mean, String table){

        String Delete_voca = "DELETE FROM "+ table
                +" WHERE Voca = '" + voca
                +"' AND Mean ='"+mean+"';";
        v_DataBase.execSQL(Delete_voca);

    }

    public void CreateVocabulary_T(String voca){

        String Create_voca = "CREATE TABLE `"+ voca +
                "` (`Mean` TEXT, " +
                "`Voca` TEXT, `Pro` TEXT, " +
                "`Class_V` INTEGER, " +
                "`Check_lev` INTEGER);";
        String update_list_voca = "INSERT INTO VOCA_LIST VALUES ('"+ voca +"',0);";

        v_DataBase.execSQL(Create_voca);
        v_DataBase.execSQL(update_list_voca);

    }

    public void DeleteVocabulary_T(String voca){

        String update_list_voca = "DELETE FROM VOCA_LIST WHERE NAME = '" + voca+"';";
        String Delete_voca = "DROP TABLE "+ voca + ";";

        v_DataBase.execSQL(Delete_voca);
        v_DataBase.execSQL(update_list_voca);

    }

}

