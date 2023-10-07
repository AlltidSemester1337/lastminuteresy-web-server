package com.example.adapter.http

class TableServiceImpl : TableService {
    private var freeTables = 5

    override fun getNumberOfFreeTables(): Int {
        return freeTables
    }

    override fun bookTable() {
        freeTables--
    }

    override fun unbookTable() {
        freeTables++
    }
}