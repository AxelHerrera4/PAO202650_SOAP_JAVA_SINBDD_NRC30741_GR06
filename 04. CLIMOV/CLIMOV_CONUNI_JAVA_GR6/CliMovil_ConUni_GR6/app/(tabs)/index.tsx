import React, { useState } from 'react';
import { StyleSheet, Text, View, TextInput, TouchableOpacity, Alert, ScrollView } from 'react-native';
import { Picker } from '@react-native-picker/picker'; // Asegúrate de instalar esto

// RECUERDA USAR TU IP REAL
const WS_URL = "http://192.168.1.12:8080/ws_ConUni_Java_GR6/ConversorWS"; 

export default function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [valor, setValor] = useState('');
  const [origen, setOrigen] = useState('Celsius');
  const [destino, setDestino] = useState('Fahrenheit');
  const [resultado, setResultado] = useState('...');

  const calcularSOAP = async () => {
    if (!valor) return Alert.alert("Aviso", "Ingresa un valor");

    // 1. Lógica dinámica de nombres (Igual que en tu WS Java)
    const iniO = origen.charAt(0); // C, F o K
    const iniD = destino.charAt(0);
    const metodo = `convertir${iniO}to${iniD}`;
    
    let paramName = "gradosC"; 
    if (iniO === 'F') paramName = "gradosF";
    if (iniO === 'K') paramName = "gradosK";

    const xmlBody = `
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://ws.ec.edu.monster/">
       <soapenv:Header/>
       <soapenv:Body>
          <ser:${metodo}>
             <${paramName}>${valor}</${paramName}>
          </ser:${metodo}>
       </soapenv:Body>
    </soapenv:Envelope>`;

    try {
      const response = await fetch(WS_URL, {
        method: "POST",
        headers: { "Content-Type": "text/xml" },
        body: xmlBody
      });
      const data = await response.text();
      const match = data.match(/<valorDestino>(.*?)<\/valorDestino>/);
      setResultado(match ? parseFloat(match[1]).toFixed(2) : "Error");
    } catch (e) {
      Alert.alert("Error", "No se pudo conectar con Payara");
    }
  };

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <Text style={styles.title}>CONVERSOR GR6</Text>
      
      <View style={styles.card}>
        <Text style={styles.label}>De:</Text>
        <Picker selectedValue={origen} onValueChange={(v) => setOrigen(v)} style={styles.picker}>
          <Picker.Item label="Celsius" value="Celsius" />
          <Picker.Item label="Fahrenheit" value="Fahrenheit" />
          <Picker.Item label="Kelvin" value="Kelvin" />
        </Picker>

        <Text style={styles.label}>A:</Text>
        <Picker selectedValue={destino} onValueChange={(v) => setDestino(v)} style={styles.picker}>
          <Picker.Item label="Fahrenheit" value="Fahrenheit" />
          <Picker.Item label="Celsius" value="Celsius" />
          <Picker.Item label="Kelvin" value="Kelvin" />
        </Picker>

        <TextInput 
          style={styles.input} 
          placeholder="Valor a convertir" 
          keyboardType="numeric" 
          onChangeText={setValor} 
        />

        <Text style={styles.resText}>{resultado} {destino}</Text>

        <TouchableOpacity style={styles.button} onPress={calcularSOAP}>
          <Text style={styles.buttonText}>CALCULAR</Text>
        </TouchableOpacity>
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: { flexGrow: 1, backgroundColor: '#2c3e50', alignItems: 'center', justifyContent: 'center', paddingVertical: 50 },
  title: { fontSize: 28, color: '#fff', fontWeight: 'bold', marginBottom: 20 },
  card: { width: '90%', backgroundColor: '#009966', padding: 20, borderRadius: 15, elevation: 10 },
  label: { color: '#fff', fontWeight: 'bold', marginTop: 10 },
  picker: { backgroundColor: '#fff', marginVertical: 10, borderRadius: 10 },
  input: { backgroundColor: '#fff', padding: 15, borderRadius: 10, fontSize: 18, marginTop: 10 },
  resText: { color: '#fff', fontSize: 22, fontWeight: 'bold', textAlign: 'center', marginVertical: 20 },
  button: { backgroundColor: '#34495e', padding: 15, borderRadius: 10, alignItems: 'center' },
  buttonText: { color: '#fff', fontWeight: 'bold', fontSize: 18 }
});