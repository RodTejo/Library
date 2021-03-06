package com.example.library.features.booklist.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.library.R
import com.example.library.core.utils.GenericUtility
import com.example.library.databinding.LayoutBookCardBinding
import com.example.library.features.booklist.data.entity.BookEntity
import com.squareup.picasso.Picasso

class BookListAdapter(
    private val books: List<BookEntity>,
    private val listener: BookListAdapterListener<String>,
    private val genericUtility: GenericUtility
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
            parent.context,
            genericUtility
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
        private val context: Context,
        private val genericUtility: GenericUtility
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(book: BookEntity) {
            Picasso.get().load(book.imageUrl)
                .placeholder(R.drawable.placeholder_missing_image)
                .error(R.drawable.placeholder_missing_image)
                .into(binding.bookImage)
            val notFoundString: String = context.getString(R.string.not_found)
            binding.bookTitle.text =
                context.getString(R.string.title_label, book.title ?: notFoundString)
            binding.bookAuthor.text = context.getString(R.string.author_label, genericUtility.generateAuthorFullName(
                book.authorFirstName ?: notFoundString,
                book.authorLastName ?: notFoundString
            ))
            binding.bookCategory.text =
                context.getString(R.string.category_label, book.category ?: notFoundString)
            binding.root.setOnClickListener { listener.onClick(book.id)}
        }
    }

    interface BookListAdapterListener<T> {
        fun onClick(arg: T)
    }
}