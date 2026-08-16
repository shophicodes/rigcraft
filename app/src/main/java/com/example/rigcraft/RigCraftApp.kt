package com.example.rigcraft

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RigCraftApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}