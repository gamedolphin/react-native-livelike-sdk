package com.livelike.rnsdk

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.livelike.engagementsdk.EngagementSDK
import com.livelike.engagementsdk.utils.registerLogsHandler

class LivelikeSDKModule(private val application: Application, private val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    lateinit var engagementSDK : EngagementSDK

    val LIVE_LIKE_LOG: String = "Livelike"

    override fun getName(): String {
        return "LivelikeSdk"
    }

    @ReactMethod
    fun sampleMethod(stringArgument: String, numberArgument: Int, callback: Callback) {
        // TODO: Implement some actually useful functionality
        callback.invoke("Received numberArgument: $numberArgument stringArgument: $stringArgument")
    }

    @ReactMethod
    fun initializeSDK(clientId: String, userAccessToken: String?, promise: Promise?) {

        engagementSDK = if(userAccessToken != null) { EngagementSDK(clientId, application, userAccessToken) } else { EngagementSDK(clientId, application) }
        engagementSDK.userStream.subscribe("_") {
            it?.let {
                promise?.resolve(it.accessToken)
            }

        }
        registerLogsHandler(object : (String) -> Unit {
            override fun invoke(text: String) {
                Handler(Looper.getMainLooper()).post {
                    Log.v(LIVE_LIKE_LOG,text)
                }
            }
        })
    }
}