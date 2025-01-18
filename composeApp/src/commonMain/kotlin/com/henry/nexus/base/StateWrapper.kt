//package com.henry.nexus.base
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import org.koin.compose.koinInject
//
//@Composable
//inline fun <reified VM : BaseViewModel<S, E>, S, E> StateWrapper(
//    noinline content: @Composable (state: S, onEvent: (E) -> Unit) -> Unit
//) {
//    val viewModel = koinInject<VM>()
//    val state by viewModel.state.collectAsState()
//
//    content(state) { event ->
//        viewModel.onEvent(event)
//    }
//}