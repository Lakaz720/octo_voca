package com.bombelab.lakaz.octo_voca;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Pomiring on 2016-06-01.
 */

public class Tab4 extends Activity implements SearchView.OnQueryTextListener{

    private ListView mListView;
    private SearchView mSearchView;
    private Tab4_CustomListViewAdapter mCustomListViewAdapter;


    /*DB variable*/
    V_DBHelper v_dbHelper = new V_DBHelper(Tab4.this);
    private ArrayList<V_CustomRow> info;
    private ArrayList<S_CustomRow> listdbinfo;
    public static String db_arr[][];
    public static String db_arr_set[][];
    int db_size;
    int table_num;
    int all_count = 0;
    String temp_table;
    String cal_image;
    int select_num = 0;

    public static ArrayList<Tab4_AllVocaData> all_arr;
    List<Tab4_VocaData> Vdata;

    S_SetVal setting = new S_SetVal();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.tab4_activity);

        mListView= (ListView)findViewById(R.id.listview);
        mSearchView = (SearchView)findViewById(R.id.searchView);
        all_arr = new ArrayList<>();
        Vdata = new ArrayList<>();

        temp_table=setting.table_name;

        listtab4DBread();

        for(int i=0;i<table_num;i++){
            tab4DBread(db_arr_set[i][0]);
            all_count = all_count + db_size;
        }

        setting.table_name=temp_table;

        listListener();

        mCustomListViewAdapter = new Tab4_CustomListViewAdapter(this,Vdata);
        mListView.setAdapter(mCustomListViewAdapter);
        mListView.setTextFilterEnabled(true);

        searchListener();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                TextView temp_word = (TextView) view.findViewById(R.id.voca_kanji);
                TextView temp_mean = (TextView) view.findViewById(R.id.voca_kor);
                TextView temp_tablename = (TextView) view.findViewById(R.id.voca_table);

                String str_word = String.valueOf(temp_word.getText());
                String str_mean = String.valueOf(temp_mean.getText());
                String str_table = String.valueOf(temp_tablename.getText());

                for(int i =0; i<mCustomListViewAdapter.getCount();i++){
                    if(str_word.equals(all_arr.get(i).getWord_Text())
                            && str_mean.equals(all_arr.get(i).getMean_Text())
                            && str_table.equals(all_arr.get(i).getTable_Text())){
                        select_num = i;
                    }
                }

                Messagebox(select_num);

            }
        });

    }/*onCreate end*/

    public void onResume(){
        super.onResume();

    }

    public void Messagebox(int number){

        AlertDialog.Builder dialog = new AlertDialog.Builder(Tab4.this);
        /*
        * .............
        * */
        dialog.show();

    }/*Messagebox*/

    public void searchListener(){
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here");
    }

    public boolean onQueryTextChange(String newText) {
        /*...........filter........*/
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    public void listListener(){

        for(int i = 0 ; i<all_count ; i++){

            cal_image=all_arr.get(i).getCal_Text();
            switch (cal_image){
                case "1":
                    Vdata.add(new Tab4_VocaData(all_arr.get(i).getWord_Text(),
                            all_arr.get(i).getMean_Text(),
                            R.drawable.noun_icon,all_arr.get(i).getTable_Text()));
                    break;
                /*.....more case.......*/
            }

        }

    }/*listListener end*/


    public void listtab4DBread(){
        try {
            v_dbHelper.createDataBase();
        } catch (IOException e) {
            //e.printStackTrace();
        }
        v_dbHelper.openDataBase();

        listdbinfo = v_dbHelper.listgetInfo();
        db_size = listdbinfo.size();

        db_arr_set = new String[db_size][2];

        for (int i = 0; i < db_size; i++) {
            /*Make a DB Array*/

            S_CustomRow temp = listdbinfo.get(i);

            /*...............sample source*/

        }

        table_num = table_num + db_size;

    }/*listtab4DBread end*/


    public void tab4DBread(String table){

        setting.table_name=table;
        info = v_dbHelper.getInfo();
        //info = v_dbHelper.getInfo(true, true, true);
        db_size = info.size();

        db_arr = new String[db_size][5];

        for (int i = 0; i < db_size; i++) {
            /*Make a DB Array*/

            V_CustomRow temp = info.get(i);

            db_arr[i][0] = temp.getMean();
            db_arr[i][1] = temp.getWord();
            db_arr[i][2] = temp.getPro();
            db_arr[i][3] = "none";/*........sample app*/
            db_arr[i][4] = "none";/*........sample app*/

            all_arr.add(new Tab4_AllVocaData(db_arr[i][1],db_arr[i][2],db_arr[i][0],db_arr[i][3],table));

        }

    }/*tab4DBread end*/

}
