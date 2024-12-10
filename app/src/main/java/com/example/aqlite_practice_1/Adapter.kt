package com.example.aqlite_practice_1

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class Adapter(private val context: Context, checkList: MutableList<Check>):
     ArrayAdapter<Check>(context,R.layout.listitem,checkList) {

     @SuppressLint("SetTextI18n")
     override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
              val check = getItem(position)
              var view = convertView
                  if (view == null) view = LayoutInflater.from(context).inflate(R.layout.listitem,parent,false)

                   val name_item = view?.findViewById<TextView>(R.id.name_item)
                   val weight_item = view?.findViewById<TextView>(R.id.weight_item)
                   val value_item = view?.findViewById<TextView>(R.id.value_item)


               name_item?.text = "Продукт:    ${check?.name}"
               weight_item?.text = "Цена:  ${check?.price}           Вес:  ${check?.weight}"
               value_item?.text = "ИТОГО:  ${check?.value}"



          return view!!
     }
}