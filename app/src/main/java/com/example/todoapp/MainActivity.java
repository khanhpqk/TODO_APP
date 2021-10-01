package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import java.nio.charset.Charset;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    List<String> item;
    Button addBut;
    EditText edit;
    ItemAdapter adapter;
    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item = new ArrayList<>();
        addBut = findViewById(R.id.addi);
        edit = findViewById(R.id.edit_txt);
        recycler = findViewById(R.id.recycler_view);
        loadItem();
        ItemAdapter.OnLongClickedListener onLongClicked = new ItemAdapter.OnLongClickedListener(){

            @Override
            public void onLongClicked(int position) {
                item.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was successfully removed.", Toast.LENGTH_SHORT).show();
                saveItem();
            }
        };
        adapter = new ItemAdapter(item, onLongClicked);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        addBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addedList = edit.getText().toString();
                item.add(addedList);
                adapter.notifyItemInserted(item.size() - 1);
                edit.setText("");
                Toast.makeText(getApplicationContext(), "Item was added!", Toast.LENGTH_SHORT).show();
                saveItem();
            }
        });
    }


    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    //load item before add
    private void loadItem(){
        try{
            item = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        }
        catch(IOException e){
            Log.e("MainActivity", "Error", e);
            item = new ArrayList<>();

        }
    }

    private void saveItem(){
        try{
            FileUtils.writeLines(getDataFile(), item);
        }
        catch(IOException e){
            Log.e("MainActivity", "Error", e);
        }
    }
}