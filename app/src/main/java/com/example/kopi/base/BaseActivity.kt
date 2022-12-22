package com.example.kopi.base

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity() {

    internal lateinit var binding: V

    @get:LayoutRes
    protected abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)
        onCreated(savedInstanceState)

        var actionBar = getSupportActionBar()

        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    protected abstract fun onCreated(instance: Bundle?)

    abstract fun backPressedAction()

    fun showToastMessage(message: String, isPositive: Boolean = false) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}