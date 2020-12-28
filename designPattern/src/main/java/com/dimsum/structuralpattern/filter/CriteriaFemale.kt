package com.dimsum.structuralpattern.filter

class CriteriaFemale: Criteria {
    private val femalePersons: ArrayList<Person> = ArrayList()
    override fun meetCriteria(persons: List<Person>): ArrayList<Person> {
        for (person in persons) {
            if (person.gender.equals("FEMALE", true)) {
                femalePersons.add(person)
            }
        }
        return femalePersons
    }
}