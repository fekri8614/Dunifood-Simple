package com.app.dunifoodsimple.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dunifoodsimple.databinding.ActivityMainBinding
import com.app.dunifoodsimple.databinding.DialogAddNewItemBinding
import com.app.dunifoodsimple.databinding.DialogDeleteItemBinding
import com.app.dunifoodsimple.databinding.DialogUpdateItemBinding
import com.app.dunifoodsimple.ux.adapter.FoodAdapter
import com.app.dunifoodsimple.ux.dataclass.Food
import com.app.dunifoodsimple.ux.room.FoodDao
import com.app.dunifoodsimple.ux.room.FoodDatabase

/*
 * implement Room:
 * 1.Entity
 * 2.Dao
 * */

const val BASE_URL_IMAGE = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food"

class MainActivity : AppCompatActivity(), FoodAdapter.FoodEvents {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: FoodAdapter
    private lateinit var foodDao: FoodDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foodDao = FoodDatabase.getDatabase(this).foodDao

        val sharedPreferences = getSharedPreferences("duniFood", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("first_run", true)) {
            firstRun()
            sharedPreferences.edit().putBoolean("first_run", false).apply()
        }

        showAllData()
        // remove all data from database
        binding.btnRemoveAllFoods.setOnClickListener {
            removeAllData()
        }

        binding.btnAddNewFood.setOnClickListener {
            addNewFood()
        }
    }

    private fun addNewFood() {

        val dialog = AlertDialog.Builder(this).create()

        val dialogBinding = DialogAddNewItemBinding.inflate(layoutInflater)
        dialog.setView(dialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogBinding.dialogBtnDone.setOnClickListener {

            if (
                dialogBinding.dialogEdtFoodName.text!!.isNotEmpty() &&
                dialogBinding.dialogEdtFoodCity.text!!.isNotEmpty() &&
                dialogBinding.dialogEdtFoodPrice.text!!.isNotEmpty() &&
                dialogBinding.dialogEdtFoodDistance.text!!.isNotEmpty()
            ) {

                val txtName = dialogBinding.dialogEdtFoodName.text.toString()
                val txtCity = dialogBinding.dialogEdtFoodCity.text.toString()
                val txtPrice = dialogBinding.dialogEdtFoodPrice.text.toString()
                val txtDistance = dialogBinding.dialogEdtFoodDistance.text.toString()
                val txtRatingNumber: Int = (1..150).random()
                val ratingBarStar: Float = (1..5).random().toFloat()

                val randomForUrl = (1 until 12).random()
                val urlPic = "$BASE_URL_IMAGE$randomForUrl.jpg"

                val newFood = Food(
                    txtSubject = txtName,
                    txtCity = txtCity,
                    txtPrice = txtPrice,
                    txtDistance = txtDistance,
                    urlImage = urlPic,
                    rating = ratingBarStar,
                    numOfRating = txtRatingNumber
                )

                myAdapter.addFood(newFood)
                foodDao.insertFood( newFood )

                dialog.dismiss()
                binding.recyclerviewMain.scrollToPosition(0)

            } else {
                Toast.makeText(this, "Please, fill out the blanks", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun removeAllData() {
        foodDao.deleteAllFood() // remove all data
        showAllData() // show data
    }

    private fun firstRun() {
        val foodList = arrayListOf(
            Food(
                txtSubject = "Hamburger",
                txtPrice = "15",
                txtDistance = "3",
                txtCity = "Isfahan, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
                numOfRating = 20,
                rating = 4.5f
            ),
            Food(
                txtSubject = "Grilled fish",
                txtPrice = "20",
                txtDistance = "2.1",
                txtCity = "Tehran, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
                numOfRating = 10,
                rating = 4f
            ),
            Food(
                txtSubject = "Lasania",
                txtPrice = "40",
                txtDistance = "1.4",
                txtCity = "Isfahan, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
                numOfRating = 30,
                rating = 2f
            ),
            Food(
                txtSubject = "pizza",
                txtPrice = "10",
                txtDistance = "2.5",
                txtCity = "Zahedan, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg",
                numOfRating = 80,
                rating = 1.5f
            ),
            Food(
                txtSubject = "Sushi",
                txtPrice = "20",
                txtDistance = "3.2",
                txtCity = "Mashhad, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
                numOfRating = 200,
                rating = 3f
            ),
            Food(
                txtSubject = "Roasted Fish",
                txtPrice = "40",
                txtDistance = "3.7",
                txtCity = "Jolfa, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
                numOfRating = 50,
                rating = 3.5f
            ),
            Food(
                txtSubject = "Fried chicken",
                txtPrice = "70",
                txtDistance = "3.5",
                txtCity = "NewYork, USA",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
                numOfRating = 70,
                rating = 2.5f
            ),
            Food(
                txtSubject = "Vegetable salad",
                txtPrice = "12",
                txtDistance = "3.6",
                txtCity = "Berlin, Germany",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
                numOfRating = 40,
                rating = 4.5f
            ),
            Food(
                txtSubject = "Grilled chicken",
                txtPrice = "10",
                txtDistance = "3.7",
                txtCity = "Beijing, China",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",
                numOfRating = 15,
                rating = 5f
            ),
            Food(
                txtSubject = "Baryooni",
                txtPrice = "16",
                txtDistance = "10",
                txtCity = "Ilam, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",
                numOfRating = 28,
                rating = 4.5f
            ),
            Food(
                txtSubject = "Ghorme Sabzi",
                txtPrice = "11.5",
                txtDistance = "7.5",
                txtCity = "Karaj, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",
                numOfRating = 27,
                rating = 5f
            ),
            Food(
                txtSubject = "Rice",
                txtPrice = "12.5",
                txtDistance = "2.4",
                txtCity = "Shiraz, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
                numOfRating = 35,
                rating = 2.5f
            ),
        )
        foodDao.insertAllFood(foodList)
    }

    private fun showAllData() {
        val foodData = foodDao.getAllFood()

        myAdapter = FoodAdapter(ArrayList(foodData), this)
        binding.recyclerviewMain.adapter = myAdapter
        binding.recyclerviewMain.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun onFoodClicked(food: Food, position: Int) {

        val dialog = AlertDialog.Builder(this).create()

        val dialogUpdateItemBinding = DialogUpdateItemBinding.inflate(layoutInflater)
        dialog.setView(dialogUpdateItemBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogUpdateItemBinding.dialogEdtFoodName.setText(food.txtSubject)
        dialogUpdateItemBinding.dialogEdtFoodCity.setText(food.txtCity)
        dialogUpdateItemBinding.dialogEdtFoodDistance.setText(food.txtDistance)
        dialogUpdateItemBinding.dialogEdtFoodPrice.setText(food.txtPrice)

        dialogUpdateItemBinding.dialogUpdateBtnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogUpdateItemBinding.dialogUpdateBtnUpdate.setOnClickListener {

            if (
                dialogUpdateItemBinding.dialogEdtFoodName.length() > 0 &&
                dialogUpdateItemBinding.dialogEdtFoodCity.length() > 0 &&
                dialogUpdateItemBinding.dialogEdtFoodDistance.length() > 0 &&
                dialogUpdateItemBinding.dialogEdtFoodPrice.length() > 0
            ) {

                val txtName = dialogUpdateItemBinding.dialogEdtFoodName.text.toString()
                val txtPrice = dialogUpdateItemBinding.dialogEdtFoodPrice.text.toString()
                val txtDistance = dialogUpdateItemBinding.dialogEdtFoodDistance.text.toString()
                val txtCity = dialogUpdateItemBinding.dialogEdtFoodCity.text.toString()

                //create new food to add to recyclerview
                val newFood = Food(
                    txtSubject = txtName,
                    txtPrice = txtPrice,
                    txtDistance = txtDistance,
                    txtCity = txtCity,
                    urlImage = food.urlImage,
                    numOfRating = food.numOfRating,
                    rating = food.rating
                )

                // update item :
                myAdapter.updateFood(newFood, position)

                dialog.dismiss()
            } else {
                Toast.makeText(this, "Please, fill out all :-)", Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun onFoodLonClicked(food: Food, position: Int) {

        val dialog = AlertDialog.Builder(this).create()
        val dialogDeleteBinding = DialogDeleteItemBinding.inflate(layoutInflater)
        dialog.setView(dialogDeleteBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogDeleteBinding.dialogBtnDeleteCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogDeleteBinding.dialogBtnDeleteYes.setOnClickListener {
            dialog.dismiss()

            myAdapter.deleteFood(food, position)
            foodDao.deleteFood(food)
        }
    }
}