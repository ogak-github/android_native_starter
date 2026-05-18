package com.example.android_native_starter.router

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.navigation3.runtime.metadata
import androidx.navigation3.ui.NavDisplay

const val animSpeedDuration: Int = 425;


val sharedSlideIn: ContentTransform =
    slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = tween(animSpeedDuration)
    ) togetherWith slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = tween(animSpeedDuration)
    )

val sharedSlidePop: ContentTransform =
    slideInHorizontally(
        initialOffsetX = { -it },
        animationSpec = tween(animSpeedDuration)
    ) togetherWith slideOutHorizontally(
        targetOffsetX = { it },
        animationSpec = tween(animSpeedDuration)
    )

val sharedSlideInVertical: ContentTransform =  slideInVertically(
    initialOffsetY = { it },
    animationSpec = tween(durationMillis = animSpeedDuration)
) togetherWith ExitTransition.None

val sharedSlidePopVertical: ContentTransform =  EnterTransition.None togetherWith
        slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(animSpeedDuration)
        )

// Helper
fun navMetadata(
    enter: ContentTransform,
    exit: ContentTransform
) = metadata {
    put(NavDisplay.TransitionKey) { enter }
    put(NavDisplay.PopTransitionKey) { exit }
    put(NavDisplay.PredictivePopTransitionKey) { exit }
}

// Use this metadata to override animation Global in Nav3.kt
val horizontalSlideMetadata = navMetadata(sharedSlideIn, sharedSlidePop)
val verticalSlideMetadata = navMetadata(sharedSlideInVertical, sharedSlidePopVertical)





