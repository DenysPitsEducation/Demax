package com.demax.android

import com.demax.core.data.model.AmountDataModel
import com.demax.feature.destruction.details.data.model.DestructionDetailsDataModel
import com.demax.feature.destruction.details.data.model.DestructionStatisticsDataModel
import com.demax.feature.destruction.details.data.model.NeedDataModel
import com.demax.core.data.model.DestructionDataModel
import com.demax.core.data.model.ResourceDetailsDataModel
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
            // initDestructionDetails()
            // initResources()
            // initResourceDetails()
        }
    }

    private suspend fun initDestructions() {
        val database = Firebase.firestore
        val destructionsCollection = database.collection("destructions")
        val destructions = listOf(
            DestructionDataModel(
                imageUrl = "https://picsum.photos/seed/destruction0/200/200",
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
                imageUrl = "https://picsum.photos/seed/destruction1/300/200",
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
                imageUrl = "https://picsum.photos/seed/destruction0/1200/800",
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
                imageUrl = "https://picsum.photos/seed/destruction1/1200/600",
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

    private suspend fun initResources() {
        val database = Firebase.firestore
        val resourcesCollection = database.collection("resources")
        val resources = listOf(
            ResourceDataModel(
                imageUrl = "https://picsum.photos/seed/resource0/200/300",
                name = "Бинти",
                category = "medical_products",
                amount = AmountDataModel(currentAmount = 100, totalAmount = 100)
            ),
            ResourceDataModel(
                imageUrl = "https://picsum.photos/seed/resource1/200/300",
                name = "Консерви",
                category = "food",
                amount = AmountDataModel(currentAmount = 200, totalAmount = 500)
            ),
            ResourceDataModel(
                imageUrl = "https://picsum.photos/seed/resource2/200/300",
                name = "Теплі куртки",
                category = "clothes",
                amount = AmountDataModel(currentAmount = 75, totalAmount = 150)
            ),
            ResourceDataModel(
                imageUrl = "https://picsum.photos/seed/resource3/200/300",
                name = "Аптечка",
                category = "rescue_equipment",
                amount = AmountDataModel(currentAmount = 30, totalAmount = 50)
            ),
            ResourceDataModel(
                imageUrl = "https://picsum.photos/seed/resource4/200/300",
                name = "Каністри з паливом",
                category = "vehicles_and_fuel",
                amount = AmountDataModel(currentAmount = 75, totalAmount = 200)
            ),
        )

        database.batch().apply {
            resources.forEachIndexed { index, resource ->
                set(resourcesCollection.document(index.toString()), resource)
            }
        }.commit()
    }

    private suspend fun initResourceDetails() {
        val database = Firebase.firestore
        val collection = database.collection("resource_details")
        val resourcesDetails = listOf(
            ResourceDetailsDataModel(
                imageUrl = "https://picsum.photos/seed/resource0/800/1000",
                name = "Бинти",
                category = "medical_products",
                amount = AmountDataModel(currentAmount = 100, totalAmount = 100),
                description = "Стерильні бинти для надання першої медичної допомоги.",
                destructionId = "0"
            ),
            ResourceDetailsDataModel(
                imageUrl = "https://picsum.photos/seed/resource1/800/1000",
                name = "Консерви",
                category = "food",
                amount = AmountDataModel(currentAmount = 200, totalAmount = 500),
                description = "Консервовані продукти тривалого зберігання.",
                destructionId = "0"
            ),
            ResourceDetailsDataModel(
                imageUrl = "https://picsum.photos/seed/resource2/800/1000",
                name = "Теплі куртки",
                category = "clothes",
                amount = AmountDataModel(currentAmount = 75, totalAmount = 150),
                description = "Зимові куртки для захисту від холоду.",
                destructionId = "0"
            ),
            ResourceDetailsDataModel(
                imageUrl = "https://picsum.photos/seed/resource3/800/1000",
                name = "Аптечка",
                category = "rescue_equipment",
                amount = AmountDataModel(currentAmount = 30, totalAmount = 50),
                description = "Аптечка для надання невідкладної допомоги.",
                destructionId = "1"
            ),
            ResourceDetailsDataModel(
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