package com.example.kopi.shoppinglist

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.contains
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kopi.R
import com.example.kopi.base.BaseActivity
import com.example.kopi.data.db.ShoppingDatabase
import com.example.kopi.data.db.entities.ShoppingItem
import com.example.kopi.databinding.ActivityStockBinding
import com.example.kopi.other.ItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_shopping.*
import kotlinx.android.synthetic.main.activity_shopping.fab
import kotlinx.android.synthetic.main.activity_shopping.placeholder
import kotlinx.android.synthetic.main.activity_shopping.rvShoppingItems
import kotlinx.android.synthetic.main.activity_shopping.totaltxt
import kotlinx.android.synthetic.main.activity_stock.*
import kotlinx.android.synthetic.main.adapter_item.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

private const val TAG = "StockActivity"

@AndroidEntryPoint
class StockActivity : BaseActivity<ActivityStockBinding>() {


    private val viewModel: StockViewModel by viewModels()
    private val disposable = CompositeDisposable()


    override val layoutRes: Int
        get() = R.layout.activity_stock

    override fun onCreated(instance: Bundle?) {

        val adapter = ItemAdapter(listOf(), viewModel)

        rvShoppingItems.layoutManager = LinearLayoutManager(this)
        rvShoppingItems.adapter = adapter




        viewModel.getAllShoppingItems().observe(this, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()

            if (it.isEmpty()) {
                placeholder.text = "Keranjang Belanja Kosong"
            }

        })



        viewModel.getAllShoppingItems().observe(this, Observer {
            if (it.isEmpty()) {
                placeholder.text = "Keranjang Belanja Kosong"
            }
            // Calculate Subtotal
            totaltxt.text = "Total Bayar: Rp. ${it.map { it.count * it.price }.sum()}"
            Log.d(TAG, "Total daftar barang: ${it}")
        })
    }

    override fun backPressedAction() {
        onBackPressed()
    }


}