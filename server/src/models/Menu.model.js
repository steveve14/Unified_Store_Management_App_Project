const mongoose = require('mongoose');

const MenuOptionSchema = new mongoose.Schema({
    name: { type: String, required: true }, // 예: "맵기 조절", "사이즈"
    options: [{
        name: { type: String, required: true }, // 예: "순한맛", "M"
        price: { type: Number, default: 0 }, // 옵션 추가 금액
    }],
});

const MenuSchema = new mongoose.Schema({
    // 이 메뉴가 속한 가게 ID
    storeId: { type: mongoose.Schema.Types.ObjectId, ref: 'Store', required: true },
    name: { type: String, required: true },
    description: { type: String },
    price: { type: Number, required: true },
    imageUrl: { type: String },
    category: { type: String, default: '메인 메뉴' },
    options: [MenuOptionSchema],
    isSoldOut: { type: Boolean, default: false }, // 재고와 연동되거나 수동으로 품절 처리
}, { timestamps: true });

module.exports = mongoose.model('Menu', MenuSchema);
