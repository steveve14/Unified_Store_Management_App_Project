import React, { useState, useEffect } from 'react';
import { fetchOrders, updateOrderStatusApi } from '../services/api';

const OrderManagement = () => {
    const [orders, setOrders] = useState([]);

    // 컴포넌트가 처음 렌더링될 때 주문 데이터를 불러옵니다.
    useEffect(() => {
        loadOrders();
    }, []);

    const loadOrders = async () => {
        try {
            const response = await fetchOrders();
            setOrders(response.data);
        } catch (error) {
            console.error("주문 목록을 불러오는 데 실패했습니다:", error);
        }
    };

    // 주문 상태 변경 핸들러
    const handleStatusChange = async (orderId, newStatus) => {
        try {
            await updateOrderStatusApi(orderId, newStatus);
            // 상태 변경 성공 시, 주문 목록을 다시 불러와 화면을 갱신합니다.
            loadOrders();
        } catch (error) {
            console.error("주문 상태 변경에 실패했습니다:", error);
        }
    };

    const statusOptions = ['PENDING', 'COOKING', 'SERVED', 'PAID'];

    return (
        <div style={{ padding: '20px' }}>
            <h1>실시간 주문 관리</h1>
            <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                <thead>
                    <tr style={{ background: '#f2f2f2' }}>
                        <th style={tableHeaderStyle}>주문 ID</th>
                        <th style={tableHeaderStyle}>유형</th>
                        <th style={tableHeaderStyle}>테이블/정보</th>
                        <th style={tableHeaderStyle}>상태 변경</th>
                    </tr>
                </thead>
                <tbody>
                    {orders.map((order) => (
                        <tr key={order.orderId}>
                            <td style={tableCellStyle}>{order.orderId}</td>
                            <td style={tableCellStyle}>{order.orderType}</td>
                            <td style={tableCellStyle}>
                                {order.orderType === 'DINE_IN' ? `테이블 ${order.tableNumber}` : '배달'}
                            </td>
                            <td style={tableCellStyle}>
                                <select
                                    value={order.status}
                                    onChange={(e) => handleStatusChange(order.orderId, e.target.value)}
                                    style={{ padding: '5px' }}
                                >
                                    {statusOptions.map(status => (
                                        <option key={status} value={status}>{status}</option>
                                    ))}
                                </select>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

// 간단한 스타일
const tableHeaderStyle = { padding: '10px', border: '1px solid #ddd', textAlign: 'left' };
const tableCellStyle = { padding: '10px', border: '1px solid #ddd' };

export default OrderManagement;