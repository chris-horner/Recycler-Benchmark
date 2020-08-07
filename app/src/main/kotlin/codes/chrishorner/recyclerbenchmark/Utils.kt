package codes.chrishorner.recyclerbenchmark

import android.graphics.Rect
import android.view.View
import android.view.WindowInsets
import androidx.annotation.MainThread
import androidx.core.view.updatePadding
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

/**
 * Indicates that this view should update its own padding to match that of [WindowInsets].
 *
 * This is useful for views that display themselves under the status bar or navigation bar,
 * allowing content to be displayed edge-to-edge.
 */
fun View.updatePaddingWithInsets(
    left: Boolean = false,
    top: Boolean = false,
    right: Boolean = false,
    bottom: Boolean = false
) {
  // Create a snapshot of padding.
  val initialPadding = Rect(paddingLeft, paddingTop, paddingRight, paddingBottom)

  doOnApplyWindowInsets { insets ->
    updatePadding(
        left = if (left) initialPadding.left + insets.systemWindowInsetLeft else initialPadding.left,
        top = if (top) initialPadding.top + insets.systemWindowInsetTop else initialPadding.top,
        right = if (right) initialPadding.right + insets.systemWindowInsetRight else initialPadding.right,
        bottom = if (bottom) initialPadding.bottom + insets.systemWindowInsetBottom else initialPadding.bottom
    )
  }
}

inline fun View.doOnApplyWindowInsets(crossinline block: (insets: WindowInsets) -> Unit) {
  // Set an actual OnApplyWindowInsetsListener which proxies to the given lambda.
  setOnApplyWindowInsetsListener { _, insets ->
    block(insets)
    return@setOnApplyWindowInsetsListener insets
  }

  if (isAttachedToWindow) {
    // We're already attached, just request as normal.
    requestApplyInsets()
  } else {
    // We're not attached to the hierarchy. Add a listener to request when we are.
    addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
      override fun onViewAttachedToWindow(v: View) {
        v.removeOnAttachStateChangeListener(this)
        v.requestApplyInsets()
      }

      override fun onViewDetachedFromWindow(v: View) = Unit
    })
  }
}
