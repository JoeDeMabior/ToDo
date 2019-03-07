package com.joey.todo.model

class Item {

    companion object Factory {
        fun create(): Item = Item()
    }

    var id: String? = null
    var text: String? = null
    var done: Boolean? = false

}