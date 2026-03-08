// automatically picks the correct URL based on if ran 'npm run dev' or 'npm run build'
const BASE_URL = import.meta.env.VITE_API_BASE_URL;

export const postData = async (endpoint, payload) => {
  try {
    const response = await fetch(`${BASE_URL}${endpoint}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: JSON.stringify(payload),
    });

    const data = await response.json();

    if (!response.ok) {
      // Handle Rate Limiting (Bucket4j 429)
      if (response.status === 429) {
        throw new Error(
          "Slow down! You've reached the request limit. Please try again in a minute.",
        );
      }

      // Handle Spring Boot Validation Errors (400)
      // Extracts details from your ErrorResponse DTO
      const errorMessage = data.details
        ? data.details.join(", ")
        : data.message;
      throw new Error(errorMessage || "Server error occurred");
    }

    return data;
  } catch (error) {
    console.error("API Fetch Error:", error.message);
    throw error;
  }
};
