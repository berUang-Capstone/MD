package com.example.subs_inter.domain.usecase

import com.example.subs_inter.domain.repository.IAuthRepository
import com.example.subs_inter.domain.repository.ITokenRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: IAuthRepository,
    private val tokenRepository: ITokenRepository
) {
    suspend operator fun invoke() {
        authRepository.logout()
        tokenRepository.removeSession()
    }
}