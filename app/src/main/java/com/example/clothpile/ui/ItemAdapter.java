package com.example.clothpile.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.clothpile.R;

import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> mClothList;
    ArrayList<Integer> numberOfItems;

    public ItemAdapter(Context context, ArrayList<String> itemsList, ArrayList<Integer> numberOfItems){
        super();
        mContext = context;
        mClothList = itemsList;
        this.numberOfItems = numberOfItems;
    }
    @Override
    public int getCount() {
        return mClothList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        // inflate the layout for each item of listView
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_listview_row, parent, false);


        // get the reference of textView and button
        TextView txtSchoolTitle =  view.findViewById(R.id.cloth_item_name);
        ElegantNumberButton elegantNumberButton =  view.findViewById(R.id.elegant_number_button);

        // Set the title and button name
        txtSchoolTitle.setText(mClothList.get(position));

        elegantNumberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                //Log.d(TAG, String.format("oldValue: %d   newValue: %d", oldValue, newValue));
                numberOfItems.set(position, newValue);

                Toast.makeText(mContext, "update!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
