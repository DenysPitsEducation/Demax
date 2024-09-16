package com.demax.android

import com.demax.core.data.model.AmountDataModel
import com.demax.core.data.model.DestructionStatisticsDataModel
import com.demax.core.data.model.VolunteerNeedDataModel
import com.demax.core.data.model.DestructionDataModel
import com.demax.core.data.model.ResourceDataModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppInitializer {
    fun initialize() {
        CoroutineScope(Dispatchers.IO).launch {
            // initDestructions()
            // initResources()
        }
    }

    private suspend fun initDestructions() {
        val database = Firebase.firestore
        val collection = database.collection("destructions")
        val models = listOf(
            DestructionDataModel(
                imageUrl = "https://picsum.photos/seed/destruction0/1200/800",
                buildingType = "residential",
                address = "вул Чорновола, 28",
                destructionStatistics = DestructionStatisticsDataModel(
                    destroyedFloors = "10",
                    destroyedSections = "1"
                ),
                destructionDate = "2024-07-08",
                description = "Будівля зазнала невиправних руйнувань, приблизна кількість жертв становить ...",
                volunteerNeeds = listOf(
                    VolunteerNeedDataModel(name = "Психотерапія", amount = AmountDataModel(3, 10)),
                    VolunteerNeedDataModel(name = "Педіатрія", amount = AmountDataModel(1, 2)),
                ),
                resourceNeeds = listOf("0", "1"),
                priority = 5,
            ),
            DestructionDataModel(
                imageUrl = "https://picsum.photos/seed/destruction1/1200/600",
                buildingType = "medical_institution",
                address = "вул Лобановського, 28",
                destructionStatistics = DestructionStatisticsDataModel(
                    destroyedFloors = "5",
                    destroyedSections = "2"
                ),
                destructionDate = "2022-07-08",
                description = "Будівля зазнала невиправних руйнувань, приблизна кількість жертв становить ...",
                volunteerNeeds = listOf(
                    VolunteerNeedDataModel(name = "Психотерапія", amount = AmountDataModel(3, 10)),
                    VolunteerNeedDataModel(name = "Педіатрія", amount = AmountDataModel(1, 2)),
                ),
                resourceNeeds = listOf("2", "3", "4"),
                priority = 6,
            ),
        )
        database.batch().apply {
            models.forEachIndexed { index, destructionDetails ->
                set(collection.document(index.toString()), destructionDetails)
            }
        }.commit()
    }

    private suspend fun initResources() {
        val database = Firebase.firestore
        val collection = database.collection("resources")
        val resourcesDetails = listOf(
            ResourceDataModel(
                imageUrl = "https://picsum.photos/seed/resource0/800/1000",
                name = "Бинти",
                category = "medical_products",
                amount = AmountDataModel(currentAmount = 100, totalAmount = 100),
                description = "Стерильні бинти для надання першої медичної допомоги.",
                destructionId = "0"
            ),
            ResourceDataModel(
                imageUrl = "https://picsum.photos/seed/resource1/800/1000",
                name = "Консерви",
                category = "food",
                amount = AmountDataModel(currentAmount = 200, totalAmount = 500),
                description = "Консервовані продукти тривалого зберігання.",
                destructionId = "0"
            ),
            ResourceDataModel(
                imageUrl = "https://picsum.photos/seed/resource2/800/1000",
                name = "Теплі куртки",
                category = "clothes",
                amount = AmountDataModel(currentAmount = 75, totalAmount = 150),
                description = "Зимові куртки для захисту від холоду.",
                destructionId = "0"
            ),
            ResourceDataModel(
                imageUrl = "https://picsum.photos/seed/resource3/800/1000",
                name = "Аптечка",
                category = "rescue_equipment",
                amount = AmountDataModel(currentAmount = 30, totalAmount = 50),
                description = "Аптечка для надання невідкладної допомоги.",
                destructionId = "1"
            ),
            ResourceDataModel(
                imageUrl = "https://picsum.photos/seed/resource4/800/1000",
                name = "Каністри з паливом",
                category = "vehicles_and_fuel",
                amount = AmountDataModel(currentAmount = 75, totalAmount = 200),
                description = "Металеві каністри для зберігання палива.",
                destructionId = "1"
            )
        )
        database.batch().apply {
            resourcesDetails.forEachIndexed { index, resource ->
                set(collection.document(index.toString()), resource)
            }
        }.commit()
    }
}