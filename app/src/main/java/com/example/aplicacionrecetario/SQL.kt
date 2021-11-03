package com.example.aplicacionrecetario

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase.CursorFactory

class SQL (context: Context, name:String, factory:CursorFactory?, version: Int): SQLiteOpenHelper(context, name, factory, version){

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table bebidas (nombreBebida text primary key, ingredientes text, procedimiento text)")
        db?.execSQL("create table postres (nombrePostre text primary key, ingredientes text, procedimiento text)")
        db?.execSQL("create table platosFuertes (nombrePlatoFuerte text primary key, ingredientes text, procedimiento text)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }


}