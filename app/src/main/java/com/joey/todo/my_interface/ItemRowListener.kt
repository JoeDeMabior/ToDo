package com.joey.todo.my_interface

interface ItemRowListener {
    fun modifyItemState(id: String, done: Boolean)
    fun onItemDelete(id: String)
}
