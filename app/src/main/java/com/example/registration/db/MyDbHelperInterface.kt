package com.example.registration.db

import com.example.registration.models.MyShablon

interface MyDbHelperInterface {
    fun addUser(myShablon: MyShablon)
    fun getAllUser():ArrayList<MyShablon>
}