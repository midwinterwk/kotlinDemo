package com.dimsum.designpattern

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimsum.behavioralpattern.chain.ChainPatternDemo
import com.dimsum.behavioralpattern.command.CommandPatternDemo
import com.dimsum.behavioralpattern.interpreter.InterpreterPatternDemo
import com.dimsum.behavioralpattern.iterator.IteratorPatternDemo
import com.dimsum.behavioralpattern.mediator.MediatorPatternDemo
import com.dimsum.behavioralpattern.memento.MementoPatternDemo
import com.dimsum.behavioralpattern.nullobject.NullPatternDemo
import com.dimsum.behavioralpattern.observer.ObserverPatternDemo
import com.dimsum.behavioralpattern.state.StatePatternDemo
import com.dimsum.behavioralpattern.strategy.StrategyPatternDemo
import com.dimsum.behavioralpattern.template.TemplatePatternDemo
import com.dimsum.behavioralpattern.visitor.VisitorPatternDemo
import com.dimsum.creationalpattern.abstractfactory.AbstractFactoryPatternDemo
import com.dimsum.creationalpattern.builder.BuilderDemo
import com.dimsum.creationalpattern.factory.FactoryPatternDemo
import com.dimsum.creationalpattern.prototype.PrototypePatternDemo
import com.dimsum.creationalpattern.singleton.SingletonPatternDemo
import com.dimsum.structuralpattern.adapter.AdapterPatternDemo
import com.dimsum.structuralpattern.bridge.BridgePatternDemo
import com.dimsum.structuralpattern.composite.CompositePatternDemo
import com.dimsum.structuralpattern.decorator.DecoratorPatternDemo
import com.dimsum.structuralpattern.facade.FacadePatternDemo
import com.dimsum.structuralpattern.filter.CriteriaPatternDemo
import com.dimsum.structuralpattern.flyweight.FlyweightPatternDemo
import com.dimsum.structuralpattern.proxy.ProxyPatternDemo
import kotlinx.android.synthetic.main.activity_design_main.*

class DesignMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_design_main)
        rv_design_pattern.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val designAdapter = DesignAdapter(
            arrayListOf<String>(
                // Create Pattern
                "Factory Pattern",
                "Abstract Factory Pattern",
                "Singleton Pattern",
                "Builder Pattern",
                "Prototype Pattern",
                // Structural Pattern
                "Adapter Pattern",
                "Bridge Pattern",
                "Filter Pattern",
                "Composite Pattern",
                "Decorator Pattern",
                "Facade Pattern",
                "Flyweight Pattern",
                "Proxy Pattern",
                // Behavioral Pattern
                "Chain of Responsibility Pattern",
                "Command Pattern",
                "Interpreter Pattern",
                "Iterator Pattern",
                "Mediator Pattern",
                "Memento Pattern",
                "Observer Pattern",
                "State Pattern",
                "Null Object Pattern",
                "Strategy Pattern",
                "Template Pattern",
                "Visitor Pattern"
            )
        )
        rv_design_pattern.adapter = designAdapter
        designAdapter.setOnDemoItemClickListener(object : DesignAdapter.IDemoItemClickListener {
            override fun onItemClickListener(position: Int) {
                when (position) {
                    // Create Pattern
                    0 -> FactoryPatternDemo.test()
                    1 -> AbstractFactoryPatternDemo.test()
                    2 -> SingletonPatternDemo.test()
                    3 -> BuilderDemo.test()
                    4 -> PrototypePatternDemo.test()
                    // Structural Pattern
                    5 -> AdapterPatternDemo.test()
                    6 -> BridgePatternDemo.test()
                    7 -> CriteriaPatternDemo.test()
                    8 -> CompositePatternDemo.test()
                    9 -> DecoratorPatternDemo.test()
                    10 -> FacadePatternDemo.test()
                    11 -> FlyweightPatternDemo.test()
                    12 -> ProxyPatternDemo.test()
                    // Behavioral Pattern
                    13 -> ChainPatternDemo.test()
                    14 -> CommandPatternDemo.test()
                    15 -> InterpreterPatternDemo.test()
                    16 -> IteratorPatternDemo.test()
                    17 -> MediatorPatternDemo.test()
                    18 -> MementoPatternDemo.test()
                    19 -> ObserverPatternDemo.test()
                    20 -> StatePatternDemo.test()
                    21 -> NullPatternDemo.test()
                    22 -> StrategyPatternDemo.test()
                    23 -> TemplatePatternDemo.test()
                    24 -> VisitorPatternDemo.test()
                }
            }
        })
    }
}