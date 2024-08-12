package com.demax.android

import com.demax.feature.destruction.details.data.model.AmountDataModel
import com.demax.feature.destruction.details.data.model.DestructionDetailsDataModel
import com.demax.feature.destruction.details.data.model.DestructionStatisticsDataModel
import com.demax.feature.destruction.details.data.model.NeedDataModel
import com.demax.feature.destruction.details.domain.model.AmountDomainModel
import com.demax.feature.destruction.details.domain.model.DestructionDetailsDomainModel
import com.demax.feature.destruction.details.domain.model.DestructionStatisticsDomainModel
import com.demax.feature.destruction.details.domain.model.NeedDomainModel
import com.demax.feature.destructions.data.model.DestructionDataModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class AppInitializer {
    fun initialize() {
        CoroutineScope(Dispatchers.IO).launch {
            // initDestructions()
            // initDestructionDetails()
        }
    }

    private suspend fun initDestructions() {
        val database = Firebase.firestore
        val destructionsCollection = database.collection("destructions")
        val destructions = listOf(
            DestructionDataModel(
                imageUrl = "https://picsum.photos/200/200",
                buildingType = "residential",
                address = "вул Чорновола, 28",
                destructionDate = "2024-07-08",
                resourceProgress = 0.3,
                volunteerProgress = 1.0,
                specializations = listOf("Психотерапія", "Педіатрія"),
                status = "active",
                priority = 7,
            ),
            DestructionDataModel(
                imageUrl = "https://picsum.photos/300/200",
                buildingType = "medical_institution",
                address = "вул Лобановського, 28",
                destructionDate = "2022-07-08",
                resourceProgress = 1.0,
                volunteerProgress = 0.3,
                specializations = listOf("Психотерапія", "Педіатрія"),
                status = "completed",
                priority = 10,
            ),
        )
        database.batch().apply {
            destructions.forEachIndexed { index, destruction ->
                set(destructionsCollection.document(index.toString()), destruction)
            }
        }.commit()
    }

    private suspend fun initDestructionDetails() {
        val database = Firebase.firestore
        val collection = database.collection("destruction_details")
        val models = listOf(
            DestructionDetailsDataModel(
                imageUrl = "https://picsum.photos/1200/800",
                status = "active",
                buildingType = "residential",
                address = "вул Чорновола, 28",
                destructionStatistics = DestructionStatisticsDataModel(
                    destroyedFloors = "10",
                    destroyedSections = "1"
                ),
                destructionDate = "2024-07-08",
                description = "Будівля зазнала невиправних руйнувань, приблизна кількість жертв становить ...",
                volunteerNeeds = listOf(
                    NeedDataModel(name = "Психотерапія", amount = AmountDataModel(3, 10)),
                    NeedDataModel(name = "Педіатрія", amount = AmountDataModel(1, 2)),
                ),
                resourceNeeds = listOf(
                    NeedDataModel(name = "Зарядні пристрої", amount = AmountDataModel(8, 8)),
                ),
            ),
            DestructionDetailsDataModel(
                imageUrl = "https://picsum.photos/1200/600",
                status = "completed",
                buildingType = "medical_institution",
                address = "вул Лобановського, 28",
                destructionStatistics = DestructionStatisticsDataModel(
                    destroyedFloors = "5",
                    destroyedSections = "2"
                ),
                destructionDate = "2022-07-08",
                description = "Будівля зазнала невиправних руйнувань, приблизна кількість жертв становить ...",
                volunteerNeeds = listOf(
                    NeedDataModel(name = "Психотерапія", amount = AmountDataModel(3, 10)),
                    NeedDataModel(name = "Педіатрія", amount = AmountDataModel(1, 2)),
                ),
                resourceNeeds = listOf(
                    NeedDataModel(name = "Зарядні пристрої", amount = AmountDataModel(8, 8)),
                ),
            ),
        )
        database.batch().apply {
            models.forEachIndexed { index, destructionDetails ->
                set(collection.document(index.toString()), destructionDetails)
            }
        }.commit()
    }
}