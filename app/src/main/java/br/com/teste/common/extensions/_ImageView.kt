package br.com.teste.common.extensions

import android.widget.ImageView
import br.com.teste.R
import com.bumptech.glide.Glide

fun ImageView.loadImage(imageUrl: String?) {
    Glide.with(this.context)
        .load(imageUrl)
        .placeholder(R.drawable.ic_person_)
        .into(this)
}