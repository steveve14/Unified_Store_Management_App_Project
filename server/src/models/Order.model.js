// const mongoose = require('mongoose');
//
// const OrderSchema = new mongoose.Schema({
//   orderId: { type: String, required: true, unique: true },
//   orderType: { type: String, enum: ['DELIVERY', 'DINE_IN', 'TAKE_OUT'], required: true },
//   tableNumber: { type: String }, // DINE_IN일 경우
//   items: [
//     {
//       menuName: String,
//       quantity: Number,
//       price: Number,
//     },
//   ],
//   status: { type: String, enum: ['PENDING', 'COOKING', 'SERVED', 'PAID'], default: 'PENDING' },
//   createdAt: { type: Date, default: Date.now },
// });
//
// module.exports = mongoose.model('Order', OrderSchema);