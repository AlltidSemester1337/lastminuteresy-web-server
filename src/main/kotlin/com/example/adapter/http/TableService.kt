package com.example.adapter.http

interface TableService {
    fun getNumberOfFreeTables() : Int
    fun bookTable()

    // TODO: Should this be moved somewhere else? Might only be applicable for demo
    fun unbookTable()
}