import React from 'react';
import { ScrollView, View, Text, TextInput, TouchableOpacity, StyleSheet } from 'react-native';
import { Picker } from '@react-native-picker/picker';

export interface Operation {
  code: string;
  label: string;
  method: string;
  param: string;
}

export interface ConverterCardProps {
  category: string;
  operation: string;
  valor: string;
  resultado: string;
  unidadOrigen: string;
  unidadDestino: string;
  operationsList: Operation[];
  onCategoryChange: (category: string) => void;
  onOperationChange: (code: string) => void;
  onValorChange: (value: string) => void;
  onConvert: () => void;
  onClear: () => void;
  onLogout: () => void;
}

export const ConverterCard: React.FC<ConverterCardProps> = ({
  category,
  operation,
  valor,
  resultado,
  unidadOrigen,
  unidadDestino,
  operationsList,
  onCategoryChange,
  onOperationChange,
  onValorChange,
  onConvert,
  onClear,
  onLogout,
}) => {
  return (
    <ScrollView contentContainerStyle={styles.container}>
      <View style={styles.headerSection}>
        <Text style={styles.title}>ConUni</Text>
        <Text style={styles.subtitle}>{category}</Text>
        <Text style={styles.headerDescription}>
          {category === 'Temperatura'
            ? 'Convierte entre Celsius, Fahrenheit y Kelvin'
            : category === 'Longitud'
            ? 'Convierte entre Kilometros, Metros, Centimetros, Pulgadas, Pies y Millas'
            : 'Convierte entre Kilogramos, Gramos, Miligramos, Libras, Onzas y Toneladas'}
        </Text>
      </View>

      <View style={styles.categorySelector}>
        <TouchableOpacity
          style={[styles.categoryBtn, category === 'Temperatura' && styles.activeCategoryBtn]}
          onPress={() => onCategoryChange('Temperatura')}
        >
          <Text style={styles.categoryBtnText}>Temperatura</Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={[styles.categoryBtn, category === 'Longitud' && styles.activeCategoryBtn]}
          onPress={() => onCategoryChange('Longitud')}
        >
          <Text style={styles.categoryBtnText}>Longitud</Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={[styles.categoryBtn, category === 'Masa' && styles.activeCategoryBtn]}
          onPress={() => onCategoryChange('Masa')}
        >
          <Text style={styles.categoryBtnText}>Masa</Text>
        </TouchableOpacity>
      </View>

      <View style={styles.card}>
        <Text style={styles.label}>Operación:</Text>
        <Picker selectedValue={operation} onValueChange={onOperationChange} style={styles.picker}>
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
          onChangeText={onValorChange}
        />

        <View style={styles.resultBox}>
          <Text style={styles.resValue}>{resultado}</Text>
          <Text style={styles.resUnit}>{unidadOrigen}</Text>
          <Text style={styles.resUnit}>{unidadDestino}</Text>
        </View>

        <View style={styles.buttonRow}>
          <TouchableOpacity style={styles.button} onPress={onConvert}>
            <Text style={styles.buttonText}>CONVERTIR</Text>
          </TouchableOpacity>
          <TouchableOpacity style={[styles.button, styles.buttonSecondary]} onPress={onClear}>
            <Text style={styles.buttonText}>LIMPIAR</Text>
          </TouchableOpacity>
        </View>

        <TouchableOpacity style={styles.logoutButton} onPress={onLogout}>
          <Text style={styles.buttonText}>VOLVER</Text>
        </TouchableOpacity>
      </View>
    </ScrollView>
  );
};

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
