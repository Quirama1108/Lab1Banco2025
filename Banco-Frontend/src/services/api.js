import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

export const api = axios.create({
    baseURL: API_URL,
    headers: {
        'Content-Type': 'application/json'
    }
});

// Clientes
export const getAllCustomers = () => api.get('/customers');

// Transacciones
export const getAllTransactions = () => api.get('/transactions');
export const getTransactionsByAccount = (accountNumber) =>
    api.get(`/transactions/account/${accountNumber}`);
export const createTransaction = (transaction) =>
    api.post('/transactions', transaction);
