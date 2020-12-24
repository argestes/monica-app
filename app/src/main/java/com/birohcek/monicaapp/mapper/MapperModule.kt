package com.birohcek.monicaapp.mapper

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object MapperModule {

    @Provides
    fun provideRelationshipContainerMapper() = RelationshipContainerMapper()

    @Provides
    fun provideContactEntryMapper() = ContactEntryMapper()

    @Provides
    fun contactMapper(
        contactRelMapper: RelationshipContainerMapper,
        singleDateMapper: SingleDateMapper,
        contactEntryMapper: ContactEntryMapper
    ) = ContactMapper(singleDateMapper)

    @Provides
    fun singleDateMapper() = SingleDateMapper()
}