package com.mario.test.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mario.network.RxAuthBus
import com.mario.network.auth.AuthPass
import com.mario.network.auth.NotAuthorised
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import java.util.concurrent.TimeUnit

abstract class BaseActivity : AppCompatActivity(), KodeinAware {
    override val kodein by closestKodein()
    private val rxAuthBus by instance<RxAuthBus>()
    private val compositeDisposable = CompositeDisposable()
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