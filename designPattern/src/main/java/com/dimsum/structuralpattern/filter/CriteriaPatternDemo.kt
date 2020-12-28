package com.dimsum.structuralpattern.filter

class CriteriaPatternDemo {
    companion object {
        fun test() {
            val persons: MutableList<Person> = ArrayList()

            persons.add(Person("Robert", "Male", "Single"))
            persons.add(Person("John", "Male", "Married"))
            persons.add(Person("Laura", "Female", "Married"))
            persons.add(Person("Diana", "Female", "Single"))
            persons.add(Person("Mike", "Male", "Single"))
            persons.add(Person("Bobby", "Male", "Single"))

            val male: Criteria = CriteriaMale()
            val female: Criteria = CriteriaFemale()
            val single: Criteria = CriteriaSingle()
            val singleMale: Criteria = AndCriteria(single, male)
            val singleOrFemale: Criteria = OrCriteria(single, female)

            println("Males: ")
            printPersons(male.meetCriteria(persons))

            println("\nFemales: ")
            printPersons(female.meetCriteria(persons))

            println("\nSingle Males: ")
            printPersons(singleMale.meetCriteria(persons))

            println("\nSingle Or Females: ")
            printPersons(singleOrFemale.meetCriteria(persons))
        }

        private fun printPersons(persons: ArrayList<Person>) {
            for (person in persons) {
                println("$person")
            }
        }
    }
}