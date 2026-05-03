import React, { useState, useMemo, useEffect } from 'react';
import { StyleSheet, Text, View, TextInput, TouchableOpacity, Alert, ScrollView } from 'react-native';
import { Picker } from '@react-native-picker/picker';

const WS_URL = "http://localhost:8080/ws_ConUni_Java_GR6/ConversorWS"; 

// Operation lists mirror the desktop/web client — single-operation selection
const OPERACIONES_TEMP_LIST = [
  { code: 'c_f', label: 'Celsius a Fahrenheit', method: 'convertirCtoF', param: 'gradosC' },
  { code: 'c_k', label: 'Celsius a Kelvin', method: 'convertirCtoK', param: 'gradosC' },
  { code: 'f_c', label: 'Fahrenheit a Celsius', method: 'convertirFtoC', param: 'gradosF' },
  { code: 'f_k', label: 'Fahrenheit a Kelvin', method: 'convertirFtoK', param: 'gradosF' },
  { code: 'k_c', label: 'Kelvin a Celsius', method: 'convertirKtoC', param: 'gradosK' },
  { code: 'k_f', label: 'Kelvin a Fahrenheit', method: 'convertirKtoF', param: 'gradosK' }
];

const OPERACIONES_LONGITUD_LIST = [
  { code: 'km_m', label: 'Kilometros a Metros', method: 'convertirKmAMetros', param: 'kilometros' },
  { code: 'm_cm', label: 'Metros a Centimetros', method: 'convertirMetrosACm', param: 'metros' },
  { code: 'in_cm', label: 'Pulgadas a Centimetros', method: 'convertirPulgadasACm', param: 'pulgadas' },
  { code: 'ft_m', label: 'Pies a Metros', method: 'convertirPiesAMetros', param: 'pies' },
  { code: 'mi_km', label: 'Millas a Kilometros', method: 'convertirMillasAKm', param: 'millas' }
];

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
    return category === 'Temperatura' ? OPERACIONES_TEMP_LIST : OPERACIONES_LONGITUD_LIST;
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
    const ops = newCategory === 'Temperatura' ? OPERACIONES_TEMP_LIST : OPERACIONES_LONGITUD_LIST;
    setOperation(ops[0].code);
    setResultado('...');
  };

  const calcularSOAP = async () => {
    if (!valor) return Alert.alert("Aviso", "Ingresa un valor");

    const ops = category === 'Temperatura' ? OPERACIONES_TEMP_LIST : OPERACIONES_LONGITUD_LIST;
    const op = ops.find(o => o.code === operation);

    if (!op) {
      Alert.alert("Error", "Conversión no disponible");
      return;
    }

    // Extract origin and destination units from operation label (e.g., "Celsius a Fahrenheit")
    const parts = op.label.split(' a ');
    const origen = parts[0] || '';
    const destino = parts[1] || '';

    const xmlBody = `<?xml version="1.0" encoding="UTF-8"?>
  <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
           xmlns:ws="http://ws.ec.edu.monster/">
    <soap:Body>
      <ws:${op.method}>
        <${op.param}>${valor}</${op.param}>
      </ws:${op.method}>
    </soap:Body>
  </soap:Envelope>`;

    try {
      const response = await fetch(WS_URL, {
        method: "POST",
        headers: { "Content-Type": "text/xml; charset=utf-8" },
        body: xmlBody
      });
      const data = await response.text();
      const m = data.match(/<valorDestino>([^<]+)<\/valorDestino>/);
      if (m) {
        const resultValue = parseFloat(m[1]).toFixed(2);
        setResultado(resultValue);
        setUnidadOrigen(`Origen: ${origen}`);
        setUnidadDestino(`Destino: ${resultValue}`);
      }
      else {
        const m2 = data.match(/>([-+]?[0-9]*\.?[0-9]+)</);
        if (m2) {
          const resultValue = parseFloat(m2[1]).toFixed(2);
          setResultado(resultValue);
          setUnidadOrigen(`Origen: ${origen}`);
          setUnidadDestino(`Destino: ${resultValue}`);
        } else {
          setResultado('Error');
          setUnidadOrigen('');
          setUnidadDestino('');
        }
      }
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
      <ScrollView contentContainerStyle={styles.container}>
        <View style={styles.heroSection}>
          <Text style={styles.title}>ConUni</Text>
          <Text style={styles.subtitle}>Conversor Universal</Text>
          <Text style={styles.heroDescription}>Convierte unidades de temperatura y longitud de forma rápida y sencilla</Text>
        </View>
        
        <View style={styles.loginCard}>
          <Text style={styles.loginTitle}>Iniciar Sesión</Text>
          
          <Text style={styles.label}>Usuario:</Text>
          <TextInput
            style={styles.input}
            placeholder="usuario"
            placeholderTextColor="#999"
            value={username}
            onChangeText={setUsername}
          />
          
          <Text style={styles.label}>Contraseña:</Text>
          <TextInput
            style={styles.input}
            placeholder="contraseña"
            placeholderTextColor="#999"
            secureTextEntry
            value={password}
            onChangeText={setPassword}
          />
          
          <TouchableOpacity style={styles.loginButton} onPress={handleLogin}>
            <Text style={styles.buttonText}>ACCEDER</Text>
          </TouchableOpacity>
        </View>
      </ScrollView>
    );
  }

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <View style={styles.headerSection}>
        <Text style={styles.title}>ConUni</Text>
        <Text style={styles.subtitle}>{category}</Text>
        <Text style={styles.headerDescription}>
          {category === 'Temperatura' 
            ? 'Convierte entre Celsius, Fahrenheit y Kelvin' 
            : 'Convierte entre Kilometros, Metros, Centimetros, Pulgadas, Pies y Millas'}
        </Text>
      </View>
      
      {/* Category Selector */}
      <View style={styles.categorySelector}>
        <TouchableOpacity 
          style={[styles.categoryBtn, category === 'Temperatura' && styles.activeCategoryBtn]}
          onPress={() => handleCategoryChange('Temperatura')}
        >
          <Text style={styles.categoryBtnText}>Temperatura</Text>
        </TouchableOpacity>
        <TouchableOpacity 
          style={[styles.categoryBtn, category === 'Longitud' && styles.activeCategoryBtn]}
          onPress={() => handleCategoryChange('Longitud')}
        >
          <Text style={styles.categoryBtnText}>Longitud</Text>
        </TouchableOpacity>
      </View>

      <View style={styles.card}>
        <Text style={styles.label}>Operación:</Text>
        <Picker selectedValue={operation} onValueChange={setOperation} style={styles.picker}>
          {operationsList.map(op => (
            <Picker.Item key={op.code} label={op.label} value={op.code} />
          ))}
        </Picker>

        <TextInput 
          style={styles.input} 
          placeholder="Valor a convertir" 
          placeholderTextColor="#999"
          keyboardType="numeric" 
          value={valor}
          onChangeText={handleValorChange} 
        />

        <View style={styles.resultBox}>
          <Text style={styles.resValue}>{resultado}</Text>
          <Text style={styles.resUnit}>{unidadOrigen}</Text>
          <Text style={styles.resUnit}>{unidadDestino}</Text>
        </View>

        <View style={styles.buttonRow}>
          <TouchableOpacity style={styles.button} onPress={calcularSOAP}>
            <Text style={styles.buttonText}>CONVERTIR</Text>
          </TouchableOpacity>
          <TouchableOpacity style={[styles.button, styles.buttonSecondary]} onPress={handleLimpiar}>
            <Text style={styles.buttonText}>LIMPIAR</Text>
          </TouchableOpacity>
        </View>

        <TouchableOpacity style={styles.logoutButton} onPress={handleVolver}>
          <Text style={styles.buttonText}>VOLVER</Text>
        </TouchableOpacity>
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: { 
    flexGrow: 1, 
    backgroundColor: '#0f172a', 
    alignItems: 'center', 
    justifyContent: 'center', 
    paddingVertical: 40,
    paddingHorizontal: 16
  },
  title: { 
    fontSize: 36, 
    color: '#60a5fa', 
    fontWeight: 'bold', 
    marginBottom: 4 
  },
  subtitle: { 
    fontSize: 18, 
    color: '#bfdbfe', 
    marginBottom: 8 
  },
  heroSection: {
    marginBottom: 32,
    alignItems: 'center'
  },
  heroDescription: {
    fontSize: 14,
    color: '#cbd5e1',
    marginTop: 12,
    textAlign: 'center',
    paddingHorizontal: 16
  },
  headerSection: {
    marginBottom: 20,
    alignItems: 'center'
  },
  headerDescription: {
    fontSize: 13,
    color: '#64748b',
    marginTop: 8,
    textAlign: 'center',
    paddingHorizontal: 16
  },
  loginCard: { 
    width: '100%', 
    backgroundColor: '#f8fafc', 
    padding: 20, 
    borderRadius: 12,
    marginBottom: 20
  },
  loginButton: {
    backgroundColor: '#2563eb',
    padding: 12,
    borderRadius: 8,
    alignItems: 'center',
    marginTop: 16
  },
  loginTitle: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#0f172a',
    marginBottom: 16
  },
  categorySelector: {
    flexDirection: 'row',
    gap: 12,
    marginBottom: 20,
    width: '100%'
  },
  categoryBtn: {
    flex: 1,
    padding: 12,
    backgroundColor: '#334155',
    borderRadius: 8,
    alignItems: 'center',
    borderWidth: 2,
    borderColor: 'transparent'
  },
  activeCategoryBtn: {
    backgroundColor: '#2563eb',
    borderColor: '#60a5fa'
  },
  categoryBtnText: {
    color: '#fff',
    fontWeight: '600',
    fontSize: 14
  },
  card: { 
    width: '100%',
    maxWidth: 500,
    backgroundColor: '#f8fafc', 
    padding: 24, 
    borderRadius: 12,
    elevation: 5
  },
  label: { 
    color: '#334155', 
    fontWeight: 'bold', 
    marginTop: 14,
    fontSize: 14
  },
  picker: { 
    backgroundColor: '#e2e8f0', 
    marginVertical: 8, 
    borderRadius: 8,
    color: '#1e293b'
  },
  input: { 
    backgroundColor: '#e2e8f0', 
    padding: 12, 
    borderRadius: 8, 
    fontSize: 16, 
    marginTop: 8,
    color: '#1e293b'
  },
  resultBox: {
    backgroundColor: '#f1f5f9',
    borderRadius: 8,
    padding: 16,
    marginVertical: 16,
    alignItems: 'center',
    borderWidth: 1,
    borderColor: '#cbd5e1'
  },
  resValue: { 
    color: '#0f172a', 
    fontSize: 28, 
    fontWeight: 'bold'
  },
  resUnit: {
    color: '#475569',
    fontSize: 14,
    marginTop: 4
  },
  buttonRow: {
    flexDirection: 'row',
    gap: 12,
    marginTop: 16
  },
  button: { 
    flex: 1,
    backgroundColor: '#2563eb', 
    padding: 12, 
    borderRadius: 8, 
    alignItems: 'center'
  },
  buttonSecondary: {
    backgroundColor: '#0ea5e9'
  },
  logoutButton: {
    backgroundColor: '#f87171',
    padding: 12,
    borderRadius: 8,
    alignItems: 'center',
    marginTop: 12
  },
  buttonText: { 
    color: '#fff', 
    fontWeight: 'bold', 
    fontSize: 14
  }
});