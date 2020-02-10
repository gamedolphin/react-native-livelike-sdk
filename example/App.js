import React, { Component, useEffect, useState } from "react";
import { Platform, StyleSheet, Text, View, AsyncStorage } from "react-native";
import LivelikeSdk from "react-native-livelike-sdk";

const clientId = "4etKALJXv2HhuEZG0I3uX8H8DITuD8poaJIRdXhq";
const tokenKey = "userToken";

const App = () => {
  const [initialized, setInitialized] = useState(false);

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
        alert("INITIALIZED " + token);
      } catch (err) {
        alert(JSON.stringify(err));
      }
    };

    initialize();
  }, []);

  return (
    <View style={styles.container}>
      <Text style={styles.welcome}>☆LivelikeSdk example☆</Text>
      <Text style={styles.welcome}>☆NATIVE CALLBACK MESSAGE☆</Text>
      <Text style={styles.instructions}>{JSON.stringify(initialized)}</Text>
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
  }
});
