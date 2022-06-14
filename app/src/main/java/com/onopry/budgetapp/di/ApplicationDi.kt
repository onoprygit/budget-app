package com.onopry.budgetapp.di

import com.onopry.budgetapp.model.repo.AuthRepository
import com.onopry.budgetapp.model.services.*
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
    fun provideAuthRepo() = AuthRepository()

    @Provides
    @Singleton
    fun providePeriodService() = PeriodService()

    @Provides
    @Singleton
    fun provideCategoriesService(authRepository: AuthRepository) = CategoriesService(authRepository)

    @Provides
    @Singleton
    fun provideTargetService(authRepository: AuthRepository, categoriesService: CategoriesService) =
        TargetService(authRepository, categoriesService)

    @Provides
    @Singleton
    fun provideOperationsService(targetService: TargetService, categoriesService: CategoriesService) =
        OperationsService(targetService = targetService, categoriesService = categoriesService)

    @Provides
    @Singleton
    fun provideAccountsService(authRepository: AuthRepository, operationsService: OperationsService) =
        AccountsService(authRepository, operationsService)

}

/*
@Module
@InstallIn(ViewModelComponent::class)
class ViewModelsDi{


}*/
