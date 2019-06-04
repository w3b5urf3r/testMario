package com.mario.test


import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.MultiDexApplication
import com.mario.test.di.MainComponent.appModule
import com.mario.test.di.networkModule
import com.mario.test.util.Constants
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.conf.ConfigurableKodein
import org.kodein.di.generic.instance
import rx_activity_result2.RxActivityResult
import timber.log.Timber

@SuppressLint("Registered")
class App : MultiDexApplication(), KodeinAware {
    override val kodein = ConfigurableKodein(mutable = true)
    private val constants: Constants by instance()
    private var overrideModule: Kodein.Module? = null

    companion object {
        lateinit var context: App
    }

    override fun onCreate() {
        super.onCreate()

        App.context = this
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
            //reviewer this is a proof of concepts for DI testability
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
}

fun Context.asApp() = this.applicationContext as App
