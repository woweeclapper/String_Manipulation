import { useState } from "react";
import StringPanel from "./features/StringOperations/StringPanel";
import ArrayPanel from "./features/ArrayOperations/ArrayPanel";
import "./App.css"; // Global layout styles

{
  /*TODO: Styling and Layout post implmentation */
}
function App() {
  const [mode, setMode] = useState(null);
  const [botMessage, setBotMessage] = useState(
    "Hello! Would you like to work with Letters or Numbers?",
  );
  const [isProcessing, setIsProcessing] = useState(false);

  // Helper to update bot when switching modes
  const handleModeSelect = (newMode) => {
    setMode(newMode);
    const msg =
      newMode === "string"
        ? "Magical Letters! What should I do with them?"
        : "Math Wizardry! Give me some numbers to crunch.";
    setBotMessage(msg);
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
        {/* 2. The "Conditional Loop" Logic */}
        {!mode ? (
          <div className="selection-screen">
            <h2 className="choice-header">
              Would you like to work with Letters or Numbers?
            </h2>
            <div className="choice-container">
              <div className="choice-card" onClick={() => setMode("string")}>
                <h3>Letters</h3>
                <p>Perform Magical Operation on a set of letters</p>
              </div>
              <div className="choice-card" onClick={() => setMode("array")}>
                <h3>Numbers</h3>
                <p>Activate your inner Math Wizard on a set of numbers</p>
              </div>
            </div>
          </div>
        ) : mode === "string" ? (
          <StringPanel />
        ) : (
          <ArrayPanel />
        )}
      </main>
    </div>
  );
}

export default App;
