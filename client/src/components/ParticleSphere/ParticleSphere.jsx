import { useEffect, useRef, useState } from "react";
import { initSphere, triggerMorph } from "./SphereEngine";
import "./ParticleSphere.css";

const ParticleSphere = () => {
  const [inputValue, setInputValue] = useState("");
  const mountRef = useRef(null);

  //react version of the event listener
  const handleMorph = () => {
    if (inputValue.trim()) {
      triggerMorph(inputValue.trim());
      setInputValue(""); //clear input after morphing
    }
  };
  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      handleMorph();
    }
  };
  useEffect(() => {
    // This is the "Bridge" - it runs AFTER the HTML is on the screen
    const cleanup = initSphere(mountRef.current);
    return () => cleanup();
  }, []);

  return (
    <div className="particle-sphere-wrapper">
      <div ref={mountRef} id="container" />

      <div className="input-container">
        <div className="input-wrapper">
          <input
            type="text"
            id="morphText"
            value={inputValue}
            onChange={(e) => setInputValue(e.target.value)} //updates as you typed
            onKeyDown={handleKeyDown} //handles "Enter" key
            placeholder="Type something..."
            maxLength="20"
          />
          <button id="typeBtn" onClick={handleMorph}>
            <span>Create</span>
          </button>
        </div>
      </div>
    </div>
  );
};

export default ParticleSphere;
