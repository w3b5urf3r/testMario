package com.mario.test.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mario.test.util.RxBus
import com.sentia.network.RxAuthBus
import com.sentia.network.auth.AuthPass
import com.sentia.network.auth.NotAuthorised
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import java.util.concurrent.TimeUnit

/**
 * Created by mariolopez on 27/12/17.
 */
abstract class BaseActivity : AppCompatActivity(), KodeinAware {
    override val kodein by closestKodein()
    protected val rxBus by instance<RxBus>()
    protected val rxAuthBus by instance<RxAuthBus>()
    protected val compositeDisposable = CompositeDisposable()
    protected open val handleExitScreenEvent = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositeDisposable += rxAuthBus.observe(AuthPass::class)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    when (it) {
                        is NotAuthorised -> {
                            if (handleExitScreenEvent) {
                                finishAffinity()
                                // startActivity(intentFor<YourLoginActivityHere>())
                            }
                        }
                    }
                }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}