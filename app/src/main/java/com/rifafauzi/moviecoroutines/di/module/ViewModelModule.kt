package com.rifafauzi.moviecoroutines.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rifafauzi.moviecoroutines.di.factory.ViewModelFactory
import com.rifafauzi.moviecoroutines.ui.movie.MovieViewModel
import com.rifafauzi.moviecoroutines.utils.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by rrifafauzikomara on 2020-02-20.
 */

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory) : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    internal abstract fun providesMovieViewModel(viewModel: MovieViewModel) : ViewModel

}