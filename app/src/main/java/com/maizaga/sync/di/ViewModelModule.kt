package com.maizaga.sync.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maizaga.sync.ViewModelFactory
import com.maizaga.sync.viewmodels.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 *
 * Created by maizaga on 2019-08-24.
 */
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel
}