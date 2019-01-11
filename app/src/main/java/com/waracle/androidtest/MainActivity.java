package com.waracle.androidtest;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.waracle.androidtest.Adapters.MainAdapter;
import com.waracle.androidtest.Model.CakeModel;
import com.waracle.androidtest.databinding.ActivityMainBinding;
import com.waracle.androidtest.viewModels.CakeViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    private CakeViewModel cakeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cakeViewModel = ViewModelProviders.of(this).get(CakeViewModel.class);
        cakeViewModel.getCakeModels().observe(this, new Observer<List<CakeModel>>() {
            @Override
            public void onChanged(List<CakeModel> cakeModels) {
                if (recyclerView.getAdapter() != null) {
                    ((MainAdapter) recyclerView.getAdapter()).setItems(cakeModels);
                }
            }
        });

        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setCakeViewModel(cakeViewModel);
        recyclerView = findViewById(R.id.cake_list);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void refreshList() {
        if (recyclerView.getAdapter() != null) {
            ((MainAdapter) recyclerView.getAdapter()).clear();
        }
        MainApplication.getDataSource().clearDataSources();
        cakeViewModel.fetchCakeModels();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
