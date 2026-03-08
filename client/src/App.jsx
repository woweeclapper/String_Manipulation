import { useState } from "react";
import StringPanel from "./features/StringOperations/StringPanel";
import ArrayPanel from "./features/ArrayOperations/ArrayPanel";
import "./App.css"; // Global layout styles

{
  /*TODO: Styling and Layout post implmentation */
}
function App() {
  const [mode, setMode] = useState(null);

  return (
    <div className="app-container">
      <header className="app-header">
        <h1>Java String Manipulator</h1>
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
            <h2>What would you like to work with?</h2>
            <div className="choice-container">
              <div className="choice-card" onClick={() => setMode("string")}>
                <h3>String</h3>
                <p>Reverse and Shift operations</p>
              </div>
              <div className="choice-card" onClick={() => setMode("array")}>
                <h3>Array</h3>
                <p>Sum, Sort, and Separation</p>
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
