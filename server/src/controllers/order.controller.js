// server/src/controllers/order.controller.js

// 필요한 모델들을 가져옵니다.
const Order = require('../models/Order.model');
const Menu = require('../models/Menu.model'); // 주문 생성 시 메뉴 가격 등을 참조하기 위해
const User = require('../models/User.model'); // 주문 생성 시 고객 정보를 참조하기 위해 (필요 시)

/**
 * 새로운 주문을 생성하는 컨트롤러
 * - 안드로이드 앱에서 결제 후 주문 정보를 보낼 때 사용됩니다.
 */
exports.createOrder = async (req, res) => {
    try {
        // body에서 주문 정보를 받아옵니다.
        // 실제 앱에서는 사용자 인증(JWT 토큰 등)을 통해 customerId를 가져와야 합니다.
        const { customerId, storeId, orderType, items, totalPrice, deliveryAddress, tableNumber } = req.body;

        // 새로운 주문 객체를 생성합니다.
        const newOrder = new Order({
            customerId,
            storeId,
            orderType,
            items,
            totalPrice,
            deliveryAddress, // 배달이 아닐 경우 null일 수 있음
            tableNumber,     // 매장 식사가 아닐 경우 null일 수 있음
            status: 'PENDING', // 초기 상태는 '주문 대기'
            payment: {
                status: 'PAID' // 선불 결제를 가정
            }
        });

        // 데이터베이스에 주문을 저장합니다.
        const savedOrder = await newOrder.save();

        // TODO: 재고 관리 기능이 구현되면, 여기서 해당 주문의 레시피에 따라 재고를 차감하는 로직을 추가해야 합니다.

        res.status(201).json(savedOrder);

    } catch (error) {
        console.error("주문 생성 중 오류 발생:", error);
        res.status(500).json({ message: "서버 오류로 주문을 생성할 수 없습니다.", error: error.message });
    }
};


/**
 * 모든 주문 목록을 가져오는 컨트롤러
 * - 사장님 대시보드에서 실시간 주문 현황을 볼 때 사용됩니다.
 */
exports.getAllOrders = async (req, res) => {
    try {
        // 쿼리 파라미터로 가게 ID를 받아 해당 가게의 주문만 필터링할 수 있습니다.
        const { storeId } = req.query;
        const filter = storeId ? { storeId: storeId } : {};

        // 데이터베이스에서 주문을 찾아 최신순으로 정렬합니다.
        // populate를 사용하여 고객과 가게의 상세 정보를 함께 가져올 수 있습니다.
        const orders = await Order.find(filter)
            .populate('customerId', 'name email') // 고객의 이름과 이메일 정보 포함
            .sort({ createdAt: -1 }); // 최신 주문이 위로 오도록 정렬

        res.status(200).json(orders);

    } catch (error) {
        console.error("주문 목록 조회 중 오류 발생:", error);
        res.status(500).json({ message: "서버 오류로 주문 목록을 조회할 수 없습니다." });
    }
};

/**
 * 특정 주문의 상태를 변경하는 컨트롤러
 * - 사장님 대시보드에서 '조리중', '배달시작' 등으로 상태를 변경할 때 사용됩니다.
 */
exports.updateOrderStatus = async (req, res) => {
    try {
        const { orderId } = req.params;
        const { status } = req.body;

        // 유효한 상태 값인지 확인 (선택 사항이지만 안정성을 높임)
        const validStatuses = ['PENDING', 'ACCEPTED', 'COOKING', 'READY', 'PICKED_UP', 'ON_THE_WAY', 'DELIVERED', 'COMPLETED', 'CANCELLED'];
        if (!validStatuses.includes(status)) {
            return res.status(400).json({ message: "유효하지 않은 주문 상태입니다." });
        }

        // DB에서 해당 ID의 주문을 찾아 상태를 업데이트합니다.
        // { new: true } 옵션은 업데이트된 후의 문서를 반환하도록 합니다.
        const updatedOrder = await Order.findByIdAndUpdate(
            orderId,
            { status: status },
            { new: true }
        );

        if (!updatedOrder) {
            return res.status(404).json({ message: '주문을 찾을 수 없습니다.' });
        }

        // TODO: 주문 상태 변경(예: 배달 시작)에 따라 고객에게 푸시 알림을 보내는 로직을 추가할 수 있습니다.

        res.status(200).json(updatedOrder);

    } catch (error) {
        console.error("주문 상태 변경 중 오류 발생:", error);
        res.status(500).json({ message: "서버 오류로 주문 상태를 변경할 수 없습니다." });
    }
};