export const OPERACIONES_LONGITUD = [
  { code: 'km_m', label: 'Kilometros a Metros', method: 'convertirKmAMetros', param: 'kilometros' },
  { code: 'm_cm', label: 'Metros a Centimetros', method: 'convertirMetrosACm', param: 'metros' },
  { code: 'in_cm', label: 'Pulgadas a Centimetros', method: 'convertirPulgadasACm', param: 'pulgadas' },
  { code: 'ft_m', label: 'Pies a Metros', method: 'convertirPiesAMetros', param: 'pies' },
  { code: 'mi_km', label: 'Millas a Kilometros', method: 'convertirMillasAKm', param: 'millas' },
]

export const OPERACIONES_MASA = [
  { code: 'kg_g', label: 'Kilogramos a Gramos', method: 'convertirKgAGramos', param: 'kilogramos' },
  { code: 'g_mg', label: 'Gramos a Miligramos', method: 'convertirGramosAMg', param: 'gramos' },
  { code: 'lb_kg', label: 'Libras a Kilogramos', method: 'convertirLibrasAKg', param: 'libras' },
  { code: 'oz_g', label: 'Onzas a Gramos', method: 'convertirOnzasAGramos', param: 'onzas' },
  { code: 't_kg', label: 'Toneladas a Kilogramos', method: 'convertirToneladasAKg', param: 'toneladas' },
]

export const OPERACIONES_TEMP = [
  { code: 'c_f', label: 'Celsius a Fahrenheit', method: 'convertirCtoF', param: 'gradosC' },
  { code: 'f_c', label: 'Fahrenheit a Celsius', method: 'convertirFtoC', param: 'gradosF' },
  { code: 'c_k', label: 'Celsius a Kelvin', method: 'convertirCtoK', param: 'gradosC' },
  { code: 'k_c', label: 'Kelvin a Celsius', method: 'convertirKtoC', param: 'gradosK' },
  { code: 'f_k', label: 'Fahrenheit a Kelvin', method: 'convertirFtoK', param: 'gradosF' },
  { code: 'k_f', label: 'Kelvin a Fahrenheit', method: 'convertirKtoF', param: 'gradosK' },
]
