package com.example.library.features.booklist.presentation

import android.os.Bundle
import com.example.library.R
import com.example.library.core.framework.BaseActivity
import com.example.library.databinding.ActivityBookListBinding
import javax.inject.Inject

class BookListActivity : BaseActivity() {
    private lateinit var binding: ActivityBookListBinding
    private lateinit var bookListVM: BookListVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bookListVM = getViewModel(BookListVM::class.java)
    }
}