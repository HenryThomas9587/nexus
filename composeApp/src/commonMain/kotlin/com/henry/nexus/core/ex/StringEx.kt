package com.henry.nexus.core.ex

fun String.capitalize() =
    replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }