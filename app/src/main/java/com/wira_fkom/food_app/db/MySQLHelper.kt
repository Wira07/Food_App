package com.wira_fkom.food_app.db

import android.os.StrictMode
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object MySQLHelper {
    private const val URL = "jdbc:mysql://localhost:3306/food_app"
    private const val USER = "root"
    private const val PASSWORD = ""

    init {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }

    @Throws(SQLException::class)
    fun getConnection(): Connection {
        return DriverManager.getConnection(URL, USER, PASSWORD)
    }
}