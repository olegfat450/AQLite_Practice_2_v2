package com.example.aqlite_practice_1

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context,factory: SQLiteDatabase.CursorFactory?): SQLiteOpenHelper(context,DATABASE_NAME,factory,DATABASE_VERSION) {

             companion object{
                 private val DATABASE_NAME = "CHECK_DATABASE"
                 private val DATABASE_VERSION = 1
                         val TABLE_NAME = "check_table"
                       //  val KEY_ID = "id"
                         val KEY_NAME = "name"
                         val KEY_PRICE = "price"
                         val KEY_WEIGHT = "weight"
                         val KEY_VALUE = "value" }


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(db: SQLiteDatabase?) {
           val query = ("CREATE TABLE " + TABLE_NAME + " (" +
                     //  KEY_ID + " INTEGER PRIMARY KEY, " +
                       KEY_NAME + " TEXT, " +
                       KEY_PRICE + " TEXT, " +
                       KEY_WEIGHT + " TEXT, " +
                       KEY_VALUE + " TEXT " + ")")
                    db?.execSQL(query) }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
              db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
              onCreate(db) }

          fun addCheck(check: Check){

              val db = this.writableDatabase
              val values = ContentValues()
              values.put(KEY_NAME,check.name)
              values.put(KEY_PRICE,check.price)
              values.put(KEY_WEIGHT,check.weight)
              values.put(KEY_VALUE,check.value)
              db.insert(TABLE_NAME,null,values)
              db.close() }

          fun removeAll(){ val db = this.writableDatabase; db.delete(TABLE_NAME,null,null)}

         @SuppressLint("Range", "SuspiciousIndentation")
         fun readCheck(): MutableList<Check>{

             val listCheck: MutableList<Check> = mutableListOf()
             val selectQuery = "SELECT * FROM $TABLE_NAME"
             val db = this.readableDatabase
             var cursor: Cursor? = null
                            try { cursor = db.rawQuery(selectQuery,null) } catch (e: SQLException ) { db.execSQL(selectQuery); return listCheck }

                 var name: String
                 var price: String
                 var weight: String
                 var value: String

                 if (cursor.moveToFirst()){
                     do{
                          name = cursor.getString(cursor.getColumnIndex("name"))
                          price = cursor.getString(cursor.getColumnIndex("price"))
                          weight = cursor.getString(cursor.getColumnIndex("weight"))
                          value = cursor.getString(cursor.getColumnIndex("value"))

                          listCheck.add(Check(name,price,weight,value))

                     } while ( cursor.moveToNext())
                 }
                             return listCheck
         }















}