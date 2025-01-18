//package com.henry.nexus.base
//
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//
//abstract class BaseViewModel<S, E>(initialState: S) {
//    private val _state = MutableStateFlow(initialState)
//    val state: StateFlow<S> = _state.asStateFlow()
//
//    protected val scope = CoroutineScope(Dispatchers.Main)
//
//    protected fun updateState(reduce: S.() -> S) {
//        val newState = _state.value.reduce()
//        _state.value = newState
//    }
//
//    abstract fun onEvent(event: E)
//}