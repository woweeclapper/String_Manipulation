import { useEffect, useRef } from "react";
import { initSphere, triggerMorph } from "./SphereEngine";
import "./ParticleSphere.css";

//TODO: hook this up to display results after executing from the panel
const ParticleSphere = ({ result }) => {
  const mountRef = useRef(null);

  useEffect(() => {
    if (result?.trim() && typeof triggerMorph === "function") {
      triggerMorph(result.trim());
    }
  }, [result]);

  useEffect(() => {
    // This is the "Bridge" - it runs AFTER the HTML is on the screen
    const cleanup = initSphere(mountRef.current);
    return () => cleanup();
  }, []);

  return (
    <div className="particle-sphere-wrapper">
      <div ref={mountRef} id="container" />
    </div>
  );
};

export default ParticleSphere;
