package com.rifafauzi.moviecoroutines.di.component

import com.rifafauzi.moviecoroutines.MovieApp
import com.rifafauzi.moviecoroutines.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by rrifafauzikomara on 2020-02-20.
 */

@Singleton
@Component(
    modules = [
    ActivityBuilder::class,
    AppModule::class,
    FragmentModule::class,
    NetworkModule::class,
    ViewModelModule::class,
    AndroidInjectionModule::class
    ]
)

interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MovieApp): Builder

        fun build(): AppComponent
    }

    fun inject(app: MovieApp)

}