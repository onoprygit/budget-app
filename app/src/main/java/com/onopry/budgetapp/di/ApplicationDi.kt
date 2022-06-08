package com.onopry.budgetapp.di

import com.onopry.budgetapp.model.repo.AuthRepository
import com.onopry.budgetapp.model.services.CategoriesService
import com.onopry.budgetapp.model.services.OperationsService
import com.onopry.budgetapp.model.services.PeriodService
import com.onopry.budgetapp.model.services.TargetService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServicesDi {

    @Provides
    @Singleton
    fun provideCategoriesService() = CategoriesService()

    @Provides
    @Singleton
    fun provideTargetService(categoriesService: CategoriesService) = TargetService(categoriesService)

    @Provides
    @Singleton
    fun providePeriodService() = PeriodService()

    @Provides
    @Singleton
    fun provideOperationsService(targetService: TargetService, categoriesService: CategoriesService) =
        OperationsService(targetService = targetService, categoriesService = categoriesService)

    @Provides
    @Singleton
    fun provideAuthRepo(categoriesService: CategoriesService) = AuthRepository(categoriesService)
}

/*
@Module
@InstallIn(ViewModelComponent::class)
class ViewModelsDi{


}*/
