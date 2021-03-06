package com.example.anotheone;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


public class ViewCharts extends AppCompatActivity {
    static Map<String, List<String>> charts = new HashMap<>();
    static ArrayList<String> chartsList = new ArrayList<>();
    static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_charts);

        ListView lvCharts = (ListView) findViewById(R.id.lvCharts);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.anotheone"
                , Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>)sharedPreferences.getStringSet("charts", null);
        if(set == null) {
            chartsList.add("new");
        }else {
            chartsList = new ArrayList(charts.keySet());

        }
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,chartsList );
        lvCharts.setAdapter(arrayAdapter);


        lvCharts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), AddChart.class);
                intent.putExtra("chartId",chartsList.get(position));
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.anotheone"
                        , Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<String>(ViewCharts.charts.keySet());
                sharedPreferences.edit().putStringSet("charts", set).apply();
                startActivity(intent);
            }
        });



        lvCharts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(ViewCharts.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this chart?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                chartsList.remove(position);
                                arrayAdapter.notifyDataSetChanged();

                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.anotheone"
                                        , Context.MODE_PRIVATE);
                                HashSet<String> set = new HashSet<String>(ViewCharts.charts.keySet());
                                sharedPreferences.edit().putStringSet("charts", set).apply();
                            }
                        })
                        .setNegativeButton("NO!", null)
                        .show();
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.second, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.add_chart:
                Intent intent = new Intent(getApplicationContext(), AddChart.class);
                startActivity(intent);
                return true;
        }
        return false;

    }

}
