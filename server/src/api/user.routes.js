// server/src/api/user.routes.js
const express = require('express');
const router = express.Router();
const userController = require('../controllers/user.controller');

// POST /api/users/register - 회원가입
router.post('/register', userController.register);

// POST /api/users/login - 로그인
router.post('/login', userController.login);

module.exports = router;