package com.example.library.core.di

import android.app.Application
import com.example.library.AppController
import com.example.library.core.di.network.NetworkModule
import com.example.library.core.di.storage.StorageModule
import com.example.library.core.di.ui.ActivityModule
import com.example.library.core.di.viewmodel.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [NetworkModule::class,
                        StorageModule::class,
                        ViewModelModule::class,
                        ActivityModule::class,
                        AndroidSupportInjectionModule::class])
@Singleton
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(appController: AppController)
}