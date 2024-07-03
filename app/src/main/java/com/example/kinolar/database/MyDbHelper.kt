package com.example.kinolar.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.kinolar.models.Category
import com.example.kinolar.models.Film
import com.example.kinolar.myinterfase.MyPlan
import java.util.Locale

const val VERSION = 1
const val NAME = "offline.db"

const val TABLE_NAME = "category"
const val CATEGORY_ID = "id"
const val CATEGORY_NAME = "name"

const val TABLE_FILM = "film"
const val FILM_ID = "id"
const val FILM_NAME = "name"
const val FILM_DATE = "year"
const val FILM_TYPE = "category"

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION),MyPlan  {

    override fun onCreate(db: SQLiteDatabase?) {
        val queryCategory = "create table $TABLE_NAME($CATEGORY_ID integer not null primary key autoincrement unique,$CATEGORY_NAME text not null )"
        val queryFilm = "create table $TABLE_FILM($FILM_ID integer not null primary key autoincrement unique,$FILM_NAME text not null,$FILM_DATE text not null,$FILM_TYPE integer not null,foreign key($FILM_TYPE)references $TABLE_NAME($CATEGORY_ID))"

        db?.execSQL(queryCategory)
        db?.execSQL(queryFilm)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun addFilm(film: Film) {
        val write = this.writableDatabase
        val contentValues=ContentValues()
        contentValues.put(FILM_ID,film.id)
        contentValues.put(FILM_NAME,film.name)
        contentValues.put(FILM_DATE,film.yili)
        contentValues.put(FILM_TYPE,film.category?.id)
        write.insert(TABLE_FILM,null,contentValues)
        write.close()
    }

    override fun addCategory(category: Category) {
        val write = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CATEGORY_ID,category.id)
        contentValues.put(CATEGORY_NAME,category.name)
        write.insert(TABLE_NAME,null,contentValues)
        write.close()
    }

    override fun getFilms(): ArrayList<Film> {
        val reader = this.readableDatabase
        val list=ArrayList<Film>()
        val query = "select * from $TABLE_FILM"
        val crusor=reader.rawQuery(query,null)
        if (crusor.moveToFirst()){
            do {
                val film=Film(crusor.getInt(0),crusor.getString(1),crusor.getString(2),getCategoryById(crusor.getInt(3)))
                list.add(film)
            }while (crusor.moveToNext())
        }
        return list
    }

    override fun getCategories(): ArrayList<Category> {
        val reader=this.readableDatabase
         val list=ArrayList<Category>()
        val query="select * from $TABLE_NAME"
        val crusor=reader.rawQuery(query,null)
        if (crusor.moveToFirst()){
            do {
                val category = Category(crusor.getInt(0),crusor.getString(1))
                list.add(category)
            }while (crusor.moveToNext())
        }
        return list
    }

    override fun editFilm(film: Film) {
        val editor = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(FILM_ID,film.id)
        contentValues.put(FILM_NAME,film.name)
        contentValues.put(FILM_DATE,film.yili)
        contentValues.put(FILM_TYPE,film.category?.id)
        editor.update(TABLE_FILM,contentValues,"$FILM_ID = ?", arrayOf(film.id.toString()))

    }

    override fun deleteFilm(film: Film) {
        val query = "select * from$TABLE_FILM where$FILM_ID=${film.id}"
        val db = this.writableDatabase
        val crusor = db.rawQuery(query,null)
        if (crusor.moveToFirst()){
            do {
                var film = Film(crusor.getInt(0),crusor.getString(1),crusor.getString(2),getCategoryById(crusor.getInt(3)))
                deleteOnlyFilm(film)
            }while (crusor.moveToNext())
        }
    }

    override fun deleteCategory(category: Category) {
        val database = this.readableDatabase
        database.delete(TABLE_NAME,"$CATEGORY_ID =?", arrayOf(category.id.toString()))
    }

    override fun getCategoryById(id: Int): Category {
        val db=this.readableDatabase
        val crusor = db.query(TABLE_NAME, arrayOf(CATEGORY_ID, CATEGORY_NAME),"$CATEGORY_ID = ?",
            arrayOf(id.toString()),null,null,null)
        crusor.moveToFirst()
        val category = Category(crusor.getInt(0),crusor.getString(1))
        return category
    }

    override fun deleteOnlyFilm(film: Film) {
        val db=this.writableDatabase
        db.delete(TABLE_FILM,"${FILM_ID} = ?", arrayOf(film.id.toString()))
        db.close()
    }

}
