require('dotenv').config();
const express = require('express');
const cors = require('cors');

// 라우터 파일 가져오기
const orderRoutes = require('./src/api/order.routes');
const storeRoutes = require('./src/api/store.routes');

const app = express();
const PORT = process.env.PORT || 5000;

// 미들웨어 설정
app.use(cors()); // CORS 허용
app.use(express.json()); // JSON 요청 본문 파싱

// API 라우트 연결
app.use('/api/orders', orderRoutes);
app.use('/api/stores', storeRoutes);

// 서버 실행
app.listen(PORT, () => {
    console.log(`✅ 서버가 http://localhost:${PORT} 에서 실행 중입니다.`);
});