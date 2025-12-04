// hooks/useAuth.js

import { useState, useEffect } from "react";
import axios from "axios";

// Cria uma instância fixa do axios pro seu backend Spring
const api = axios.create({
  baseURL: "http://localhost:8080", // porta do seu Spring Boot
});

export const useAuth = () => {
  const [token, setToken] = useState(localStorage.getItem("token"));

  // Configura o interceptor UMA VEZ SÓ
  useEffect(() => {
    const interceptor = api.interceptors.request.use(config => {
      const localToken = localStorage.getItem("token");

      if (localToken) {
        config.headers.Authorization = `Bearer ${localToken}`;
      }

      return config;
    });

    return () => {
      api.interceptors.request.eject(interceptor);
    };
  }, []);

  // LOGIN CONECTADO AO SEU SPRING
  const login = async (email, senha) => {
    try {
      const response = await api.post("/auth/login", {
        email: email,
        senha: senha
      });

      const newToken = response.data.token;

      localStorage.setItem("token", newToken);
      setToken(newToken);

      return response.data;

    } catch (error) {
      console.error("Erro ao fazer login: ", error);
      throw error;
    }
  };

  const logout = () => {
    localStorage.removeItem("token");
    setToken(null);
  };

  return { token, login, logout, api };
};
