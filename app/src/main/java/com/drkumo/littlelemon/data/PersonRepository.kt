package com.drkumo.littlelemon.data

import com.drkumo.littlelemon.model.Person

interface PersonRepository {
    suspend fun savePerson(person: Person)
    suspend fun readPerson(): Person?
    suspend fun clearPerson()
}