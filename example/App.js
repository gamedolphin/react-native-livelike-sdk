import React, { Component, useEffect, useState } from "react";
import {
  LayoutAnimation,
  Platform,
  StyleSheet,
  Text,
  View,
  AsyncStorage,
  UIManager
} from "react-native";
import LivelikeSdk, { LivelikeWidgetView } from "react-native-livelike-sdk";

const clientId = "4etKALJXv2HhuEZG0I3uX8H8DITuD8poaJIRdXhq";
const tokenKey = "userToken";

const App = () => {
  const [initialized, setInitialized] = useState(false);
  const [widgetHeight, setWidgetHeight] = useState(0);

  useEffect(() => {
    if (Platform.OS === "android") {
      if (UIManager.setLayoutAnimationEnabledExperimental) {
        UIManager.setLayoutAnimationEnabledExperimental(true);
      }
    }
  }, []);

  useEffect(() => {
    const initialize = async () => {
      try {
        const userToken = await AsyncStorage.getItem(tokenKey);
        const token =
          userToken !== null
            ? await LivelikeSdk.initializeSDK(clientId, userToken)
            : await LivelikeSdk.initializeSDK(clientId);
        await AsyncStorage.setItem(tokenKey, token);

        setInitialized(true);
      } catch (err) {
        alert(JSON.stringify(err));
      }
    };

    initialize();

    const listener = ({ height, width }) => {
      LayoutAnimation.configureNext(LayoutAnimation.Presets.linear);
      console.log("[Livelike] set height " + height);
      setWidgetHeight(height);
    };
    const hideListener = () => {
      LayoutAnimation.configureNext(LayoutAnimation.Presets.linear);
      setWidgetHeight(0);
    };

    const analyticsListener = ({ eventKey, eventJson }) => {
      console.log(`[Livelike] ${eventKey} ${eventJson}`);
    };

    const emitter = LivelikeSdk.getWidgetListener();
    emitter.addListener("WidgetShown", listener);
    emitter.addListener("WidgetHidden", hideListener);
    emitter.addListener("AnalyticsEvent", analyticsListener);

    return () => {
      emitter.removeListener("WidgetShown", listener);
      emitter.removeListener("WidgetHidden", hideListener);
      emitter.removeListener("AnalyticsEvent", analyticsListener);
    };
  }, []);

  return (
    <View style={styles.container}>
      <View
        style={{
          height: widgetHeight,
          width: "100%",
          backgroundColor: "black",
          overflow: "hidden",
          justifyContent: "flex-start",
          flexDirection: "column",
          alignItems: "stretch"
        }}
      >
        <View
          style={{ minHeight: 1000, width: "100%", backgroundColor: "green" }}
        >
          {initialized ? (
            <LivelikeWidgetView
              programId="2b8b7ec1-5188-403e-ace0-1d117439537a"
              style={{ flex: 1 }}
            />
          ) : null}
        </View>
      </View>
      <Text style={styles.welcome}>☆LivelikeSdk example☆</Text>
      <Text style={styles.instructions}>
        Engagement SDK Ready : {JSON.stringify(initialized)}
      </Text>
    </View>
  );
};

export default App;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#F5FCFF"
  },
  welcome: {
    fontSize: 20,
    textAlign: "center",
    margin: 10
  },
  instructions: {
    textAlign: "center",
    color: "#333333",
    marginBottom: 5
  },

  sdk: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#000000"
  }
});
