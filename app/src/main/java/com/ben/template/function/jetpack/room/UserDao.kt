package com.ben.template.function.jetpack.room

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Room查询接口
 *
 * @author Benhero
 * @date   2019/10/31
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user")
    fun getUserList(): List<User>

    @Query("SELECT * FROM user WHERE name = :name")
    fun getUser(name: String): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserList(list: List<User>)
}
