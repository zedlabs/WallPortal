package tk.zedlabs.wallportal.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineScope
import tk.zedlabs.wallportal.data.JsonApi
import tk.zedlabs.wallportal.repository.PopularDataSource
import tk.zedlabs.wallportal.repository.PostDataSource

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    //    @Provides
//    @ActivityRetainedScoped
//    fun provideCoroutineScope(coroutineScope: CoroutineScope): CoroutineScope {
//        return coroutineScope
//    }
    @Provides
    @ActivityRetainedScoped
    fun providePostDataSource(
        jsonApi: JsonApi
    ): PostDataSource {
        return PostDataSource(jsonApi)
    }

    @Provides
    @ActivityRetainedScoped
    fun providePopularDataSource(
        jsonApi: JsonApi
    ): PopularDataSource {
        return PopularDataSource(jsonApi)
    }
}