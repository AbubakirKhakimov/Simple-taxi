package uz.abubakir_khakimov.simple_taxi.provider.local.database.features.locations.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.abubakir_khakimov.simple_taxi.provider.local.database.features.locations.dao.LocationsDao
import uz.abubakir_khakimov.simple_taxi.provider.local.database.initializer.MainDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocationsDaoModule {

    @Provides
    @Singleton
    fun provideLocationsDao(mainDatabase: MainDatabase): LocationsDao =
        mainDatabase.locationsDao()
}