package com.vbanjan.newsnext

import java.io.Serializable

class Source(val id: String, val name: String, val description: String) : Serializable {

    val sourceId: String = id
    val sourceName: String = name
    val sourceDesciption: String = description

    fun getID(): String {
        return id;
    }

    override fun toString(): String {
        return "Source(id='$id', name='$name', description='$description')\n"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Source

        if (id != other.id) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (sourceId != other.sourceId) return false
        if (sourceName != other.sourceName) return false
        if (sourceDesciption != other.sourceDesciption) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + sourceId.hashCode()
        result = 31 * result + sourceName.hashCode()
        result = 31 * result + sourceDesciption.hashCode()
        return result
    }

}