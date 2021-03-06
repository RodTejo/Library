package com.example.library.features.booklist.presentation

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.library.R
import com.example.library.core.framework.BaseActivity
import com.example.library.databinding.ActivityBookListBinding
import com.example.library.features.booklist.BookDownloadStates
import com.example.library.features.booklist.data.entity.BookEntity
import javax.inject.Inject

class BookListActivity : BaseActivity(), BookListAdapter.BookListAdapterListener<String> {
    private lateinit var binding: ActivityBookListBinding
    private lateinit var bookListVM: BookListVM
    private lateinit var adapter: BookListAdapter
    private var bookList: ArrayList<BookEntity> = ArrayList<BookEntity>()

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
        bookListVM.getBooksLiveData().observe(this, Observer<List<BookEntity>> { bookList -> updateBookList(bookList) })
        bookListVM.getDownloadStateLiveData().observe(this, Observer { this::handleDownloadStates })
        bookListVM.initObservers()
        bookListVM.downLoadBooks()
    }



    private fun updateBookList(bookList: List<BookEntity>) {
        this.bookList.clear()
        this.bookList.addAll(bookList)
        adapter.notifyDataSetChanged()
    }

    private fun handleDownloadStates(bookDownloadStates: BookDownloadStates) {

    }

    override fun onClick(arg: String) {
        TODO("Not yet implemented")
    }
}