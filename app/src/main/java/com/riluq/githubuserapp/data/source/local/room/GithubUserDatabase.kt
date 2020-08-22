package com.riluq.githubuserapp.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.riluq.githubuserapp.data.source.local.entity.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GithubUserDatabase : RoomDatabase() {
    abstract fun githubUserDao(): GithubUserDao

    companion object {
        const val DATABASE_NAME = "GithubUser.db"
        const val FAVORITE_TABLE_NAME = "favorite"
    }
}