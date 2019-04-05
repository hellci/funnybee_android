package app.junhyounglee.android.funnybee.app.domain

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.junhyounglee.android.funnybee.app.domain.model.Post
import app.junhyounglee.android.funnybee.app.domain.model.PostDao
import app.junhyounglee.android.funnybee.app.domain.model.User
import app.junhyounglee.android.funnybee.app.domain.model.UserDao

/**
 * Create: 9/03/19
 * Author: Jun Hyoung Lee
 * Email: niceguy0315@hotmail.com
 */
/*
 * reviewer comment
 * - harjot:
 *      please consider the standards for explanation comments
 */
@Database(entities = [User::class, Post::class], version = 1)
abstract class FunnyBeeDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun postDao(): PostDao

    companion object {
        @Volatile
        private var INSTANCE: FunnyBeeDatabase? = null

        fun getDatabase(context: Context): FunnyBeeDatabase {
            val temp = INSTANCE
            temp?.let {
                return@getDatabase it
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FunnyBeeDatabase::class.java,
                    "FunnyBeeDatabase"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}