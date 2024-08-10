package com.demax.android

import com.demax.feature.destructions.data.model.DestructionDataModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppInitializer {
    fun initialize() {
        CoroutineScope(Dispatchers.IO).launch {
            //initDestructions()
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
                buildingType = "medical_order",
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
            destructions.forEach { destruction ->
                set(destructionsCollection.document, destruction)
            }
        }.commit()
    }
}