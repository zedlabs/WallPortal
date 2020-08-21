package tk.zedlabs.wallportal.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineScope

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

//    @Provides
//    @ActivityRetainedScoped
//    fun provideCoroutineScope(
//        coroutineScope: CoroutineScope
//    ): CoroutineScope {
//        return coroutineScope
//    }
}