import React, { useState, useMemo, useEffect } from 'react';
import { Alert } from 'react-native';
import { OPERACIONES_TEMP_LIST, OPERACIONES_LONGITUD_LIST, OPERACIONES_MASA_LIST } from '../../src/ec/edu/monster/model/operaciones';
import { invokeSoap } from '../../src/ec/edu/monster/controlador/soapService';
import { LoginCard } from '../../src/ec/edu/monster/vista/LoginCard';
import { ConverterCard } from '../../src/ec/edu/monster/vista/ConverterCard';

export default function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [category, setCategory] = useState('Temperatura');
  const [valor, setValor] = useState('');
  const [operation, setOperation] = useState('');
  const [resultado, setResultado] = useState('...');
  const [unidadOrigen, setUnidadOrigen] = useState('');
  const [unidadDestino, setUnidadDestino] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  // Available operations depending on category
  const operationsList = useMemo(() => {
    if (category === 'Temperatura') return OPERACIONES_TEMP_LIST;
    if (category === 'Longitud') return OPERACIONES_LONGITUD_LIST;
    if (category === 'Masa') return OPERACIONES_MASA_LIST;
    return [];
  }, [category]);

  // Initialize selected operation when list changes
  useEffect(() => {
    if (!operation && operationsList.length > 0) setOperation(operationsList[0].code);
  }, [operationsList]);

  const handleValorChange = (raw: string) => {
    const normalized = raw.replace(',', '.');
    if (/^-?\d*\.?\d*$/.test(normalized)) setValor(normalized);
  };

  // Ensure an operation is selected when category changes

  const handleLogin = () => {
    if (username === 'monster' && password === 'monster9') {
      setIsLoggedIn(true);
      setUsername('');
      setPassword('');
    } else {
      Alert.alert('Error', 'Credenciales incorrectas');
    }
  };

  const handleCategoryChange = (newCategory: string) => {
    setCategory(newCategory);
    let ops: typeof OPERACIONES_TEMP_LIST = [];
    if (newCategory === 'Temperatura') ops = OPERACIONES_TEMP_LIST;
    else if (newCategory === 'Longitud') ops = OPERACIONES_LONGITUD_LIST;
    else if (newCategory === 'Masa') ops = OPERACIONES_MASA_LIST;
    setOperation(ops[0]?.code || '');
    setResultado('...');
  };

  const calcularSOAP = async () => {
    if (!valor) return Alert.alert("Aviso", "Ingresa un valor");

    let ops: typeof OPERACIONES_TEMP_LIST = [];
    if (category === 'Temperatura') ops = OPERACIONES_TEMP_LIST;
    else if (category === 'Longitud') ops = OPERACIONES_LONGITUD_LIST;
    else if (category === 'Masa') ops = OPERACIONES_MASA_LIST;
    const op = ops.find(o => o.code === operation);

    if (!op) {
      Alert.alert("Error", "Conversión no disponible");
      return;
    }

    const parts = op.label.split(' a ');
    const origen = parts[0] || '';

    try {
      const resultValue = await invokeSoap(op.method, op.param, valor);
      setResultado(resultValue);
      setUnidadOrigen(`Origen: ${origen}`);
      setUnidadDestino(`Destino: ${resultValue}`);
    } catch (e) {
      Alert.alert("Error", "No se pudo conectar con el servidor");
      console.error(e);
    }
  };

  const handleVolver = () => {
    setIsLoggedIn(false);
    setValor('');
    setResultado('...');
    setUnidadOrigen('');
    setUnidadDestino('');
    setCategory('Temperatura');
    setOperation('');
  };

  const handleLimpiar = () => {
    setValor('');
    setResultado('...');
  };

  if (!isLoggedIn) {
    return (
      <LoginCard
        username={username}
        password={password}
        onUsernameChange={setUsername}
        onPasswordChange={setPassword}
        onLogin={handleLogin}
      />
    );
  }

  return (
    <ConverterCard
      category={category}
      operation={operation}
      valor={valor}
      resultado={resultado}
      unidadOrigen={unidadOrigen}
      unidadDestino={unidadDestino}
      operationsList={operationsList}
      onCategoryChange={handleCategoryChange}
      onOperationChange={setOperation}
      onValorChange={handleValorChange}
      onConvert={calcularSOAP}
      onClear={handleLimpiar}
      onLogout={handleVolver}
    />
  );
}