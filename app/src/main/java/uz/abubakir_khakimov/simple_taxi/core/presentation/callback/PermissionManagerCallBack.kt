package uz.abubakir_khakimov.simple_taxi.core.presentation.callback

interface PermissionManagerCallBack {

    fun onActivityResult(result: Map<String, Boolean>, tag: Any)
}