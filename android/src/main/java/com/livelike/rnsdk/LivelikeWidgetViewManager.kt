package com.livelike.rnsdk

import android.util.Log
import android.widget.LinearLayout
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.uimanager.annotations.ReactProp
import com.livelike.engagementsdk.WidgetListener
import com.livelike.rnsdk.util.ViewUtils
import com.facebook.react.uimanager.events.RCTEventEmitter
import com.facebook.react.common.MapBuilder

class LivelikeWidgetViewManager(val applicationContext: ReactApplicationContext) : ViewGroupManager<LinearLayout>() {

    companion object {
        const val REACT_CLASS = "LivelikeWidgetView"
        const val WIDGET_SHOWN_EVENT = "onWidgetShown"
        const val WIDGET_HIDDEN_EVENT = "onWidgetHidden"
        const val ANALYTICS_EVENT = "onAnalytics"
        const val REG_TYPE = "registrationName"
    }

    override fun getName(): String {
        return REACT_CLASS
    }

    override fun createViewInstance(reactContext: ThemedReactContext): LinearLayout {
        return LivelikeWidgetView(reactContext, applicationContext)
    }


    @ReactProp(name = "programId")
    fun setProgramId(view: LivelikeWidgetView, programId: String) {
        Log.v(LivelikeSDKModule.LIVE_LIKE_LOG, "setProgramId $programId")
        val session = LivelikeSDKModule.engagementSDK.createContentSession(programId)
        view.updateContentSession(session)
    }

    override fun getExportedCustomDirectEventTypeConstants() : Map<String,Any> {
        return MapBuilder.builder<String, Any>()
                .put(WIDGET_SHOWN_EVENT, MapBuilder.of(REG_TYPE, WIDGET_SHOWN_EVENT))
                .put(WIDGET_HIDDEN_EVENT, MapBuilder.of(REG_TYPE, WIDGET_HIDDEN_EVENT))
                .put(ANALYTICS_EVENT, MapBuilder.of(REG_TYPE, ANALYTICS_EVENT))
                .build()
    }

    override fun onDropViewInstance(view: LinearLayout) {
        super.onDropViewInstance(view)
        Log.v(LivelikeSDKModule.LIVE_LIKE_LOG, "onDropViewInstance")
    }
}
