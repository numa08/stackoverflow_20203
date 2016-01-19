package net.numa08.stackoverflow_20203;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class MyAdapter extends ArrayAdapter<MainActivity.RowData> {
    public MyAdapter(Context context, List<MainActivity.RowData> objects) {
        super(context, android.R.layout.simple_list_item_1, android.R.id.text1, objects);
    }
}
