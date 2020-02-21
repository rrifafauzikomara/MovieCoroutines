package com.rifafauzi.moviecoroutines.di.module

import com.rifafauzi.moviecoroutines.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by rrifafauzikomara on 2020-02-20.
 */
 
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity

}