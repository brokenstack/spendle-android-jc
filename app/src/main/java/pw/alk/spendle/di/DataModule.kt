package pw.alk.spendle.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pw.alk.spendle.ui.utils.TokenDataStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun providesDataStoreRepository(@ApplicationContext context: Context) = TokenDataStore(context)
}