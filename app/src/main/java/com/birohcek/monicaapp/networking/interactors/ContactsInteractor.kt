package com.birohcek.monicaapp.networking.interactors

//class ContactsInteractor @Inject constructor(
//    private val apiProvider: StoreModule.IApiProvider,
//    private val singleContactStore: Store<Long, ContactDto>,
//    private val relationshipContainerMapper: RelationshipContainerMapper,
//    private val contactEntryMapper: ContactEntryMapper,
//    private val contactMapper: ContactMapper,
//    private val petStore: Store<Long, List<PetDto>>
//) {
//
//    companion object {
////        const val token =
////            "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMGU0OTY4MTQyYmE1ZGFlYjE2N2ViZDNiNDJjNTAxOWRjMTA3N2RmNWVkMTc0Nzg1ZDQ1OGRiOThjZGNlMWYzYWU4NzBlZjEyZTdhZDViM2MiLCJpYXQiOjE2MDM0NzExODAsIm5iZiI6MTYwMzQ3MTE4MCwiZXhwIjoxNjM1MDA3MTgwLCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.O25Xzh8FLy4oewOBRDpOL96X1sK2iMxhTbilTbZ2TClNtaBfzr3mUtODeLP_W8C-OhbVYyMYkHPAw1rbDXqlsNv7QeA6Tusj2Gd96MwemE0cqOjPxBUY04kJP4V2xRCk18A2vJNR5xudN7C-87gto5nZEp1cyT__OI8TX2-Z4EHiSGPMypMwUBgPfszaAObgLqEp7YdPqPmpcGUMOrZr9U1ny5lWcvB8d24PHnqlczouptOYzjHumRMO6-SaeVA8--XMYCmK5YsIk9-rcBhpTRltj4hhYBBPfGDzUy2QYE2HHpb6bHyDhyW8fWYkzXs3YJkIkeK10hz2iYvsjMkrfVUNHjeWue-1n84nyOf66qYJT3eY4GnoMqxxDxsNUffwnt7h6Ub--Pb8sfK5Ztc9cG97MU0nuLIcd0MyG65Z1ri9K3zAZvgcHimGz3ii_jGN56127lx7UH_B0opAdrGY-8pUqHCiVwu3JRHX99jKQBdoxzNnpdHuelR6VKGS5gjrIC_5QivKhsUZMDJUAhAoXnj9QIp4iS_y35c5RLagHcZELVlIWTtfLnNU_wLpY5y0xbtKmEU4m5MY9CYB2DZQr64DUXSBYPVsDe0uPWj8J_QSsZMtWhbkJ70Kc3q3oQYaHkaUmEniZBgmq06iBw2R0ffjefVTo5B03iulucMmRlQ"
//
////        const val token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxNiIsImp0aSI6ImIzZjdhM2I0ZDQ2ODQzM2JkZWU5ZTI4YzliNjhhNjhiMWQ0YzY1YjljNzU3OTVlYjVmZWU0ZWFmZjc4ODI5ZDdlMTliZmUyMTU2MDIyMmYxIiwiaWF0IjoxNjA1ODEwOTM5LCJuYmYiOjE2MDU4MTA5MzksImV4cCI6MTYzNzM0NjkzOSwic3ViIjoiMjcyMSIsInNjb3BlcyI6W119.O6T1H8dz_ArNoHC5dm7jeEUkSTrvylprB3ou6ea-8NFBZrL0hTuhfJP9paymQjEGy92teNL5neTozj7oqRcnJ_0sX2EzAI9ozQKwjjU9CS7A7boLtYc8SQZ2wWjZmAacqshOd0TnOvOYoHGavvtQWVRNL51JHs-SB-NOyHvCuWUmb4JqwNLcvFQz9qpjbDvxE6jbb1c5xf23vKbevMlMTAIeWPunA3XBtysOSYVpFi3DYpiYZ8tTpH5cTnpjRP5WZn_bsqcnJ7b_YZEXUYyUnHLOQN4i7uiZ6rW978B_3nSzdDwSsZQ8erRAV7XCUbNrbJb_1aHQ34XXHKEQB9wG7OI8JZlrAwytwYGGzPIJcMrK7Qg3eCGn7CDSDC-YqHEbHO-7GCsiN0Wl9-VDyxiu-q1WJVSkiy7YVjQYV81jX3EGjVD89jVmauXV4AbNsCOzC2E42be0Cf0ZDEDPH9R087Hl8qv7uLUNxjpSS353ts-bhRBYiQywwsQxilY_SkTwkOXVuqw5PhkkEWw33aHz2Q3bzp41v45WV8F00-vEiYVadv3-SuV2GVSUshep1x9xMoSVqnNy0PHK84SUpIzk1eZrExKcCsHf_2XtzOV88cigLBF8ci_LiuSF5Vohtc-YzqF5Rhl3y2oso72pPyh3Gnw1fOZ3qGgRvjXAmQ-VEeQ"
//    }
//
//    private fun contactModel(it: ContactDto): ContactModel {
//        return contactMapper.deserialize(it)
//    }
//
//    private fun ContactDto.rels(): List<RelationshipModel> {
//        return information?.relationshipTypeGroups?.let { it1 ->
//            relationshipContainerMapper.deserialize(
//                it1
//            )
//        }.orEmpty()
//    }
//
//    private fun fullname(it: ContactDto) =
//        it.firstName + " " + (it.lastName ?: "")
//
//    fun getSingleContact(contactId: Long): Observable<StoreResponse<ContactModel>> {
//        val contactModelsFlow =
//            singleContactStore.stream(StoreRequest.cached(contactId, refresh = true)).map {
//                when (it) {
//                    is StoreResponse.Data -> {
//                        StoreResponse.Data(contactModel(it.value), it.origin)
//                    }
//                    is StoreResponse -> {
//                        it as StoreResponse<ContactModel>
//                    }
//                }
//            }
//
//        val petStream = petStore.stream(StoreRequest.cached(contactId, refresh = true))
//
//        return contactModelsFlow.combine(petStream) { a, b ->
//            if (a is StoreResponse.Data) {
//
//                when (b) {
//                    is StoreResponse.Data -> {
//                        val contact = a.value
//                        val pets = b.value
//                        StoreResponse.Data(contact.withPets(pets), a.origin)
//                    }
//                    is StoreResponse.Loading -> {
//                        b
//                    }
//                    else -> {
//                        b as StoreResponse.Error
//                    }
//                }
//            } else {
//                a
//            }
//        }.asObservable()
//    }
//
//    private fun ContactModel.withPets(
//        pets: List<PetDto>
//    ): ContactModel {
//        return buildUpon().withPetModels(
//            pets.map {
//                PetModel(
//                    it.id,
//                    it.name,
//                    it.petCategoryDto.name
//                )
//            }
//        ).build()
//    }
//
//
////    fun getContactPets(contactId: Long): Single<List<PetModel>> {
////        return api.getPetsOf(token, contactId).map {
////            it.data.map { pet ->
////                PetModel(
////                    pet.id,
////                    pet.name,
////                    pet.petCategoryDto.name
////                )
////            }
////        }.observeOn(AndroidSchedulers.mainThread())
////    }
//
//    fun updateBirthDateOf(contact: ContactModel, birthDate: DateModel): Single<ContactModel> {
//
//        val age = if (birthDate.year == null) 0 else {
//            LocalDate.now().year - birthDate.year
//        }
//
//        val req = UpdateBirthdateRequest(
//            firstName = contact.firstName,
//            day = birthDate.day,
//            month = birthDate.month,
//            year = birthDate.year,
//            isAgeBased = birthDate.isAgeBased,
//            isDeceased = false,
//            isDeceasedDateKnown = false,
//            isKnown = birthDate.day != null || birthDate.year != null,
//            age = age
//        )
//
////        return api.updateBirthDate(token, contact.id, req)
////            .map { it.data }
////            .map { basicContact(it) }
////            .observeOn(AndroidSchedulers.mainThread())
//
//        return Single.error(NotImplementedError())
//    }
//
//    private fun basicContact(it: ContactDto): ContactModel {
//        return contactMapper.deserialize(it)
//    }
//
////    fun getGenders(): Single<List<GenderModel>> {
////
////        return genderApi.fetchGenders(token)
////            .map { it.data }
////            .map { genders ->
////                genders.map {
////                    GenderModel(it.id, it.name)
////                }
////            }
////            .observeOn(AndroidSchedulers.mainThread())
////
////    }
//
//    fun createContact(
//        firstName: CharSequence,
//        lastName: CharSequence?,
//        middleName: CharSequence?,
//        nickname: CharSequence?,
//        gender: GenderModel?
//    ): Single<ContactModel> {
//
//        return Single.error(NotImplementedError())
//
////        return api.createContact(
////            token,
////
////            CreateContactRequest(
////                firstName = firstName.toString(),
////                lastName = lastName?.toString(),
////                genderId = gender?.id,
////                nickName = nickname?.toString()
////            )
////        ).map { it.data }
////            .map {
////                basicContact(it)
////            }
////            .observeOn(AndroidSchedulers.mainThread())
//    }
//
//
//    fun getContactInfo(id: Long): Single<List<ContactInfoEntryModel>> {
//
////        return api.getContactFields(token, id).map { it.data }
////            .map { it.map { contactEntryMapper.deserialize(it) } }
////            .observeOn(AndroidSchedulers.mainThread())
//
//
//        return Single.error(NotImplementedError())
//
//    }
//
//    private fun ContactDto.contactInfos(): List<ContactInfoEntryModel> {
//        return this.contactFields?.map {
//            contactEntryMapper.deserialize(it)
//        }.orEmpty()
//    }
//
//}

