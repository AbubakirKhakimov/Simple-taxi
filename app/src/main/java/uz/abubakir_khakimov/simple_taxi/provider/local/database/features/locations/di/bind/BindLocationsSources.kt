package uz.abubakir_khakimov.simple_taxi.provider.local.database.features.locations.di.bind

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.abubakir_khakimov.simple_taxi.provider.local.database.features.locations.LocationsLocalDataSource
import uz.abubakir_khakimov.simple_taxi.provider.local.database.features.locations.sources.LocationsLocalDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BindLocationsSources {

    @Binds
    @Singleton
    fun bindLocationsLocalDataSource(
        locationsLocalDataSourceImpl: LocationsLocalDataSourceImpl
    ): LocationsLocalDataSource
}