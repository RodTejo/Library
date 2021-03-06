package com.example.library.features.booklist.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.library.core.framework.BaseActivity
import com.example.library.core.utils.GenericUtility
import com.example.library.databinding.ActivityBookListBinding
import com.example.library.features.booklist.BookDownloadStates
import com.example.library.features.booklist.BookListStorageException
import com.example.library.features.booklist.data.entity.BookEntity
import javax.inject.Inject

class BookListActivity : BaseActivity(),
        BookListAdapter.BookListAdapterListener<String> {

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
        adapter = BookListAdapter(bookList, this)
        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
        binding.bookListRecyclerview.setHasFixedSize(true)
        binding.bookListRecyclerview.layoutManager = linearLayoutManager
        binding.bookListRecyclerview.adapter = adapter
    }

    private fun initVM() {
        bookListVM = getViewModel(BookListVM::class.java)
        bookListVM.getBooksLiveData().observe(this,
                Observer<List<BookEntity>> { bookList -> updateBookList(bookList) })
        bookListVM.getDownloadStateLiveData().observe(this,
                Observer<BookDownloadStates> { bookDownloadState -> handleDownloadStates(bookDownloadState) })
        bookListVM.getFailureLiveData().observe(this, 
                Observer<Throwable> { throwable -> hendleError(throwable) })
        bookListVM.initObservers()
        bookListVM.downLoadBooks()
    }

    /**
     * Redundant implementation, used to represent a way to handle different errors and the
     * possible different reactions
     */
    private fun hendleError(throwable: Throwable?) {
        when(throwable) {
            is BookListStorageException -> genericUtility.showErrorMessage(throwable.message, this)
            is Throwable -> {
                genericUtility.showErrorMessage(throwable.message, this)
                binding.bookListProgressBar.visibility = View.GONE
            }
        }
    }

    private fun updateBookList(bookList: List<BookEntity>) {
        this.bookList.clear()
        this.bookList.addAll(bookList)
        adapter.notifyDataSetChanged()
    }

    private fun handleDownloadStates(bookDownloadStates: BookDownloadStates) {
        when(bookDownloadStates) {
            BookDownloadStates.DOWNLOAD_INIT -> binding.bookListProgressBar.visibility = View.VISIBLE
            BookDownloadStates.DOWNLOAD_SUCCESS -> binding.bookListProgressBar.visibility = View.GONE
        }
    }

    override fun onClick(arg: String) {
        TODO("Not yet implemented")
    }
}