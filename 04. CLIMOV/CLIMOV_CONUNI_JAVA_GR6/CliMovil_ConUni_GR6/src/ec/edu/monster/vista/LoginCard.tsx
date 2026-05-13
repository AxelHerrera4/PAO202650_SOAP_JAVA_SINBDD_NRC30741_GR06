import React from 'react';
import { ScrollView, View, Text, TextInput, TouchableOpacity, StyleSheet } from 'react-native';

export interface LoginCardProps {
  username: string;
  password: string;
  onUsernameChange: (value: string) => void;
  onPasswordChange: (value: string) => void;
  onLogin: () => void;
}

export const LoginCard: React.FC<LoginCardProps> = ({
  username,
  password,
  onUsernameChange,
  onPasswordChange,
  onLogin,
}) => {
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
          onChangeText={onUsernameChange}
        />

        <Text style={styles.label}>Contraseña:</Text>
        <TextInput
          style={styles.input}
          placeholder="contraseña"
          placeholderTextColor="#999"
          secureTextEntry
          value={password}
          onChangeText={onPasswordChange}
        />

        <TouchableOpacity style={styles.loginButton} onPress={onLogin}>
          <Text style={styles.buttonText}>ACCEDER</Text>
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
  label: {
    color: '#334155',
    fontWeight: 'bold',
    marginTop: 14,
    fontSize: 14
  },
  input: {
    backgroundColor: '#e2e8f0',
    padding: 12,
    borderRadius: 8,
    fontSize: 16,
    marginTop: 8,
    color: '#1e293b'
  },
  buttonText: {
    color: '#fff',
    fontWeight: 'bold',
    fontSize: 14
  }
});
