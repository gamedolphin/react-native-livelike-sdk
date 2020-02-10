package com.livelike.rnsdk

import android.util.Log
import android.widget.LinearLayout
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp

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
        view.updateContentSession(session)
    }

    override fun onDropViewInstance(view: LinearLayout) {
        super.onDropViewInstance(view)
        Log.v(LivelikeSDKModule.LIVE_LIKE_LOG, "onDropViewInstance")
    }
}
