package com.vbanjan.newsnext

import java.io.Serializable

class Source(val id: String, val name: String, val description: String) : Serializable {

    val sourceId: String = id
    val sourceName: String = name
    val sourceDesciption: String = description

    fun getID() : String{
        return id;
    }
    override fun toString(): String {
        return "Source(id='$id', name='$name', description='$description')\n"
    }

}