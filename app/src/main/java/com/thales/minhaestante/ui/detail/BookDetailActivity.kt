package com.thales.minhaestante.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.thales.minhaestante.AppContainer
import com.thales.minhaestante.R
import com.thales.minhaestante.data.model.ReadingStatus
import com.thales.minhaestante.databinding.ActivityBookDetailBinding
import com.thales.minhaestante.ui.common.applySystemBarsPadding

class BookDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookDetailBinding
    private val repository = AppContainer.bookRepository

    private var initialStatus: ReadingStatus? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.applySystemBarsPadding(top = true, horizontal = true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        val book = intent.getStringExtra(EXTRA_BOOK_ID)?.let(repository::getBook)
        if (book == null) {
            Toast.makeText(this, R.string.book_not_found, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val startStatus = ReadingStatus.fromName(savedInstanceState?.getString(KEY_INITIAL_STATUS))
            ?: book.status
        initialStatus = startStatus
        publishResult(book.id, startStatus, currentStatus = book.status)

        supportFragmentManager.setFragmentResultListener(
            BookDetailFragment.REQUEST_STATUS_CHANGED,
            this,
        ) { _, result ->
            val newStatus = ReadingStatus.fromName(result.getString(BookDetailFragment.RESULT_NEW_STATUS))
            if (newStatus != null) publishResult(book.id, startStatus, newStatus)
        }

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.detailContainer, BookDetailFragment.newInstance(book.id))
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        initialStatus?.let { outState.putString(KEY_INITIAL_STATUS, it.name) }
    }

    private fun publishResult(bookId: String, startStatus: ReadingStatus, currentStatus: ReadingStatus) {
        if (currentStatus == startStatus) {
            setResult(RESULT_CANCELED)
            return
        }
        val data = Intent()
            .putExtra(EXTRA_BOOK_ID, bookId)
            .putExtra(EXTRA_PREVIOUS_STATUS, startStatus.name)
            .putExtra(EXTRA_NEW_STATUS, currentStatus.name)
        setResult(RESULT_OK, data)
    }

    companion object {
        const val EXTRA_BOOK_ID = "com.thales.minhaestante.extra.BOOK_ID"
        const val EXTRA_PREVIOUS_STATUS = "com.thales.minhaestante.extra.PREVIOUS_STATUS"
        const val EXTRA_NEW_STATUS = "com.thales.minhaestante.extra.NEW_STATUS"

        private const val KEY_INITIAL_STATUS = "initial_status"

        fun newIntent(context: Context, bookId: String): Intent =
            Intent(context, BookDetailActivity::class.java)
                .putExtra(EXTRA_BOOK_ID, bookId)
    }
}
