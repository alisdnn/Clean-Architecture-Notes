package com.example.cleannotes.di

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.example.cleannotes.business.domain.util.DateUtil
import com.example.cleannotes.framework.presentation.TestNoteFragmentFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton

@FlowPreview
@ExperimentalCoroutinesApi
@Module
object TestNoteFragmentFactoryModule {

    @Singleton
    @JvmStatic
    @Provides
    fun provideNoteFragmentFactory(
        viewModelFactory: ViewModelProvider.Factory,
        dateUtil: DateUtil
    ): FragmentFactory {
        return TestNoteFragmentFactory(viewModelFactory, dateUtil)
    }
}