package com.example.registration.models

import android.widget.EditText

class MyShablon {
    var id:Int? = null
    var name:String? = null
    var number:String? = null
    var country:Int? = null
    var address:String? = null
    var password:String? = null
    var image:String? = null

    constructor(
        id: Int?,
        name: String?,
        number: String?,
        country: Int?,
        address: String?,
        password: String?,
        image:String?,
    ) {
        this.id = id
        this.name = name
        this.number = number
        this.country = country
        this.address = address
        this.password = password
        this.image = image
    }

    constructor(
        name: String?,
        number: String?,
        country: Int?,
        address: String?,
        password: String?,
        image: String?,
    ) {
        this.name = name
        this.number = number
        this.country = country
        this.address = address
        this.password = password
        this.image = image
    }

    constructor()


}