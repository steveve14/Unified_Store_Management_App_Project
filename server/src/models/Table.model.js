const mongoose = require('mongoose');

const TableSchema = new mongoose.Schema({
    storeId: { type: mongoose.Schema.Types.ObjectId, ref: 'Store', required: true },
    tableNumber: { type: String, required: true }, // 예: "3", "A-1"
    status: {
        type: String,
        enum: ['EMPTY', 'OCCUPIED', 'AWAITING_PAYMENT', 'NEEDS_CLEANING'],
        default: 'EMPTY',
    },
    // 현재 이 테이블에서 진행 중인 DINE_IN 주문의 ID
    currentOrderId: { type: mongoose.Schema.Types.ObjectId, ref: 'Order', default: null },
}, { timestamps: true });

// 한 가게 안에서 테이블 번호는 고유해야 함
TableSchema.index({ storeId: 1, tableNumber: 1 }, { unique: true });

module.exports = mongoose.model('Table', TableSchema);