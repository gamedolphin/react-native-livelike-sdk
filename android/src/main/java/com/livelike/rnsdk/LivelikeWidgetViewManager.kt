package com.livelike.rnsdk

import android.util.Log
import android.widget.LinearLayout
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.uimanager.annotations.ReactProp


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

        return mapOf(
                WIDGET_SHOWN_EVENT  to mapOf(REG_TYPE to WIDGET_SHOWN_EVENT),
                WIDGET_HIDDEN_EVENT to mapOf(REG_TYPE to WIDGET_HIDDEN_EVENT),
                ANALYTICS_EVENT to mapOf(REG_TYPE to ANALYTICS_EVENT)
        )

//        val original = super.getExportedCustomDirectEventTypeConstants()
//
//        return if (original != null) {
//            (map.asSequence() + original.asSequence())
//                    .distinct<Map.Entry<String, Any>>()
//                    .groupBy({ it.key }, { it.value })
//        }
//        else {
//            map
//        }
    }

    override fun onDropViewInstance(view: LinearLayout) {
        super.onDropViewInstance(view)
        Log.v(LivelikeSDKModule.LIVE_LIKE_LOG, "onDropViewInstance")
    }
}
