import React, { useState } from 'react';
import { createTransaction } from '../services/api';

function TransferForm() {
    const [form, setForm] = useState({
        senderAccountNumber: '',
        receiverAccountNumber: '',
        amount: ''
    });

    const [message, setMessage] = useState(null);

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        createTransaction(form)
            .then((res) => {
                setMessage(`Transferencia realizada con éxito. ID: ${res.data.id}`);
                setForm({ senderAccountNumber: '', receiverAccountNumber: '', amount: '' });
            })
            .catch((err) => {
                console.error(err);
                setMessage('Error al realizar la transferencia');
            });
    };

    return (
        <div>
            <h2>Realizar Transferencia</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Cuenta Remitente:</label>
                    <input
                        type="text"
                        name="senderAccountNumber"
                        value={form.senderAccountNumber}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Cuenta Receptora:</label>
                    <input
                        type="text"
                        name="receiverAccountNumber"
                        value={form.receiverAccountNumber}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Monto:</label>
                    <input
                        type="number"
                        name="amount"
                        value={form.amount}
                        onChange={handleChange}
                        required
                    />
                </div>
                <button type="submit">Transferir</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
}

export default TransferForm;
