package com.livelike.rnsdk;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class LivelikeSdkModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public LivelikeSdkModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "LivelikeSdk";
    }

    @ReactMethod
    public void sampleMethod(String stringArgument, int numberArgument, Callback callback) {
        // TODO: Implement some actually useful functionality
        callback.invoke("Received numberArgument: " + numberArgument + " stringArgument: " + stringArgument);
    }
}
