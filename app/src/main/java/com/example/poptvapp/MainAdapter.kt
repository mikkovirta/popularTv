package com.example.poptvapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.api.get

import coil.api.load
import kotlinx.android.synthetic.main.list_row.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okio.blackholeSink


class MainAdapter: RecyclerView.Adapter<CustomViewHolder>() {
    val IMG_URL = "https://images.cdn.yle.fi/image/upload/w_300,h_150,c_fit/"
    val PLACEHOLDER_URL = "https://images.cdn.yle.fi/image/upload/w_40,h_20,c_fit/"
    override fun getItemCount(): Int {
        return Programs.program.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.list_row, parent, false)
        return CustomViewHolder(layoutInflater, 0)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.id = position
        val program = Programs.program[position]
        val count = position + 1
        holder.view.textView_count.text = count.toString() + "."
        holder.view.textView_title.text = program.title?.fi
        holder.view.textView_description.text = program.description?.fi
        val url = IMG_URL + program.image?.id + ".png"
        val placeholderURL = PLACEHOLDER_URL + program.image?.id + ".png"
        GlobalScope.launch { val thumb = Coil.get(placeholderURL)
            holder.view.progress_imageBar.visibility = View.INVISIBLE
            holder.view.imageView_main.load(url) {
                crossfade(true)
                placeholder(thumb)
            }
        }
    }
}

class  CustomViewHolder(val view: View, var id: Int?): RecyclerView.ViewHolder(view) {
    companion object {
        val ID_KEY = "ID"
    }
    init {
        view.setOnClickListener {
            val intent = Intent(view.context, SingleActivity::class.java )
            intent.putExtra(ID_KEY, id)
            view.context.startActivity(intent)
        }
    }
}