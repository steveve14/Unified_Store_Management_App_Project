// server/src/controllers/user.controller.js
const User = require('../models/User.model');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');

// 회원가입
exports.register = async (req, res) => {
    try {
        const { email, password, name } = req.body;

        // 이메일 중복 확인
        const existingUser = await User.findOne({ email });
        if (existingUser) {
            return res.status(400).json({ message: "이미 사용 중인 이메일입니다." });
        }

        // 사용자 생성
        const user = new User({ email, password, name });
        await user.save();

        res.status(201).json({ message: "회원가입이 성공적으로 완료되었습니다." });
    } catch (error) {
        res.status(500).json({ message: "서버 오류가 발생했습니다.", error });
    }
};

// 로그인
exports.login = async (req, res) => {
    try {
        const { email, password } = req.body;
        const user = await User.findOne({ email });
        if (!user) {
            return res.status(401).json({ message: "이메일 또는 비밀번호가 일치하지 않습니다." });
        }

        const isMatch = await bcrypt.compare(password, user.password);
        if (!isMatch) {
            return res.status(401).json({ message: "이메일 또는 비밀번호가 일치하지 않습니다." });
        }

        // JWT 토큰 생성
        const token = jwt.sign(
            { userId: user._id, role: user.role },
            process.env.JWT_SECRET,
            { expiresIn: '1d' } // 토큰 유효기간: 1일
        );

        res.status(200).json({ token, userId: user._id, name: user.name });

    } catch (error) {
        res.status(500).json({ message: "서버 오류가 발생했습니다.", error });
    }
};