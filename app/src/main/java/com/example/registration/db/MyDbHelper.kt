package com.example.registration.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.registration.constant.MyObject.ADDRESS
import com.example.registration.constant.MyObject.COUNTRY
import com.example.registration.constant.MyObject.DB_NAME
import com.example.registration.constant.MyObject.DB_TABLE_NAME
import com.example.registration.constant.MyObject.DB_VERSION
import com.example.registration.constant.MyObject.ID
import com.example.registration.constant.MyObject.IMAGE
import com.example.registration.constant.MyObject.PASSWORD
import com.example.registration.constant.MyObject.USER_NAME
import com.example.registration.constant.MyObject.USER_NUMBER
import com.example.registration.models.MyShablon

class MyDbHelper(context: Context):SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION),MyDbHelperInterface{
    override fun onCreate(db: SQLiteDatabase?) {
        val userTable =
            "create table $DB_TABLE_NAME ($ID integer not null primary key autoincrement unique,$USER_NAME text not null,$USER_NUMBER text not null,$COUNTRY text not null,$ADDRESS text not null,$PASSWORD text not null,$IMAGE text not null)"
        db?.execSQL(userTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}


    override fun addUser(myShablon: MyShablon) {
        val dataBase = this.writableDatabase
        val contentvalues = ContentValues()
        contentvalues.put(USER_NAME,myShablon.name)
        contentvalues.put(USER_NUMBER,myShablon.number)
        contentvalues.put(ADDRESS,myShablon.address)
        contentvalues.put(COUNTRY,myShablon.country)
        contentvalues.put(PASSWORD,myShablon.password)
        contentvalues.put(IMAGE,myShablon.image)
        dataBase.insert(DB_TABLE_NAME,null,contentvalues)
        dataBase.close()
    }

    override fun getAllUser(): ArrayList<MyShablon> {
        val list = ArrayList<MyShablon>()
        val database = this.readableDatabase
        val query = "select * from $DB_TABLE_NAME "
        val cursor = database.rawQuery(query,null)

        if (cursor.moveToFirst()){
            do {
                val myShablon = MyShablon(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
                )
                list.add(myShablon)
            }while (cursor.moveToNext())
        }
        return list
    }
}