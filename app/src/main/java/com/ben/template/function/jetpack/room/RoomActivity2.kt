package com.ben.template.function.jetpack.room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ben.template.R
import kotlinx.android.synthetic.main.activity_room2.*

/**
 * Room页面2
 *
 * @author Benhero
 * @date   2019/10/31
 */
class RoomActivity2 : AppCompatActivity() {
    private val names = listOf("Jame", "June", "Carry", "Tom", "Tim", "Jake", "Zim", "Fake", "Kris")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room2)

        button5.setOnClickListener {
            addUser()
        }
    }

    private fun addUser() {
        Thread {
            val user = User(System.currentTimeMillis().toInt(), names.random(), 1)
            AppDatabase.getInstance(this.applicationContext).getUserDao().insertUser(user)
        }.start()
    }
}