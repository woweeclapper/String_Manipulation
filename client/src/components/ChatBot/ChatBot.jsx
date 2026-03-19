import "./ChatBot.css";
import askGif from "../../assets/chatbot_ask.gif";
import answerGif from "../../assets/chatbot_answer.gif";
import idleGif from "../../assets/chatbot_idle.gif";

const ChatBot = ({ message, isAnimating, status }) => {
  //logic to determine which gif is shown
  const getBotImage = () => {
    switch (status) {
      case "asking":
        return askGif;
      case "answering": //TODO: take a look at this case so it actually show when the server returned an answer
        return answerGif;
      default:
        return idleGif;
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
