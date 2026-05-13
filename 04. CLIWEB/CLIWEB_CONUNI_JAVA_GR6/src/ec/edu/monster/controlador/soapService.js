const WS_URL = 'http://192.168.137.171:8080/ws_ConUni_Java_GR6/ConversorWS'

export function buildSoapEnvelope(method, paramName, valor) {
  return `<?xml version='1.0' encoding='UTF-8'?>\n<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ws="http://ws.ec.edu.monster/">\n  <soapenv:Header/>\n  <soapenv:Body>\n    <ws:${method}>\n      <${paramName}>${valor}</${paramName}>\n    </ws:${method}>\n  </soapenv:Body>\n</soapenv:Envelope>`
}

export async function invokeSoap(method, paramName, valor) {
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
