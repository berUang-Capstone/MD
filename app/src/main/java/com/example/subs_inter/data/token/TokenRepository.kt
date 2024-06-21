package com.example.subs_inter.data.token

import com.example.subs_inter.data.token.source.TokenManager
import com.example.subs_inter.domain.repository.ITokenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TokenRepository @Inject constructor(
    private val tokenManager: TokenManager
): ITokenRepository {
    override suspend fun removeSession() {
        tokenManager.logout()
    }

    override fun isLogin(): Flow<Boolean> = tokenManager.isUserLogin()

    override suspend fun login(email: String, name: String) {
        tokenManager.setUserName(email)
        tokenManager.setUserEmail(email)
        tokenManager.setUserLogin()
    }
}