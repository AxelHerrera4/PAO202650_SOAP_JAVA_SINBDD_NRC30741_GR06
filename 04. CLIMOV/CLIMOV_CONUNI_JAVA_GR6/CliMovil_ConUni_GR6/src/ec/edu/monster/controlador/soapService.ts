const WS_URL = "http://localhost:8080/ws_ConUni_Java_GR6/ConversorWS";

export const buildSoapEnvelope = (method: string, param: string, valor: string): string => {
  return `<?xml version="1.0" encoding="UTF-8"?>
  <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
           xmlns:ws="http://ws.ec.edu.monster/">
    <soap:Body>
      <ws:${method}>
        <${param}>${valor}</${param}>
      </ws:${method}>
    </soap:Body>
  </soap:Envelope>`;
};

export const invokeSoap = async (method: string, param: string, valor: string): Promise<string> => {
  const xmlBody = buildSoapEnvelope(method, param, valor);

  try {
    const response = await fetch(WS_URL, {
      method: "POST",
      headers: { "Content-Type": "text/xml; charset=utf-8" },
      body: xmlBody
    });

    const data = await response.text();
    const m = data.match(/<valorDestino>([^<]+)<\/valorDestino>/);

    if (m) {
      return parseFloat(m[1]).toFixed(2);
    }

    const m2 = data.match(/>([-+]?[0-9]*\.?[0-9]+)</);
    if (m2) {
      return parseFloat(m2[1]).toFixed(2);
    }

    return 'Error';
  } catch (e) {
    throw new Error('No se pudo conectar con el servidor');
  }
};
