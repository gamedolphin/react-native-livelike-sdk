import { NativeModules } from "react-native";
const { LivelikeSdk } = NativeModules;
import LivelikeWidgetView from "./LivelikeWidgetView.js";

const initializeSDK = (clientId, userAccessToken) =>
  LivelikeSdk.initializeSDK(clientId, userAccessToken);

const Wrapper = {
  initializeSDK
};

export default Wrapper;
export { LivelikeWidgetView };
