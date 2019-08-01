import React, { useEffect, useState } from "react";
import { usePeerState } from "react-peer";
import Peer from "peerjs";
import Receive from "./Receive";
import "./App.css";

const App = () => {
  const [inputValue, setInputValue] = useState("");
  const [test, setTest] = useState("test-broker-id");
  const [state, setState, brokerId, connections, error] = usePeerState(
    {
      message: "hello",
      status: "connected"
    },
    { brokerId: test }
  );
  const [video, setVideo] = useState();

  useEffect(() => {
    sessionStorage.setItem("test", brokerId);
    console.log(brokerId, connections);
  }, []);

  console.log(connections);

  const getWebcam = async () => {
    try {
      const stream = await navigator.mediaDevices.getUserMedia({ video: true });
      console.log(stream);
      setVideo(stream);
    } catch (e) {
      return console.log(e);
    }
  };

  return (
    <div className="App">
      {video && (
        <video width="500" height="300" src={video} type="video/mp4" />
        /* <source src={video.MediaStream} type="video/mp4" /> */
        /* </video> */
      )}
      <button onClick={getWebcam}>Get webcam</button>
      <input value={inputValue} onChange={e => setInputValue(e.target.value)} />
      <button onClick={() => setState({ message: inputValue })}>
        Click here to connect with Nam
      </button>
      <div>{state ? state.message : null}</div>
      <Receive peerId={connections.length > 0 ? connections[0].peer : null} />
      <button onClick={() => setState({ ...state, status: "disconnected" })}>
        Close connection
      </button>
      <button
        onClick={async () => {
          connections[0].close();
          /* await setState({ ...state, status: "disconnected" });
          connections[0].open = false; */
        }}
      >
        Close canal
      </button>
    </div>
  );
};

export default App;
