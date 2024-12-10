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
import androidx.appcompat.app.AlertDialog
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


                           var id = 0
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        init()
         checkAdapter = Adapter(this,check)
          listTv.adapter = checkAdapter

            check.addAll(db.readCheck()); checkAdapter.notifyDataSetChanged()

            id = check.maxOfOrNull { it.id }?.toInt() ?: 0;


            button.setOnClickListener { try{ val value = weightTv.text.toString().toDouble(); valueTv.setText((value * price).toString()) } catch (_:Exception) {return@setOnClickListener} }


            buttonSave.setOnClickListener { if(valueTv.text.isEmpty()) return@setOnClickListener
                id++
                       db.addCheck(Check(Product.product[select].name,price.toString(),weightTv.text.toString(),valueTv.text.toString(),id))
                      check.clear(); check.addAll(db.readCheck()); checkAdapter.notifyDataSetChanged()
                    weightTv.text.clear(); valueTv.text = ""; spinner.setSelection(0); }



                  listTv.onItemClickListener = AdapterView.OnItemClickListener{ s,v,position,id ->

                      val builder = AlertDialog.Builder(this)
                      builder.setTitle("Выберите действие")
                          .setPositiveButton("Удалить"){ _,_ -> db.deleteCheck(check[position]); check.clear(); check.addAll(db.readCheck()); checkAdapter.notifyDataSetChanged()}
                          .setNeutralButton("Отмена"){ d,t -> d.cancel() }
                              .setNegativeButton("Редактировать"){ d,t -> updateCheck(position) }.create()

                      builder.show() }







        val itemSelected: AdapterView.OnItemSelectedListener =
            object: AdapterView.OnItemSelectedListener{

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {  select = position; price = Product.product[select].price
                    priceTv.text = price.toString() }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        spinner.onItemSelectedListener = itemSelected }

    @SuppressLint("SuspiciousIndentation")
    private fun updateCheck( position: Int ) {

        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.update_item,null)
        val name_upd = dialogView.findViewById<EditText>(R.id.name_upd)
        val price_upd = dialogView.findViewById<EditText>(R.id.price_upd)
        val weight_upd = dialogView.findViewById<EditText>(R.id.weight_upd)
        val value_upd = dialogView.findViewById<EditText>(R.id.value_upd)

            name_upd.setText(check[position].name)
            price_upd.setText(check[position].price)
            weight_upd.setText(check[position].weight)
            value_upd.setText(check[position].value)

            builder.setView(dialogView)
            builder.setTitle("Редактирование")
                .setNegativeButton("Отмена"){d,_ -> d.cancel()}
                .setPositiveButton("Сохранить"){d,_ ->



                       val name = name_upd.text.toString()
                       val price = price_upd.text.toString()
                       val weight = weight_upd.text.toString()
                       val value = value_upd.text.toString()

                    if( name.trim() != "" && price.trim() != "" && weight.trim() != "" && value.trim() != "")
                          {
                                 val check1 = Check(name,price,weight,value,check[position].id)
                                    db.updateCheck(check1); check.clear(); check.addAll(db.readCheck())
                                       checkAdapter.notifyDataSetChanged()

                            }
                }.create()
        builder.show()
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
            R.id.clearBase -> { db.removeAll(); check.clear(); id = 0; checkAdapter.notifyDataSetChanged() ;Toast.makeText(this,"База очищена",Toast.LENGTH_LONG).show()}
        }

        return super.onOptionsItemSelected(item)
    }






}





