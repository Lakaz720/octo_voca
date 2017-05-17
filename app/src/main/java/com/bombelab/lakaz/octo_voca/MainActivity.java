package com.bombelab.lakaz.octo_voca;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;


public class MainActivity extends TabActivity {

    public static TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        Resources res = getResources();

        intent = new Intent().setClass(this, Tab1.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        spec = tabHost.newTabSpec("Voca").setIndicator("",res.getDrawable(R.drawable.voca_icon)).setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, Tab2.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        spec = tabHost.newTabSpec("Quiz").setIndicator("",res.getDrawable(R.drawable.quiz_icon)).setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, Tab3.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        spec = tabHost.newTabSpec("Add").setIndicator("",res.getDrawable(R.drawable.add_icon)).setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, Tab4.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        spec = tabHost.newTabSpec("List").setIndicator("",res.getDrawable(R.drawable.list_icon)).setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, Tab5.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        spec = tabHost.newTabSpec("Setting").setIndicator("",res.getDrawable(R.drawable.setting_icon_tab)).setContent(intent);
        tabHost.addTab(spec);

    }

}
