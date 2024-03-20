package com.example.loginusingsqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DBHelper : SQLiteOpenHelper {
    lateinit var context: Context

    companion object {
        val DATABASE_NAME = "UserCredentials.db"
        val DATABASE_VERSION = 1

        val TABLE_NAME = "User_Information"
        val COLUMN_ID = "id"
        val COLUMN_NAME = "User_Name"
        val COLUMN_EMAIL = "User_Email"
        val COLUMN_PASSWORD = "User_Password"
        val COLUMN_GENDER = "User_Gender"
    }

    constructor(context: Context) : super(
        context,
        DATABASE_NAME,
        null,
        DATABASE_VERSION
    ) {  //checks if DATABASE EXISTS
        this.context = context
    }

    override fun onCreate(db: SQLiteDatabase?) { //This method is called when the database is created for the first time
        //The below (val query) defines an SQL query string that creates a table.
        // It uses string interpolation to insert table and column names into the query
        val query =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT, $COLUMN_EMAIL TEXT, $COLUMN_PASSWORD TEXT, $COLUMN_GENDER TEXT);" //This is the SQL CREATE TABLE statement that defines the structure of the table to be created. It specifies the table name and the columns

        db?.execSQL(query)//This line executes the SQL query (query) using the execSQL method of the SQLiteDatabase object (db).
        //The ?. operator is used to safely call execSQL only if db is not null.
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun registerUser(name: String, email: String, password: String, gender: String) {
        val db: SQLiteDatabase = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COLUMN_NAME, name)
        contentValues.put(COLUMN_EMAIL, email)
        contentValues.put(COLUMN_PASSWORD, password)
        contentValues.put(COLUMN_GENDER, gender)

        val result: Long = db.insert(TABLE_NAME, null, contentValues)

        if (result.toInt() == -1) {
            Toast.makeText(context, "Registration Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Registration Successfully Done!", Toast.LENGTH_SHORT).show()
        }
        db.close()  //Remember to always call db.close() when you're done using the database instance.
    }


    //CHECK USER LOGIN
    var loggedin: Boolean = false
    fun loginUser(email: String, password: String): Boolean {

        val db: SQLiteDatabase = this.readableDatabase
        val query =
            "SELECT * FROM $TABLE_NAME WHERE $COLUMN_EMAIL = '$email' AND $COLUMN_PASSWORD = '$password'"

        val cursor: Cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            loggedin = true
        } else {
            loggedin = false
        }
        return loggedin
    }

    fun getLoggedinUserDetails(email: String): ArrayList<UserModel> {

        var arraylist = ArrayList<UserModel>()

        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_EMAIL = '$email'";
        val db: SQLiteDatabase = this.readableDatabase  // It retrieves all data from a specified table (tableName) and returns a Cursor object representing the result set.

        val cursor: Cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {

            arraylist.add(UserModel(cursor.getString(1),cursor.getString(2), cursor.getString(4))) //name, email, gender

            //This method is called on an instance of SQLiteDatabase (db in this case) to execute a raw SQL query.
            //The first parameter query is a string containing the SQL query to be executed.
            //The second parameter is an array of strings representing the query arguments,
            //which can be null if the SQL query doesn't have any arguments.
            //rawQuery() - This method allows you to execute any SQL query directly without the need for predefined selection, update, deletion, or insertion methods.
        }
        return arraylist
    }
}

//moveToFirst() method moves the cursor to the first row of the result set.
//It returns true if the cursor is not empty and successfully moved to the first row;
// otherwise, it returns false.

//moveToNext() method moves the cursor to the next row of the result set.
//It returns true if there is a next row and successfully moved;
// otherwise, it returns false.