package com.dimsum.creationalpattern.singleton

class SingletonPatternDemo {

    companion object {
        fun test() {
            LazySingleton.getInstance().showMessage()
            LazySafeSingleton.getInstance().showMessage()
            HungrySingleton.getInstance().showMessage()
            DCLSingleton.getInstance().showMessage()
            SCSingleton.getInstance().showMessage()
            EnumSingleton.INSTANCE.whateverMethod()
        }
    }
}