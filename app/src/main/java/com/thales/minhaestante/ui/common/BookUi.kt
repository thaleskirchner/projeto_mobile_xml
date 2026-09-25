package com.thales.minhaestante.ui.common

import android.content.res.ColorStateList
import android.content.res.Resources
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.color.MaterialColors
import com.thales.minhaestante.R
import com.thales.minhaestante.data.model.Book
import com.thales.minhaestante.data.model.ReadingStatus
import androidx.appcompat.R as AppCompatR
import com.google.android.material.R as MaterialR

@get:StringRes
val ReadingStatus.labelRes: Int
    get() = when (this) {
        ReadingStatus.WANT_TO_READ -> R.string.status_want_to_read
        ReadingStatus.READING -> R.string.status_reading
        ReadingStatus.READ -> R.string.status_read
    }

fun TextView.showStatus(status: ReadingStatus) {
    val (containerAttr, contentAttr) = when (status) {
        ReadingStatus.WANT_TO_READ ->
            MaterialR.attr.colorSecondaryContainer to MaterialR.attr.colorOnSecondaryContainer
        ReadingStatus.READING ->
            MaterialR.attr.colorTertiaryContainer to MaterialR.attr.colorOnTertiaryContainer
        ReadingStatus.READ ->
            AppCompatR.attr.colorPrimary to MaterialR.attr.colorOnPrimary
    }
    setText(status.labelRes)
    backgroundTintList = ColorStateList.valueOf(MaterialColors.getColor(this, containerAttr))
    setTextColor(MaterialColors.getColor(this, contentAttr))
}

private val coverColors = intArrayOf(
    R.color.cover_1,
    R.color.cover_2,
    R.color.cover_3,
    R.color.cover_4,
    R.color.cover_5,
    R.color.cover_6,
)

fun View.tintAsCover(bookId: String) {
    val colorRes = coverColors[bookId.hashCode().mod(coverColors.size)]
    backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
}

fun Book.metaLine(resources: Resources): String? =
    listOfNotNull(
        publicationYear?.toString(),
        pageCount?.let { resources.getQuantityString(R.plurals.page_count, it, it) },
    ).takeIf { it.isNotEmpty() }?.joinToString(separator = " · ")
