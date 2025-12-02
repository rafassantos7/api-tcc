import axios from 'axios';

const api = axios.create({
  baseURL: 'https://deploy-levelup.onrender.com',
});

// Interceptor de Requisição: Adiciona o token ao cabeçalho Authorization
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Interceptor de Resposta: Trata erros de autenticação (401/403)
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && (error.response.status === 401 || error.response.status === 403)) {
      // Se o token for inválido ou expirado, limpa o storage e redireciona
      localStorage.removeItem('token');
      // Redirecionamento forçado para login (usando window.location pois estamos fora do componente React)
      if (!window.location.pathname.includes('/auth/login')) {
         window.location.href = '/auth/login';
      }
    }
    return Promise.reject(error);
  }
);

export default api;