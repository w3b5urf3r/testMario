package com.mario.persistence

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mario.persistence.util.DbTest
import com.mario.persistence.util.LiveDataTestUtil.getValue
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */

@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest : DbTest() {
    private val sampleDao: NumbersDao by lazy { db.numbersDao() }

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    @Throws(Exception::class)
    fun insertUserAndLoadLiveData() {
        val sample = TestUtil.createSample(2, "sample1")
        sampleDao.insertAll(sample)

        val byName = getValue(sampleDao.findSampleByName("sample1"))
        assertEquals(byName?.name, sample.name)
    }

    @Test
    @Throws(Exception::class)
    fun insertUserAndLoadCoroutines() {
        runBlocking {
            val sample = TestUtil.createSample(2, "sample1")
            sampleDao.insertAll(sample)

            val byName = sampleDao.findSampleByNameCoroutine("sample1")
            assertEquals(byName?.name, sample.name)
        }
    }
}