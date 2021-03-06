package com.example.library.features.bookdetail.presentation

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.library.R
import com.example.library.core.common.AppConstants.BookDetail.BUNDLE_KEY_BOOK_ID
import com.example.library.core.common.AppConstants.BookDetail.DateFormat.BOOK_DETAIL_DATE_FORMAT
import com.example.library.core.framework.BaseActivity
import com.example.library.core.utils.formatTo
import com.example.library.core.utils.toDate
import com.example.library.databinding.ActivityBookDetailBinding
import com.example.library.features.booklist.data.entity.BookEntity
import com.squareup.picasso.Picasso

class BookDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityBookDetailBinding
    private lateinit var bookDetailVM: BookDetailVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initVM()
        getExtrasFromIntent()
        bookDetailVM.getBookDetail()
    }

    private fun getExtrasFromIntent() {
        bookDetailVM.bookId = intent.getStringExtra(BUNDLE_KEY_BOOK_ID)
    }

    private fun initVM() {
        bookDetailVM = getViewModel(BookDetailVM::class.java)
        bookDetailVM.getBookDetailLiveData().observe(this,
            Observer<BookEntity> { bookEntity ->  showBookDetail(bookEntity)})
        bookDetailVM.getFailureLiveData().observe(this,
            Observer<Throwable> { throwable -> handleError(throwable)})
    }

    private fun showBookDetail(bookEntity: BookEntity) {
        val notFoundString: String = getString(R.string.not_found)
        supportActionBar?.title = bookEntity.title ?: notFoundString
        if(bookEntity.imageUrl!!.isNotEmpty()) {
            Picasso.get().load(bookEntity.imageUrl)
                .placeholder(R.drawable.placeholder_missing_image)
                .error(R.drawable.placeholder_missing_image)
                .into(binding.image)
        } else {
            binding.image.setImageResource(R.drawable.placeholder_missing_image)
        }

        binding.title.text = getString(R.string.title_label, bookEntity.title ?: notFoundString)
        binding.author.text = getString(R.string.author_label, genericUtility.generateAuthorFullName(
            bookEntity.authorFirstName ?: notFoundString,
            bookEntity.authorLastName ?: notFoundString
        ))
        binding.category.text = getString(R.string.category_label, bookEntity.category ?: notFoundString)
        binding.date.text = getString(R.string.date_label, bookEntity.creationDate?.toDate()?.formatTo(BOOK_DETAIL_DATE_FORMAT))
        binding.publisher.text = getString(R.string.publisher_label, bookEntity.publisher ?: notFoundString)
        binding.pages.text = getString(R.string.page_number_label, bookEntity.pages.toString())
        binding.isbn.text = getString(R.string.isbn_label, bookEntity.isbn ?: notFoundString)
        binding.description.text = bookEntity.description ?: notFoundString
    }
}
