package uz.abubakir_khakimov.simple_taxi.core.presentation.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun bitmapFromDrawableRes(@DrawableRes resourceId: Int): Bitmap? =
    convertDrawableToBitmap(
        sourceDrawable = AppCompatResources
            .getDrawable(/* context = */ LocalContext.current, /* resId = */ resourceId)
    )

fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int): Bitmap? =
    convertDrawableToBitmap(
        sourceDrawable = AppCompatResources
            .getDrawable(/* context = */ context, /* resId = */ resourceId)
    )

private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
    if (sourceDrawable == null) return null

    return if (sourceDrawable is BitmapDrawable) sourceDrawable.bitmap
    else {
        val constantState = sourceDrawable.constantState ?: return null
        val drawable = constantState.newDrawable().mutate()
        val bitmap: Bitmap = Bitmap.createBitmap(
            /* width = */ drawable.intrinsicWidth,
            /* height = */ drawable.intrinsicHeight,
            /* config = */ Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(/* bitmap = */ bitmap)
        drawable.setBounds(
            /* left = */ 0,
            /* top = */ 0,
            /* right = */ canvas.width,
            /* bottom = */ canvas.height
        )
        drawable.draw(/* canvas = */ canvas)
        bitmap
    }
}