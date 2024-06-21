package com.example.subs_inter.data.user

import com.example.subs_inter.data.Resource
import com.example.subs_inter.data.user.mapper.WalletMapper.mapWalletResponseToModel
import com.example.subs_inter.data.user.source.UserApiService
import com.example.subs_inter.domain.model.wallet.WalletModel
import com.example.subs_inter.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApiService: UserApiService
): IUserRepository {
    override fun fetchWalelt(): Flow<Resource<WalletModel>> = flow {
        emit(Resource.Loading)
        emit(
            try {
                val response = userApiService.fetchWallet()
                Resource.Success(mapWalletResponseToModel(response.wallet))
            } catch (e: Exception) {
                Resource.Error(e.message.toString())
            }
        )
    }
}