package com.dimsum.creationalpattern.builder

class BuilderDemo {

    companion object {
        fun test() {
            val mealBuilder = MealBuilder()

            val vegMeal = mealBuilder.prepareVegMeal()
            println("Veg Meal")
            vegMeal.showItems()
            println("Total Cost: " + vegMeal.getCost())

            val nonVegMeal = mealBuilder.prepareNonVegMeal()
            println("Non-Veg Meal")
            nonVegMeal.showItems()
            println("Total Cost: " + nonVegMeal.getCost())
        }
    }
}