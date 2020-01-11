package br.com.teste.common.extensions

import java.text.SimpleDateFormat
import java.util.*

const val DD_MM_YYYY = "dd/MM/yyyy"

fun Date.applyFormat(format: String = DD_MM_YYYY): String {
    return SimpleDateFormat(format, Locale.getDefault()).format(this)
}