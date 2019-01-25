package com.thorebenoit.enamel.kotlin.threading

import com.nhaarman.mockito_kotlin.whenever
import com.thorebenoit.enamel.kotlin.print
import org.junit.Assert.*
import org.junit.Test
import org.mockito.InOrder

class CoroutineLockTest {


    private enum class State {
        START,
        COROUTINE_STARTED,
        READY_TO_UNLOCK,
        ON_UNLOCK_CALLBACK,
        COROUTINE_UNLOCKED,
        TEST_FINISHED
    }

    @Test
    fun generic_test() {

        val sequence = CallSequenceAssertEnum<CoroutineLockTest.State>(true)

        with(sequence) {

            assertState(State.START)

            val lock = CoroutineLock {
                assertState(State.ON_UNLOCK_CALLBACK)
            }

            coroutine {
                assertState(State.COROUTINE_STARTED)
                lock.wait()
                assertState(State.COROUTINE_UNLOCKED)
            }

            Thread.sleep(500)
            assertState(State.READY_TO_UNLOCK)

            lock.unlock()

            Thread.sleep(50)
            assertState(State.TEST_FINISHED)

        }

    }
}