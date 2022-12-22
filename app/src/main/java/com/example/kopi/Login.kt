package com.example.kopi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout


class Login: AppCompatActivity() {
    var editTextEmail: EditText? = null
    var editTextPassword: EditText? = null
    var textInputLayoutEmail: TextInputLayout? = null
    var textInputLayoutPassword: TextInputLayout? = null
    var buttonLogin: Button? = null
    var sqliteHelper: SqliteHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sqliteHelper = SqliteHelper(this)
        initCreateAccountTextView()
        initViews()
        val btn = findViewById<Button>(R.id.buttonRegister)

        btn.setOnClickListener {
            startActivity(
                Intent(
                    this@Login,
                    Register::class.java
                )
            )
        }

        buttonLogin!!.setOnClickListener {
            if (validate()) {
                val Email = editTextEmail!!.text.toString()
                val Password = editTextPassword!!.text.toString()
                val currentUser = sqliteHelper!!.Authenticate(
                    User(
                        null,
                        null,
                        Email,
                        Password
                    )
                )
                if (currentUser != null) {
                    Snackbar.make(
                        buttonLogin!!,
                        "Successfully Logged in!",
                        Snackbar.LENGTH_LONG
                    ).show()
                    val intent = Intent(this@Login, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {

                    Snackbar.make(
                        buttonLogin!!,
                        "Failed to log in , please try again",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    private fun initCreateAccountTextView() {
        val textViewCreateAccount = findViewById<View>(R.id.textViewCreateAccount) as TextView
        textViewCreateAccount.text =
            fromHtml("<font color='#ffffff'>I don't have account yet. </font><font color='#0c0099'></font>")
        textViewCreateAccount.setOnClickListener {
            val intent = Intent(this@Login, Register::class.java)
            startActivity(intent)
        }
    }

    private fun initViews() {
        editTextEmail = findViewById<View>(R.id.editTextEmail) as EditText
        editTextPassword = findViewById<View>(R.id.editTextPassword) as EditText
        textInputLayoutEmail = findViewById<View>(R.id.textInputLayoutEmail) as TextInputLayout
        textInputLayoutPassword =
            findViewById<View>(R.id.textInputLayoutPassword) as TextInputLayout
        buttonLogin = findViewById<View>(R.id.buttonLogin) as Button
    }

    fun validate(): Boolean {
        var valid = false
        val Email = editTextEmail!!.text.toString()
        val Password = editTextPassword!!.text.toString()


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

    companion object {
        fun fromHtml(html: String?): Spanned {
            val result: Spanned
            result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(html)
            }
            return result
        }
    }
}