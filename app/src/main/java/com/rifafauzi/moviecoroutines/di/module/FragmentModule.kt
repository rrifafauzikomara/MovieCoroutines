package com.rifafauzi.moviecoroutines.di.module

import com.rifafauzi.moviecoroutines.ui.movie.MovieFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by rrifafauzikomara on 2020-02-20.
 */

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributesMovieFragment() : MovieFragment

}