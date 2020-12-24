package com.birohcek.monicaapp.screens.contacts.edit

//import android.util.Log
//import com.birohcek.monicaapp.models.ContactModel
//import com.birohcek.monicaapp.models.DateModel
//import com.birohcek.monicaapp.networking.interceptors.ContactsInteractor
//import java.time.LocalDate
//import java.time.Period
//import javax.inject.Inject

//class ContactEditPresenter @Inject constructor(private val contactsInteractor: ContactsInteractor) :
//    ContactEditContract.Presenter {
//
//    private var contactId: Int? = null
//    private var contact: ContactModel? = null
//    var age = 25
//    var year = 1995
//    var day = 1
//    var month = 1
//
//    var view: ContactEditContract.View? = null
//
//    var ageUnknown = true
//    var ageBased = true
//    var isExact = false
//
//
//    override fun onSave() {
//
//        contactsInteractor.updateBirthDateOf(
//            contact ?: return, DateModel(
//                day,
//                month,
//                if (!ageUnknown &&
//                    !ageBased &&
//                    !isExact
//                ) null else year,
//                ageBased,
//                isExact
//            )
//        ).subscribe({
//            view?.pop()
//
//        }, {
//            Log.d("ContactEditPre", "Error while updating", it)
//        })
//    }
//
//    override fun onDayPicked(day: Int) {
//        this.day = day
//        calculateAge()
//    }
//
//    private fun calculateAge() {
//        val of = LocalDate.of(year, month, day)
//        val now = LocalDate.now()
//        age = Period.between(of, now).years
//    }
//
//    override fun onMonthPicked(month: Int) {
//        this.month = month
//        calculateAge()
//    }
//
//    override fun onYearPicked(year: Int) {
//        this.year = year
//        calculateAge()
//    }
//
//    override fun setContactId(id: Long) {
////        contactsInteractor.getSingleContact(id)
////            .subscribe({ response ->
////
////
////                this.contact = contact
////                onContactReceived()
////            }, {
////
////            })
//    }
//
//
//    private fun onContactReceived() {
//        val contactModel = this.contact!!
//        val birthDate = contactModel.birthDate
//        if (birthDate == null) {
//            ageUnknown = true
//        } else {
//            ageUnknown = false
//            if (birthDate.isAgeBased) {
//                year = birthDate.year!!
//            } else {
//                day = birthDate.day!!
//                month = birthDate.month!!
//
//                if (birthDate.isExact) {
//                    year = birthDate.year!!
//                }
//            }
//        }
//    }
//
//    override fun onAgePicked(age: Int) {
//        this.age = age
//        this.year = LocalDate.now().year - age
//    }
//
//    override fun aboutAgeSelected() {
//        view?.showAgePicker(age)
//        ageUnknown = false
//        ageBased = true
//        isExact = false
//    }
//
//    override fun knowDayAndMonthSelected() {
//        view?.showBirthdayPicker(day, month, null)
//        ageUnknown = false
//        ageBased = false
//        isExact = false
//    }
//
//    override fun onExactBirthdayKnownSelected() {
//        view?.showBirthdayPicker(day, month, year)
//        ageUnknown = false
//        ageBased = false
//        isExact = true
//    }
//
//    override fun onAgeUnknownSelected() {
//        view?.hideDateSelections()
//        ageUnknown = true
//        ageBased = false
//        isExact = false
//    }
//
//    override fun onDestroy() {
//        this.view = null
//    }
//
//    override fun onBind(view: ContactEditContract.View) {
//        this.view = view
//        onViewBound()
//    }
//
//    override fun onViewBound() {
//    }
//
//}
