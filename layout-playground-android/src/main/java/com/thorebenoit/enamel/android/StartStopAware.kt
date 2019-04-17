package com.thorebenoit.enamel.android

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

interface StartStopAware<ViewClass> {

    var view: ViewClass?
    fun start(view: ViewClass) {
        this.view = view
        onStart(view)
    }

    fun onStart(view: ViewClass) {}
    fun onStop(view: ViewClass) {}

    fun stop() {
        onStop(view!!)
        this.view = null
    }

}

fun <T : Any> LifecycleOwner.registerStartStopAware(presenter: StartStopAware<T>, view: T) {
    lifecycle.addObserver(object : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun start() {
            presenter.start(view)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun stop() {
            presenter.stop()
        }
    })
}