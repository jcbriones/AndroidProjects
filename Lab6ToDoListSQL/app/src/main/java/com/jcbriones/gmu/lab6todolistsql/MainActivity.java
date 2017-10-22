package com.jcbriones.gmu.lab6todolistsql;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static com.jcbriones.gmu.lab6todolistsql.DatabaseOpenHelper.TABLE_NAME;

public class MainActivity extends AppCompatActivity {
    EditText elem;
    ListView listView;
    public SimpleCursorAdapter myAdapter;
    AlertDialog actions;
    int currentPos;
    SQLiteDatabase db = null;
    DatabaseOpenHelper dbHelper = null;
    Cursor mCursor;
    String[] columns = new String[] {"_id", DatabaseOpenHelper.ITEM};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseOpenHelper(this);
        db = dbHelper.getWritableDatabase();

        mCursor = db.query(TABLE_NAME, columns, null, null, null, null,
                null);

        listView = (ListView) findViewById(R.id.toDoList);

        myAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                mCursor,
                new String[] { "item" },
                new int[] { android.R.id.text1 });

        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCursor.moveToPosition(position);
                String task = mCursor.getString(1);

                if (task.startsWith("Done: ")) {
                    // TODO Lab 6
                    //               String newtask = task.substring(6);
                    //myAdapter.remove(myAdapter.getItem(position));
                    //myAdapter.insert(newtask,0);
                    if (db == null)
                        db = dbHelper.getWritableDatabase();
                    doDelete(task);
                    doAddTop(task.substring(6));
                    myAdapter.notifyDataSetChanged();

                } else {
                    // TODO Lab 6
                    //                   myAdapter.remove(myAdapter.getItem(position));
                    //                   myAdapter.add("Done: " + task);
                    if (db == null)
                        db = dbHelper.getWritableDatabase();
                    doDelete(task);
                    doAdd("Done: " + task);
                    myAdapter.notifyDataSetChanged();
                }

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                                    Toast.makeText(getApplicationContext(), "Removing " + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                                                    currentPos = position;
                                                    actions.show();
                                                    return true;
                                                }
                                            }
        );

        elem =  (EditText)findViewById(R.id.inputText);

        //myAdapter.add("Lab 3: Prof. White");
        AlertDialog.Builder builder = new
                AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to delete this item?");
        String[] options = {"Delete"};
        builder.setItems(options, actionListener);
        builder.setNegativeButton("Cancel", null);
        actions = builder.create();

    }
    DialogInterface.OnClickListener actionListener =
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0: // Delete
                            mCursor.moveToPosition(currentPos);
                            String rowId = mCursor.getString(0);  // get the id
                            if (db == null)
                                db = dbHelper.getWritableDatabase();
                            db.delete(dbHelper.TABLE_NAME,"_id = ?",new String[]{rowId});
                            mCursor.requery();
                            myAdapter.notifyDataSetChanged();
                            // remove item from DB
                            break;
                        default:
                            break;
                    }
                }
            };


    public void onPause() {
        super.onPause();
        db.close();
    }


    public void buttonAdd(View v) {
        String input = elem.getText().toString();
        doAddTop(input);
        elem.setText("");

        Toast.makeText(getApplicationContext(), "Adding " + input, Toast.LENGTH_SHORT).show();
    }

    private void doAddTop(String input) {
        if (!input.equals("")) {
            // Add to Database
            ContentValues cv = new ContentValues(1);
            cv.put(DatabaseOpenHelper.ITEM, input);
            if (db == null)
                db = dbHelper.getWritableDatabase();
            db.insert(TABLE_NAME, null, cv);
            mCursor.requery();
            myAdapter.notifyDataSetChanged();
        }
    }

    private void doAdd(String input) {
        if (!input.equals("")) {
            // Add to Database
            ContentValues cv = new ContentValues(1);
            cv.put(DatabaseOpenHelper.ITEM, input);
            if (db == null)
                db = dbHelper.getWritableDatabase();
            db.insert(TABLE_NAME, null, cv);
            mCursor.requery();
            myAdapter.notifyDataSetChanged();
        }
    }

    public int doDelete(String input) {
        return db.delete(dbHelper.TABLE_NAME, "item = ?", new String[] {input});
    }

    public void buttonDeleteAllDone(View v) {
        int len = myAdapter.getCount();
        if (myAdapter.isEmpty()) {
            // Throw a warning to user
            Toast.makeText(getApplicationContext(), "Your to-do list is empty. Nothing to delete", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Deleting all completed task", Toast.LENGTH_SHORT).show();
            for (int i = len - 1; i >= 0; i--) {
                mCursor.moveToPosition(i);
                String task = mCursor.getString(1);
                if (task.startsWith("Done: ")) {
                    if (db == null)
                        db = dbHelper.getWritableDatabase();

                    doDelete(task);
                    mCursor.requery();
                    myAdapter.notifyDataSetChanged();
                }
            }
        }
    }

}