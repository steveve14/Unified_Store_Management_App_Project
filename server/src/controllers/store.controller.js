// server/src/controllers/store.controller.js

const Store = require('../models/Store.model');
const Menu = require('../models/Menu.model');
const User = require('../models/User.model');

/**
 * 새로운 가게를 등록하는 컨트롤러
 * - 사장님이 가입 후 자신의 가게 정보를 처음 등록할 때 사용됩니다.
 */
exports.createStore = async (req, res) => {
    try {
        // body에서 가게 정보를 받아옵니다.
        // 실제 앱에서는 사용자 인증(JWT)을 통해 ownerId를 자동으로 할당해야 합니다.
        const { ownerId, name, description, address, phone, minOrderAmount, deliveryFee } = req.body;

        // 필수 정보 확인
        if (!ownerId || !name || !address) {
            return res.status(400).json({ message: "사장님 ID, 가게 이름, 주소는 필수입니다." });
        }

        // 이미 가게를 등록했는지 확인 (선택 사항: 1명의 사장님은 1개의 가게만)
        const existingStore = await Store.findOne({ ownerId });
        if (existingStore) {
            return res.status(409).json({ message: "이미 등록된 가게가 있습니다." });
        }

        // 새로운 가게 객체 생성
        const newStore = new Store({
            ownerId,
            name,
            description,
            address,
            phone,
            minOrderAmount,
            deliveryFee
            // TODO: 주소를 좌표로 변환하는 로직(Geocoding)을 추가하여 location 필드도 저장해야 합니다.
        });

        const savedStore = await newStore.save();

        // 가게를 생성한 유저의 role을 'OWNER'로 변경하고, storeId를 연결합니다.
        await User.findByIdAndUpdate(ownerId, { role: 'OWNER', storeId: savedStore._id });

        res.status(201).json(savedStore);

    } catch (error) {
        console.error("가게 생성 중 오류 발생:", error);
        res.status(500).json({ message: "서버 오류로 가게를 생성할 수 없습니다.", error: error.message });
    }
};

/**
 * 모든 가게 목록을 조회하는 컨트롤러
 * - 고객용 앱의 메인 화면에서 사용됩니다.
 */
exports.getAllStores = async (req, res) => {
    try {
        // DB에서 모든 가게 정보를 조회합니다.
        const stores = await Store.find({});
        res.status(200).json(stores);
    } catch (error) {
        console.error("가게 목록 조회 중 오류 발생:", error);
        res.status(500).json({ message: "서버 오류로 가게 목록을 조회할 수 없습니다." });
    }
};

/**
 * 특정 가게의 상세 정보와 메뉴 목록을 조회하는 컨트롤러
 * - 고객이 가게 목록에서 특정 가게를 클릭했을 때 사용됩니다.
 */
exports.getStoreById = async (req, res) => {
    try {
        const { storeId } = req.params;

        // 가게 상세 정보 조회
        const store = await Store.findById(storeId);
        if (!store) {
            return res.status(404).json({ message: "가게를 찾을 수 없습니다." });
        }

        // 해당 가게에 속한 모든 메뉴 목록 조회
        const menus = await Menu.find({ storeId: storeId });

        // 가게 정보와 메뉴 정보를 함께 응답으로 보냅니다.
        res.status(200).json({
            store: store,
            menus: menus
        });

    } catch (error) {
        console.error("특정 가게 정보 조회 중 오류 발생:", error);
        res.status(500).json({ message: "서버 오류로 가게 정보를 조회할 수 없습니다." });
    }
};

/**
 * 가게 정보를 수정하는 컨트롤러
 * - 사장님 대시보드에서 가게 정보를 변경할 때 사용됩니다.
 */
exports.updateStore = async (req, res) => {
    try {
        const { storeId } = req.params;
        const updateData = req.body;

        const updatedStore = await Store.findByIdAndUpdate(
            storeId,
            updateData,
            { new: true } // 업데이트된 문서를 반환
        );

        if (!updatedStore) {
            return res.status(404).json({ message: '가게를 찾을 수 없습니다.' });
        }
        res.status(200).json(updatedStore);

    } catch (error) {
        console.error("가게 정보 수정 중 오류 발생:", error);
        res.status(500).json({ message: "서버 오류로 가게 정보를 수정할 수 없습니다." });
    }
};