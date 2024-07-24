package com.demax.core.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun <T> SideEffectLaunchedEffect(
    sideEffectsFlow: Flow<T>,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    sideEffectHandler: suspend CoroutineScope.(T) -> Unit,
) {
    LaunchedEffect(Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            sideEffectsFlow.collect { label ->
                launch {
                    sideEffectHandler.invoke(this, label)
                }
            }
        }
    }
}
