package uz.abubakir_khakimov.simple_taxi.wiring.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.abubakir_khakimov.simple_taxi.core.presentation.managers.LocationManager
import uz.abubakir_khakimov.simple_taxi.core.presentation.managers.LocationManagerImpl
import uz.abubakir_khakimov.simple_taxi.core.presentation.managers.PermissionManager
import uz.abubakir_khakimov.simple_taxi.core.presentation.managers.PermissionManagerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PresentationManagersModule {

    @Provides
    fun provideLocationManager(): LocationManager = LocationManagerImpl()

    @Provides
    fun providePermissionManager(): PermissionManager = PermissionManagerImpl()
}