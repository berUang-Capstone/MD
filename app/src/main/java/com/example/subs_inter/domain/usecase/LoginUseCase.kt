package com.example.subs_inter.domain.usecase

import android.util.Log
import com.example.subs_inter.data.Resource
import com.example.subs_inter.domain.model.auth.LoginModel
import com.example.subs_inter.domain.repository.IAuthRepository
import com.example.subs_inter.domain.repository.ITokenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: IAuthRepository,
    private val tokenRepository: ITokenRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<LoginModel>> {
        return authRepository.login(email, password).apply {
            collect {
                Log.d("LUS", it.toString())
                if (it is Resource.Success) {
                    tokenRepository.login(email, email)
                }
            }
        }
    }
}