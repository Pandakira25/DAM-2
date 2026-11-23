from datetime import datetime

propositos = int(input("Ingrese la cantidad de propósitos de insidencia que introducirá:\n"))

incidencias = []

for i in range(propositos):
    desc = input("Ingrse la descripción de la incidencia:\n")
    ahora = datetime.now()
    fecha = ahora.strftime("%d/%m/%Y")
    hora = ahora.strftime("%H/%M/%S")
    incidencia = {"descripcion":desc , "fecha" : fecha , "hora" : hora}
    incidencias.append(incidencia)

for inc in incidencias:
    print()
    print(f"Descripción: {inc['descripcion']}, fecha: {inc['fecha']}, hora: {inc['hora']}")

conteo_dias = {}

for inc in incidencias:
    fecha = inc['fecha']
    if conteo_dias.get(fecha,0) != 0:
        conteo_dias[fecha] = conteo_dias[fecha] + 1
    else:
        conteo_dias[fecha] = 1
    

for c,v in conteo_dias.items():
    print()
    print(f"Día: {c}, cantidad de incidencias: {v}")