package com.waracle.androidtest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;

import com.waracle.androidtest.Adapters.MainAdapter;
import com.waracle.androidtest.DataSource.CakeDataSources;
import com.waracle.androidtest.DataSource.DataSources;
import com.waracle.androidtest.Model.CakeModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @SuppressWarnings("FieldCanBeLocal")
    private DataSources.DataListeners<List<CakeModel>> dataListener;
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.cake_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mainAdapter = new MainAdapter();
        recyclerView.setAdapter(mainAdapter);
        getData();
    }

    private void getData() {
        dataListener = new DataSources.DataListeners<List<CakeModel>>() {
            @Override
            public void onDataRetrieved(final List<CakeModel> result) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mainAdapter.setItems(result);
                    }
                });
            }
        };

        String JSON_URL = "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";
        MainApplication.getDataSource().addToMap(JSON_URL, new CakeDataSources(), dataListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void refreshList() {
        mainAdapter.clear();
        MainApplication.getDataSource().clearDataSources();
        getData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            refreshList();
//            PlaceholderFragment placeholderFragment = ((PlaceholderFragment) getSupportFragmentManager().findFragmentByTag(PlaceholderFragment.TAG));
//            if(placeholderFragment != null){
//                placeholderFragment.refreshList();
//            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
