const mongoose = require('mongoose');

const IngredientSchema = new mongoose.Schema({
    // 이 식자재가 속한 가게 ID
    storeId: { type: mongoose.Schema.Types.ObjectId, ref: 'Store', required: true },
    name: { type: String, required: true }, // 예: "돼지고기", "양파"
    unit: { type: String, required: true }, // 예: "g", "kg", "개"
    stock: { type: Number, default: 0 }, // 현재 재고량
    safetyStock: { type: Number, default: 0 }, // 재고 부족 알림을 보낼 기준량
}, { timestamps: true });

module.exports = mongoose.model('Ingredient', IngredientSchema);