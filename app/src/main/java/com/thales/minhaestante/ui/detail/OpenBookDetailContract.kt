package com.thales.minhaestante.ui.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.thales.minhaestante.data.model.ReadingStatus

data class StatusChange(
    val bookId: String,
    val previousStatus: ReadingStatus,
    val newStatus: ReadingStatus,
)

class OpenBookDetailContract : ActivityResultContract<String, StatusChange?>() {

    override fun createIntent(context: Context, input: String): Intent =
        BookDetailActivity.newIntent(context, bookId = input)

    override fun parseResult(resultCode: Int, intent: Intent?): StatusChange? {
        if (resultCode != Activity.RESULT_OK || intent == null) return null

        val bookId = intent.getStringExtra(BookDetailActivity.EXTRA_BOOK_ID) ?: return null
        val previous = ReadingStatus.fromName(
            intent.getStringExtra(BookDetailActivity.EXTRA_PREVIOUS_STATUS)
        ) ?: return null
        val new = ReadingStatus.fromName(
            intent.getStringExtra(BookDetailActivity.EXTRA_NEW_STATUS)
        ) ?: return null

        return StatusChange(bookId, previousStatus = previous, newStatus = new)
    }
}
