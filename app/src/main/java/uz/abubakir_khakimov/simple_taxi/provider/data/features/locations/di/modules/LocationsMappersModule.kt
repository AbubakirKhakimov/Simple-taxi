package uz.abubakir_khakimov.simple_taxi.provider.data.features.locations.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.abubakir_khakimov.simple_taxi.core.common.EntityMapper
import uz.abubakir_khakimov.simple_taxi.domain.locations.models.Location
import uz.abubakir_khakimov.simple_taxi.provider.data.features.locations.mappers.LocationLocalMapper
import uz.abubakir_khakimov.simple_taxi.provider.data.features.locations.mappers.LocationMapper
import uz.abubakir_khakimov.simple_taxi.provider.local.database.features.locations.entities.LocationLocalEntity
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocationsMappersModule {

    @Provides
    @Singleton
    fun provideLocationLocalMapper(): EntityMapper<LocationLocalEntity, Location> = LocationLocalMapper()

    @Provides
    @Singleton
    fun provideLocationMapper(): EntityMapper<Location, LocationLocalEntity> = LocationMapper()
}