package com.jcbriones.gmu.lab3todolist;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    // Variables
    private static ArrayList<String> toDoArrayList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    protected int currentPos;
    // View Variables
    protected AlertDialog actions;
    protected TextView inputText;
    protected ListView toDoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeVariables();

        // Alert Pop-up
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to delete this item?");
        String[] options = {"Delete"};
        builder.setItems(options, actionListener);
        builder.setNegativeButton("Cancel", null);
        actions = builder.create();

        // Set the adapter in toDoListView
        toDoList.setAdapter(adapter);

        // OnItemClick
        toDoList.setOnItemClickListener(actionItemClickListener);
        toDoList.setOnItemLongClickListener(actionItemLongClickListener);

        // Add start up value for ToDoList
        adapter.add("Lab3: Jc Briones");
    }

    AdapterView.OnItemClickListener actionItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String item = adapter.getItem(position);
            //
            if (item != null && item.startsWith("Done: ")) {
                adapter.remove(item);
                adapter.insert(item.replace("Done: ",""), 0);
            }
            else {
                adapter.remove(item);
                adapter.add("Done: " + item);
            }
            //
        }
    };

    AdapterView.OnItemLongClickListener actionItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            currentPos = position;
            actions.show();
            return true;
        }
    };

    DialogInterface.OnClickListener actionListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch(which) {
                case 0: // Delete
                    Toast.makeText(getApplicationContext(), "Deleting : " + adapter.getItem(currentPos), Toast.LENGTH_SHORT).show();
                    adapter.remove(adapter.getItem(currentPos));
                    break;
                default:
                    break;
            }
        }
    };

    public void buttonAdd(View v) {
        String val = inputText.getText().toString();
        if (val.isEmpty()) {
            // Show a warning to user
            Toast.makeText(getApplicationContext(), "Input field is empty. Please enter a task.", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Adding : " + val, Toast.LENGTH_SHORT).show();
            adapter.insert(val,0);
            // Clear the text once it's done
            inputText.setText("");
        }
    }


    public void buttonDeleteAllDone(View v) {
        if (adapter.isEmpty()) {
            // Throw a warning to user
            Toast.makeText(getApplicationContext(), "Your to-do list is empty. Nothing to delete", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Deleting all completed task", Toast.LENGTH_SHORT).show();
            Iterator<String> iterator = toDoArrayList.iterator();
            while (iterator.hasNext()) {
                String nextData = iterator.next();
                if (nextData.startsWith("Done")) {
                    adapter.remove(nextData);
                    iterator = toDoArrayList.iterator();
                }
            }
        }
    }

    private void initializeVariables() {
        inputText = (TextView) findViewById(R.id.inputText);
        toDoList = (ListView) findViewById(R.id.toDoList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item, toDoArrayList);
    }


}
