package com.example.kopi.other

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kopi.R
import com.example.kopi.data.db.entities.ShoppingItem
import com.example.kopi.shoppinglist.StockViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_stock.view.*
import kotlinx.android.synthetic.main.adapter_item.*
import kotlinx.android.synthetic.main.shopping_item.view.*
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.adapter_item.view.*
import kotlinx.android.synthetic.main.shopping_item.view.ivMinus
import kotlinx.android.synthetic.main.shopping_item.view.ivPlus
import kotlinx.android.synthetic.main.shopping_item.view.tvAmount
import kotlinx.android.synthetic.main.shopping_item.view.tvName
import kotlinx.android.synthetic.main.shopping_item.view.tvPrice
import android.app.AlertDialog
import android.content.DialogInterface

class ItemAdapter(
    var items: List<ShoppingItem>,
    private val viewModel: StockViewModel,
     var jumlahReaktif: Int = 0

) : RecyclerView.Adapter<ItemAdapter.ShoppingViewHolder>() {
    private val disposable = CompositeDisposable()


    // Mendeklarasikan Subject untuk memancarkan jumlah pengunjung
    private val jumlahSubject: BehaviorSubject<Int> = BehaviorSubject.createDefault(0)

    // Untuk mengakses Subject dari Activity
    fun getJumlahSubject(): BehaviorSubject<Int> {
        return jumlahSubject
    }

    fun tambahReaktif(): Unit {
        jumlahReaktif += 1
        jumlahSubject.onNext(jumlahReaktif)
    }
    fun kurangReaktif(): Unit {
        jumlahReaktif -= 1
        jumlahSubject.onNext(jumlahReaktif)
    }
    fun setToZero(): Unit {
        jumlahReaktif = 0
        jumlahSubject.onNext(jumlahReaktif)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_item, parent, false)
        return ShoppingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        var curShoppingItem = items[position]

//        getJumlahSubject().subscribe {
//            holder.itemView.tvAmount.setText(it.toString())
//        }.addTo(disposable)
//        getJumlahSubject().subscribe {
//            curShoppingItem.count=it
//        }.addTo(disposable)
//        viewModel.upsert(curShoppingItem)


        holder.itemView.tvName.text = curShoppingItem.name
        holder.itemView.tvStock.text="(${curShoppingItem.quantity})"
        holder.itemView.tvAmount.text = "${curShoppingItem.count}"
        holder.itemView.tvPrice.text = "Price: ${curShoppingItem.price}"



        holder.itemView.ivMinus.setOnClickListener {
            if(curShoppingItem.count!=0) {

                curShoppingItem.count--
//               kurangReaktif()
//                getJumlahSubject().subscribe {
//                    curShoppingItem.count=it
//                }.addTo(disposable)
                viewModel.upsert(curShoppingItem)
            }
        }

        holder.itemView.ivPlus.setOnClickListener {
            if(curShoppingItem.quantity!=0) {

                curShoppingItem.count++
            //               tambahReaktif()
            //            getJumlahSubject().subscribe {
            //                curShoppingItem.count=it
            //            }.addTo(disposable)
                viewModel.upsert(curShoppingItem)
            }

        }
        holder.itemView.ivPay.setOnClickListener {
            curShoppingItem.quantity -=curShoppingItem.count
            setToZero()
            getJumlahSubject().subscribe {
                curShoppingItem.count=it
            }.addTo(disposable)
            viewModel.upsert(curShoppingItem)
        }


//        holder.itemView.ivEdit.setOnClickListener {
//
//        }


    }


    inner class ShoppingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}