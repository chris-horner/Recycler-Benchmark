package codes.chrishorner.recyclerbenchmark.ui

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.core.view.updatePadding
import timber.log.Timber
import timber.log.Timber.DebugTree

// This file contains a collection of odds and ends that make this little sample app easier to read.

private var timberPlanted = false

/**
 * Plant a [DebugTree] if it hasn't been already.
 */
@MainThread fun initTimber() {
  if (!timberPlanted) {
    Timber.plant(DebugTree())
    timberPlanted = true
  }
}

/**
 * Nicer version of [LayoutInflater.inflate].
 */
@Suppress("UNCHECKED_CAST")
fun <T : View> ViewGroup.inflate(@LayoutRes layout: Int, attach: Boolean = false): T =
    LayoutInflater.from(context).inflate(layout, this, attach) as T

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

/**
 * Nicer version of [Spinner.setOnItemSelectedListener].
 */
inline fun Spinner.onItemSelected(crossinline action: (position: Int) -> Unit) {

  onItemSelectedListener = object : OnItemSelectedListener {

    override fun onNothingSelected(parent: AdapterView<*>?) = Unit

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
      action(position)
    }
  }
}

fun View.dpToPx(dp: Int): Int = dpToPx(dp.toFloat()).toInt()

fun View.dpToPx(dp: Float): Float = dp * resources.displayMetrics.density
