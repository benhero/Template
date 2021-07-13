package com.ben.template.function.jetpack.room

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 数据Bean
 *
 * @author Benhero
 * @date   2019/10/31
 */
@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    val name: String,
    var age: Int = 0
)