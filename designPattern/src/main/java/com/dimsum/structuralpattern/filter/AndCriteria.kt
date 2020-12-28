package com.dimsum.structuralpattern.filter

class AndCriteria(val criteria: Criteria, val otherCriteria: Criteria): Criteria {
    override fun meetCriteria(persons: List<Person>): ArrayList<Person> {
        val firstCriteriaPersons = criteria.meetCriteria(persons)
        return otherCriteria.meetCriteria(firstCriteriaPersons)
    }
}