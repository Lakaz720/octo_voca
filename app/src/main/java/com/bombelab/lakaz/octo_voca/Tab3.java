package com.bombelab.lakaz.octo_voca;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pomiring on 2016-06-01.
 */
public class Tab3 extends Activity {

    /*Layout*/
    //ImageButton seab, txb, extxb;
    Button sab, wabsb;
    EditText word_v, pro_v, mean_v;
    Spinner v_list, v_class;
    TextView spinner_view;
    Boolean txtcheck;
    String cal_s="noun";
    String list_s;
    String word_s, pro_s, mean_s;
    int num;

    /*Text*/
    V_Check v_check = new V_Check();
    boolean check=true;

    /*DB SQL*/
    V_DBHelper v_dbHelper = new V_DBHelper(Tab3.this);
    private ArrayList<S_CustomRow> listdbinfo;
    public static String db_arr[][];
    List<String> categories = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab3_activity);

        sab=(Button)findViewById(R.id.savebutton);
        wabsb=(Button)findViewById(R.id.websearchbutton);

        word_v=(EditText)findViewById(R.id.editword);
        pro_v=(EditText)findViewById(R.id.editpro);
        mean_v=(EditText)findViewById(R.id.editmean);

        v_list=(Spinner)findViewById(R.id.spinner_v_list);
        v_class=(Spinner)findViewById(R.id.v_class);

        spinner_view= (TextView)v_class.getSelectedView();

        tab3DBread();
        SpinnerListener_tab3();
        addButtonListener_tab3();

    }/*onCreate end*/


    public void SpinnerListener_tab3(){

        ArrayAdapter<String> dataAdapter =
                new ArrayAdapter<String>(this, R.layout.spinner_item, categories);
        dataAdapter.setDropDownViewResource(R.layout.spinner_do_item);
        v_list.setAdapter(dataAdapter);

        ArrayAdapter adapter = ArrayAdapter.
                createFromResource(this, R.array.v_class_list, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_do_item);
        v_class.setAdapter(adapter);

        v_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                list_s= (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        v_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                cal_s= (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }/*Spinner end*/


    public void addButtonListener_tab3(){

        sab.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                v_check= new V_Check();

                word_s = word_v.getText().toString();
                pro_s = pro_v.getText().toString();
                mean_s = mean_v.getText().toString();

                txtcheck =  v_check.V_Check(word_s);
                if(txtcheck ==false){
                    word_s="";
                    word_v.setText("");
                    pro_s="";
                    pro_v.setText("");
                    mean_s="";
                    mean_v.setText("");
                    check=false;
                }

                txtcheck =  v_check.V_Check(pro_s);
                if(txtcheck ==false){
                   /*...............sample source*/
                }

                txtcheck =  v_check.V_Check(mean_s);
                if(txtcheck ==false){
                    /*...............sample source*/
                }

                num = caltonum(cal_s);
                Messagebox(check);

            }
        });


        wabsb.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                goToUrl("http://www.google.com/");
            }
        });

    }/*addButton end*/

    public void Messagebox(boolean ans){

        AlertDialog.Builder dialog = new AlertDialog.Builder(Tab3.this);

        if(ans==true) {
            dialog.setTitle("Do you want to save in " + list_s +" ?");
            dialog.setMessage(word_s +" / "+ pro_s +" / "+ mean_s);
        } else {
            dialog.setTitle("Fail");
            dialog.setMessage("It contain special characters. ");
        }

        dialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                /*...............sample source*/
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.show();
        word_v.setText("");
        pro_v.setText("");
        mean_v.setText("");

    }/*Messagebox*/

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public int caltonum(String cal){

        switch (cal){
            case "noun":
                return 1;
            /*...............sample source*/
        }

        return 0;
    }/*caltonum end*/


    public void tab3DBread(){

        try {
            v_dbHelper.createDataBase();
        } catch (IOException e) {
            //e.printStackTrace();
        }
        v_dbHelper.openDataBase();

        listdbinfo = v_dbHelper.listgetInfo();
        int db_size = listdbinfo.size();

        db_arr = new String[db_size][2];

        for (int i = 0; i < db_size; i++) {
            /*Make a DB Array*/

            S_CustomRow temp = listdbinfo.get(i);

            db_arr[i][0] = temp.getMean();
            db_arr[i][1] = "00";

        }

        for(int i = 0 ; i<db_arr.length;i++){
            categories.add(db_arr[i][0]);
        }

    }/*tab3DBread end*/



}
