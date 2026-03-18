import { useState } from "react";
import StringPanel from "./features/StringOperations/StringPanel";
import ArrayPanel from "./features/ArrayOperations/ArrayPanel";
import ChatBot from "./components/ChatBot/ChatBot";
//import ParticleSphere from "./components/ParticleSphere/ParticleSphere";
import "./App.css"; // Global layout styles

{
  /*TODO: Styling and Layout post implmentation */
}
function App() {
  const [mode, setMode] = useState(null);
  const [botMessage, setBotMessage] = useState(
    "Hello! Would you like to work with Letters or Numbers?",
  );
  const [botStatus, setBotStatus] = useState("asking"); // "idle", "asking", "answering"
  const [isProcessing, setIsProcessing] = useState(false);
  // Helper to update bot when switching modes
  const handleModeSelect = (newMode) => {
    setMode(newMode);
    const msg =
      newMode === "string"
        ? "Magical Letters! What should I do with them?"
        : newMode === "array"
          ? "Math Wizardry! Give me some numbers to crunch."
          : "Hello again! Would you like to work with Letters or Numbers?";
    setBotMessage(msg);
    setBotStatus("idle");
  };

  return (
    <div className="app-container">
      <header className="app-header">
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
        {/* <ParticleSphere /> */}
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
          />
        ) : (
          <ArrayPanel
            setBotMessage={setBotMessage}
            setIsProcessing={setIsProcessing}
          />
        )}
      </main>
    </div>
  );
}

export default App;
