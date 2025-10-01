import React, { useEffect, useState } from 'react';
import { getAllCustomers } from '../services/api';

function CustomersList() {
    const [customers, setCustomers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        getAllCustomers()
            .then((res) => {
                setCustomers(res.data); // Axios guarda la respuesta en res.data
                setLoading(false);
            })
            .catch((err) => {
                console.error(err);
                setError('Error al cargar los clientes');
                setLoading(false);
            });
    }, []);

    if (loading) return <p>Cargando clientes...</p>;
    if (error) return <p>{error}</p>;

    return (
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Número de Cuenta</th>
                <th>Saldo</th>
                <th>Acciones</th>
            </tr>
            </thead>
            <tbody>
            {customers.map((c) => (
                <tr key={c.id}>
                    <td>{c.id}</td>
                    <td>{c.firstName} {c.lastName}</td>
                    <td>{c.accountNumber}</td>
                    <td>{c.balance}</td>
                    <td>
                        <button className="button">Editar</button>
                        <button className="button">Eliminar</button>
                    </td>
                </tr>
            ))}
            </tbody>
        </table>
    );
}

export default CustomersList;

