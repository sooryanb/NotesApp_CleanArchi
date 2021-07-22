package com.example.notecleanarchi.framework.di

import com.example.notecleanarchi.framework.ListViewModel
import com.example.notecleanarchi.framework.NoteViewModel
import dagger.Component

@Component(modules = [ApplicationModule::class, RepositoryModule::class, UseCasesModule::class])
interface ViewModelComponent {
    fun inject(noteViewModel: NoteViewModel)
    fun inject(listViewModel: ListViewModel)
}