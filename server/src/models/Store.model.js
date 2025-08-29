const mongoose = require('mongoose');

const StoreSchema = new mongoose.Schema({
    // 가게를 소유한 사장님의 User ID
    ownerId: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
    name: { type: String, required: true },
    description: { type: String },
    address: { type: String, required: true },
    // 지도 검색을 위한 좌표 정보 (GeoJSON)
    location: {
        type: { type: String, enum: ['Point'], default: 'Point' },
        coordinates: { type: [Number], required: true }, // [longitude, latitude]
    },
    phone: { type: String },
    isOpen: { type: Boolean, default: true }, // 사장님이 수동으로 영업 상태 변경
    minOrderAmount: { type: Number, default: 0 }, // 최소 주문 금액
    deliveryFee: { type: Number, default: 3000 }, // 배달비
}, { timestamps: true });

// 좌표 기반 검색을 위해 2dsphere 인덱스 생성
StoreSchema.index({ location: '2dsphere' });

module.exports = mongoose.model('Store', StoreSchema);