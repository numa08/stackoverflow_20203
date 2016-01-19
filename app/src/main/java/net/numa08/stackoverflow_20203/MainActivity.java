package net.numa08.stackoverflow_20203;

import android.app.Activity;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener{

    public static class RowData implements Parcelable {
        private String name;
        private String code;

        public RowData(String name, String code) {
            this.name = name;
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeString(this.code);
        }

        protected RowData(Parcel in) {
            this.name = in.readString();
            this.code = in.readString();
        }

        public static final Parcelable.Creator<RowData> CREATOR = new Parcelable.Creator<RowData>() {
            public RowData createFromParcel(Parcel source) {
                return new RowData(source);
            }

            public RowData[] newArray(int size) {
                return new RowData[size];
            }
        };

        @Override
        public String toString() {
            return "RowData{" +
                    "name='" + name + '\'' +
                    ", code='" + code + '\'' +
                    '}';
        }
    }

    private Spinner spinner;
    private ArrayList<RowData> rowList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.reload_button).setOnClickListener(this);

        spinner = (Spinner) findViewById(R.id.spinner);
    }


    @Override
    public void onClick(View v) {
        final Cursor cursor = load();
        // 読み込んだデータを表示用に修正
        rowList = new ArrayList<>(cursor.getCount());
        while(cursor.moveToNext()) {
            final String name = cursor.getString(cursor.getColumnIndex("NAME"));
            final String code = cursor.getString(cursor.getColumnIndex("CODE"));
            rowList.add(new RowData(name, code));
        }
        final MyAdapter adapter = new MyAdapter(this, rowList);
        spinner.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (rowList != null) {
            outState.putParcelableArrayList("content", rowList);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final ArrayList<RowData> content = savedInstanceState.getParcelableArrayList("content");
        if (content != null) {
            rowList = content;
            final MyAdapter myAdapter = new MyAdapter(this, rowList);
            spinner.setAdapter(myAdapter);
        }
    }

    public Cursor load() {
        final MatrixCursor cursor = new MatrixCursor(new String[]{"NAME", "CODE", "NAK"});
        cursor.newRow()
                .add("row1")
                .add("1")
                .add("nak");
        cursor.newRow()
                .add("row2")
                .add("2")
                .add("nak");
        cursor.newRow()
                .add("row3")
                .add("3")
                .add("nak");
        return cursor;
    }
}
