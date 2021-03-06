package com.example.library.features.bookdetail.presentation

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.library.R
import com.example.library.core.common.AppConstants
import com.example.library.core.common.AppConstants.BookDetail.BUNDLE_KEY_BOOK_ID
import com.example.library.core.common.AppConstants.BookDetail.DateFormat.BOOK_DETAIL_DATE_FORMAT
import com.example.library.core.framework.BaseActivity
import com.example.library.core.utils.GenericUtility
import com.example.library.databinding.ActivityBookDetailBinding
import com.example.library.features.booklist.data.entity.BookEntity
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class BookDetailActivity : BaseActivity() {

    @Inject
    lateinit var genericUtility: GenericUtility

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
        Picasso.get().load(bookEntity.imageUrl)
            .placeholder(R.drawable.placeholder_missing_image)
            .error(R.drawable.placeholder_missing_image)
            .into(binding.image)
        binding.title.text = getString(R.string.title_label, bookEntity.title ?: notFoundString)
        binding.author.text = getString(R.string.author_label, genericUtility.generateAuthorFullName(
            bookEntity.authorFirstName ?: notFoundString,
            bookEntity.authorLastName ?: notFoundString
        ))
        binding.category.text = getString(R.string.category_label, bookEntity.category ?: notFoundString)
        binding.date.text = getString(R.string.date_label, bookEntity.creationDate?.toDate()?.formatTo(BOOK_DETAIL_DATE_FORMAT))
        binding.pages.text = getString(R.string.page_number_label, bookEntity.pages.toString())
        binding.isbn.text = getString(R.string.isbn_label, bookEntity.isbn ?: notFoundString)
        binding.description.text = bookEntity.description ?: notFoundString
    }

    private fun handleError(throwable: Throwable?) {
        genericUtility.showErrorMessage(throwable?.message, this)
    }

    /**
     * Extension function used to transform default utc date obtained from server
     * to a Date object which will be formatted to our desired date format on
     * Date.formatTo extension function
      */
    private fun String.toDate(dateFormat: String = AppConstants.BookDetail.DateFormat.UTC_FORMAT,
                      timeZone: TimeZone = TimeZone.getTimeZone(AppConstants.BookDetail.DateFormat.UTC_TIMEZONE)): Date? {
        val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
        parser.timeZone = timeZone
        return parser.parse(this)
    }

    /**
     * Extension function that generates a date string in our given date format from a
     * Date object
     */
    private fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
        formatter.timeZone = timeZone
        return formatter.format(this)
    }


}