package com.example.library.features.addbook.presentation

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.DatePicker
import androidx.lifecycle.Observer
import com.example.library.R
import com.example.library.core.common.AppConstants.BookDetail.DateFormat.BOOK_DETAIL_DATE_FORMAT
import com.example.library.core.common.AppConstants.BookDetail.DateFormat.UTC_FORMAT
import com.example.library.core.framework.BaseActivity
import com.example.library.core.utils.formatTo
import com.example.library.databinding.ActivityAddBookBinding
import com.example.library.features.booklist.BookDownloadStates
import com.example.library.features.booklist.data.model.AuthorModel
import java.util.*

class AddBookActivity : BaseActivity(),
    View.OnClickListener,
    DatePickerDialog.OnDateSetListener{

    private lateinit var binding: ActivityAddBookBinding
    private lateinit var addBookVM: AddBookVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initVM()
        initViews()
    }

    private fun initVM() {
        addBookVM = getViewModel(AddBookVM::class.java)
        addBookVM.getBookAdditionStateLiveData().observe(this,
            Observer<BookDownloadStates> { bookAdditionState -> handleBookAdditionState(bookAdditionState) })
        addBookVM.getFailureLiveData().observe(this,
            Observer { throwable -> handleError(throwable) })
    }

    override fun handleError(throwable: Throwable?) {
        super.handleError(throwable)
        uiStateDefault()
    }

    private fun handleBookAdditionState(bookAdditionStates: BookDownloadStates) {
        when(bookAdditionStates) {
            BookDownloadStates.DOWNLOAD_INIT -> uiStateDownloading()
            BookDownloadStates.DOWNLOAD_SUCCESS -> handleBookAdditionSuccess()
        }
    }

    private fun initViews() {
        supportActionBar?.title = getString(R.string.add_book)
        binding.addBookDate.setOnClickListener(this)
        binding.addBookButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.addBookDate.id -> showDatePicker()
            binding.addBookButton.id -> sendNewBook()
        }
    }

    private fun showDatePicker() {
        val datePickerFragment = DatePickerFragment(this)
        datePickerFragment.show(supportFragmentManager, "datePicker")
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        addBookVM.getNewBook().creationDate = calendar.time.formatTo(UTC_FORMAT)
        binding.addBookDate.setText(calendar.time.formatTo(BOOK_DETAIL_DATE_FORMAT))
    }

    private fun sendNewBook() {
        val isbn = binding.addBookIsbn.text.toString()
        if (isbn.length < 10) {
            genericUtility.showMessage(getString(R.string.wrong_isbn), this)
            return
        }
        addBookVM.getNewBook().isbn = isbn
        addBookVM.getNewBook().title = binding.addBookTitle.text.toString()
        addBookVM.getNewBook().author = generateAuthorModel(binding.addBookAuthor.text.toString())
        addBookVM.getNewBook().category = binding.addBookCategory.text.toString()
        val pages = binding.addBookPageNumber.text.toString()
        addBookVM.getNewBook().publisher = binding.addBookPublisher.text.toString()
        addBookVM.getNewBook().pages = if (pages.isNotEmpty()) pages.toLong() else 0
        addBookVM.getNewBook().description = binding.addBookDescription.text.toString()
        addBookVM.getNewBook().imageUrl = binding.addBookImage.text.toString()
        addBookVM.addBook()
    }

    private fun handleBookAdditionSuccess() {
        uiStateDefault()
        clearEditText()
        genericUtility.showMessage(getString(R.string.book_addition_success), this)
    }

    private fun uiStateDownloading() {
        binding.addBookProgressBar.visibility = View.VISIBLE
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun uiStateDefault() {
        binding.addBookProgressBar.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun clearEditText() {
        binding.addBookIsbn.setText("")
        binding.addBookTitle.setText("")
        binding.addBookAuthor.setText("")
        binding.addBookCategory.setText("")
        binding.addBookDate.setText("")
        binding.addBookPageNumber.setText("")
        binding.addBookDescription.setText("")
        binding.addBookImage.setText("")
    }

    private fun generateAuthorModel(authorName: String): AuthorModel {
        val author = AuthorModel()
        if (authorName.isEmpty()) {
            return author
        }

        val splitName: List<String> = authorName.split(" ")
        author.authorName = splitName[0]

        if(splitName.size < 2){
            author.authorLastName = ""
            return author
        }

        val authorLastName = StringBuilder()
        for (i in 1 until splitName.size - 1) {
            authorLastName.append(splitName[i]).append(" ")
        }
        author.authorLastName = authorLastName.toString().trim()
        return author
    }
}