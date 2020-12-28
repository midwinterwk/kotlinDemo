package com.dimsum.behavioralpattern.iterator

class IteratorPatternDemo {
    companion object {
        fun test() {
            val namesRepository = NameRepository()

            val iterator: Iterator =
                namesRepository.getIterator()
            while (iterator.hasNext()) {
                val name = iterator.next() as String?
                println("Name : $name")
            }
        }
    }
}