package com.waracle.androidtest

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.waracle.androidtest.adapters.MainAdapter
import com.waracle.androidtest.model.CakeModel
import com.waracle.androidtest.databinding.ActivityMainBinding
import com.waracle.androidtest.viewModels.CakeViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {

    lateinit var cakeViewModel :CakeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cakeViewModel = ViewModelProviders.of(this).get(CakeViewModel::class.java)
        val activityMainBinding:ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        cakeViewModel.cakeModels.observe(this, Observer<MutableList<CakeModel>> {
            if(cake_list.adapter != null) {
                (cake_list.adapter as MainAdapter).setItems(it)
            }
        })
        activityMainBinding.cakeViewModel = cakeViewModel
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    fun refreshList(){
        (cake_list.adapter as MainAdapter).clear()
        cakeViewModel.fetchCakeModels()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.action_refresh ->  {
                refreshList()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}