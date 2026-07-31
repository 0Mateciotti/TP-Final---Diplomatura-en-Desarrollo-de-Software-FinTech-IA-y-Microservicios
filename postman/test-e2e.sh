#!/bin/bash
# Prueba end-to-end de los 4 servicios via curl.
# Requiere que eureka-server, config-server, product-service y customer-service ya esten corriendo.
set -e

CUSTOMER_URL="http://localhost:8081"
PRODUCT_URL="http://localhost:8082"
EUREKA_URL="http://localhost:8761"

extract_id() {
  node -e "let d='';process.stdin.on('data',c=>d+=c);process.stdin.on('end',()=>console.log(JSON.parse(d).id))"
}

echo "== 0. Eureka: servicios registrados =="
curl -s "$EUREKA_URL/eureka/apps" -H "Accept: application/json" \
  | node -e "let d='';process.stdin.on('data',c=>d+=c);process.stdin.on('end',()=>{JSON.parse(d).applications.application.forEach(a=>console.log(a.name,'->',a.instance.map(i=>i.status).join(',')))})"

echo
echo "== 1. Crear cliente =="
CUSTOMER=$(curl -s -X POST "$CUSTOMER_URL/customers" -H "Content-Type: application/json" \
  -d '{"nombre":"Test","apellido":"E2E","documento":"12345678","direccion":"Calle 123","email":"test@e2e.com"}')
echo "$CUSTOMER"
CID=$(echo "$CUSTOMER" | extract_id)

echo
echo "== 2. Validacion: POST invalido (debe dar 400) =="
curl -s -i -X POST "$CUSTOMER_URL/customers" -H "Content-Type: application/json" -d '{"nombre":"","email":""}' | head -6

echo
echo "== 3. Crear cuenta, prestamo y tarjeta para el cliente $CID =="
CUENTA=$(curl -s -X POST "$PRODUCT_URL/cuentas" -H "Content-Type: application/json" \
  -d "{\"clienteId\":$CID,\"tipoCuenta\":\"CUENTA_CORRIENTE\",\"saldo\":150000,\"activo\":true}")
echo "$CUENTA"
PRESTAMO=$(curl -s -X POST "$PRODUCT_URL/prestamos" -H "Content-Type: application/json" \
  -d "{\"clienteId\":$CID,\"montoPrestamo\":500000,\"tasaInteres\":45.5,\"cantidadCuotas\":12,\"activo\":true}")
echo "$PRESTAMO"
TARJETA=$(curl -s -X POST "$PRODUCT_URL/tarjetas" -H "Content-Type: application/json" \
  -d "{\"clienteId\":$CID,\"limite\":300000,\"activo\":true}")
echo "$TARJETA"

CUENTA_ID=$(echo "$CUENTA" | extract_id)
PRESTAMO_ID=$(echo "$PRESTAMO" | extract_id)
TARJETA_ID=$(echo "$TARJETA" | extract_id)

echo
echo "== 4. Agregador Feign: GET /customers/$CID/products =="
curl -s "$CUSTOMER_URL/customers/$CID/products"

echo
echo
echo "== 5. 404 esperado =="
curl -s "$CUSTOMER_URL/customers/999999"
echo
curl -s "$PRODUCT_URL/cuentas/999999"
echo

echo
echo "== 6. Cleanup =="
curl -s -i -X DELETE "$PRODUCT_URL/cuentas/$CUENTA_ID" | head -2
curl -s -i -X DELETE "$PRODUCT_URL/prestamos/$PRESTAMO_ID" | head -2
curl -s -i -X DELETE "$PRODUCT_URL/tarjetas/$TARJETA_ID" | head -2
curl -s -i -X DELETE "$CUSTOMER_URL/customers/$CID" | head -2

echo
echo "== Listo =="
