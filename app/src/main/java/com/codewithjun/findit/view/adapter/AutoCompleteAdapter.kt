package com.codewithjun.findit.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codewithjun.findit.R
import com.codewithjun.findit.network.model.Prediction
import com.codewithjun.findit.view.MainActivity
import kotlinx.android.synthetic.main.adapter_autocomplete.view.*

class AutoCompleteAdapter(private val context: Context) :
    RecyclerView.Adapter<AutoCompleteAdapter.ViewHolder>() {

    private var list: List<Prediction> = ArrayList()

    fun setResultList(list: List<Prediction>) {
        this.list = list
        notifyDataSetChanged()
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val autocompleteName = v.autocomplete_name!!
        val autocompleteAddress = v.autocomplete_address!!
        val rootView = v.autocomplete_adapter_layout!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.adapter_autocomplete, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.autocompleteAddress.text = list[position].structured_formatting.secondary_text
        holder.autocompleteName.text = list[position].structured_formatting.main_text
        holder.rootView.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("place_id", list[position].place_id)
            context.startActivity(intent)
        }
    }
}

