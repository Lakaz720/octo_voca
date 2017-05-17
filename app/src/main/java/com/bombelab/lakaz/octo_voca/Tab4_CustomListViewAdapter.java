package com.bombelab.lakaz.octo_voca;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.provider.SyncStateContract.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by Pomiring on 2016-06-03.
 */
public class Tab4_CustomListViewAdapter extends BaseAdapter
        /*implements Filterable{*/ {

    private Context mContext;
    private List<Tab4_VocaData> mVocaDataList;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Tab4_VocaData> arraylist;


    public Tab4_CustomListViewAdapter(Context context, List<Tab4_VocaData> vocaDataList){
        this.mContext = context;
        this.mVocaDataList = vocaDataList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Tab4_VocaData>();
        this.arraylist.addAll(mVocaDataList);

    }

    @Override
    public int getCount(){
        return mVocaDataList.size();
    }

    @Override
    public Object getItem(int position){
        return mVocaDataList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.tab4_item_listview, null);

        }

        ImageView img_c = (ImageView)view.findViewById(R.id.img_class);
        TextView kanji_t = (TextView)view.findViewById(R.id.voca_kanji);
        TextView kor_t = (TextView)view.findViewById(R.id.voca_kor);
        TextView table_t = (TextView)view.findViewById(R.id.voca_table);

        Tab4_VocaData vocaData = mVocaDataList.get(position);

        img_c.setImageResource(vocaData.getImgId());
        kanji_t.setText(vocaData.getKanji_Text());
        kor_t.setText(vocaData.getKor_Text());
        table_t.setText(vocaData.getTable_Text());

        return view;

    }

    // Filter Class
    public void filter(String charText) {

        mVocaDataList.clear();
        if (charText.length()==0) {
            mVocaDataList.addAll(arraylist);
        }
        else
        {
            for (Tab4_VocaData wp : arraylist)
            {

                if (wp.getKanji_Text().contains(charText)){ mVocaDataList.add(wp); }
                if (wp.getKor_Text().contains(charText)){ mVocaDataList.add(wp); }

            }
        }
        notifyDataSetChanged();
    }

}
