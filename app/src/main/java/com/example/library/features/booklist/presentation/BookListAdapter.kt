package com.example.library.features.booklist.presentation

import android.content.Context
import android.icu.number.NumberFormatter.with
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.library.R
import com.example.library.databinding.LayoutBookCardBinding
import com.example.library.features.booklist.data.entity.BookEntity
import com.squareup.picasso.Picasso

class BookListAdapter(
    private val books: List<BookEntity>,
    private val listener: BookListAdapterListener<String>
) :
    RecyclerView.Adapter<BookListAdapter.BookListViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListViewHolder {
        return BookListViewHolder(
            LayoutBookCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener,
            parent.context
        )
    }

    override fun onBindViewHolder(holder: BookListViewHolder, position: Int) {
        holder.bind(books[position])
    }

    override fun getItemCount(): Int {
        return books.size
    }

    class BookListViewHolder(
        private val binding: LayoutBookCardBinding,
        private val listener: BookListAdapterListener<String>,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(book: BookEntity) {
            Picasso.get().load(book.imageUrl)
                .placeholder(R.drawable.placeholder_missing_image)
                .error(R.drawable.placeholder_missing_image)
                .into(binding.bookImage)
            val notFoundString: String = context.getString(R.string.not_found)
            binding.bookTitle.text = book.title ?: notFoundString
            binding.bookAuthor.text = book.authorFirstName ?: notFoundString + " " +book.authorLastName ?: notFoundString
            binding.bookCategory.text = book.category ?: notFoundString
            binding.root.setOnClickListener { listener.onClick(book.id)}
        }
    }

    interface BookListAdapterListener<T> {
        fun onClick(arg: T)
    }
}