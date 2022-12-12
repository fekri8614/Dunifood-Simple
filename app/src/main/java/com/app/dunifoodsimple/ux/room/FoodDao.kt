package com.app.dunifoodsimple.ux.room

import androidx.room.*
import com.app.dunifoodsimple.ux.dataclass.Food

@Dao
interface FoodDao {

    @Insert
    fun insertFood(food: Food)

    @Insert
    fun insertAllFood(data: List<Food>)

    @Update
    fun updateFood(food: Food)

    @Delete
    fun deleteFood(food: Food)

    @Query("DELETE FROM table_food")
    fun deleteAllFood()

    @Query("SELECT * FROM table_food")
    fun getAllFood(): List<Food>

    @Query("SELECT * FROM table_food WHERE txtSubject LIKE '%' || :searching || '%'")
    fun searchFoods( searching: String ): List<Food>

}