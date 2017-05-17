package com.bombelab.lakaz.octo_voca;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Peng! 2016-07-13
 * Modify by Pomiring
 */

public class Tab2 extends Activity {

    private RelativeLayout Tab2_mScreen;

    /*DB variable*/
    V_DBHelper v_dbHelper = new V_DBHelper(Tab2.this);
    private ArrayList<V_CustomRow> info;
    public static String db_arr[][];

    private ArrayList<S_CustomRow> maindbinfo;
    public static String db_arr_set[][];

    Random rand = new Random();
    int state_count = 0;
    int level_check=0;

    TextView quiz_txt;
    ImageButton level;

    S_SetVal setting = new S_SetVal();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab2_activity);

        Tab2_mScreen = (RelativeLayout)findViewById(R.id.tab2_Back);

        quiz_txt = (TextView)findViewById(R.id.kanJi);
        level = (ImageButton)findViewById(R.id.leveltab2b);

        mainDBread();
        tab2DBread();

        if(db_arr.length>4) {

            addButtonListener();

            level_check=Integer.parseInt(db_arr[state_count][4]);
            quiz_txt.setText(db_arr[state_count][1]);
            level_check_icon(level_check);

        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(Tab2.this);
            dialog.setTitle("No Data");
            dialog.setMessage("VOCA list has less than 4 word.\nPlease add more word to the list.");
            dialog.show();
        }

    }/*onCreate end*/


    public void addButtonListener() {

        final Button[] btn = new Button[4];

        btn[0] = (Button) findViewById(R.id.Button1);
        btn[1] = (Button) findViewById(R.id.Button2);
        btn[2] = (Button) findViewById(R.id.Button3);
        btn[3] = (Button) findViewById(R.id.Button4);

        int[] arr = new int[btn.length];
        int randnum1=0, randnum2=0;

        for (int i = 0; i < arr.length; i++) {
            randnum1 = rand.nextInt(db_arr.length);
            for (int j = 0; j < i; j++) {
                if (arr[j] == randnum1||
                        randnum1==state_count||
                        arr[j]==state_count) {
                    i--;
                }
            }
            arr[i]=randnum1;
        }

        for(int i = 0; i < arr.length; i++){ btn[i].setText(db_arr[arr[i]][0]); }

        if(!btn[0].getText().equals(db_arr[state_count][0]) &&
                !btn[1].getText().equals(db_arr[state_count][0]) &&
                !btn[2].getText().equals(db_arr[state_count][0]) &&
                !btn[3].getText().equals(db_arr[state_count][0])){

            randnum2 = rand.nextInt(btn.length);
            btn[randnum2].setText(db_arr[state_count][0]);
        }

        btn[0].setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Correctquiz((String) btn[0].getText(),db_arr[state_count][0],state_count);
            }
        });

        btn[1].setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Correctquiz((String) btn[1].getText(),db_arr[state_count][0],state_count);
            }
        });

        btn[2].setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Correctquiz((String) btn[2].getText(),db_arr[state_count][0],state_count);
            }
        });


        btn[3].setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Correctquiz((String) btn[3].getText(),db_arr[state_count][0],state_count);
            }
        });

        level.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){

                        level_check++;
                        if(level_check>2) level_check=0;

                        level_check_icon(level_check);

                        db_arr[state_count][4]= String.valueOf(level_check);
                        try{
                            v_dbHelper.Updatelevelcheck(db_arr[state_count][1],
                                    db_arr[state_count][0],
                                    level_check);
                        }catch (Exception e){}
                    }
        });

    }/*addButtonListener end*/


    public void transView() {

        state_count=rand.nextInt(db_arr.length);

        if(5 < db_arr[state_count][1].length()){
            quiz_txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP,40);
            quiz_txt.setText(db_arr[state_count][1]);
        }
        else {
            quiz_txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP,60);
            quiz_txt.setText(db_arr[state_count][1]);
        }

        level_check=Integer.parseInt(db_arr[state_count][4]);
        level_check_icon(level_check);

        addButtonListener();

    }/*tranView end*/

    public boolean Correctquiz(String select, String answer, int s_num){

        boolean correct;

        if(select.equals(answer)){
            correct=true;
            Messagebox(correct, s_num);

        } else {
            correct=false;
            Messagebox(correct ,s_num);
        }
        return correct;
    }/*Correctquiz end*/


    public void Messagebox(boolean ans, int state_number){

        AlertDialog.Builder dialog = new AlertDialog.Builder(Tab2.this);

        if(ans==true){
            dialog.setTitle("Correct! :-D");
        }
        else{
            dialog.setTitle("Incorrect. '_`...");
        }
        dialog.setMessage(" "+db_arr[state_number][1] +"\n "+ db_arr[state_number][2] +"\n "+db_arr[state_number][0]);

        dialog.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.show();
        transView();

    }/*Messagebox*/

    public void level_check_icon(int check){

        if (check > 2) {
            check=0;
        }

        if(check==0){
            level.setBackgroundResource(R.drawable.level1_icon);
        }
        else if(check==1){
            level.setBackgroundResource(R.drawable.level2_icon);
        }
        else if(check==2){
            level.setBackgroundResource(R.drawable.level3_icon);
        }

    }/*levelcheck end*/

    public void tab2DBread(){

        try {
            v_dbHelper.createDataBase();
        } catch (IOException e) {
        }

        v_dbHelper.openDataBase();
        info = v_dbHelper.getInfo_selectlevel(setting.level_1, setting.level_2, setting.level_3);
        int db_size = info.size();

        db_arr = new String[db_size][5];

        for (int i = 0; i < db_size; i++) {
            /*Make a DB Array*/

            V_CustomRow temp = info.get(i);

            db_arr[i][0] = temp.getMean();
            db_arr[i][1] = temp.getWord();
            db_arr[i][2] = temp.getPro();
            db_arr[i][3] = Integer.toString(temp.getV_cal());
            db_arr[i][4] = Integer.toString(temp.getV_re()) ;

        }
    }/*tab2DBread end*/

    public void mainDBread(){

        try {
            v_dbHelper.createDataBase();
        } catch (IOException e) {
            //e.printStackTrace();
        }
        v_dbHelper.openDataBase();

        maindbinfo = v_dbHelper.maingetInfo();
        int db_size = maindbinfo.size();

        db_arr_set = new String[db_size+1][2];

        for (int i = 0; i < db_size; i++) {
            /*Make a DB Array*/

            S_CustomRow temp = maindbinfo.get(i);

            db_arr_set[i][0] = temp.getMean();
            db_arr_set[i][1] = temp.getValues();

        }

        setting.table_name = db_arr_set[0][1];
        setting.voca_speed = Integer.valueOf(db_arr_set[1][1]);
        setting.word_hide = Boolean.valueOf(db_arr_set[2][1]);
        setting.pro_hide = Boolean.valueOf(db_arr_set[3][1]);
        setting.mean_hide = Boolean.valueOf(db_arr_set[4][1]);
        setting.level_1 = Boolean.valueOf(db_arr_set[5][1]);
        setting.level_2 = Boolean.valueOf(db_arr_set[6][1]);
        setting.level_3 = Boolean.valueOf(db_arr_set[7][1]);

    }/*mainDBread end*/

}



