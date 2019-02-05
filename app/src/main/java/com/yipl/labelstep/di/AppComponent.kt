package com.yipl.labelstep.di

import android.app.Application
import com.yipl.labelstep.ui.MainApplication
import com.yipl.labelstep.di.module.AppModule
import com.yipl.labelstep.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class, AppModule::class, ActivityBuilder::class, ViewModelModule::class))
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(app: Application): Builder
        fun build(): AppComponent
    }

    fun inject(app: MainApplication)
}