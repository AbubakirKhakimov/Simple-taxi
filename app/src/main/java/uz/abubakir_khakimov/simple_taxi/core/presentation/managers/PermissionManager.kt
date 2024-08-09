package uz.abubakir_khakimov.simple_taxi.core.presentation.managers

import androidx.activity.ComponentActivity
import uz.abubakir_khakimov.simple_taxi.core.presentation.callback.PermissionManagerCallBack

interface PermissionManager {

    fun registerActivityResult(
        activity: ComponentActivity,
        callBack: PermissionManagerCallBack? = null
    )

    fun checkPermissions(
        vararg permissions: String,
        autoAsk: Boolean = false,
        tag: Any = Unit
    ): Boolean

    fun askPermissions(vararg permissions: String, tag: Any = Unit)
}