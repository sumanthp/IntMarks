package com.example.hp.intmarks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by HP on 30/03/2017.
 */


//ArrayAdapter can only display data from one array in one textview. You will have to provide this functionality yourself by extending BaseAdapter yourself. I can't compile android code at the moment, so the following code is untested... but it should at least give you an indication of what you can do.

public class TripleArrayAdapter extends BaseAdapter {
    private String[] array1, array2, array3;
    private LayoutInflater inflater;

    public TripleArrayAdapter(Context context, String[] a1, String[] a2) {
        array1 = a1;
        array2 = a2;
        inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return array1.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View currentView = convertView;

        if(currentView==null) {
            currentView = inflater.inflate(R.layout.item_marks, parent, false);
        }

        TextView tView = (TextView)currentView.findViewById(R.id.type);
        tView.setText(array1[position]);

        tView = (TextView)currentView.findViewById(R.id.marks);
        tView.setText(array2[position]);



        return currentView;
    }
}
