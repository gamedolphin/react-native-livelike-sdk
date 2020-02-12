import React, { Component, useEffect, useState } from "react";
import {
  LayoutAnimation,
  Platform,
  StyleSheet,
  Text,
  View,
  UIManager
} from "react-native";
import AsyncStorage from "@react-native-community/async-storage";
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
  }, []);

  const analyticsListener = ({ eventKey, eventJson }) => {
    console.log(`[Livelike] ${eventKey} ${eventJson}`);
  };

  return (
    <View style={styles.container}>
      {initialized ? (
        <LivelikeWidgetView
          programId="2b8b7ec1-5188-403e-ace0-1d117439537a"
          style={{ flex: 1 }}
          onAnalytics={analyticsListener}
          animationPreset={LayoutAnimation.Presets.linear}
        />
      ) : null}
      <Text style={styles.welcome}>☆LivelikeSdk example☆</Text>
      <Text style={styles.instructions}>
        Engagement SDK Ready : {JSON.stringify(initialized)}
      </Text>
      {initialized ? (
        <LivelikeWidgetView
          programId="d96c979e-90e4-4f9f-8800-0816572b99a0"
          style={{ flex: 1 }}
          onAnalytics={analyticsListener}
          animationPreset={LayoutAnimation.Presets.linear}
        />
      ) : null}
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
