package com.birohcek.monicaapp.models

class RelationshipModel(
    val type: RelationMainType,
    val relationName: String,
    val contactFullName: String,
    val contactId: Long
)