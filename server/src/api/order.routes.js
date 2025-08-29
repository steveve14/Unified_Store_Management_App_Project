const express = require('express');
const router = express.Router();
const orderController = require('../controllers/order.controller');

// POST /api/orders - 새로운 주문 생성
router.post('/', orderController.createOrder);

// GET /api/orders - 모든 주문 목록 조회
router.get('/', orderController.getAllOrders);

// PUT /api/orders/:orderId/status - 특정 주문의 상태 변경 (ID 형식을 맞추기 위해 :id로 변경하는 것을 추천)
// 예: router.put('/:id/status', orderController.updateOrderStatus);
// MongoDB의 _id는 orderId와 같은 문자열이 아닐 수 있으므로 일반적인 id를 사용합니다.
router.put('/:orderId/status', orderController.updateOrderStatus);


module.exports = router;