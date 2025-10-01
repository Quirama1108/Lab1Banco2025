import React, { useEffect, useState } from 'react';
import { getAllTransactions } from '../services/api';

function TransactionHistory() {
    const [transactions, setTransactions] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        getAllTransactions()
            .then((res) => {
                setTransactions(res.data);
                setLoading(false);
            })
            .catch((err) => {
                console.error(err);
                setError('Error al cargar las transacciones');
                setLoading(false);
            });
    }, []);

    if (loading) return <p>Cargando transacciones...</p>;
    if (error) return <p>{error}</p>;

    return (
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Cuenta Remitente</th>
                <th>Cuenta Receptora</th>
                <th>Monto</th>
                <th>Fecha</th>
            </tr>
            </thead>
            <tbody>
            {transactions.map((t) => (
                <tr key={t.id}>
                    <td>{t.id}</td>
                    <td>{t.senderAccountNumber}</td>
                    <td>{t.receiverAccountNumber}</td>
                    <td>{t.amount}</td>
                    <td>{new Date(t.transactionDate).toLocaleString()}</td>
                </tr>
            ))}
            </tbody>
        </table>
    );
}

export default TransactionHistory;


