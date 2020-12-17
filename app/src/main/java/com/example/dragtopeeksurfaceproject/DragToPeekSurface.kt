package com.example.dragtopeeksurfaceproject

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animatedFloat
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.gesture.DragObserver
import androidx.compose.ui.gesture.dragGestureFilter
import androidx.compose.ui.graphics.Color

@ExperimentalAnimationApi
@Composable
fun DragToPeekSurface(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
    coverContentAnim: AnimationSpec<Float> = defaultCoverAnim,
    backgroundContent: @Composable () -> Unit,
    coverContent: @Composable () -> Unit
) {
    val dragPositionX = remember { mutableStateOf(0f) }
    val xPosToAnimateCoverContent = animatedFloat(initVal = 0f)

    Surface(color = backgroundColor, modifier = modifier) {
        Box {
            Box(modifier = Modifier.clip(getBackgroundClipShape(dragPositionX.value))) {
                backgroundContent()
            }
            Box(
                modifier = Modifier
                    .clip(getCoverClipShape(dragPositionX.value))
                    .offset(x = { xPosToAnimateCoverContent.value })
                    .dragGestureFilter(object : DragObserver {
                        override fun onStart(downPosition: Offset) {
                            dragPositionX.value += downPosition.x
                        }

                        override fun onDrag(dragDistance: Offset): Offset {
                            dragPositionX.value += dragDistance.x
                            return super.onDrag(dragDistance)
                        }

                        override fun onStop(velocity: Offset) {
                            val dragPosition = dragPositionX.value
                            if (dragPosition > 0f) {
                                xPosToAnimateCoverContent.snapTo(dragPosition)
                                dragPositionX.value = 0f
                                xPosToAnimateCoverContent.animateTo(
                                    targetValue = 0f,
                                    anim = coverContentAnim,
                                )
                            }
                        }
                    })
            ) {
                coverContent()
            }
        }
    }
}

internal val defaultCoverAnim: TweenSpec<Float> = tween(
    durationMillis = 250,
    easing = CubicBezierEasing(0.1f, 0.56f, 0.42f, 1.31f)
)

private fun getBackgroundClipShape(dragPointX: Float) = GenericShape { size ->
    moveTo(0f, 0f)
    lineTo(dragPointX, 0f)
    lineTo(dragPointX, size.height)
    lineTo(0f, size.height)
}

private fun getCoverClipShape(dragPointX: Float) = GenericShape { size ->
    moveTo(Float.MAX_VALUE, 0f)
    lineTo(dragPointX, 0f)
    lineTo(dragPointX, size.height)
    lineTo(Float.MAX_VALUE, size.height)
}
