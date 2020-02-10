import { NativeEventEmitter, NativeModules } from "react-native";
const { LivelikeSdk } = NativeModules;
import LivelikeWidgetView from "./LivelikeWidgetView.js";

const initializeSDK = (clientId, userAccessToken) =>
  LivelikeSdk.initializeSDK(clientId, userAccessToken);

const eventEmitter = () => new NativeEventEmitter(LivelikeSdk);

const Wrapper = {
  initializeSDK,
  getWidgetListener: eventEmitter
};

export default Wrapper;
export { LivelikeWidgetView };
