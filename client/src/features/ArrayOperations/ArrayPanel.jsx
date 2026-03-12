import { useState } from "react";
import { postData } from "../../api/apiClient";
import "./ArrayPanel.css";

const ArrayPanel = () => {
  const [rawInput, setRawInput] = useState("");
  const [operation, setOperation] = useState("sum");
  const [sortDirection, setSortDirection] = useState("ascending");
  const [sepType, setSepType] = useState("parity");

  const [result, setResult] = useState(null);
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  // Helper to turn "1, 2, 3" into [1, 2, 3]
  const parseInput = (str) => {
    return str
      .split(",")
      .map((num) => num.trim())
      .filter((num) => num !== "" && !isNaN(num))
      .map(Number);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    setError(null);
    setResult(null);

    const numbersList = parseInput(rawInput);

    if (numbersList.length === 0) {
      setError("Please enter at least one valid number.");
      setIsLoading(false);
      return;
    }

    try {
      let endpoint = `/array/${operation}`;
      let payload = { numbersList };

      // Append extra DTO fields based on operation
      if (operation === "sort") payload.orderType = sortDirection;
      if (operation === "separate") payload.separationType = sepType;

      const data = await postData(endpoint, payload);
      setResult(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="operation-card array-card">
      <h2>Array Manipulator</h2>
      <form onSubmit={handleSubmit}>
        <div className="input-group">
          <label>Numbers (comma separated):</label>
          <input
            type="text"
            value={rawInput}
            onChange={(e) => setRawInput(e.target.value)}
            placeholder="e.g. 10, -5, 22.5, 0"
            required
          />
        </div>

        <div className="input-group">
          <label>Operation:</label>
          <select
            value={operation}
            onChange={(e) => setOperation(e.target.value)}
          >
            <option value="sum">Total Sum</option>
            <option value="sort">Sort</option>
            <option value="separate">Separate</option>
          </select>
        </div>

        {/* Dynamic Options based on Backend DTOs */}
        {operation === "sort" && (
          <div className="input-group">
            <label>Order:</label>
            <select
              value={sortDirection}
              onChange={(e) => setSortDirection(e.target.value)}
            >
              <option value="ascending">Ascending</option>
              <option value="descending">Descending</option>
            </select>
          </div>
        )}

        {operation === "separate" && (
          <div className="input-group">
            <label>Criteria:</label>
            <select
              value={sepType}
              onChange={(e) => setSepType(e.target.value)}
            >
              <option value="parity">Even/Odd</option>
              <option value="sign">Positive/Negative</option>
            </select>
          </div>
        )}

        <button type="submit" disabled={isLoading}>
          {isLoading ? "Calculating..." : "Compute"}
        </button>
      </form>

      {error && <div className="error-banner">{error}</div>}

      {result && (
        <div className="result-area">
          <h3>Result:</h3>
          <pre className="highlight">{JSON.stringify(result, null, 2)}</pre>
        </div>
      )}
    </div>
  );
};

export default ArrayPanel;
