package com.demax.core.domain.repository

import com.demax.core.domain.model.UserDomainModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun initUser()
    fun getUserFlow(): Flow<UserDomainModel?>
}