/*
* App to fetch list of most popular tv programs from yle in a user selected time frame.
* 3rd party libs in use:
*   - OkHttp for http requests
*   - Coil for downloading images
*   - Gson for serializing
* */

package com.example.poptvapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView_main.layoutManager = LinearLayoutManager(this)
        recyclerView_main.visibility = View.INVISIBLE

        ArrayAdapter.createFromResource(
            this,
            R.array.timeSpan,
            android.R.layout.simple_spinner_item
        ).also {adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            timeSpanSpinner.adapter = adapter
        }

        timeSpanSpinner.onItemSelectedListener = this
    }

    override fun onNothingSelected(parent: AdapterView<*>?) { }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        getList(position)
    }

    // Get list of top programs from yle and store them in programs singleton. Takes position of selected item from spinner as parameter.
    private fun getList(pos: Int) {
        val url = "https://external.api.yle.fi/v1/"
        val key = "app_id=87621d2e&app_key=297e1284b7697fffe496700d353bacf7"
        val timeFrame = arrayOf("24h", "week", "month")
        val request = Request.Builder().url(url + "programs/items.json?&order=playcount.${timeFrame[pos]}:desc&limit=10&" + key).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                Programs.program.clear()
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val data = gson.fromJson(body, MainFeed::class.java)
                data.data.forEach{
                    Programs.program.add(it)
                }
                runOnUiThread{
                    recyclerView_main.adapter = MainAdapter()
                    progressBar.visibility = View.INVISIBLE
                    recyclerView_main.visibility = View.VISIBLE
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                println("fetch failed.")
            }
        })
    }

}


