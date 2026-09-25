package com.thales.minhaestante.ui.list

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.thales.minhaestante.AppContainer
import com.thales.minhaestante.R
import com.thales.minhaestante.data.model.Book
import com.thales.minhaestante.data.model.ReadingStatus
import com.thales.minhaestante.databinding.ActivityMainBinding
import com.thales.minhaestante.ui.common.applySystemBarsPadding
import com.thales.minhaestante.ui.common.labelRes
import com.thales.minhaestante.ui.detail.OpenBookDetailContract
import com.thales.minhaestante.ui.detail.StatusChange

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val repository = AppContainer.bookRepository
    private val bookAdapter = BookAdapter(onBookClick = ::openBookDetail)

    private var selectedFilter: ReadingStatus? = null

    private val bookDetailLauncher = registerForActivityResult(OpenBookDetailContract()) { change ->
        if (change != null) onStatusChanged(change)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.applySystemBarsPadding(top = true, horizontal = true)
        binding.bookList.applySystemBarsPadding(bottom = true)

        selectedFilter = ReadingStatus.fromName(savedInstanceState?.getString(KEY_SELECTED_FILTER))

        binding.bookList.adapter = bookAdapter
        setupFilterChips()
        renderBooks()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_SELECTED_FILTER, selectedFilter?.name)
    }

    private fun setupFilterChips() {
        binding.filterGroup.check(chipIdFor(selectedFilter))
        binding.filterGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            selectedFilter = checkedIds.firstOrNull()?.let(::filterForChip)
            renderBooks()
        }
    }

    private fun renderBooks() {
        val allBooks = repository.getBooks()
        val visibleBooks = repository.getBooks(selectedFilter)
        bookAdapter.submitList(visibleBooks)

        val filter = selectedFilter
        binding.summaryText.text = if (filter == null) {
            resources.getQuantityString(R.plurals.books_total, allBooks.size, allBooks.size)
        } else {
            getString(R.string.books_filtered, visibleBooks.size, allBooks.size)
        }

        val isEmpty = visibleBooks.isEmpty()
        binding.bookList.isVisible = !isEmpty
        binding.emptyState.root.isVisible = isEmpty
        if (isEmpty) {
            binding.emptyState.emptyMessage.text = if (filter == null) {
                getString(R.string.empty_message_all)
            } else {
                getString(R.string.empty_message_filtered, getString(filter.labelRes))
            }
        }
    }

    private fun openBookDetail(book: Book) {
        bookDetailLauncher.launch(book.id)
    }

    private fun onStatusChanged(change: StatusChange) {
        renderBooks()
        val book = repository.getBook(change.bookId) ?: return

        val message = getString(
            R.string.status_changed_message,
            book.title,
            getString(change.newStatus.labelRes),
        )
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setAction(R.string.action_undo) {
                repository.updateStatus(change.bookId, change.previousStatus)
                renderBooks()
            }
            .show()
    }

    private fun chipIdFor(filter: ReadingStatus?): Int = when (filter) {
        null -> R.id.filterAll
        ReadingStatus.WANT_TO_READ -> R.id.filterWantToRead
        ReadingStatus.READING -> R.id.filterReading
        ReadingStatus.READ -> R.id.filterRead
    }

    private fun filterForChip(chipId: Int): ReadingStatus? = when (chipId) {
        R.id.filterWantToRead -> ReadingStatus.WANT_TO_READ
        R.id.filterReading -> ReadingStatus.READING
        R.id.filterRead -> ReadingStatus.READ
        else -> null
    }

    private companion object {
        const val KEY_SELECTED_FILTER = "selected_filter"
    }
}
