package com.example.library.features.booklist.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.library.R
import com.example.library.core.common.AppConstants.BookDetail.DateFormat.BOOK_DETAIL_DATE_FORMAT
import com.example.library.core.utils.GenericUtility
import com.example.library.core.utils.formatTo
import com.example.library.core.utils.toDate
import com.example.library.databinding.LayoutBookCardBinding
import com.example.library.features.booklist.data.entity.BookEntity
import com.squareup.picasso.Picasso

class BookListAdapter(
    private val books: ArrayList<BookEntity>,
    private val listener: BookListAdapterListener<String>,
    private val genericUtility: GenericUtility
) :
    RecyclerView.Adapter<BookListAdapter.BookListViewHolder>(),
    Filterable{

    private val booksFilter: BooksFilter = BooksFilter(this)
    private val auxBooks = ArrayList<BookEntity>()

    init {
        auxBooks.addAll(books)
    }

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

    fun updateData(bookList: List<BookEntity>) {
        books.clear()
        books.addAll(bookList)
        auxBooks.clear()
        auxBooks.addAll(bookList)
        notifyDataSetChanged()
    }

    class BookListViewHolder(
        private val binding: LayoutBookCardBinding,
        private val listener: BookListAdapterListener<String>,
        private val context: Context,
        private val genericUtility: GenericUtility
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(book: BookEntity) {
            if (book.imageUrl!!.isNotEmpty()) {
                Picasso.get().load(book.imageUrl)
                    .placeholder(R.drawable.placeholder_missing_image)
                    .error(R.drawable.placeholder_missing_image)
                    .into(binding.bookImage)
            } else {
                binding.bookImage.setImageResource(R.drawable.placeholder_missing_image)
            }

            val notFoundString: String = context.getString(R.string.not_found)
            binding.bookTitle.text =
                context.getString(R.string.title_label, book.title ?: notFoundString)
            binding.bookAuthor.text = context.getString(R.string.author_label, genericUtility.generateAuthorFullName(
                book.authorFirstName ?: notFoundString,
                book.authorLastName ?: notFoundString
            ))
            binding.bookCategory.text =
                context.getString(R.string.category_label, book.category ?: notFoundString)
            binding.root.setOnClickListener { listener.onBookCardClick(book.id)}
        }
    }

    class BooksFilter(
        private val adapter: BookListAdapter
    ) : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            adapter.books.clear()
            val filterResults = FilterResults()

            if (constraint?.isEmpty()!!) {
                adapter.books.addAll(adapter.auxBooks)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim()
                for (book in adapter.auxBooks) {
                    if (evaluateBook(filterPattern, book)) {
                        adapter.books.add(book)
                    }
                }
            }
            filterResults.values = adapter.books
            filterResults.count = adapter.books.size
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            adapter.notifyDataSetChanged()
        }

        private fun evaluateBook(filterPattern: String, book: BookEntity) : Boolean {
            val formattedDate = book.creationDate?.toDate()?.formatTo(BOOK_DETAIL_DATE_FORMAT)
            val tempBook = BookEntity("",
                    book.isbn,
                    book.title,
                    book.category,
                    book.description,
                    "",
                    book.authorFirstName,
                    book.authorLastName,
                    formattedDate,
                    book.publisher,
                    book.pages)
            return tempBook.toString().contains(filterPattern, true)
        }

    }

    interface BookListAdapterListener<T> {
        fun onBookCardClick(arg: T)
    }

    override fun getFilter(): Filter = booksFilter
}