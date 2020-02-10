# react-native-livelike-sdk

## Getting started

Clone this repository and install the plugin locally

`$ npm install <location_of_project> --save`

### Mostly automatic installation

`$ react-native link react-native-livelike-sdk`

### Engagement SDK

This currently is only a wrapper around the android version of the Engagement SDK and uses a custom version of the SDK `v1.5.3.3`.

## Usage
```javascript
import LivelikeSdk, { LivelikeWidgetView } from "react-native-livelike-sdk";

// Initialize with locally stored user token
... 
const userToken = await AsyncStorage.getItem(tokenKey);
const token =
  userToken !== null
    ? await LivelikeSdk.initializeSDK(clientId, userToken)
    : await LivelikeSdk.initializeSDK(clientId);
await AsyncStorage.setItem(tokenKey, token);
...

// Listen to events
const emitter = LivelikeSdk.getWidgetListener();
emitter.addListener("WidgetShown", showListener);
emitter.addListener("WidgetHidden", hideListener);
emitter.addListener("AnalyticsEvent", analyticsListener);

// Render the widget view after initialization

<LivelikeWidgetView
    programId={programId}
    style={{ flex: 1 }} />

```

The example in the project lists out most of the wrapper api. 

### Customization
#### Android

Override the `colors.xml` and `dimens.xml` to customize the look and feel of the android sdk. It would not be possible to provide a unified JS controlled wrapper api right now without significant changes to the native SDKs. 

### iOS Support
Not yet
