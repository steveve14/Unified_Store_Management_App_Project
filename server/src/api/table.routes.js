// server/src/api/table.routes.js

const express = require('express');
const router = express.Router();
const tableController = require('../controllers/table.controller');

// GET /api/tables/store/:storeId - 특정 가게의 모든 테이블 정보 조회
router.get('/store/:storeId', tableController.getTablesByStore);

// POST /api/tables - 새로운 테이블 생성
router.post('/', tableController.createTable);

// PUT /api/tables/:tableId/status - 특정 테이블의 상태 변경
router.put('/:tableId/status', tableController.updateTableStatus);

// DELETE /api/tables/:tableId - 특정 테이블 삭제
router.delete('/:tableId', tableController.deleteTable);


// **중요**: router 객체 자체를 내보냅니다.
module.exports = router;