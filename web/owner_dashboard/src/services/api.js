import axios from 'axios';

// 서버 주소
const API_BASE_URL = 'http://localhost:5000/api';

const apiClient = axios.create({
  baseURL: API_BASE_URL,
});

// 모든 주문을 가져오는 API 함수
export const fetchOrders = () => {
  return apiClient.get('/orders');
};

// 주문 상태를 업데이트하는 API 함수
export const updateOrderStatusApi = (orderId, status) => {
  return apiClient.put(`/orders/${orderId}/status`, { status });
};