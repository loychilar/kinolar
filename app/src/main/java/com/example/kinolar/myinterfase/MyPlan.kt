package com.example.kinolar.myinterfase

import com.example.kinolar.models.Category
import com.example.kinolar.models.Film

interface MyPlan {
    fun addFilm(film: Film)
    fun addCategory(category: Category)
    fun getFilms():ArrayList<Film>
    fun getCategories():ArrayList<Category>
    fun editFilm(film: Film)
    fun deleteFilm(film: Film)
    fun deleteCategory(category: Category)
    fun getCategoryById(int: Int): Category?
    fun deleteOnlyFilm(film: Film)
}