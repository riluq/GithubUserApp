package com.riluq.githubuserapp.data.source.local.room

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.riluq.githubuserapp.data.source.local.entity.FavoriteEntity

@Dao
interface GithubUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteEntity: FavoriteEntity): Long

    @Delete
    suspend fun delete(favoriteEntity: FavoriteEntity): Int

    @Delete
    suspend fun update(favoriteEntity: FavoriteEntity): Int

    @Query("SELECT * from favorite ORDER by id DESC")
    fun getAllFavorite(): DataSource.Factory<Int, FavoriteEntity>

    @Query("SELECT * FROM favorite WHERE username = :username")
    fun getFavoriteByUsername(username: String): LiveData<FavoriteEntity>

    @Query("SELECT * from favorite ORDER by id DESC")
    fun getAllFavoriteProvider(): Cursor

    @Query("SELECT * FROM favorite WHERE username = :username")
    fun getFavoriteByUsernameProvider(username: String): Cursor

    @Query("DELETE FROM favorite WHERE id = :id")
    suspend fun delete(id: Long): Int
}