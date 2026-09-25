package com.thales.minhaestante.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.thales.minhaestante.R
import com.thales.minhaestante.data.model.Book
import com.thales.minhaestante.databinding.ItemBookBinding
import com.thales.minhaestante.ui.common.metaLine
import com.thales.minhaestante.ui.common.showStatus
import com.thales.minhaestante.ui.common.tintAsCover

class BookAdapter(
    private val onBookClick: (Book) -> Unit,
) : ListAdapter<Book, BookAdapter.BookViewHolder>(BookDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding, onBookClick)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BookViewHolder(
        private val binding: ItemBookBinding,
        onBookClick: (Book) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        private var boundBook: Book? = null

        init {
            binding.root.setOnClickListener { boundBook?.let(onBookClick) }
        }

        fun bind(book: Book) {
            boundBook = book
            val resources = binding.root.resources

            binding.cover.tintAsCover(book.id)
            binding.bookTitle.text = book.title
            binding.bookAuthor.text = book.author

            val meta = book.metaLine(resources)
            binding.bookMeta.text = meta
            binding.bookMeta.isVisible = meta != null

            binding.statusLabel.showStatus(book.status)

            val rating = book.rating
            binding.ratingLabel.isVisible = rating != null
            if (rating != null) {
                binding.ratingLabel.text = resources.getString(R.string.rating_short, rating)
                binding.ratingLabel.contentDescription =
                    resources.getString(R.string.rating_content_description, rating)
            }
        }
    }

    private object BookDiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean =
            oldItem == newItem
    }
}
