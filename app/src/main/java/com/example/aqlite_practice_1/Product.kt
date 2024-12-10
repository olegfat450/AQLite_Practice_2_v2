package com.example.aqlite_practice_1

class Product(val name: String,val price: Double) {

    companion object{

        val product = arrayOf(Product("ПРОДУКТ", 0.0),Product("Груша",30.0), Product("Арбуз",95.5), Product("Авокадо",340.0),

                              Product("Виноград",195.0),Product("Дыня",120.0),Product("Яблоко",95.0),Product("Банан",105.0))


    }


}