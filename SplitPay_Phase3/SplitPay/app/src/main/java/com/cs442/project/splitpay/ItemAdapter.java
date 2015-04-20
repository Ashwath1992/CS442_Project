package com.cs442.project.splitpay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemAdapter extends ArrayAdapter<Item> {
    int resource;

    public ItemAdapter(Context context, int resource, List<Item> items) {

        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout itemView;
        final Item item = getItem(position);

        if (convertView == null) {
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li;
            li = (LayoutInflater)getContext().getSystemService(inflater);
            li.inflate(resource, itemView, true);
        } else {
            itemView = (LinearLayout) convertView;
        }

        TextView memberName = (TextView)itemView.findViewById(R.id.memberName);
        TextView amountOwed = (TextView)itemView.findViewById(R.id.amountOwed);
        TextView amountPaid = (TextView)itemView.findViewById(R.id.amountPaid);
        CheckBox cb = (CheckBox)itemView.findViewById(R.id.cb1);

        memberName.setText(item.getMemberName());
        amountOwed.setText(String.valueOf(item.getTotalAmountOwed()));
        amountPaid.setText(String.valueOf(item.getTotalAmountSpent()));
        cb.setChecked(item.getChkBox());

        if (item.getTotalAmountOwed() > 0.0) {
            amountOwed.setTextColor(Color.RED);
        }else{
            amountOwed.setTextColor(Color.BLACK);
        }

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    //checked
                    //buttonView.getRootView().
                    View v = (View)buttonView;
                    //int pos = getPositionForView(v);
                    //cbValue = true;
                    item.setChkBox(true);
                    System.out.println("Pos checked");
                } else {
                    //not checked
                    item.setChkBox(false);
                    System.out.println("Pos not checked");
                }
            }
        });

        return itemView;
    }
}