package com.thales.minhaestante.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.thales.minhaestante.AppContainer
import com.thales.minhaestante.R
import com.thales.minhaestante.data.model.Book
import com.thales.minhaestante.data.model.ReadingStatus
import com.thales.minhaestante.databinding.FragmentBookDetailBinding
import com.thales.minhaestante.databinding.ItemGenreChipBinding
import com.thales.minhaestante.ui.common.applySystemBarsPadding
import com.thales.minhaestante.ui.common.tintAsCover

class BookDetailFragment : Fragment() {

    private var _binding: FragmentBookDetailBinding? = null
    private val binding get() = checkNotNull(_binding) { "Binding acessado fora do ciclo de vida da View" }

    private val repository = AppContainer.bookRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBookDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailScroll.applySystemBarsPadding(bottom = true)

        val book = arguments?.getString(ARG_BOOK_ID)?.let(repository::getBook) ?: return
        bindBook(book)
        binding.statusSelector.onStatusChanged = { newStatus -> changeStatus(book.id, newStatus) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindBook(book: Book) = with(binding) {
        coverContainer.tintAsCover(book.id)
        bookTitle.text = book.title
        bookSubtitle.text = book.subtitle
        bookSubtitle.isVisible = !book.subtitle.isNullOrBlank()
        bookAuthor.text = book.author

        val notInformed = getString(R.string.not_informed)
        infoYear.infoLabel.setText(R.string.label_year)
        infoYear.infoValue.text = book.publicationYear?.toString() ?: notInformed
        infoPages.infoLabel.setText(R.string.label_pages)
        infoPages.infoValue.text = book.pageCount?.toString() ?: notInformed
        infoPublisher.infoLabel.setText(R.string.label_publisher)
        infoPublisher.infoValue.text = book.publisher ?: notInformed

        val rating = book.rating
        ratingBar.isVisible = rating != null
        if (rating != null) {
            ratingBar.rating = rating
            ratingText.text = getString(R.string.rating_value, rating)
        } else {
            ratingText.setText(R.string.no_rating)
        }

        genresLabel.isVisible = book.genres.isNotEmpty()
        genreGroup.isVisible = book.genres.isNotEmpty()
        genreGroup.removeAllViews()
        book.genres.forEach { genre ->
            val chip = ItemGenreChipBinding.inflate(layoutInflater, genreGroup, false).root
            chip.text = genre
            genreGroup.addView(chip)
        }

        synopsis.text = book.synopsis ?: getString(R.string.no_synopsis)
        statusSelector.setStatus(book.status)
    }

    private fun changeStatus(bookId: String, newStatus: ReadingStatus) {
        val updated = repository.updateStatus(bookId, newStatus) ?: return
        val result = Bundle().apply { putString(RESULT_NEW_STATUS, updated.status.name) }
        setFragmentResult(REQUEST_STATUS_CHANGED, result)
    }

    companion object {
        const val REQUEST_STATUS_CHANGED = "request_status_changed"
        const val RESULT_NEW_STATUS = "result_new_status"
        private const val ARG_BOOK_ID = "arg_book_id"

        fun newInstance(bookId: String): BookDetailFragment =
            BookDetailFragment().apply {
                arguments = Bundle().apply { putString(ARG_BOOK_ID, bookId) }
            }
    }
}
