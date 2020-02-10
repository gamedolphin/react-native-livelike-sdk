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
  return <WidgetViewNative programId={programId} style={style} />;
};

export default LivelikeWidgetView;
