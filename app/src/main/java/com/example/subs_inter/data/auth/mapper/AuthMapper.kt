package com.example.subs_inter.data.auth.mapper

import com.example.subs_inter.data.auth.response.LoginResponse
import com.example.subs_inter.domain.model.auth.LoginModel

object AuthMapper {
    fun loginResponseToModel(q: LoginResponse): LoginModel = LoginModel(message = q.message)
}