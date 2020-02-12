package com.livelike.rnsdk

import android.util.Log
import android.view.Choreographer
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.facebook.react.bridge.*
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.events.RCTEventEmitter
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

    fun updateContentSession(contentSession: LiveLikeContentSession ) {

        Log.v(LivelikeSDKModule.LIVE_LIKE_LOG, "updateContentSession")
        this.contentSession = contentSession
        contentSession.widgetInterceptor = object : WidgetInterceptor() {
            override fun widgetWantsToShow() {
                showWidget()
                Choreographer.getInstance().postFrameCallback(fallback)
            }

        }
        widgetView.setSession(contentSession,  object : WidgetListener {
            override fun onNewWidget(height: Int, width: Int) {
                Log.v(LivelikeSDKModule.LIVE_LIKE_LOG, "widget shown $height")
                Log.v(LivelikeSDKModule.LIVE_LIKE_LOG, "widget shown " + ViewUtils.pxToDp(applicationContext, height))

                val params = Arguments.createMap()
                params.putInt("height", ViewUtils.pxToDp(applicationContext, height))
                params.putInt("width", ViewUtils.pxToDp(applicationContext, width))
                sendEvent(LivelikeWidgetViewManager.WIDGET_SHOWN_EVENT,params)

            }

            override fun onRemoveWidget() {
                val params = Arguments.createMap()
                sendEvent(LivelikeWidgetViewManager.WIDGET_HIDDEN_EVENT, params)
            }
        })

        contentSession.analyticService.setEventObserver { eventKey, eventJson ->
            run {
                val params = Arguments.createMap()
                params.putString("eventKey", eventKey)
                params.putString("eventJson", eventJson.toString())
                sendEvent(LivelikeWidgetViewManager.ANALYTICS_EVENT, params)
            }
        }
    }

    private fun sendEvent(eventName: String, params: WritableMap) {
        val reactContext = context as ReactContext
        reactContext.getJSModule(RCTEventEmitter::class.java).receiveEvent(
                id,
                eventName,
                params)
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