const mongoose = require('mongoose');

const OrderSchema = new mongoose.Schema({
    customerId: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
    storeId: { type: mongoose.Schema.Types.ObjectId, ref: 'Store', required: true },
    riderId: { type: mongoose.Schema.Types.ObjectId, ref: 'User', default: null },
    orderType: {
        type: String,
        enum: ['DELIVERY', 'DINE_IN', 'TAKE_OUT'],
        required: true,
    },
    // DINE_IN (매장 식사)일 경우 사용될 테이블 번호
    tableNumber: { type: String },
    items: [{
        menuId: { type: mongoose.Schema.Types.ObjectId, ref: 'Menu' },
        name: { type: String, required: true }, // 메뉴 이름 (원본 메뉴가 삭제/변경되어도 기록 유지)
        quantity: { type: Number, required: true },
        price: { type: Number, required: true }, // 주문 시점의 가격
        selectedOptions: [{
            groupName: String,
            optionName: String,
        }],
    }],
    totalPrice: { type: Number, required: true },
    deliveryAddress: { type: String }, // DELIVERY일 경우
    status: {
        type: String,
        enum: [
            'PENDING',        // 주문 대기 (고객이 주문 완료)
            'ACCEPTED',       // 주문 접수 (가게가 확인)
            'COOKING',        // 조리 중
            'READY',          // 조리 완료 (포장) / 서빙 완료 (매장) / 픽업 대기 (배달)
            'PICKED_UP',      // 픽업 완료 (라이더)
            'ON_THE_WAY',     // 배달 중
            'DELIVERED',      // 배달 완료
            'COMPLETED',      // 최종 완료 (매장/포장 주문)
            'CANCELLED'       // 주문 취소
        ],
        default: 'PENDING',
    },
    payment: {
        method: { type: String, enum: ['CARD', 'CASH', 'ETC'] },
        status: { type: String, enum: ['PAID', 'UNPAID'], default: 'UNPAID' }, // 후불 결제를 위해
        transactionId: { type: String },
    },
}, { timestamps: true });

module.exports = mongoose.model('Order', OrderSchema);