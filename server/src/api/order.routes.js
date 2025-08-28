const express = require('express');
const router = express.Router();
const orderController = require('../controllers/order.controller');

// GET /api/orders - 모든 주문 목록 조회
router.get('/', orderController.getAllOrders);

// PUT /api/orders/:orderId/status - 특정 주문의 상태 변경
router.put('/:orderId/status', orderController.updateOrderStatus);

module.exports = router;