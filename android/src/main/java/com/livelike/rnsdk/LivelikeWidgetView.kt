package com.livelike.rnsdk

import android.util.Log
import android.view.Choreographer
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.LifecycleEventListener
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ThemedReactContext
import com.livelike.engagementsdk.LiveLikeContentSession
import com.livelike.engagementsdk.WidgetListener
import com.livelike.engagementsdk.services.messaging.proxies.WidgetInterceptor
import com.livelike.engagementsdk.widget.view.WidgetView
import com.livelike.rnsdk.util.ViewUtils

class LivelikeWidgetView(context: ThemedReactContext, private val applicationContext: ReactApplicationContext) : LinearLayout(context), LifecycleEventListener {

    private lateinit var contentSession: LiveLikeContentSession

    private var widgetView: WidgetView
    private var fallback: Choreographer.FrameCallback

    init {
        this.applicationContext.addLifecycleEventListener(this)
        this.fallback = Choreographer.FrameCallback {
            manuallyLayoutChildren()
            viewTreeObserver.dispatchOnGlobalLayout();
            // Choreographer.getInstance().postFrameCallback(this!!.fallback)
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

    fun updateContentSession(contentSession: LiveLikeContentSession, listener: WidgetListener ) {
        Log.v(LivelikeSDKModule.LIVE_LIKE_LOG, "updateContentSession")
        this.contentSession = contentSession
        contentSession.widgetInterceptor = object : WidgetInterceptor() {
            override fun widgetWantsToShow() {
                showWidget()
                Choreographer.getInstance().postFrameCallback(fallback)
            }

        }
        widgetView.setSession(contentSession, listener)
    }

    private fun manuallyLayoutChildren() {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.measure(MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY))
            child.layout(0, 0, child.measuredWidth, child.measuredHeight)
        }
    }
}