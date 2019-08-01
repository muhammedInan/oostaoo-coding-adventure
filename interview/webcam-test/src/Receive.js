import React from "react";
import { useReceivePeerState } from "react-peer";
import "./App.css";

const Receive = () => {
  const [state, isConnected, error] = useReceivePeerState("test-broker-id");

  return (
    <div className="App">
      <button>{state ? state.message : "waiting to connect"}</button>
    </div>
  );
};

export default Receive;
