package com.dimsum.structuralpattern.filter

class CriteriaSingle: Criteria {
    private val singlePersons: ArrayList<Person> = ArrayList()
    override fun meetCriteria(persons: List<Person>): ArrayList<Person> {
        for (person in persons) {
           if (person.maritalStatus.equals("SINGLE",true)) {
               singlePersons.add(person)
           }
        }
        return singlePersons
    }
}