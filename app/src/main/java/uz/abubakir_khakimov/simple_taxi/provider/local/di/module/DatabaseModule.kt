package uz.abubakir_khakimov.simple_taxi.provider.local.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.abubakir_khakimov.simple_taxi.provider.local.database.initializer.MainDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideMainDatabase(@ApplicationContext context: Context): MainDatabase =
        Room.databaseBuilder(
            context = context,
            klass = MainDatabase::class.java,
            name = MAIN_DATABASE_NAME
        ).build()

    companion object{

        private const val MAIN_DATABASE_NAME = "main_database"
    }
}