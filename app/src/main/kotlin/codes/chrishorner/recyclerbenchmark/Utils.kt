package codes.chrishorner.recyclerbenchmark

import androidx.annotation.MainThread
import timber.log.Timber
import timber.log.Timber.DebugTree

private var timberPlanted = false

@MainThread
fun initTimber() {
  if (!timberPlanted) {
    Timber.plant(DebugTree())
    timberPlanted = true
  }
}
