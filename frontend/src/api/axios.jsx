import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",
  headers: {
    "Access-Control-Allow-Origin": "*",
    "ngrok-skip-browser-warning": "true",
    "Content-Type": "application/json",
  },
});

api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("accessToken");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

let isTokenExpiredAlertShown = false;

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response) {
      const expectedTokenExpiredMessage = "JWT was expired or incorrect";
      const expectedAuthRequiredMessage =
        "Full authentication is required to access this resource";
      const actualMessage = error.response.data.message || "";

      if (
        (actualMessage === expectedTokenExpiredMessage ||
          actualMessage === expectedAuthRequiredMessage) &&
        !isTokenExpiredAlertShown
      ) {
        isTokenExpiredAlertShown = true;

        alert(
          actualMessage === expectedTokenExpiredMessage
            ? `Twój token wygasł. Zaloguj się ponownie.`
            : `Nie masz odpowiednich uprawnień.`
        );

        localStorage.removeItem("accessToken");
        localStorage.removeItem("id");
        window.location.href = "http://localhost:5173";
      }

      return Promise.reject(error);
    }
  }
);

export const resetTokenExpiredAlert = () => {
  isTokenExpiredAlertShown = false;
};

export default api;
