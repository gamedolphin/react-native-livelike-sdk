package com.livelike.rnsdk

import android.util.Log
import android.widget.LinearLayout
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactContext
import com.livelike.engagementsdk.WidgetListener


class LivelikeWidgetViewManager (val applicationContext: ReactApplicationContext) : ViewGroupManager<LinearLayout>() {

    companion object {
        const val REACT_CLASS = "LivelikeWidgetView"
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
        view.updateContentSession(session, object: WidgetListener {
            override fun onNewWidget(height: Int, width: Int) {
                Log.v(LivelikeSDKModule.LIVE_LIKE_LOG, "widget shown $height")
                val params = Arguments.createMap()
                params.putInt("height", height)
                params.putInt("width", width)
                sendEvent(applicationContext, "WidgetShown", params)
            }

            override fun onRemoveWidget() {
                val params = Arguments.createMap()
                sendEvent(applicationContext, "WidgetHidden", params)
            }
        })
    }

    override fun onDropViewInstance(view: LinearLayout) {
        super.onDropViewInstance(view)
        Log.v(LivelikeSDKModule.LIVE_LIKE_LOG, "onDropViewInstance")
    }

    private fun sendEvent(reactContext: ReactContext,
                          eventName: String,
                          params: WritableMap?) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                .emit(eventName, params)
    }
}
