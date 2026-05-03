import { useState } from 'react'
import './App.css'

const WS_URL = 'http://localhost:8080/ws_ConUni_Java_GR6/ConversorWS'

const OPERACIONES_LONGITUD = [
  { code: 'km_m', label: 'Kilometros a Metros', method: 'convertirKmAMetros', param: 'kilometros' },
  { code: 'm_cm', label: 'Metros a Centimetros', method: 'convertirMetrosACm', param: 'metros' },
  { code: 'in_cm', label: 'Pulgadas a Centimetros', method: 'convertirPulgadasACm', param: 'pulgadas' },
  { code: 'ft_m', label: 'Pies a Metros', method: 'convertirPiesAMetros', param: 'pies' },
  { code: 'mi_km', label: 'Millas a Kilometros', method: 'convertirMillasAKm', param: 'millas' },
]

const OPERACIONES_TEMP = [
  { code: 'c_f', label: 'Celsius a Fahrenheit', method: 'convertirCtoF', param: 'gradosC' },
  { code: 'f_c', label: 'Fahrenheit a Celsius', method: 'convertirFtoC', param: 'gradosF' },
  { code: 'c_k', label: 'Celsius a Kelvin', method: 'convertirCtoK', param: 'gradosC' },
  { code: 'k_c', label: 'Kelvin a Celsius', method: 'convertirKtoC', param: 'gradosK' },
  { code: 'f_k', label: 'Fahrenheit a Kelvin', method: 'convertirFtoK', param: 'gradosF' },
  { code: 'k_f', label: 'Kelvin a Fahrenheit', method: 'convertirKtoF', param: 'gradosK' },
]

function buildSoapEnvelope(method, paramName, valor) {
  return `<?xml version='1.0' encoding='UTF-8'?>\n<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ws="http://ws.ec.edu.monster/">\n  <soapenv:Header/>\n  <soapenv:Body>\n    <ws:${method}>\n      <${paramName}>${valor}</${paramName}>\n    </ws:${method}>\n  </soapenv:Body>\n</soapenv:Envelope>`
}

async function invokeSoap(method, paramName, valor) {
  const xml = buildSoapEnvelope(method, paramName, valor)
  const res = await fetch(WS_URL, {
    method: 'POST',
    headers: { 'Content-Type': 'text/xml; charset=utf-8' },
    body: xml,
  })
  const text = await res.text()
  const m = text.match(/<valorDestino>([^<]+)<\/valorDestino>/)
  if (m) return m[1]
  const m2 = text.match(/>([-+]?[0-9]*\.?[0-9]+)<\//)
  if (m2) return m2[1]
  return text
}

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false)
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [loginError, setLoginError] = useState('')
  const [category, setCategory] = useState('longitud')
  const [opCode, setOpCode] = useState(OPERACIONES_LONGITUD[0].code)
  const [valor, setValor] = useState('')
  const [resultado, setResultado] = useState(null)
  const [unidadOrigen, setUnidadOrigen] = useState('')
  const [unidadDestino, setUnidadDestino] = useState('')
  const operations = category === 'longitud' ? OPERACIONES_LONGITUD : OPERACIONES_TEMP

  const handleLogin = (event) => {
    event.preventDefault()
    if (username === 'monster' && password === 'monster9') {
      setIsLoggedIn(true)
      setLoginError('')
      return
    }
    setLoginError('Credenciales inválidas')
  }

  const handleConvert = async () => {
    if (!valor) return
    const op = operations.find(o => o.code === opCode)
    try {
      const r = await invokeSoap(op.method, op.param, valor)
      setResultado(r)
      setUnidadOrigen(`Origen: ${valor}`)
      setUnidadDestino(`Destino: ${r}`)
    } catch (e) {
      setResultado('Error: ' + e.message)
      setUnidadOrigen('')
      setUnidadDestino('')
    }
  }

  const handleValorChange = (raw) => {
    // normalize comma to dot
    const v = raw.replace(',', '.')
    // allow empty, optional leading -, digits, optional one dot, digits
    if (v === '' || /^-?\d*\.?\d*$/.test(v)) {
      setValor(v)
    }
    // otherwise ignore the input (prevents letters)
  }

  if (!isLoggedIn) {
    return (
      <div className="desktop-shell login-shell">
        <div className="login-layout">
          <section className="login-hero">
            <h1>MONSTER</h1>
            <h2>Sistema de Conversion SOAP</h2>
            <p>Temperatura y longitud en una interfaz limpia, rapida y ordenada.</p>
            <span>Acceso protegido para usuarios autorizados.</span>
          </section>

          <form className="login-panel" onSubmit={handleLogin}>
            <h3>Iniciar sesion</h3>
            <p>Ingresa tus credenciales para continuar</p>

            <label>
              Usuario
              <input
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                autoComplete="username"
              />
            </label>

            <label>
              Contraseña
              <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                autoComplete="current-password"
              />
            </label>

            {loginError ? <div className="error-text">{loginError}</div> : null}

            <div className="login-actions">
              <button type="submit" className="primary-btn">Ingresar</button>
              <button type="button" className="secondary-btn" onClick={() => { setUsername(''); setPassword(''); setLoginError('') }}>
                Salir
              </button>
            </div>
          </form>
        </div>
      </div>
    )
  }

  return (
    <div className="desktop-shell">
      <div className="desktop-window">
        <header className="window-header">
          <h1>{category === 'longitud' ? 'CONVERSOR MONSTER | LONGITUD' : 'CONVERSOR MONSTER | TEMPERATURA'}</h1>
          <p>{category === 'longitud' ? 'Convierte entre kilometros, metros, centimetros, pulgadas, pies y millas.' : 'Convierte entre Celsius, Fahrenheit y Kelvin.'}</p>
        </header>

        <main className="converter-card">
          <label className="field-label">Categoria</label>
          <select
            className="field-input"
            value={category}
            onChange={(e) => {
              const nextCategory = e.target.value
              setCategory(nextCategory)
              const nextOps = nextCategory === 'longitud' ? OPERACIONES_LONGITUD : OPERACIONES_TEMP
              setOpCode(nextOps[0].code)
              setResultado(null)
              setUnidadOrigen('')
              setUnidadDestino('')
            }}
          >
            <option value="temperatura">Temperatura</option>
            <option value="longitud">Longitud</option>
          </select>

          <label className="field-label">Operacion</label>
          <select className="field-input" value={opCode} onChange={e => setOpCode(e.target.value)}>
            {operations.map(op => (
              <option key={op.code} value={op.code}>{op.label}</option>
            ))}
          </select>

          <label className="field-label">Valor a convertir</label>
          <input
            className="field-input"
            value={valor}
            onChange={e => handleValorChange(e.target.value)}
            placeholder="Ingrese valor"
            inputMode="decimal"
          />

          <label className="field-label">Resultado</label>
          <section className="result-box">
            <strong>{resultado ?? 'Esperando conversion...'}</strong>
            <span>{unidadOrigen}</span>
            <span>{unidadDestino}</span>
          </section>

          <div className="buttons-grid">
            <button type="button" className="primary-btn" onClick={handleConvert}>Convertir</button>
            <button
              type="button"
              className="secondary-btn"
              onClick={() => {
                setValor('')
                setResultado(null)
                setUnidadOrigen('')
                setUnidadDestino('')
              }}
            >
              Limpiar
            </button>
            <button type="button" className="secondary-btn" onClick={() => setIsLoggedIn(false)}>Volver</button>
          </div>
        </main>
      </div>
    </div>
  )
}

export default App
