package com.example.homebutton

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homebutton.adapter.FavoritesAdapter
import com.example.homebutton.entity.Favorite
import com.example.homebutton.utils.MyDb
import kotlinx.android.synthetic.main.activity_favorites.*
import kotlinx.android.synthetic.main.common_title.*

class History : AppCompatActivity() {

    var myDb : SQLiteDatabase? = null
    var list = mutableListOf<Favorite>()
    var favoritesAdapter : FavoritesAdapter? = null
    var flag = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        supportActionBar?.hide()

        val db = MyDb(this, "myData.db", 1)
        myDb = db?.writableDatabase


        val linearLayoutManager = LinearLayoutManager(this)
        rev.layoutManager=linearLayoutManager
        favoritesAdapter = FavoritesAdapter(this, list)
        rev.adapter = favoritesAdapter
        ref()
        quan.setOnClickListener {
            val children = rev.children
            if (flag){
                flag=false
                for (it in children){
                    val ck = it.findViewById<CheckBox>(R.id.ck)
                    ck.isChecked = true
                }
            }else{
                flag=true
                for (it in children){
                    val ck = it.findViewById<CheckBox>(R.id.ck)
                    ck.isChecked = false
                }
            }

        }

        del.setOnClickListener {
            val children = rev.children
            for (it in children){
                val ck = it.findViewById<CheckBox>(R.id.ck)
                val tk = it.findViewById<TextView>(R.id.tk)
                if (ck.isChecked){
                    myDb?.execSQL("delete from history where id = ? ", arrayOf(tk.text.toString().toInt()))
                }
            }
            ref()
        }

        comTitle.text="我的记录"
        imgback.setOnClickListener{
            finish()
        }

    }

    override fun onRestart() {
        super.onRestart()
        ref()
    }

    fun ref(){
        list.clear()
        val rawQuery = myDb?.rawQuery("select * from history", null)
        if (rawQuery?.moveToFirst()!!){
            while (rawQuery.moveToNext()){
                val id = rawQuery.getInt(rawQuery.getColumnIndex("id"))
                val title =rawQuery.getString(rawQuery.getColumnIndex("title"))
                val img =rawQuery.getString(rawQuery.getColumnIndex("img"))
                val url =rawQuery.getString(rawQuery.getColumnIndex("url"))

                list.add(Favorite(id, title, img, url))
            }

        }
        favoritesAdapter?.notifyDataSetChanged()
    }
}