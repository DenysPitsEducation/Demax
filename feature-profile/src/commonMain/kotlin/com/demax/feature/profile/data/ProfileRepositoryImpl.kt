package com.demax.feature.profile.data

import com.demax.feature.profile.domain.ProfileRepository
import com.demax.feature.profile.domain.model.ProfileDomainModel
import kotlinx.datetime.LocalDate

class ProfileRepositoryImpl : ProfileRepository {
    override suspend fun getDestructionDetails(): Result<ProfileDomainModel> {
        return Result.success(
            ProfileDomainModel(
                id = 1,
                imageUrl = "https://picsum.photos/200/200",
                name = "Фонд Допомоги Постраждалим",
                email = "fund@gmail.com",
                phoneNumber = "380958311553",
                address = "м. Київ, вул. Володимирська 4",
                about = "Свою місію я вбачаю в активній волонтерській діяльності, що спрямована на підтримку обороноздатності країни",
                specializations = listOf("Психотерапія", "Керування вантажівкою"),
                registrationDate = LocalDate(2014, 7, 8),
                helpsCount = 4,
            )
        )
    }
}