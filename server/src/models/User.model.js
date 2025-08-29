const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');

const UserSchema = new mongoose.Schema({
    email: { type: String, required: true, unique: true, trim: true },
    password: { type: String, required: true },
    name: { type: String, required: true },
    role: {
        type: String,
        enum: ['CUSTOMER', 'OWNER', 'RIDER', 'ADMIN'],
        default: 'CUSTOMER',
        required: true,
    },
    // 'OWNER' 역할일 경우, 관리하는 가게 ID를 연결합니다.
    storeId: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Store',
        default: null,
    },
}, { timestamps: true });

// 비밀번호 암호화
UserSchema.pre('save', async function (next) {
    if (!this.isModified('password')) return next();
    const salt = await bcrypt.genSalt(10);
    this.password = await bcrypt.hash(this.password, salt);
    next();
});

module.exports = mongoose.model('User', UserSchema);