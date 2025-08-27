// 임시 데이터 (DB 연동 전 테스트용)
let mockOrders = [
    { orderId: 'ORD1001', orderType: 'DINE_IN', tableNumber: '3', items: [{ menuName: '김치찌개', quantity: 2 }], status: 'PENDING' },
    { orderId: 'ORD1002', orderType: 'DELIVERY', items: [{ menuName: '제육볶음', quantity: 1 }], status: 'COOKING' },
    { orderId: 'ORD1003', orderType: 'DINE_IN', tableNumber: '5', items: [{ menuName: '된장찌개', quantity: 1 }], status: 'PENDING' },
];

// 모든 주문 가져오기
exports.getAllOrders = (req, res) => {
    // TODO: 실제 DB에서 데이터를 조회하는 로직으로 변경해야 합니다.
    res.status(200).json(mockOrders);
};

// 특정 주문 상태 변경하기
exports.updateOrderStatus = (req, res) => {
    const { orderId } = req.params;
    const { status } = req.body;

    // TODO: 실제 DB에서 해당 주문을 찾아 상태를 업데이트해야 합니다.
    const orderIndex = mockOrders.findIndex(o => o.orderId === orderId);

    if (orderIndex === -1) {
        return res.status(404).json({ message: '주문을 찾을 수 없습니다.' });
    }

    mockOrders[orderIndex].status = status;
    res.status(200).json(mockOrders[orderIndex]);
};