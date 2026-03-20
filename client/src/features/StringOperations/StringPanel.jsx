import { useState } from "react";
import { postData } from "../../api/apiClient";
import "./StringPanel.css";

const StringPanel = ({
  //setBotMessage /*might have to get rid of this*/,
  setIsProcessing,
  onResult,
}) => {
  // 1. State Management: The "Source of Truth"
  const [inputText, setInputText] = useState("");
  const [operation, setOperation] = useState("reverse"); // Default to reverse
  const [shiftAmount, setShiftAmount] = useState(0);
  const [direction, setDirection] = useState("left");

  const [result, setResult] = useState(null);
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  // 2. The Logic: Handling the Submission
  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    setError(null);
    setResult(null);

    try {
      let endpoint = "/string/reverse";
      let payload = { text: inputText };

      // Switch payload based on selected operation
      if (operation === "shift") {
        endpoint = "/string/shift";
        payload = {
          text: inputText,
          numOfShifts: parseInt(shiftAmount),
          direction: direction,
        };
      }

      const data = await postData(endpoint, payload);
      const resultString = data.reversedText || data.shiftedText; // Assuming API returns either reversedText or shiftedText
      setResult(data);
      if (onResult) {
        onResult(resultString); // Pass result to ParticleSphere
      }
    } catch (err) {
      // This catches the 429 or 400 errors from our apiClient
      setError(err.message);
    } finally {
      setIsProcessing(false);
      setIsLoading(false);
    }
  };

  return (
    <div className="operation-card">
      <h2>String Manipulator</h2>

      <form onSubmit={handleSubmit}>
        <div className="input-group">
          <label>Input Text:</label>
          <input
            type="text"
            value={inputText}
            onChange={(e) => setInputText(e.target.value)}
            placeholder="Type something..."
            required
          />
        </div>

        <div className="input-group">
          <label>Operation:</label>
          <select
            value={operation}
            onChange={(e) => setOperation(e.target.value)}
          >
            <option value="reverse">Reverse</option>
            <option value="shift">Shift</option>
          </select>
        </div>

        {/* 3. Conditional Rendering: Only show shift options if 'shift' is selected */}
        {operation === "shift" && (
          <div className="shift-options">
            <div className="input-group">
              <label>Amount:</label>
              <input
                type="number"
                value={shiftAmount}
                onChange={(e) => setShiftAmount(e.target.value)}
                min="0"
              />
            </div>
            <div className="input-group">
              <label>Direction:</label>
              <select
                value={direction}
                onChange={(e) => setDirection(e.target.value)}
              >
                <option value="left">Left</option>
                <option value="right">Right</option>
              </select>
            </div>
          </div>
        )}

        <button type="submit" disabled={isLoading}>
          {isLoading ? "Processing..." : "Execute"}
        </button>
      </form>

      {/* 4. Feedback UI: Success and Error displays */}
      {error && <div className="error-banner">{error}</div>}

      {result && (
        <div className="result-area">
          <h3>Result:</h3>
          <p className="highlight">
            {result.reversedText || result.shiftedText}
          </p>
        </div>
      )}
    </div>
  );
};

export default StringPanel;
