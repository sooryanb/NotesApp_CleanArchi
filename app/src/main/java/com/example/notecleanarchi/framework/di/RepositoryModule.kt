package com.example.notecleanarchi.framework.di

import android.app.Application
import com.example.core.repository.NoteRepository
import com.example.notecleanarchi.framework.RoomNoteDataSource
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun provideRepository(app: Application) = NoteRepository(RoomNoteDataSource(app))
}