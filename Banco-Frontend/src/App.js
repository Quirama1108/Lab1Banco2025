import React from 'react';
import CustomersList from './components/CustomersList';
import TransferForm from './components/TransferForm';
import TransactionHistory from './components/TransactionHistory';
import './App.css';

function App() {
    return (
        <div className="App">
            {/* Header */}
            <header className="App-header">
                <h1>Banco2025</h1>
            </header>

            {/* Sección de clientes */}
            <section>
                <h2>Clientes</h2>
                <div className="table-container">
                    <CustomersList />
                </div>
            </section>

            {/* Formulario de transferencia */}
            <section>
                <TransferForm />
            </section>

            {/* Historial de transacciones */}
            <section>
                <h2>Historial de Transacciones</h2>
                <div className="table-container">
                    <TransactionHistory />
                </div>
            </section>
        </div>
    );
}

export default App;


