package uz.abubakir_khakimov.simple_taxi.core.presentation.extensions

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

inline fun <reified T : Serializable> Bundle.extractSerializable(key: String): T? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializable(/* key = */ key, /* clazz = */ T::class.java)
    } else {
        getSerializable(/* key = */ key) as T
    }

inline fun <reified T : Parcelable> Bundle.extractParcelable(key: String): T? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(/* key = */ key, /* clazz = */ T::class.java)
    } else {
        getParcelable(/* key = */ key)
    }

inline fun <reified T : Parcelable> Bundle.extractParcelableArrayList(key: String): List<T>? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableArrayList(/* key = */ key, /* clazz = */ T::class.java)
    } else {
        getParcelableArrayList(/* key = */ key)
    }

inline fun <reified T : Serializable> Intent.extractSerializable(key: String): T? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializableExtra(/* name = */ key, /* clazz = */ T::class.java)
    } else {
        getSerializableExtra(/* name = */ key) as T
    }

inline fun <reified T : Parcelable> Intent.extractParcelable(key: String): T? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(/* name = */ key, /* clazz = */ T::class.java)
    } else {
        getParcelableExtra(/* name = */ key)
    }

inline fun <reified T : Parcelable> Intent.extractParcelableArrayList(key: String): List<T>? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableArrayListExtra(/* name = */ key, /* clazz = */ T::class.java)
    } else {
        getParcelableArrayListExtra(/* name = */ key)
    }