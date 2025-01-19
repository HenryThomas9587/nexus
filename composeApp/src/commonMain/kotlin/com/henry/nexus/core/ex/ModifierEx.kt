package com.henry.nexus.core.ex

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


fun Modifier.roundedBackgroundWithPadding(
    backgroundColor: Color,
    cornerRadius: Dp,
    padding: Dp
): Modifier {
    return this
        .background(backgroundColor, shape = RoundedCornerShape(cornerRadius))
        .padding(padding)
}

fun Modifier.showIf(condition: Boolean): Modifier {
    return if (condition) this else Modifier.size(0.dp)
}

fun Modifier.animateVisibility(isVisible: Boolean): Modifier {
    return if (isVisible) {
        this.alpha(1f)
    } else {
        this.alpha(0f)
    }
}