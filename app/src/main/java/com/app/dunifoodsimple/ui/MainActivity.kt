package com.app.dunifoodsimple.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dunifoodsimple.databinding.ActivityMainBinding
import com.app.dunifoodsimple.databinding.DialogAddNewItemBinding
import com.app.dunifoodsimple.databinding.DialogDeleteItemBinding
import com.app.dunifoodsimple.databinding.DialogUpdateItemBinding
import com.app.dunifoodsimple.ux.adapter.FoodAdapter
import com.app.dunifoodsimple.ux.dataclass.Food

/*
* implement Room:
* 1.Entity
* 2.Dao
* */

class MainActivity : AppCompatActivity(), FoodAdapter.FoodEvents {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*
        * how to create recyclerview:
        *  1. create view of recyclerview in activity_main.xml
        *  2. create item for recyclerview
        *  3. create adapter and viewHolder for recyclerView
        *  4. set adapter to recyclerView and layoutManager
        *  */

        val foodList = arrayListOf<Food>(
            Food(
                txtSubject = "hamburger",
                txtPrice = "12",
                txtDistance = "13",
                txtCity = "Isfahan",
                urlImage = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Hamburger_%28black_bg%29.jpg/800px-Hamburger_%28black_bg%29.jpg",
                numOfRating = 20,
                rating = 2.6f
            ),
            Food(
                txtSubject = "Cake",
                txtPrice = "18",
                txtDistance = "20",
                txtCity = "Tehran",
                urlImage = "https://upload.wikimedia.org/wikipedia/commons/0/04/Pound_layer_cake.jpg",
                numOfRating = 30,
                rating = 4.12f
            ),
            Food(
                txtSubject = "Kebab",
                txtPrice = "20",
                txtDistance = "23",
                txtCity = "Ardabil",
                urlImage = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/16/Donner_Kebab%2C_Cologne%2C_Germany_%281057919169%29.jpg/1200px-Donner_Kebab%2C_Cologne%2C_Germany_%281057919169%29.jpg",
                numOfRating = 100,
                rating = 4.3f
            ),
            Food(
                txtSubject = "Cup Cake",
                txtPrice = "10",
                txtDistance = "18",
                txtCity = "Ardabil",
                urlImage = "https://www.noracooks.com/wp-content/uploads/2022/03/sq-3-500x500.jpg",
                numOfRating = 40,
                rating = 3.75f
            ),
            Food(
                txtSubject = "Sushi",
                txtPrice = "32",
                txtDistance = "90",
                txtCity = "Tehran",
                urlImage = "https://www.kikkoman.com/homecook/search/recipe/img/00005163.jpg",
                numOfRating = 20,
                rating = 4.5f
            ),
            Food(
                txtSubject = "Bread",
                txtPrice = "11",
                txtDistance = "29",
                txtCity = "Shanghai",
                urlImage = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/33/Fresh_made_bread_05.jpg/1200px-Fresh_made_bread_05.jpg",
                numOfRating = 20,
                rating = 4.5f
            ),
            Food(
                txtSubject = "Fatir",
                txtPrice = "20",
                txtDistance = "30",
                txtCity = "Pars-Abad",
                urlImage = "https://ifpnews.com/wp-content/uploads/2020/04/fatir-9.jpg",
                numOfRating = 80,
                rating = 5.0f
            ),
            Food(
                txtSubject = "Black Halva",
                txtPrice = "28",
                txtDistance = "50",
                txtCity = "Ardabil",
                urlImage = "https://m.media-amazon.com/images/I/51t-VhFIK7L.jpg",
                numOfRating = 50,
                rating = 5.0f
            ),
            Food(
                txtSubject = "Yogurt",
                txtPrice = "12",
                txtDistance = "20",
                txtCity = "Pars-Abad",
                urlImage = "https://s3.amazonaws.com/finecooking.s3.tauntonclud.com/app/uploads/2017/04/19000248/051141069-01-homemade-yogurt-recipe-main.jpg",
                numOfRating = 20,
                rating = 4.7f
            ),
            Food(
                txtSubject = "Gorme Sabzi",
                txtPrice = "30",
                txtDistance = "23",
                txtCity = "Shiraz",
                urlImage = "https://arga-mag.com/file/img/2019/10/Ghormeh-sabzi-recipe.jpg",
                numOfRating = 30,
                rating = 5.0f
            ),
            Food(
                txtSubject = "Milk",
                txtPrice = "12",
                txtDistance = "20",
                txtCity = "Tabriz",
                urlImage = "https://th-thumbnailer.cdn-si-edu.com/DxqNnKhkIiGNJ_qtxy86XeqxcgY=/1000x750/filters:no_upscale()/https://tf-cmsv2-smithsonianmag-media.s3.amazonaws.com/filer/6f/6e/6f6e0661-8a07-43f6-ba5c-94f0e5855dbe/istock_000005534054_large.jpg",
                numOfRating = 70,
                rating = 4.8f
            ),
            Food(
                txtSubject = "Spaghetti",
                txtPrice = "20",
                txtDistance = "10",
                txtCity = "Ardabil",
                urlImage = "https://veganwithgusto.com/wp-content/uploads/2021/05/speedy-spaghetti-arrabbiata-featured-e1649949762421.jpg",
                numOfRating = 30,
                rating = 4.8f
            )
        )
        myAdapter = FoodAdapter(foodList.clone() as ArrayList<Food>, this)
        binding.recyclerviewMain.adapter = myAdapter

        binding.recyclerviewMain.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        binding.btnAddNewFood.setOnClickListener {

            val dialog = AlertDialog.Builder(this).create()
            val dialogBinding = DialogAddNewItemBinding.inflate(layoutInflater)
            dialog.setView(dialogBinding.root)
            dialog.setCancelable(true)
            dialog.show()

            dialogBinding.dialogBtnDone.setOnClickListener {

                if (
                    dialogBinding.dialogEdtFoodName.length() > 0 &&
                    dialogBinding.dialogEdtFoodCity.length() > 0 &&
                    dialogBinding.dialogEdtFoodDistance.length() > 0 &&
                    dialogBinding.dialogEdtFoodPrice.length() > 0
                ) {

                    val txtName = dialogBinding.dialogEdtFoodName.text.toString()
                    val txtPrice = dialogBinding.dialogEdtFoodPrice.text.toString()
                    val txtDistance = dialogBinding.dialogEdtFoodDistance.text.toString()
                    val txtCity = dialogBinding.dialogEdtFoodCity.text.toString()
                    val txtRatingNumber: Int = (1..150).random()
                    val ratingBarStar: Float = (1..5).random().toFloat()

                    //for create random number with float --=>
//                    val min = 0f
//                    val max = 5f
//                    val random: Float = min + Random().nextFloat() * (max - min)

                    val randomForUrl = (0 until 12).random()
                    val urlPic = foodList[randomForUrl].urlImage

                    val newFood = Food(
                        txtSubject = txtName,
                        txtPrice = txtPrice,
                        txtDistance = txtDistance,
                        txtCity = txtCity,
                        urlImage = urlPic,
                        numOfRating = txtRatingNumber,
                        rating = ratingBarStar
                    )
                    myAdapter.addFood(newFood)

                    dialog.dismiss()

                    binding.recyclerviewMain.scrollToPosition(0)

                } else {
                    Toast.makeText(this, "Please, fill out all ;-)", Toast.LENGTH_LONG).show()
                }

            }

        }

        binding.edtSearch.addTextChangedListener { editTextInput ->

            if (editTextInput!!.isNotEmpty()) {
                // filter data 'h'
                val cloneList = foodList.clone() as ArrayList<Food>
                val filteredList = cloneList.filter { foodItem ->
                    foodItem.txtSubject.contains(editTextInput)
                }

                myAdapter.setData(ArrayList(filteredList))


            } else {
                // show our data :

                myAdapter.setData(foodList.clone() as ArrayList<Food>)

            }

        }

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

        }
    }

}