import "./ChatBot.css";

const ChatBot = ({ message, isAnimating }) => {
  // Logic to determine which GIF to show
  const getBotImage = () => {
    switch (status) {
      case "asking":
        return "/assets/chatbot_ask.gif";
      case "answering":
        return "/assets/chatbot_answer.gif";
      default:
        return "/assets/chatbot_idle.gif";
    }
  };

  return (
    <div className={`chatbot-container ${status}`}>
      <div className="chat-bubble">
        <p>{message}</p>
      </div>
      <div className={`bot-icon-container ${isAnimating ? "pulse" : ""}`}>
        {/* Replace with GIF path */}
        <img src={getBotImage()} alt="Chatbot Assistant" />
      </div>
    </div>
  );
};

export default ChatBot;
