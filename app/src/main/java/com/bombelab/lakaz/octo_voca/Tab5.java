package com.bombelab.lakaz.octo_voca;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.widget.ImageView;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.MultiSelectListPreference;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Pomiring on 2016-06-01.
 */
public class Tab5 extends PreferenceActivity implements OnSharedPreferenceChangeListener {

    /*DB SQL*/
    V_DBHelper v_dbHelper = new V_DBHelper(Tab5.this);
    private ArrayList<S_CustomRow> listdbinfo;
    private ArrayList<S_CustomRow> maindbinfo;
    public static String db_arr[][];
    int db_size;

    /*Setting button variable*/

    S_SetVal setting = new S_SetVal();

    ListPreference seltablelist;
    Preference deltablelist,infopopup;
    ListPreference sectablelsit;
    MultiSelectListPreference levelselectlist;
    EditTextPreference addvocatable;

    CharSequence[] entryValues;
    CharSequence[] entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        tab5DBread();
        addPreferencesFromResource(R.xml.tab5_setting);

        levelselectlist = (MultiSelectListPreference) findPreference("levelselectlist");
        sectablelsit = (ListPreference) findPreference("sectablelsit");
        seltablelist = (ListPreference) findPreference("seltablelist");
        addvocatable = (EditTextPreference) findPreference("addvocatable");
        deltablelist = (Preference) findPreference("deltablelist");
        infopopup = (Preference) findPreference("information");



        deltablelist.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                createListPreferenceDialog();
                return false;

            }
        });
        infopopup.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                Messagebox();
                return false;

            }
        });
    }
    private void createListPreferenceDialog() {

        Dialog dialog;

        AlertDialog.Builder d_dialog = new AlertDialog.Builder(Tab5.this);
        d_dialog.setTitle("Delete VOCA");

        d_dialog.setItems(entries, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position){

                if(String.valueOf(setting.table_name).equals(String.valueOf(entries[position]))){
                    Toast.makeText(getApplicationContext(),"Can't delete "+/*...............sample source*/
                            " is selected.",Toast.LENGTH_LONG).show();
                }else {
                    v_dbHelper.DeleteVocabulary_T(String.valueOf(entries[position]));
                    Toast.makeText(getApplicationContext(),"Delete VOCA "/*...............sample source*/,
                            Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent().setClass(Tab5.this,MainActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_FORWARD_RESULT);
                new MainActivity().tabHost.setCurrentTab(R.id.tab4_Back);
                startActivity(intent);

            }
        });

        dialog = d_dialog.create();
        dialog.show();
    }

    public void Messagebox(){

        Dialog dialog = new Dialog(Tab5.this);
        dialog.setContentView(R.layout.tab5_info_layout);
        dialog.setCancelable(true);
        dialog.setTitle("INFOMATION");
        TextView text = (TextView) dialog.findViewById(R.id.TextView01);
        text.setText("Create by Lab.Bombe \nlakaz720@gmail.com");
        ImageView img = (ImageView) dialog.findViewById(R.id.ImageView01);
        img.setImageResource(R.drawable.com_icon);

        dialog.show();

    }/*Messagebox*/

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);

        setListTableData(seltablelist);
        onSharedPreferenceChanged(null,"");

    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        boolean lev1,lev2,lev3;
        if(key.equals("levelselectlist")){

            lev1=levelselectlist.getValues().contains("0");
            lev2=levelselectlist.getValues().contains("1");
            lev3=levelselectlist.getValues().contains("2");

            v_dbHelper.Updatelevelcheck_setting(lev1,lev2,lev3);

        }
        else if(key.equals("sectablelsit")){

            String temp = String.valueOf(sectablelsit.getValue());
            v_dbHelper.Updatetablespeed(temp);

        }
        else if(key.equals("seltablelist")){

            setListTableData(seltablelist);
            String temp = String.valueOf(seltablelist.getEntry());
            v_dbHelper.Updatetablename(temp);

        }
        else if(key.equals("addvocatable")){

            String text = addvocatable.getText();
            v_dbHelper.CreateVocabulary_T(text);
            Intent intent = new Intent().setClass(Tab5.this,MainActivity.class);
            intent.setFlags(intent.FLAG_ACTIVITY_FORWARD_RESULT);
            new MainActivity().tabHost.setCurrentTab(R.id.tab4_Back);
            startActivity(intent);
            Toast.makeText(getApplicationContext(),"Add VOCA "+text,Toast.LENGTH_LONG).show();

        }
    }

    protected void setListTableData(ListPreference lp) {

        lp.setEntries(entries);
        lp.setEntryValues(entryValues);

    }

    public void tab5DBread(){

        try {
            v_dbHelper.createDataBase();
        } catch (IOException e) {
        }
        v_dbHelper.openDataBase();

        maindbinfo = v_dbHelper.maingetInfo();
        db_size = maindbinfo.size();

        db_arr = new String[db_size][2];

        for (int i = 0; i < db_size; i++) {
            /*Make a DB Array*/

            S_CustomRow temp = maindbinfo.get(i);

            db_arr[i][0] = temp.getMean();
            db_arr[i][1] = temp.getValues();

        }

        setting.table_name=db_arr[0][1];
        setting.voca_speed=Integer.valueOf(db_arr[1][1]);

        listdbinfo = v_dbHelper.listgetInfo();
        db_size = listdbinfo.size();
        entries = new CharSequence[db_size];
        entryValues = new CharSequence[db_size];

        for (int i = 0; i < db_size; i++) {
            /*Make a DB Array*/

            S_CustomRow temp = listdbinfo.get(i);

            entries[i] = temp.getMean();
            entryValues[i] = "1";/*..............sample app*/

        }

    }/*tab5DBread end*/



}
