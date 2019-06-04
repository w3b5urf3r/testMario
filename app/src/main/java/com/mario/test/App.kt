package com.mario.test

/**
 * Created by mariolopez on 27/12/17.
 */

import android.app.Application
import android.content.Context
import com.mario.test.di.MainComponent.appModule
import com.mario.test.di.networkModule
import com.mario.test.util.Constants
import com.mario.test.util.connectivity.NetworkStatusManager
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.conf.ConfigurableKodein
import org.kodein.di.generic.instance
import rx_activity_result2.RxActivityResult
import timber.log.Timber

class App : Application(), KodeinAware {
    override val kodein = ConfigurableKodein(mutable = true)
    private val constants: Constants by instance()
    var overrideModule: Kodein.Module? = null
    private val networkStatusManager: NetworkStatusManager by instance()


    override fun onCreate() {
        super.onCreate()

        context = this
        configKodein()
        constants.init()

        initTimber()

        RxActivityResult.register(this)
    }

    fun configKodein() {
        kodein.apply {
            clear()
            addImport(androidXModule(this@App))
            addImport(networkModule)
            addImport(appModule, allowOverride = true)
            if (overrideModule != null) {
                addImport(overrideModule!!, true)
            }
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String? {
                    return "[${super.createStackElementTag(element)}#${element.methodName}:${element.lineNumber}]"
                }
            })
        }
    }
    companion object {
        lateinit var context: App
    }
}
fun Context.asApp() = this.applicationContext as App
