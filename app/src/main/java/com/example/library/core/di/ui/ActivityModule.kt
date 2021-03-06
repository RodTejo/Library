package com.example.library.core.di.ui

import com.example.library.features.bookdetail.presentation.BookDetailActivity
import com.example.library.features.booklist.presentation.BookListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector()
    abstract fun contributeBookListActivity(): BookListActivity

    @ContributesAndroidInjector()
    abstract fun contributeBookDetailActivity(): BookDetailActivity
}