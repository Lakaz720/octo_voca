package com.bombelab.lakaz.octo_voca;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.ContextMenu;
import android.view.MenuItem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Pomiring on 2016-06-01.
 */

public class Tab1 extends Activity {

    private RelativeLayout Tab1_mScreen;

    TextView kanji, kor, hira;
    ImageButton setb, bfo_b, plb, sob, ranb, nex_b, lev_b;
    private Handler mHandler = new Handler();

    /*DB variable*/

    V_DBHelper v_dbHelper = new V_DBHelper(Tab1.this);

    private ArrayList<V_CustomRow> dbinfo;
    public static String db_arr[][];

    private ArrayList<S_CustomRow> maindbinfo;
    public static String db_arr_set[][];


    /*Setting button variable*/

    S_SetVal setting = new S_SetVal();


    /*Autoplay button variable*/

    boolean plb_state = false;
    int plb_state_count = 0;
    int plb_ran_count = 0;


    /*Random button variable*/

    boolean ranb_state = false;


     /*level check variable*/

    int level_check=0; /*1~3*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1_activity);

        Tab1_mScreen = (RelativeLayout)findViewById(R.id.tab1_Back);
        //Tab1_mScreen.setBackgroundColor(Color.parseColor("#a3cca3"));

        kanji = (TextView)findViewById(R.id.kanJi);
        kor = (TextView)findViewById(R.id.koR);
        hira = (TextView)findViewById(R.id.hiRa);

        setb = (ImageButton)findViewById(R.id.settingButton);
        bfo_b = (ImageButton)findViewById(R.id.beforButton);
        plb = (ImageButton)findViewById(R.id.playButton);
        sob = (ImageButton)findViewById(R.id.stopButton);
        ranb = (ImageButton)findViewById(R.id.randomButtoon);
        nex_b = (ImageButton)findViewById(R.id.nextButton);
        lev_b=(ImageButton)findViewById(R.id.levelcheck);

        registerForContextMenu(setb);
        mainDBread();
        tab1DBread();
        addButtonListener_tab1();


    }/*onCreate end*/


    public void addButtonListener_tab1(){

        setb.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                /*
                * Setting Button Listener
                * Pop-up Setting menu
                * */

                v.showContextMenu();

            }
        });

        bfo_b.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                /*Before Button Listener*/
                try{
                    if(plb_state==false){

                        plb_state_count--;

                        if(plb_state_count<0){
                            plb_state_count=db_arr.length-1;
                        }

                        printingText(plb_state_count);
                    }
                }catch (ArrayIndexOutOfBoundsException e){

                }

            }
        });

        plb.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                /*
                * AutoPlay Button Listener
                * Like a Toggle button
                * */

                try{

                    if(plb.isSelected()==false){
                    /*play*/
                        plb_state=true;
                        mHandler.removeCallbacks(TimeTask);
                        mHandler.postDelayed(TimeTask, setting.voca_speed);
                        plb.setSelected(true);
                        setb.setClickable(false);
                        sob.setClickable(false);
                        bfo_b.setClickable(false);
                        ranb.setClickable(false);
                        nex_b.setClickable(false);
                        lev_b.setClickable(false);
                    }
                    else {
                    /*stop*/
                        plb_state=false;
                        mHandler.removeCallbacks(TimeTask);
                        plb.setSelected(false);
                        setb.setClickable(true);
                        sob.setClickable(true);
                        bfo_b.setClickable(true);
                        ranb.setClickable(true);
                        nex_b.setClickable(true);
                        lev_b.setClickable(true);

                    }
                }catch (ArrayIndexOutOfBoundsException e){

                }

            }
        });

        sob.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                /*Stop Button Listener*/

                try{
                    mHandler.removeCallbacks(TimeTask);
                    plb_state_count=0;
                    printingText(plb_state_count);
                }catch (ArrayIndexOutOfBoundsException e){

                }


            }
        });

        ranb.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                /*
                * AutoRandom Button Listener
                * Like a Toggle button
                * */

                try{
                    if(ranb.isSelected()==false){
                    /*random*/
                        ranb_state=true;
                        ranb.setSelected(true);
                    }
                    else {
                    /*no random*/
                        ranb_state=false;
                        ranb.setSelected(false);
                    }
                }catch (ArrayIndexOutOfBoundsException e){

                }


            }
        });

        nex_b.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                /*Next Button Listener*/

                try{
                    if(plb_state==false){

                        plb_state_count++;

                        if(db_arr.length<=plb_state_count){
                            plb_state_count=0;
                        }

                        printingText(plb_state_count);
                    }
                }catch (ArrayIndexOutOfBoundsException e){

                }


            }
        });

        lev_b.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                /*Level Button Listener*/

                try{
                    level_check++;
                    if(level_check>2) level_check=0;

                    level_check_icon(level_check);

                    db_arr[plb_state_count][4]= String.valueOf(level_check);
                    try{
                        v_dbHelper.Updatelevelcheck(db_arr[plb_state_count][1],
                                db_arr[plb_state_count][0],
                                level_check);
                    }catch (Exception e){}
                }catch (ArrayIndexOutOfBoundsException e){

                }



            }
        });

    }/*addButtonListener end*/

    public void level_check_icon(int check){

        if (check > 2) {
            check=0;
        }

        if(check==0){
            lev_b.setBackgroundResource(R.drawable.level1_icon);
        }
        else if(check==1){
            lev_b.setBackgroundResource(R.drawable.level2_icon);
        }
        else if(check==2){
            lev_b.setBackgroundResource(R.drawable.level3_icon);
        }

    }/*levelcheck end*/


    public void printingText(int state_count){

        if(setting.mean_hide==true){
            kor.setText(db_arr[state_count][0]);
        }
        else {
            kor.setText("");
        }

        if(setting.word_hide==true){

            if(5 < db_arr[state_count][1].length()){
                kanji.setTextSize(TypedValue.COMPLEX_UNIT_DIP,40);
                kanji.setText(db_arr[state_count][1]);
            }
            else {
                kanji.setTextSize(TypedValue.COMPLEX_UNIT_DIP,70);
                kanji.setText(db_arr[state_count][1]);
            }

        } else {
            kanji.setText("");
        }

        if(setting.pro_hide==true){
            hira.setText(db_arr[state_count][2]);
        }
        else {
            hira.setText("");
        }

        level_check=Integer.parseInt(db_arr[state_count][4]);
        level_check_icon(level_check);

    }/*printingText end*/


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.tab1_setting_menu,menu);
        menu.setHeaderTitle("Setting");

        for (int i = 0; i < menu.size(); ++i) {

            MenuItem check_hide = menu.getItem(i);

            if (check_hide.getItemId() == R.id.kanJi_hide) {
                check_hide.setChecked(setting.word_hide);
            }
            if (check_hide.getItemId() == R.id.hira_hide) {
                check_hide.setChecked(setting.pro_hide);
            }
            if (check_hide.getItemId() == R.id.kor_hide) {
                check_hide.setChecked(setting.mean_hide);
            }
        }

    }/*Contextmenu end*/


    @Override
    public boolean onContextItemSelected(MenuItem item){

        switch( item.getItemId() ){
            case (R.id.kanJi_hide):
                if(item.isChecked()==true) {
                    item.setChecked(false);
                    setting.word_hide=false;
                } else {
                    item.setChecked(true);
                    setting.word_hide=true;
                }
                break;

            case R.id.hira_hide:
                if(item.isChecked()==true) {
                    item.setChecked(false);
                    setting.pro_hide=false;
                } else {
                    item.setChecked(true);
                    setting.pro_hide=true;
                }
                break;

            case R.id.kor_hide:
                if(item.isChecked()==true) {
                    item.setChecked(false);
                    setting.mean_hide=false;
                } else {
                    item.setChecked(true);
                    setting.mean_hide=true;
                }
                break;

        }
        return true;

    }/*ContextItemSelected*/

    private Runnable TimeTask = new Runnable() {
        public void run() {

            if(ranb_state==false){ /*no random*/

                if(db_arr.length<=plb_state_count){
                    plb_state_count=0;
                }

                printingText(plb_state_count);
                plb_state_count++;

            }
            else if(ranb_state==true){ /*random*/

                Random random = new Random();

                plb_ran_count= random.nextInt(db_arr.length);
                printingText(plb_ran_count);

            }

            mHandler.postDelayed(this, setting.voca_speed);
        }
    }; /*TimeTask end*/

    public void tab1DBread(){

        dbinfo = v_dbHelper.getInfo_selectlevel(setting.level_1, setting.level_2, setting.level_3);
        int db_size = dbinfo.size();

        db_arr = new String[db_size][5];

        for (int i = 0; i < db_size; i++) {
            /*Make a DB Array*/

            V_CustomRow temp = dbinfo.get(i);

            db_arr[i][0] = temp.getMean();
            db_arr[i][1] = temp.getWord();
            db_arr[i][2] = temp.getPro();
            db_arr[i][3] = Integer.toString(temp.getV_cal());
            db_arr[i][4] = Integer.toString(temp.getV_re()) ;

        }

    }/*tab1DBread end*/

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
