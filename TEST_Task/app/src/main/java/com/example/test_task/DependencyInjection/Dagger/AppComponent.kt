package com.example.test_task.DependencyInjection.Dagger

import android.content.Context
import com.example.test_task.MainModel.ModelsRepository
import com.example.test_task.Models.Retrofit.RetrofitService
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named

@Component(modules = [RetrofitDaggerModule::class, RoomDaggerModule::class])
interface AppComponent {

    fun injectAllInRepository(repos: ModelsRepository)

    fun getRetrofitService() : RetrofitService

    @Component.Builder
    interface Builder{

        @BindsInstance fun addUrl(@Named("url") url: String) : Builder
        @BindsInstance fun addDbName(@Named("dbName") dbName: String) : Builder
        @BindsInstance fun addContext(context: Context) : Builder

        fun build() : AppComponent
    }
}