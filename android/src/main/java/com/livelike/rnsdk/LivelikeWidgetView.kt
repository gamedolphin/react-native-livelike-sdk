package com.livelike.rnsdk

import android.util.Log
import android.view.Choreographer
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.facebook.react.bridge.LifecycleEventListener
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ThemedReactContext
import com.livelike.engagementsdk.LiveLikeContentSession
import com.livelike.engagementsdk.widget.view.WidgetView

class LivelikeWidgetView(context: ThemedReactContext, private val applicationContext: ReactApplicationContext) : LinearLayout(context), LifecycleEventListener {

    private lateinit var contentSession: LiveLikeContentSession

    private var widgetView: WidgetView
    private var fallback: Choreographer.FrameCallback

    init {
        this.applicationContext.addLifecycleEventListener(this)
        this.fallback = Choreographer.FrameCallback {
            manuallyLayoutChildren()
            viewTreeObserver.dispatchOnGlobalLayout();
            Choreographer.getInstance().postFrameCallback(this!!.fallback)
        }
        Choreographer.getInstance().postFrameCallback(fallback)
        val parentView = LayoutInflater.from(context).inflate(R.layout.widget_view, null) as LinearLayout;
        widgetView = parentView.findViewById(R.id.widget_view)
        addView(parentView)
        Log.v(LivelikeSDKModule.LIVE_LIKE_LOG, "Widget View Created")
    }

    override fun onHostResume() {
        contentSession.resume()
    }

    override fun onHostPause() {
        contentSession.pause()
    }

    override fun onHostDestroy() {
        contentSession.close()
    }

    fun updateContentSession(contentSession: LiveLikeContentSession) {
        Log.v(LivelikeSDKModule.LIVE_LIKE_LOG, "updateContentSession")
        this.contentSession = contentSession
        widgetView.setSession(contentSession)
    }

    private fun manuallyLayoutChildren() {
        for (i in 0 until childCount) {
            var child = getChildAt(i)
            child.measure(MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY))
            child.layout(0, 0, child.measuredWidth, child.measuredHeight)        }
    }
}