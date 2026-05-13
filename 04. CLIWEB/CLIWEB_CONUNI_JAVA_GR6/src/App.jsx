import { useState } from 'react'
import './App.css'
import { OPERACIONES_LONGITUD, OPERACIONES_MASA, OPERACIONES_TEMP } from './ec/edu/monster/model/operaciones'
import { invokeSoap } from './ec/edu/monster/controlador/soapService'
import { LoginView } from './ec/edu/monster/vista/LoginView'
import { ConverterView } from './ec/edu/monster/vista/ConverterView'

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

  const operations = category === 'longitud' ? OPERACIONES_LONGITUD : category === 'masa' ? OPERACIONES_MASA : OPERACIONES_TEMP

  const handleLogin = (event) => {
    event.preventDefault()
    if (username === 'monster' && password === 'monster9') {
      setIsLoggedIn(true)
      setLoginError('')
      return
    }
    setLoginError('Credenciales inválidas')
  }

  const handleLoginClear = () => {
    setUsername('')
    setPassword('')
    setLoginError('')
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
    const v = raw.replace(',', '.')
    if (v === '' || /^-?\d*\.?\d*$/.test(v)) {
      setValor(v)
    }
  }

  const handleCategoryChange = (nextCategory) => {
    setCategory(nextCategory)
    const nextOps = nextCategory === 'longitud' ? OPERACIONES_LONGITUD : nextCategory === 'masa' ? OPERACIONES_MASA : OPERACIONES_TEMP
    setOpCode(nextOps[0].code)
    setResultado(null)
    setUnidadOrigen('')
    setUnidadDestino('')
  }

  const handleConverterClear = () => {
    setValor('')
    setResultado(null)
    setUnidadOrigen('')
    setUnidadDestino('')
  }

  if (!isLoggedIn) {
    return (
      <LoginView
        username={username}
        password={password}
        loginError={loginError}
        onUsernameChange={setUsername}
        onPasswordChange={setPassword}
        onSubmit={handleLogin}
        onClear={handleLoginClear}
      />
    )
  }

  return (
    <ConverterView
      category={category}
      opCode={opCode}
      valor={valor}
      resultado={resultado}
      unidadOrigen={unidadOrigen}
      unidadDestino={unidadDestino}
      operations={operations}
      onCategoryChange={handleCategoryChange}
      onOpCodeChange={setOpCode}
      onValorChange={handleValorChange}
      onConvert={handleConvert}
      onClear={handleConverterClear}
      onLogout={() => setIsLoggedIn(false)}
    />
  )
}

export default App
