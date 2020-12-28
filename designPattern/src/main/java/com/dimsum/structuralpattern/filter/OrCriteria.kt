package com.dimsum.structuralpattern.filter

class OrCriteria(val criteria: Criteria, val otherCriteria: Criteria): Criteria {
    override fun meetCriteria(persons: List<Person>): ArrayList<Person> {
        val firstCriteriaItems = criteria.meetCriteria(persons)
        val otherCriteriaItems = otherCriteria.meetCriteria(persons)
        for (person in otherCriteriaItems) {
            if (!firstCriteriaItems.contains(person)) {
                firstCriteriaItems.add(person)
            }
        }
        return firstCriteriaItems
    }
}