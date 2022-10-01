package com.example.waltzLib

import com.github.mikephil.charting.data.Entry

class DynamicList(size: Int) {
    private val list: ArrayList<Entry> = ArrayList()
    private var maxSize: Int = size

    fun add(data: Entry){
        if (this.list.size < this.maxSize) {
            list.add(data)
            return
        }

        list.removeAt(0)
        list.add(data)
    }

    fun getList(): ArrayList<Entry> {
        return this.list
    }
}