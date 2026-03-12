import "./ChatBot.css";

const ChatBot = ({ message, isAnimating }) => {
  return (
    <div className="chatbot-wrapper">
      <div className="chat-bubble">
        <p>{message}</p>
      </div>
      <div className={`bot-icon-container ${isAnimating ? "pulse" : ""}`}>
        {/* Replace with your GIF path */}
        <img
          src="/assets/bot-assistant.gif"
          alt="AI Assistant"
          className="bot-gif"
        />
      </div>
    </div>
  );
};

export default ChatBot;
