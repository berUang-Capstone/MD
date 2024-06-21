package com.example.subs_inter.di

import com.example.subs_inter.data.auth.AuthRepository
import com.example.subs_inter.data.note.NoteRepository
import com.example.subs_inter.data.token.TokenRepository
import com.example.subs_inter.data.user.UserRepository
import com.example.subs_inter.domain.repository.IAuthRepository
import com.example.subs_inter.domain.repository.INoteRepository
import com.example.subs_inter.domain.repository.ITokenRepository
import com.example.subs_inter.domain.repository.IUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindNoteRepository(noteRepository: NoteRepository): INoteRepository

    @Binds
    abstract fun bindTokenRepository(tokenRepository: TokenRepository): ITokenRepository

    @Binds
    abstract fun bindAuthRepository(authRepository: AuthRepository): IAuthRepository

    @Binds
    abstract fun bindUserRepository(userRepository: UserRepository): IUserRepository
}