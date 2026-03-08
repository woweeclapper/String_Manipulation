import StringPanel from "./features/StringOperations/StringPanel";
import ArrayPanel from "./features/ArrayOperations/ArrayPanel";
import "./App.css"; // Global layout styles

{
  /*TODO: Styling and Layout post implmentation */
}
function App() {
  return (
    <div className="app-container">
      <header className="app-header">
        <h1>Java String Manipulator</h1>
        <p>Full-Stack Demo: React + Spring Boot</p>
      </header>

      <main>
        {/*where components are Hooked Up */}
        <StringPanel />
        <ArrayPanel />
      </main>

      <footer className="app-footer">
        <p>Connected to: {import.meta.env.VITE_API_BASE_URL}</p>
      </footer>
    </div>
  );
}

export default App;
