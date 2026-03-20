import { useState } from "react";
import StringPanel from "./features/StringOperations/StringPanel";
import ArrayPanel from "./features/ArrayOperations/ArrayPanel";
import ChatBot from "./components/ChatBot/ChatBot";
import ParticleSphere from "./components/ParticleSphere/ParticleSphere";
import "./App.css"; // Global layout styles

{
  /*TODO: Styling and Layout post implmentation */
  //TODO: Hook up particle sphere to display results from the panel
}
function App() {
  const [mode, setMode] = useState(null);
  const [botMessage, setBotMessage] = useState(
    "Hello! Would you like to work with Letters or Numbers?",
  );
  const [botStatus, setBotStatus] = useState("asking"); // "idle", "asking", "answering"
  const [isProcessing, setIsProcessing] = useState(false); //loading state
  const [sphereOutput, setSphereOutput] = useState(""); // State to hold output for the particle sphere
  // Helper to update bot when switching modes

  const handleModeSelect = (newMode) => {
    setMode(newMode);
    setSphereOutput(""); // Clear previous result when switching modes
    const msg =
      newMode === "string"
        ? "Magical Letters! What should I do with them?"
        : newMode === "array"
          ? "Math Wizardry! Give me some numbers to crunch."
          : "Hello again! Would you like to work with Letters or Numbers?"; //TODO: make this case shows up when switching back to selection screen
    setBotMessage(msg);
    setBotStatus("idle");
  };

  return (
    <div className="app-container">
      <header className="app-header">
        {/* TODO: prob some better title and description, but this is just a demo so its not super important */}
        <h1>Chatbot Operation</h1>
        <p>Full-Stack Demo: React + Spring Boot</p>
        {mode && (
          <button className="back-btn" onClick={() => setMode(null)}>
            ← Switch Tool
          </button>
        )}
      </header>

      <main className="full-screen-main">
        <ChatBot
          message={botMessage}
          isAnimating={isProcessing}
          status={botStatus}
        />
        <ParticleSphere result={sphereOutput} />
        {/* 2. The "Conditional Loop" Logic */}
        {!mode ? (
          <div className="selection-screen">
            <div className="choice-container">
              <div
                className="choice-card"
                onClick={() => handleModeSelect("string")}
              >
                <h3>Letters</h3>
              </div>
              <div
                className="choice-card"
                onClick={() => handleModeSelect("array")}
              >
                <h3>Numbers</h3>
              </div>
            </div>
          </div>
        ) : mode === "string" ? (
          <StringPanel
            setBotMessage={setBotMessage}
            setIsProcessing={setIsProcessing}
            onResult={setSphereOutput} // Pass result to ParticleSphere
          />
        ) : (
          <ArrayPanel
            setBotMessage={setBotMessage}
            setIsProcessing={setIsProcessing}
            onResult={setSphereOutput} // Pass result to ParticleSphere
          />
        )}
      </main>
    </div>
  );
}

export default App;
