import { useEffect, useRef } from "react";
import { initSphere } from "./SphereEngine";
import "./ParticleSphere.css";

const ParticleSphere = () => {
  const mountRef = useRef(null);

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
            placeholder="Type something..."
            maxLength="20"
          />
          <button id="typeBtn">
            <span>Create</span>
          </button>
        </div>
      </div>
    </div>
  );
};

export default ParticleSphere;
