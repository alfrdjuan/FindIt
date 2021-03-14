package com.codewithjun.findit.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.codewithjun.findit.R
import com.codewithjun.findit.view.adapter.AutoCompleteAdapter
import com.codewithjun.findit.viewModel.SearchActivityViewModel
import kotlinx.android.synthetic.main.activity_search.*
import java.util.*

class SearchActivity : AppCompatActivity() {
    private lateinit var viewModel: SearchActivityViewModel
    private lateinit var adapter: AutoCompleteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        viewModel = ViewModelProvider(this).get(SearchActivityViewModel::class.java)

        search_field.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    autocomplete_recyclerview.visibility = View.GONE
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    autocomplete_recyclerview.visibility = View.GONE
                }

                override fun afterTextChanged(s: Editable?) {
                    val sharedPreferences =
                        getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
                    viewModel.getDeviceID(sharedPreferences)
                    viewModel.deviceID.observe(this@SearchActivity, {
                        Log.d("device id", "device id from sharedpreferences:$it ")
                        getPrediction(search_field.text.toString(), it, 1, "id", "country:id")
                    })
                    autocomplete_recyclerview.visibility = View.VISIBLE
                }

            }
        )

        autocomplete_recyclerview.layoutManager = LinearLayoutManager(this)

        adapter = AutoCompleteAdapter(this)
        autocomplete_recyclerview.adapter = adapter

        back_button.setOnClickListener {
            finish()
        }

        clear_button.setOnClickListener {
            search_field.text.clear()
        }

    }

    private fun getPrediction(
        input: String,
        sessiontoken: String,
        radius: Int,
        language: String,
        components: String
    ) {
        viewModel = ViewModelProvider(this).get(SearchActivityViewModel::class.java)
        viewModel.searchLocation(
            input,
            sessiontoken,
            radius,
            language,
            components,
            getString(R.string.google_maps_key)
        )

        viewModel.autocompleteResult.observe(this, {
            adapter.setResultList(it.predictions)
        })


    }
}