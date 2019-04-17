package com.thorebenoit.enamel.kotlin.threading

import com.thorebenoit.enamel.core.threading.CoroutineLock
import com.thorebenoit.enamel.core.threading.coroutine
import org.junit.Test

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