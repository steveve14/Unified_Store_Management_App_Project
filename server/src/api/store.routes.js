// server/src/api/store.routes.js

const express = require('express');
const router = express.Router();
const storeController = require('../controllers/store.controller');

// POST /api/stores - 새로운 가게 등록
router.post('/', storeController.createStore);

// GET /api/stores - 모든 가게 목록 조회
router.get('/', storeController.getAllStores);

// GET /api/stores/:storeId - 특정 가게의 상세 정보 및 메뉴 조회
router.get('/:storeId', storeController.getStoreById);

// PUT /api/stores/:storeId - 특정 가게 정보 수정
router.put('/:storeId', storeController.updateStore);

// **중요**: router 객체 자체를 내보냅니다.
module.exports = router;