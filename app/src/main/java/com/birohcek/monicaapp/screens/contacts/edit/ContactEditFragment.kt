//package com.birohcek.monicaapp.screens.contacts.edit
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.core.view.isGone
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.findNavController
//import com.birohcek.monicaapp.R
//import com.birohcek.monicaapp.databinding.FragmentContactEditBinding
//import dagger.hilt.android.AndroidEntryPoint
//import javax.inject.Inject
//
//@AndroidEntryPoint
//class ContactEditFragment : Fragment(), ContactEditContract.View {
//
//    private var _binding: FragmentContactEditBinding? = null
//    private val binding get() = _binding!!
//
//
//    @Inject
//    lateinit var presenter: ContactEditContract.Presenter
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//        presenter.onDestroy()
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentContactEditBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        bindRadio()
//
//
//        binding.toolbar.setOnMenuItemClickListener { menuItem ->
//            onSave()
//            true
//        }
//
//
//        binding.bwBirthday.onAgeSelected = { presenter.onAgePicked(it) }
//        binding.bwBirthday.onDaySelected = { presenter.onDayPicked(it) }
//        binding.bwBirthday.onMonthSelected = { presenter.onMonthPicked(it) }
//        binding.bwBirthday.onYearSelected = { presenter.onYearPicked(it) }
//
//        presenter.onBind(this)
//        presenter.setContactId(arguments?.getLong("CONTACT_ID") ?: 0L)
//    }
//
//    private fun onSave() {
//        presenter.onSave()
//    }
//
//    private fun bindRadio() {
//
//        binding.rgRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
//            when (i) {
//                R.id.rb_dont_know -> {
//                    presenter.onAgeUnknownSelected()
//                }
//
//                R.id.rb_about_age -> {
//                    presenter.aboutAgeSelected()
//                }
//
//                R.id.rb_day_month -> {
//                    presenter.knowDayAndMonthSelected()
//                }
//
//                R.id.rb_exact -> {
//                    presenter.onExactBirthdayKnownSelected()
//                }
//            }
//        }
//    }
//
//    override fun pop() {
//        findNavController().popBackStack()
//    }
//
//    override fun hideDateSelections() {
//        binding.bwBirthday.isGone = true
//    }
//
//    override fun showAgePicker(age: Int) {
//        binding.bwBirthday.isGone = false
//        binding.bwBirthday.showAge(age)
//    }
//
//    override fun showBirthdayPicker(day: Int, month: Int, year: Int?) {
//        binding.bwBirthday.isGone = false
//
//        if (year == null) {
//            binding.bwBirthday.dayMonth(day, month)
//        } else {
//            binding.bwBirthday.dayMonthYear(day, month, year)
//        }
//    }
//}