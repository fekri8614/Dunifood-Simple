package com.app.dunifoodsimple.ux.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.dunifoodsimple.ux.dataclass.Food

@Database(version = 1, exportSchema = false, entities = [Food::class])
abstract class FoodDatabase : RoomDatabase() {
    abstract val foodDao: FoodDao

    companion object {
        private var database: FoodDatabase? = null
        fun getDatabase(context: Context): FoodDatabase {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    FoodDatabase::class.java,
                    "myDatabase.db"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return database!!
        }
    }

}