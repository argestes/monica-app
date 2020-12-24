//package com.birohcek.monicaapp.screens.contacts.edit
//
//interface BaseView {
//
//}
//
//interface BasePresenter<V : BaseView> {
//    fun onDestroy()
//
//    fun onBind(view: V)
//
//    fun onViewBound()
//
//}
//
//interface ContactEditContract {
//
//    interface View : BaseView {
//        fun hideDateSelections()
//        fun showAgePicker(age: Int)
//        fun showBirthdayPicker(day: Int, month: Int, year: Int?)
//        fun pop()
//
//    }
//
//    interface Presenter : BasePresenter<View> {
//        fun onAgeUnknownSelected()
//        fun aboutAgeSelected()
//        fun knowDayAndMonthSelected()
//        fun onExactBirthdayKnownSelected()
//        fun onSave()
//        fun onAgePicked(age: Int)
//        fun onDayPicked(day: Int)
//        fun onMonthPicked(month: Int)
//        fun onYearPicked(year: Int)
//        fun setContactId(id: Long)
//    }
//}