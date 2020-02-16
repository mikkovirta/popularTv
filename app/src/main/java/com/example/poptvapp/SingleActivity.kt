package com.example.poptvapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import coil.Coil
import coil.api.get
import coil.api.load
import kotlinx.android.synthetic.main.list_row.view.*
import kotlinx.android.synthetic.main.single.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SingleActivity : AppCompatActivity() {
    val IMG_URL = "https://images.cdn.yle.fi/image/upload/w_300,h_150,c_fit/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single)
        val id = intent.getIntExtra(CustomViewHolder.ID_KEY, -1)
        println(id)
        val program = Programs.program[id]

        supportActionBar?.title = program.title?.fi

        singleAge.text = program.contentRating?.title?.fi
        val jakso = getString(R.string.jakso) + program.title?.fi
        val sarja = getString(R.string.sarja) + program.transmissionTitle?.fi
        singleTitle.text = jakso
        singleDescription.text = program.description?.fi
        singleSeriesTitle.text = sarja
        singleSeriesDescription.text = program.partOfSeries?.description?.fi
        val url = IMG_URL + program.image?.id + ".png"

        if(program.image?.id != null) {
            singleImage.load(url)
        } else {
            singleImage.load(R.drawable.no_image)
        }


    }
}