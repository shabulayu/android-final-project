package com.example.finalproject.NewYorkTimes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finalproject.R;

import java.util.ArrayList;

/**
 * this class is used to get the saved article form the data base then to display it on the list view
 */
public class saved_list_newyork extends AppCompatActivity {

    static MyOwnAdapter adt;
    static ArrayList<TimesNews> tNewsSavd = new ArrayList<>();
    View newView;
    MyDataOpenHelper dbOpener;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_list_newyork);

        tNewsSavd.clear();
        //initialize the inner class
        adt = new MyOwnAdapter();
        //Get the fields from the screen:
        ListView savedList = (ListView)findViewById(R.id.saved_list);

        //get a database:
        dbOpener = new MyDataOpenHelper(this);
        db = dbOpener.getWritableDatabase();

        //query all the results from the database:
        String [] columns = {MyDataOpenHelper.COL_ID, MyDataOpenHelper.COL_TITLE, MyDataOpenHelper.COL_AUTHOR, MyDataOpenHelper.COL_LINK, MyDataOpenHelper.COL_DESCRIPTION};
        Cursor results = db.query(false, MyDataOpenHelper.TABLE_NAME, columns, null,null, null, null, null, null);

        //find the column indices:
        int idIndex = results.getColumnIndex(MyDataOpenHelper.COL_ID);
        int titleIndex = results.getColumnIndex(MyDataOpenHelper.COL_TITLE);
        int authorIndex = results.getColumnIndex(MyDataOpenHelper.COL_AUTHOR);
        int linkIndex = results.getColumnIndex(MyDataOpenHelper.COL_LINK);
        int desIndex = results.getColumnIndex(MyDataOpenHelper.COL_DESCRIPTION);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext()){
            long id = results.getInt(idIndex);
            String title = results.getString(titleIndex);
            String author = results.getString(authorIndex);
            String link = results.getString(linkIndex);
            String description = results.getString(desIndex);

            //add the new Message to the array list:
            tNewsSavd.add(new TimesNews(id, title, author, link, description));

        }
        //using an adapter object and send it to the listVIew
        savedList.setAdapter(adt);

        //when click the list go to the detail page
        savedList.setOnItemClickListener( (list, item, position, id) -> {

            Bundle dataToPass = new Bundle();
            dataToPass.putInt("arrayID", tNewsSavd.indexOf(tNewsSavd.get(position)));
            dataToPass.putLong("ID", tNewsSavd.get(position).getId());
            dataToPass.putString(activity_newyork.ITEM_TITLE, tNewsSavd.get(position).getTitle());
            dataToPass.putString(activity_newyork.ITEM_AUTHOR, tNewsSavd.get(position).getAuthor());
            dataToPass.putString(activity_newyork.ITEM_LINK, tNewsSavd.get(position).getLink());
            dataToPass.putString(activity_newyork.ITEM_DESCRIPTION, tNewsSavd.get(position).getDescription());

            //go to the fragment
            Intent nextActivity = new Intent(saved_list_newyork.this, Empty_newyork.class);
            nextActivity.putExtras(dataToPass); //send data to next activity
            startActivityForResult(nextActivity, 345); //make the transition


        });

    }

    /**
     * the adapter for the list view
     */
    protected class MyOwnAdapter extends BaseAdapter {
        public MyOwnAdapter() {
            super();
        }

        @Override
        public int getCount() {
            return tNewsSavd.size() ;
        }

        @Override
        public TimesNews getItem(int position) {
            return tNewsSavd.get(position);
        }

        /**
         * this method get the view for the list view single row
         * @param position
         * @param old
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View old, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();

            TimesNews messageToShow = getItem(position);
            newView = inflater.inflate(R.layout.single_row_newyork, null);
            TextView rowText = newView.findViewById(R.id.rowNews);
            rowText.setText(messageToShow.getTitle());

            return newView;
        }

        @Override
        public long getItemId(int position) {

            return position;
        }
    }
}