package com.thorebenoit.enamel.kotlin.caching

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.thorebenoit.enamel.kotlin.caching.store.FileStore
import com.thorebenoit.enamel.kotlin.core.randomString
import com.thorebenoit.enamel.kotlin.threading.blockingGet
import com.thorebenoit.enamel.kotlin.time.seconds
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.File

class CachedDataTest {

    private val file = File("test.txt")
    @Before
    fun setup() {
        file.delete()
    }

    @After
    fun tearDown() {
        file.delete()
    }

    @Test
    fun `test FileStore last cached is 0 when just created`() {
        val fileCachedData = FileStore.create<String>(file)

        assertEquals(0L, fileCachedData.get().first)
    }

    @Test
    fun `test FileStore write cache`() {
        val fileCachedData = FileStore.create<String>(file)

        fileCachedData.store("123")

        assertEquals("123", fileCachedData.get().second)

        val lastWrite = System.currentTimeMillis() - fileCachedData.get().first
        assertTrue(lastWrite < 2000)// File written less than 2 seconds ago
    }

    @Test
    fun `test FileStore overwrite cache`() {
        val fileCachedData = FileStore.create<String>(file)

        fileCachedData.store("123")

        fileCachedData.store("456")

        assertEquals("456", fileCachedData.get().second)

        val lastWrite = System.currentTimeMillis() - fileCachedData.get().first
        assertTrue(lastWrite < 2000)// File written less than 2 seconds ago
    }

    private data class TestClass(val value: String = ('a'..'z').randomString(5))

    @Test
    fun `CachedData uses in flight data`() {
        val refresh = mock<() -> TestClass?>()
        whenever(refresh.invoke()).thenAnswer {
            Thread.sleep(1000)
            TestClass()
        }


        val cache: CachedData<TestClass> =
            FileCachedData.create(file = file, useInFlight = true, cachingTime = 0, refresh = refresh)

        cache.get()
        cache.get()
        cache.get()
        cache.get()

        Thread.sleep(10) // allows coroutines to start
        verify(refresh, times(1)).invoke()
    }

    @Test
    fun `CachedData doesn't use in flight data`() {
        val refresh = mock<() -> TestClass?>()
        whenever(refresh.invoke()).thenAnswer {
            Thread.sleep(1000)
            TestClass()
        }

        val cache: CachedData<TestClass> =
            FileCachedData.create(file = file, useInFlight = false, cachingTime = 0, refresh = refresh)

        cache.get()
        cache.get()
        cache.get()
        cache.get()

        Thread.sleep(10) // allows coroutines to start
        verify(refresh, times(4)).invoke()
    }

    @Test
    fun `CachedData uses caching time`() {
        val refresh = mock<() -> TestClass?>()
        whenever(refresh.invoke()).thenAnswer { TestClass() }
        val geySystemTime = mock<GeySystemTime>()

        val startAt = 0L

        whenever(geySystemTime.invoke()).thenReturn(startAt)


        val cache: CachedData<TestClass> =
            FileCachedData.create(file = file, getSystemTime = geySystemTime, cachingTime = 1.seconds, refresh = refresh)


        val first = cache.get().blockingGet()
        val first_bis = cache.get().blockingGet() // refresh shouldn't get called here

        assertEquals(first, first_bis)

        whenever(geySystemTime.invoke()).thenReturn(Long.MAX_VALUE)


        val third = cache.get().blockingGet() // refresh should get called here
        assertNotSame(first, third)

        verify(refresh, times(2)).invoke()
    }


}

