import React from "react";
import {
  requireNativeComponent,
  StyleProp,
  ViewStyle,
  NativeModules
} from "react-native";
const { LivelikeSdk } = NativeModules;
const WidgetViewNative = requireNativeComponent("LivelikeWidgetView");

const LivelikeWidgetView = ({ programId, style }) => {
  if (LivelikeSdk.isReady()) {
    alert("READY");
    return <WidgetViewNative programId={programId} style={style} />;
  } else {
    console.log("[Livelike] Engagement SDK is not ready");
    return null;
  }
};

export default LivelikeWidgetView;
