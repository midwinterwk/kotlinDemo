package com.dimsum.structuralpattern.filter

interface Criteria {
    fun meetCriteria(persons: List<Person>): ArrayList<Person>
}