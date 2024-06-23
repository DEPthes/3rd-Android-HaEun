package com.example.myapplication.ui

import android.app.Application
import com.example.myapplication.data.db.BookmarkDatabase

class GlobalApplication : Application() {
    companion object {
        lateinit var db : BookmarkDatabase
    }
    override fun onCreate() {
        super.onCreate()
        db = BookmarkDatabase.getInstance(this)
    }
}