// server/app.js

require('dotenv').config();
const express = require('express');
const cors = require('cors');
const mongoose = require('mongoose');

// 라우터 파일 가져오기
const userRoutes = require('./src/api/user.routes');
const storeRoutes = require('./src/api/store.routes');
const orderRoutes = require('./src/api/order.routes');
const tableRoutes = require('./src/api/table.routes');

const app = express();
const PORT = process.env.PORT || 5000;

// --- 데이터베이스 연결 ---
mongoose.connect(process.env.MONGO_URI, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
})
    .then(() => console.log('✅ MongoDB에 성공적으로 연결되었습니다.'))
    .catch(err => console.error('MongoDB 연결 실패:', err));
// --- 미들웨어 설정 ---
app.use(cors());
app.use(express.json());


// --- API 라우트 연결 ---
app.use('/api/users', userRoutes);
app.use('/api/stores', storeRoutes);
app.use('/api/orders', orderRoutes);
app.use('/api/tables', tableRoutes);


// --- 서버 실행 ---
app.listen(PORT, () => {
    console.log(`✅ 서버가 http://localhost:${PORT} 에서 실행 중입니다.`);
});