package com.example.library.features.addbook.presentation

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import com.example.library.core.common.AppConstants.BookDetail.DateFormat.BOOK_DETAIL_DATE_FORMAT
import com.example.library.core.common.AppConstants.BookDetail.DateFormat.UTC_FORMAT
import com.example.library.core.framework.BaseActivity
import com.example.library.core.utils.formatTo
import com.example.library.databinding.ActivityAddBookBinding
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
    }

    private fun initViews() {
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
        val utcDate = calendar.time.formatTo(UTC_FORMAT)
        binding.addBookDate.setText(calendar.time.formatTo(BOOK_DETAIL_DATE_FORMAT))
    }

    private fun sendNewBook() {
        TODO("Not yet implemented")
    }
}