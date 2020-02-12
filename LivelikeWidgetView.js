import React, { useState } from "react";
import {
  requireNativeComponent,
  StyleProp,
  ViewStyle,
  NativeModules,
  View,
  LayoutAnimation,
  StyleSheet
} from "react-native";
const WidgetViewNative = requireNativeComponent("LivelikeWidgetView");

const noop = () => {};
const transformNative = (...fns) => event =>
  fns.forEach(fn => fn(event.nativeEvent));

const styles = StyleSheet.create({
  innerContainer: {
    minHeight: 1000,
    width: "100%"
  },

  outerContainer: {
    width: "100%",
    backgroundColor: "black",
    overflow: "hidden",
    justifyContent: "flex-start",
    flexDirection: "column",
    alignItems: "stretch"
  }
});

const LivelikeWidgetView = ({
  programId,
  style,
  onWidgetShown = noop,
  onWidgetHidden = noop,
  onAnalytics = noop,
  animationPreset = null
}) => {
  const onAnalyticsCb = transformNative(onAnalytics);

  const [widgetHeight, setWidgetHeight] = useState(0);

  const layoutCb = show => ({ height, width }) => {
    if (animationPreset) {
      LayoutAnimation.configureNext(animationPreset);
    }
    setWidgetHeight(show ? height : 0);
  };

  const onShownCb = transformNative(onWidgetShown, layoutCb(true));

  const onHideCb = transformNative(onWidgetHidden, layoutCb(false));

  const heightStyle = {
    height: widgetHeight
  };

  return (
    <View style={[styles.outerContainer, heightStyle]}>
      <View style={styles.innerContainer}>
        <WidgetViewNative
          programId={programId}
          style={style}
          onWidgetShown={onShownCb}
          onWidgetHidden={onHideCb}
          onAnalytics={onAnalyticsCb}
        />
      </View>
    </View>
  );
};

export default LivelikeWidgetView;
