// server/src/controllers/table.controller.js

const Table = require('../models/Table.model');
const Store = require('../models/Store.model');

/**
 * 특정 가게의 모든 테이블 정보를 조회하는 컨트롤러
 * - 직원용 태블릿에서 매장 전체 테이블 현황을 볼 때 사용됩니다.
 */
exports.getTablesByStore = async (req, res) => {
    try {
        const { storeId } = req.params;

        if (!storeId) {
            return res.status(400).json({ message: "가게 ID가 필요합니다." });
        }

        const tables = await Table.find({ storeId: storeId }).sort({ tableNumber: 1 });
        res.status(200).json(tables);

    } catch (error) {
        console.error("테이블 정보 조회 중 오류 발생:", error);
        res.status(500).json({ message: "서버 오류로 테이블 정보를 조회할 수 없습니다." });
    }
};

/**
 * 새로운 테이블을 생성(추가)하는 컨트롤러
 * - 사장님 대시보드에서 테이블을 추가할 때 사용됩니다.
 */
exports.createTable = async (req, res) => {
    try {
        const { storeId, tableNumber } = req.body;

        if (!storeId || !tableNumber) {
            return res.status(400).json({ message: "가게 ID와 테이블 번호는 필수입니다." });
        }

        // 해당 가게에 동일한 테이블 번호가 있는지 확인
        const existingTable = await Table.findOne({ storeId, tableNumber });
        if (existingTable) {
            return res.status(409).json({ message: "이미 존재하는 테이블 번호입니다." });
        }

        const newTable = new Table({
            storeId,
            tableNumber,
            status: 'EMPTY' // 초기 상태는 '빈 좌석'
        });

        const savedTable = await newTable.save();
        res.status(201).json(savedTable);

    } catch (error) {
        console.error("테이블 생성 중 오류 발생:", error);
        res.status(500).json({ message: "서버 오류로 테이블을 생성할 수 없습니다.", error: error.message });
    }
};


/**
 * 특정 테이블의 상태를 변경하는 컨트롤러
 * - 직원용 태블릿에서 손님이 착석하거나 결제 완료 후 테이블 상태를 변경할 때 사용됩니다.
 * - 예: EMPTY -> OCCUPIED, AWAITING_PAYMENT -> EMPTY
 */
exports.updateTableStatus = async (req, res) => {
    try {
        const { tableId } = req.params;
        const { status, currentOrderId } = req.body;

        // 유효한 상태 값인지 확인
        const validStatuses = ['EMPTY', 'OCCUPIED', 'AWAITING_PAYMENT', 'NEEDS_CLEANING'];
        if (!status || !validStatuses.includes(status)) {
            return res.status(400).json({ message: "유효하지 않은 상태 값입니다." });
        }

        const updateData = { status };

        // 테이블 상태가 'EMPTY'로 변경될 때는, 연결된 주문 ID를 초기화합니다.
        // 'OCCUPIED'가 될 때는, 새로 생성된 매장 주문 ID를 연결합니다.
        if (status === 'EMPTY') {
            updateData.currentOrderId = null;
        } else if (currentOrderId) {
            updateData.currentOrderId = currentOrderId;
        }

        const updatedTable = await Table.findByIdAndUpdate(
            tableId,
            updateData,
            { new: true }
        );

        if (!updatedTable) {
            return res.status(404).json({ message: "테이블을 찾을 수 없습니다." });
        }

        // TODO: 웹소켓(Socket.io)을 사용하여 테이블 상태 변경을 모든 클라이언트(직원용 태블릿)에 실시간으로 브로드캐스트하는 로직 추가

        res.status(200).json(updatedTable);

    } catch (error) {
        console.error("테이블 상태 변경 중 오류 발생:", error);
        res.status(500).json({ message: "서버 오류로 테이블 상태를 변경할 수 없습니다." });
    }
};


/**
 * 특정 테이블을 삭제하는 컨트롤러
 * - 사장님 대시보드에서 테이블을 제거할 때 사용됩니다.
 */
exports.deleteTable = async (req, res) => {
    try {
        const { tableId } = req.params;

        const deletedTable = await Table.findByIdAndDelete(tableId);

        if (!deletedTable) {
            return res.status(404).json({ message: "삭제할 테이블을 찾을 수 없습니다." });
        }

        res.status(200).json({ message: "테이블이 성공적으로 삭제되었습니다." });

    } catch (error) {
        console.error("테이블 삭제 중 오류 발생:", error);
    }
};
