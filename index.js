import { NativeModules } from "react-native";

const { LivelikeSdk } = NativeModules;

const initializeSDK = (clientId, userAccessToken) =>
  LivelikeSdk.initializeSDK(clientId, userAccessToken);

const Wrapper = {
  initializeSDK
};

export default Wrapper;
