package com.example.library.core.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.library.features.bookdetail.presentation.BookDetailVM
import com.example.library.features.booklist.presentation.BookListVM
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(BookListVM::class)
    protected abstract fun bookListVM(bookListVM: BookListVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BookDetailVM::class)
    protected abstract fun bookDetailVM(bookDetailVM: BookDetailVM): ViewModel
}