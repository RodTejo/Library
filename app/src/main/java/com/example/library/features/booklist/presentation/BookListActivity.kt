package com.example.library.features.booklist.presentation

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.library.R
import com.example.library.core.common.AppConstants.BookDetail.BUNDLE_KEY_BOOK_ID
import com.example.library.core.framework.BaseActivity
import com.example.library.core.utils.GenericUtility
import com.example.library.databinding.ActivityBookListBinding
import com.example.library.features.addbook.presentation.AddBookActivity
import com.example.library.features.bookdetail.presentation.BookDetailActivity
import com.example.library.features.booklist.BookDownloadStates
import com.example.library.features.booklist.data.entity.BookEntity
import io.reactivex.exceptions.CompositeException
import javax.inject.Inject

class BookListActivity : BaseActivity(),
        BookListAdapter.BookListAdapterListener<String>,
        View.OnClickListener,
        TextWatcher{

    @Inject
    lateinit var genericUtility: GenericUtility

    private lateinit var binding: ActivityBookListBinding
    private lateinit var bookListVM: BookListVM
    private lateinit var adapter: BookListAdapter
    private var bookList: ArrayList<BookEntity> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initVM()
    }

    private fun initViews() {
        adapter = BookListAdapter(bookList, this, genericUtility)
        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
        binding.bookListRecyclerview.setHasFixedSize(true)
        binding.bookListRecyclerview.layoutManager = linearLayoutManager
        binding.bookListRecyclerview.adapter = adapter
        binding.searchEditText.addTextChangedListener(this)
        binding.addBookFab.setOnClickListener(this)
    }

    private fun initVM() {
        bookListVM = getViewModel(BookListVM::class.java)
        bookListVM.getBooksLiveData().observe(this,
                Observer<List<BookEntity>> { bookList -> updateBookList(bookList) })
        bookListVM.getDownloadStateLiveData().observe(this,
                Observer<BookDownloadStates> { bookDownloadState -> handleDownloadStates(bookDownloadState) })
        bookListVM.getFailureLiveData().observe(this, 
                Observer<Throwable> { throwable -> handleError(throwable) })
        bookListVM.initObservers()
        bookListVM.downLoadBooks()
    }

    private fun handleError(throwable: Throwable?) {
        val errorMessage = if (throwable is CompositeException) {
            val stringBuilder = StringBuilder()
            for (exception in throwable.exceptions) {
                stringBuilder.append(exception.message ?: "")
                    .append("\n")
            }
            stringBuilder.toString()
        } else {
            throwable?.message ?: getString(R.string.unknown_error)
        }

        genericUtility.showErrorMessage(errorMessage, this)
        binding.bookListProgressBar.visibility = View.GONE
    }

    private fun updateBookList(bookList: List<BookEntity>) = adapter.updateData(bookList)

    private fun handleDownloadStates(bookDownloadStates: BookDownloadStates) {
        when(bookDownloadStates) {
            BookDownloadStates.DOWNLOAD_INIT -> binding.bookListProgressBar.visibility = View.VISIBLE
            BookDownloadStates.DOWNLOAD_SUCCESS -> binding.bookListProgressBar.visibility = View.GONE
        }
    }

    override fun onBookCardClick(arg: String) = launchBookDetailActivity(arg)

    private fun launchBookDetailActivity(bookId: String) {
        val intent = Intent(this, BookDetailActivity::class.java)
        intent.putExtra(BUNDLE_KEY_BOOK_ID, bookId)
        startActivity(intent)
    }

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            = adapter.filter.filter(s.toString())

    override fun onClick(v: View?) {
        when(v?.id) {
           binding.addBookFab.id -> startActivity(Intent(this, AddBookActivity::class.java))
        }
    }
}