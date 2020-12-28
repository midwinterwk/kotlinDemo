package com.dimsum.structuralpattern.filter

class CriteriaMale: Criteria {
    override fun meetCriteria(persons: List<Person>): ArrayList<Person> {
        val malePersons: ArrayList<Person> = ArrayList<Person>()
        for (person in persons) {
            if (person.gender.equals("MALE", true)) {
                malePersons.add(person)
            }
        }
        return malePersons
    }
}