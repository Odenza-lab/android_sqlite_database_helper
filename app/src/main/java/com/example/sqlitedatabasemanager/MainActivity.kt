package com.example.sqlitedatabasemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitedatabasemanager.adapter.MainAdapter
import com.example.sqlitedatabasemanager.database.DatabaseHelper
import com.example.sqlitedatabasemanager.models.MainDataModel

class MainActivity : AppCompatActivity() {

    private lateinit var recycleView: RecyclerView
    private lateinit var databaseHelper: DatabaseHelper

    private var list: List<MainDataModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper.getInstance(this)

        initData()
        initView()
    }

    private fun initView() {
        recycleView = findViewById(R.id.recycle_view)
        val adapter = MainAdapter(list, this)
        val linearLayoutManager = LinearLayoutManager(this)
        recycleView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recycleView.layoutManager = linearLayoutManager
        recycleView.adapter = adapter
    }

    private fun initData() {
        list = databaseHelper.retrieveData()
    }
}