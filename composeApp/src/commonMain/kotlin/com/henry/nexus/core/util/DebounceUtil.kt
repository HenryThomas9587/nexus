package com.henry.nexus.core.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Debounce(
    private val delayMillis: Long,
    private val coroutineScope: CoroutineScope,
    private val onDebounced: suspend () -> Unit
) {
    private var job: Job? = null

    fun debounce() {
        job?.cancel()
        job = coroutineScope.launch {
            delay(delayMillis)
            onDebounced()
        }
    }
} 