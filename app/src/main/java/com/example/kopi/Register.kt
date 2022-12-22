package com.example.kopi
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout


class Register : AppCompatActivity() {
    var editTextUserName: EditText? = null
    var editTextEmail: EditText? = null
    var editTextPassword: EditText? = null
    var textInputLayoutUserName: TextInputLayout? = null
    var textInputLayoutEmail: TextInputLayout? = null
    var textInputLayoutPassword: TextInputLayout? = null
    var buttonRegister: Button? = null
    var sqliteHelper: SqliteHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        sqliteHelper = SqliteHelper(this)
        initTextViewLogin()
        initViews()
        val btn = findViewById<TextView>(R.id.textViewLogin)

        btn.setOnClickListener {
            startActivity(
                Intent(
                    this@Register,
                    Login::class.java
                )
            )
        }
        buttonRegister!!.setOnClickListener {
            if (validate()) {
                val UserName = editTextUserName!!.text.toString()
                val Email = editTextEmail!!.text.toString()
                val Password = editTextPassword!!.text.toString()

                if (!sqliteHelper!!.isEmailExists(Email)) {
                    sqliteHelper!!.addUser(User(null, UserName, Email, Password))
                    Log.d("login","Berhasil" );
                    Snackbar.make(
                        buttonRegister!!,
                        "User created successfully! Please Login ",
                        Snackbar.LENGTH_LONG
                    ).show()
                    Handler().postDelayed(
                        {
                            val intent = Intent(this@Register, Login::class.java)
                            startActivity(intent)
                            finish()
                        },
                        Snackbar.LENGTH_LONG.toLong()
                    )
                } else {

                    Snackbar.make(
                        buttonRegister!!,
                        "User already exists with same email ",
                        Snackbar.LENGTH_LONG
                    ).show()

                }
            }
        }
    }

    private fun initTextViewLogin() {
        val textViewLogin = findViewById<View>(R.id.textViewLogin) as TextView
        textViewLogin.setOnClickListener { finish() }
    }

    private fun initViews() {
        editTextEmail = findViewById<View>(R.id.editTextEmail) as EditText
        editTextPassword = findViewById<View>(R.id.editTextPassword) as EditText
        editTextUserName = findViewById<View>(R.id.editTextUserName) as EditText
        textInputLayoutEmail = findViewById<View>(R.id.textInputLayoutEmail) as TextInputLayout
        textInputLayoutPassword =
            findViewById<View>(R.id.textInputLayoutPassword) as TextInputLayout
        textInputLayoutUserName =
            findViewById<View>(R.id.textInputLayoutUserName) as TextInputLayout
        buttonRegister = findViewById<View>(R.id.buttonRegister) as Button
    }

    fun validate(): Boolean {
        var valid = false

        val UserName = editTextUserName!!.text.toString()
        val Email = editTextEmail!!.text.toString()
        val Password = editTextPassword!!.text.toString()

        if (UserName.isEmpty()) {
            valid = false
            textInputLayoutUserName!!.error = "Please enter valid username!"
        } else {
            if (UserName.length > 5) {
                valid = true
                textInputLayoutUserName!!.error = null
            } else {
                valid = false
                textInputLayoutUserName!!.error = "Username is to short!"
            }
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false
            textInputLayoutEmail!!.error = "Please enter valid email!"
        } else {
            valid = true
            textInputLayoutEmail!!.error = null
        }

        if (Password.isEmpty()) {
            valid = false
            textInputLayoutPassword!!.error = "Please enter valid password!"
        } else {
            if (Password.length > 5) {
                valid = true
                textInputLayoutPassword!!.error = null
            } else {
                valid = false
                textInputLayoutPassword!!.error = "Password is to short!"
            }
        }
        return valid
    }
}