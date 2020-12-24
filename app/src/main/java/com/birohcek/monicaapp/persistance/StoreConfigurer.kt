package com.birohcek.monicaapp.persistance

import com.birohcek.monicaapp.models.AccountModel
import com.birohcek.monicaapp.models.PetModel
import com.birohcek.monicaapp.networking.*
import com.birohcek.monicaapp.networking.models.ContactDto
import com.birohcek.monicaapp.networking.models.TokenResponseDto
import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject


@Module
@InstallIn(ApplicationComponent::class)
class StoreModule() {

    val token =
        "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxNiIsImp0aSI6ImIzZjdhM2I0ZDQ2ODQzM2JkZWU5ZTI4YzliNjhhNjhiMWQ0YzY1YjljNzU3OTVlYjVmZWU0ZWFmZjc4ODI5ZDdlMTliZmUyMTU2MDIyMmYxIiwiaWF0IjoxNjA1ODEwOTM5LCJuYmYiOjE2MDU4MTA5MzksImV4cCI6MTYzNzM0NjkzOSwic3ViIjoiMjcyMSIsInNjb3BlcyI6W119.O6T1H8dz_ArNoHC5dm7jeEUkSTrvylprB3ou6ea-8NFBZrL0hTuhfJP9paymQjEGy92teNL5neTozj7oqRcnJ_0sX2EzAI9ozQKwjjU9CS7A7boLtYc8SQZ2wWjZmAacqshOd0TnOvOYoHGavvtQWVRNL51JHs-SB-NOyHvCuWUmb4JqwNLcvFQz9qpjbDvxE6jbb1c5xf23vKbevMlMTAIeWPunA3XBtysOSYVpFi3DYpiYZ8tTpH5cTnpjRP5WZn_bsqcnJ7b_YZEXUYyUnHLOQN4i7uiZ6rW978B_3nSzdDwSsZQ8erRAV7XCUbNrbJb_1aHQ34XXHKEQB9wG7OI8JZlrAwytwYGGzPIJcMrK7Qg3eCGn7CDSDC-YqHEbHO-7GCsiN0Wl9-VDyxiu-q1WJVSkiy7YVjQYV81jX3EGjVD89jVmauXV4AbNsCOzC2E42be0Cf0ZDEDPH9R087Hl8qv7uLUNxjpSS353ts-bhRBYiQywwsQxilY_SkTwkOXVuqw5PhkkEWw33aHz2Q3bzp41v45WV8F00-vEiYVadv3-SuV2GVSUshep1x9xMoSVqnNy0PHK84SUpIzk1eZrExKcCsHf_2XtzOV88cigLBF8ci_LiuSF5Vohtc-YzqF5Rhl3y2oso72pPyh3Gnw1fOZ3qGgRvjXAmQ-VEeQ"

//    fun getContactPets(api: ContactsApi, contactId: Long): Single<List<PetModel>> {
//        return api.getPetsOf(token, contactId).map {
//            it.data.map { pet ->
//                PetModel(
//                    pet.id,
//                    pet.name,
//                    pet.petCategoryDto.name
//                )
//            }
//        }
//    }


//    @Provides
//    fun createMultiContactDataStore(
//        apiProvider: IApiProvider
//    ): Store<Account, List<ContactDto>> {
//        val fetcher = Fetcher.of { k: Account -> apiProvider.provideContactApi(k.endpoint).getAllContacts(token).data }
//        return StoreBuilder.from(fetcher).build()
//    }

//    @Provides
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

//    @Provides
//    fun createContactPetsDataStore(
//        contactDao: ContactDao,
//        contactsApi: ContactsApi
//    ): Store<Long, List<PetDto>> {
//        val fetcher = Fetcher.of { contactId: Long ->
//            contactsApi.getPetsOfSuspending(token, contactId).data
//        }
//
//        return StoreBuilder.from(fetcher).build()
//    }

    interface IApiProvider {
        fun provideLoginApi(endpoint: String): LoginApi
        fun provideGenderApi(endpoint: String): GenderApi
        fun provideContactApi(account: AccountModel): ContactsApi
    }

    class ApiProvider @Inject constructor(
        private val clientBuilder: OkHttpClient.Builder,
        private val retrofitBuilder: Retrofit.Builder,
        private val accountDao: AccountDao
    ) :
        IApiProvider {

        private val unauthorizedRetrofitBuilder = retrofitBuilder.client(clientBuilder.build())

        override fun provideLoginApi(endpoint: String): LoginApi {
            return unauthorizedRetrofitBuilder.baseUrl(endpoint).build()
                .create(LoginApi::class.java)
        }

        override fun provideGenderApi(endpoint: String): GenderApi {
            return unauthorizedRetrofitBuilder.baseUrl(endpoint).build()
                .create(GenderApi::class.java)
        }

        private val contactApis = mutableMapOf<Long, ContactsApi>()
        override fun provideContactApi(account: AccountModel): ContactsApi {
            return contactApis.getOrPut(account.id) {
                retrofit(account).create(ContactsApi::class.java)
            }
        }

        private fun retrofit(
            account: AccountModel
        ): Retrofit {
            val token = accountDao.getUserTokenOf(account)
            val networkInterceptor = clientBuilder.addInterceptor { c ->
                val modifiedReq =
                    c.request()
                        .newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .addHeader("Connection", "close")
                        .build()
                c.proceed(modifiedReq)
            }

            val build = networkInterceptor.build()
            return retrofitBuilder.client(build).baseUrl(account.endpoint)
                .build()
        }
    }

    class LoginStoreProvider constructor(private val loginIApiProvider: IApiProvider) {
        fun provideLoginStore(endpoint: String): Store<LoginCred, TokenResponseDto> {
            val loginApi: LoginApi = loginIApiProvider.provideLoginApi(endpoint)
            return createLoginDataStore(loginApi)
        }

        private fun createLoginDataStore(
            loginApi: LoginApi
        ): Store<LoginCred, TokenResponseDto> {

            val fetcher = Fetcher.of { loginCred: LoginCred ->
                loginApi.login(loginCred.mail, loginCred.password)
            }

            return StoreBuilder.from(fetcher).build()
        }
    }

    class ContactsStoreProvider @Inject constructor(
        private val apiProvider: IApiProvider,
        val contactDao: ContactDao
    ) {
        private val accountToStoreMap = mutableMapOf<Long, Store<Any, List<ContactDto>>>()

        fun provideContactsStore(account: AccountModel): Store<Any, List<ContactDto>> {
            val contactApi = apiProvider.provideContactApi(account)
            return accountToStoreMap.getOrPut(account.id, {
                val fetcher = Fetcher.of { k: Any ->
                    contactApi.getAllContacts()
                        .data
                }

                StoreBuilder.from(fetcher).build()
            })
        }
    }

    class PetStoreProvider @Inject constructor(
        private val apiProvider: IApiProvider
    ) {
        private val accountToStoreMap = mutableMapOf<Long, Store<Long, List<PetDto>>>()

        fun providePetStore(account: AccountModel): Store<Long, List<PetDto>> {
            return accountToStoreMap.getOrPut(account.id) {
                createSinglePetStore(apiProvider.provideContactApi(account))
            }
        }

        fun createSinglePetStore(
            api: ContactsApi
        ): Store<Long, List<PetDto>> {
            val fetcher = Fetcher.of { k: Long ->
                api.getPetsOfSuspending(k).data
            }

            return StoreBuilder.from(fetcher).build()
        }

    }

    class SingleContactStoreProvider @Inject constructor(
        private val apiProvider: IApiProvider,
        private val contactDao: ContactDao
    ) {

        private val accountToStoreMap = mutableMapOf<Long, Store<Long, ContactDto>>()

        fun provideSingleContactStore(account: AccountModel): Store<Long, ContactDto> {
            return accountToStoreMap.getOrPut(account.id) {
                createSingleContactDataStore(contactDao, apiProvider.provideContactApi(account))
            }
        }

        fun createSingleContactDataStore(
            contactDao: ContactDao,
            api: ContactsApi
        ): Store<Long, ContactDto> {
            val fetcher = Fetcher.of { k: Long ->
                api.getSingleContactSuspending(k).data
            }

            return StoreBuilder.from(
                fetcher,
//                SourceOfTruth.of(
//                    reader = contactDao::readFull,
//                    writer = contactDao::insertContactDetail
//                )
            ).build()
        }

    }

    @Provides
    fun provideLoginStoreProvider(loginIApiProvider: IApiProvider): LoginStoreProvider {
        return LoginStoreProvider(loginIApiProvider)
    }

    @Provides
    fun provideSingleContactDataStoreProvider(apiProvider: IApiProvider,
                                      contactDao: ContactDao) : SingleContactStoreProvider {
        return SingleContactStoreProvider(apiProvider, contactDao)
    }

    @Provides
    fun providePetStoreProvider(apiProvider: IApiProvider) = PetStoreProvider(apiProvider)

    @Provides
    fun provideLoginApiProvider(
        clientBuilder: OkHttpClient.Builder,
        accountDao: AccountDao,
        retrofitBuilder: Retrofit.Builder
    ): IApiProvider {
        return ApiProvider(clientBuilder, retrofitBuilder, accountDao)
    }


    data class LoginCred(val endpoint: String, val mail: String, val password: String)
}