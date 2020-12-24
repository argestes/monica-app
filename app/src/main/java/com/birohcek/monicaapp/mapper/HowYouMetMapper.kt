package com.birohcek.monicaapp.mapper

import com.birohcek.monicaapp.models.HowYouMetModel
import com.birohcek.monicaapp.models.ThroughContact
import com.birohcek.monicaapp.networking.models.HowYouMetDto
import javax.inject.Inject

class HowYouMetMapper @Inject constructor(private val dateMapper: SingleDateMapper): Mapper<HowYouMetDto, HowYouMetModel> {
    override fun deserialize(from: HowYouMetDto): HowYouMetModel {


        val metDate = dateMapper.deserialize(from.firstMetDate)
        val through = from.first_met_through?.let {
            ThroughContact(it.id, it.firstName)
        }

        val desc = from.generalInfo ?: null

        return HowYouMetModel(
            metDate,
            through,
            desc
        )
    }
}