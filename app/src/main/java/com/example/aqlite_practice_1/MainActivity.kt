package com.example.aqlite_practice_1

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {


       private lateinit var toolbar: Toolbar
       private lateinit var spinner: Spinner
       private lateinit var priceTv: TextView
       private lateinit var valueTv: TextView
       private lateinit var weightTv: EditText
       private lateinit var button: Button
       private lateinit var buttonSave: Button
       private lateinit var listTv: ListView
                        var select = 0; var price = 0.0
                        val list: MutableList<String> = mutableListOf()
                        var check: MutableList<Check> = mutableListOf()
                    lateinit var checkAdapter: Adapter
                    private var db = DbHelper(this,null)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        init()
         checkAdapter = Adapter(this,check)
          listTv.adapter = checkAdapter

            check.addAll(db.readCheck()); checkAdapter.notifyDataSetChanged()

            button.setOnClickListener { try{ val value = weightTv.text.toString().toDouble(); valueTv.setText((value * price).toString()) } catch (_:Exception) {return@setOnClickListener} }


            buttonSave.setOnClickListener { if(valueTv.text.isEmpty()) return@setOnClickListener



                       db.addCheck(Check(Product.product[select].name,price.toString(),weightTv.text.toString(),valueTv.text.toString()))
                      check.clear(); check.addAll(db.readCheck()); checkAdapter.notifyDataSetChanged()
                    weightTv.text.clear(); valueTv.text = ""; spinner.setSelection(0)


            }



        val itemSelected: AdapterView.OnItemSelectedListener =
            object: AdapterView.OnItemSelectedListener{

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {  select = position; price = Product.product[select].price
                    priceTv.text = price.toString()


                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }

        spinner.onItemSelectedListener = itemSelected






          }








    fun init(){

        Product.product.forEach { list += it.name }
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setBackgroundColor(Color.BLUE)
        toolbar.setTitleTextColor(Color.WHITE)
        title = "Потребительская корзина"
        weightTv = findViewById(R.id.weightText)
        priceTv = findViewById(R.id.priceText)
        valueTv = findViewById(R.id.valueText)
        button = findViewById(R.id.button1)
        buttonSave = findViewById(R.id.buttonSave)
        spinner = findViewById(R.id.spinner)
        val nameAdapter = ArrayAdapter(this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list)
        spinner.setAdapter(nameAdapter)
        listTv = findViewById(R.id.listTv)



    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu) }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exit -> {finishAffinity()}
            R.id.clearBase -> {/* DbHelper(this,null).removeAll()*/; db.removeAll(); check.clear(); checkAdapter.notifyDataSetChanged() ;Toast.makeText(this,"База очищена",Toast.LENGTH_LONG).show()}
        }

        return super.onOptionsItemSelected(item)
    }






}





