"""
=====================================================================
 APUNTES + EJERCICIOS RESUELTOS DE PYTHON
 Bloques: CADENAS (10) · LISTAS (18) · DICCIONARIOS (6)
=====================================================================
Cómo usarlo:
  - Ejecuta el archivo y elige bloque y ejercicio desde el menú.
  - Cada ejercicio es una función. Lee el enunciado, las notas (# NOTA)
    y la solución.
  - Al final de cada bloque tienes un "CHULETA" con lo que más cae.
"""

import random


# =====================================================================
#  FUNCIONES AUXILIARES (validar entradas)
# =====================================================================
# NOTA: input() SIEMPRE devuelve str. Hay que convertir con int()/float().
# NOTA: int("abc") lanza ValueError -> lo capturamos con try/except.

def pedir_entero(msg, minimo=None, maximo=None):
    """Pide un entero y repite hasta que sea válido (y esté en rango)."""
    while True:
        try:
            n = int(input(msg))
        except ValueError:
            print("  ✗ Eso no es un número entero.")
            continue
        if minimo is not None and n < minimo:
            print(f"  ✗ Debe ser >= {minimo}.")
        elif maximo is not None and n > maximo:
            print(f"  ✗ Debe ser <= {maximo}.")
        else:
            return n


def pedir_float(msg):
    while True:
        try:
            return float(input(msg))
        except ValueError:
            print("  ✗ Eso no es un número.")


def pedir_caracter(msg):
    """Valida que lo introducido sea EXACTAMENTE un carácter."""
    while True:
        c = input(msg)
        if len(c) == 1:
            return c
        print("  ✗ Introduce un único carácter.")


def cargar_tabla(filas, columnas, manual=False):
    """
    Crea una tabla (lista de listas) filas x columnas.
    manual=False -> la rellena con aleatorios (cómodo para probar).
    manual=True  -> pide cada valor por teclado (lo que haría el examen).
    """
    # NOTA IMPORTANTE: NUNCA [[0]*c]*f  -> repite la MISMA fila f veces
    # (si cambias una, cambian todas). Hay que crear cada fila nueva:
    tabla = [[0] * columnas for _ in range(filas)]
    for i in range(filas):
        for j in range(columnas):
            if manual:
                tabla[i][j] = pedir_entero(f"  tabla[{i}][{j}] = ")
            else:
                tabla[i][j] = random.randint(1, 9)
    return tabla


def mostrar_tabla(tabla):
    for fila in tabla:
        print(" ".join(f"{v:3}" for v in fila))


# =====================================================================
#  BLOQUE 1: CADENAS DE CARACTERES
# =====================================================================
"""
APUNTES CADENAS (str)
---------------------
- Son INMUTABLES: no puedes hacer s[0] = "a". Los métodos devuelven
  una cadena NUEVA (hay que guardarla: s = s.upper()).
- Se pueden recorrer con for:             for c in s:
- Indexación:  s[0] primero, s[-1] último.
- Slicing:     s[ini:fin:paso]  (fin NO incluido)
                 s[::-1]  -> invertida
                 s[:3]    -> primeros 3
- len(s) longitud.
- Métodos útiles:
    s.upper() / s.lower() / s.swapcase() / s.capitalize() / s.title()
    s.startswith("ab")  / s.endswith("ab")
    s.count("a")        -> veces que aparece
    s.find("ab")        -> posición o -1 si no está (s.index lanza error)
    s.replace(a, b)     -> reemplaza TODAS (replace(a, b, 1) solo la 1ª)
    s.split()           -> lista de palabras (separa por espacios y
                           ignora espacios múltiples)
    s.split(",")        -> separa por coma
    "sep".join(lista)   -> une una lista de str con un separador
    s.strip()           -> quita espacios de los extremos
    s.isupper() / s.islower() / s.isalpha() / s.isdigit()
- Operador in:   "ab" in s  -> True/False (¿contiene subcadena?)
- f-strings:     f"Hola {nombre}, tienes {edad}"
"""


def cadenas_ej1():
    """Ej 1: Escribir por pantalla cada carácter de una cadena."""
    cadena = input("Introduce una cadena: ")
    # for sobre un str recorre carácter a carácter
    for c in cadena:
        print(c)


def cadenas_ej2():
    """Ej 2: Comprobar si una cadena empieza por una subcadena."""
    cadena = input("Introduce la cadena: ")
    sub = input("Introduce la subcadena: ")
    # startswith() hace justo esto
    if cadena.startswith(sub):
        print(f"'{cadena}' EMPIEZA por '{sub}'")
    else:
        print(f"'{cadena}' NO empieza por '{sub}'")
    # Alternativa con slicing: cadena[:len(sub)] == sub


def cadenas_ej3():
    """Ej 3: Pedir cadena y carácter (validado) y contar apariciones."""
    cadena = input("Introduce una cadena: ")
    car = pedir_caracter("Introduce un carácter: ")

    # A mano (lo que suelen pedir en examen):
    contador = 0
    for c in cadena:
        if c == car:
            contador += 1
    print(f"'{car}' aparece {contador} veces")
    # Alternativa: cadena.count(car)
    # NOTA: distingue mayúsculas/minúsculas. Para ignorarlas:
    #       cadena.lower().count(car.lower())


def cadenas_ej4():
    """Ej 4: Contar cuántas palabras tiene una frase."""
    frase = input("Introduce una frase: ")
    # split() sin argumentos separa por espacios y descarta los vacíos,
    # así que "hola   mundo" son 2 palabras, no 4.
    palabras = frase.split()
    print(f"La frase tiene {len(palabras)} palabras")


def cadenas_ej5():
    """Ej 5: Mostrar las iniciales en mayúsculas de nombre y apellidos."""
    nombre = input("Nombre y apellidos: ")
    iniciales = ""
    for palabra in nombre.split():
        iniciales += palabra[0].upper()
    print("Iniciales:", iniciales)
    # Versión corta: "".join(p[0].upper() for p in nombre.split())


def cadenas_ej6():
    """Ej 6: Generar otra cadena invirtiendo la primera."""
    cadena = input("Introduce una cadena: ")

    # Forma 1: slicing (la más pythónica)
    print("Slicing:", cadena[::-1])

    # Forma 2: a mano, anteponiendo cada carácter
    invertida = ""
    for c in cadena:
        invertida = c + invertida   # añadimos DELANTE
    print("Bucle:  ", invertida)


def cadenas_ej7():
    """Ej 7: Sustituir un carácter por otro en la cadena."""
    cadena = input("Introduce una cadena: ")
    viejo = pedir_caracter("Carácter a sustituir: ")
    nuevo = pedir_caracter("Carácter nuevo: ")

    # replace sustituye TODAS las apariciones
    print("Todas:   ", cadena.replace(viejo, nuevo))
    # Si el enunciado se interpreta como "solo la primera aparición":
    print("Solo 1ª: ", cadena.replace(viejo, nuevo, 1))
    # NOTA: como las cadenas son inmutables, replace devuelve una NUEVA.


def cadenas_ej8():
    """Ej 8: Convertir mayúsculas a minúsculas y viceversa."""
    cadena = input("Introduce una cadena: ")

    # Con el método:
    print("swapcase:", cadena.swapcase())

    # A mano (por si no dejan usar swapcase):
    resultado = ""
    for c in cadena:
        if c.isupper():
            resultado += c.lower()
        elif c.islower():
            resultado += c.upper()
        else:
            resultado += c      # números, espacios, símbolos: igual
    print("A mano:  ", resultado)


def cadenas_ej9():
    """Ej 9: Comprobar si una cadena contiene una subcadena."""
    cadena = input("Introduce la cadena: ")
    sub = input("Introduce la subcadena: ")
    # El operador 'in' es lo más directo
    if sub in cadena:
        print(f"'{cadena}' CONTIENE '{sub}'")
        print("Primera posición:", cadena.find(sub))
    else:
        print(f"'{cadena}' NO contiene '{sub}'")


def cadenas_ej10():
    """Ej 10: Indicar si una cadena es un palíndromo."""
    cadena = input("Introduce una cadena: ")
    # Normalizamos: minúsculas y sin espacios, para que
    # "Anita lava la tina" cuente como palíndromo.
    limpia = cadena.lower().replace(" ", "")
    if limpia == limpia[::-1]:
        print(f"'{cadena}' ES un palíndromo")
    else:
        print(f"'{cadena}' NO es un palíndromo")
    # NOTA: no quita tildes ni signos; para eso haría falta más limpieza.


# ---------------------------------------------------------------------
# CHULETA CADENAS
#   Invertir -> s[::-1]      Contiene -> "x" in s     Palabras -> s.split()
#   Contar -> s.count(x)     Reemplazar -> s.replace(a,b)
#   Recorrer -> for c in s   Comparar sin mayúsc. -> s.lower() == t.lower()
# ---------------------------------------------------------------------


# =====================================================================
#  BLOQUE 2: LISTAS
# =====================================================================
"""
APUNTES LISTAS (list)
---------------------
- Ordenadas, MUTABLES, admiten elementos repetidos y de tipos mixtos.
    lista = []                 lista = [1, 2, 3]       lista = list()
- Acceso:    lista[0], lista[-1]     Slicing: lista[1:3], lista[::-1]
- Métodos que MODIFICAN la lista (y devuelven None):
    lista.append(x)        -> añade al final
    lista.insert(i, x)     -> inserta x en la posición i
    lista.pop()            -> quita y devuelve el último
    lista.pop(i)           -> quita y devuelve el de la posición i
    lista.remove(x)        -> quita la 1ª aparición de x (error si no está)
    del lista[i]           -> borra la posición i
    lista.sort()           -> ordena la propia lista (reverse=True: al revés)
    lista.reverse()        -> invierte la propia lista
- Funciones que NO modifican:
    len(l)  sum(l)  min(l)  max(l)  sorted(l)  l.count(x)  l.index(x)
- Copiar:   copia = lista.copy()  o  lista[:]
            ¡OJO!  b = a  NO copia: ambas variables apuntan a la MISMA lista.
- Recorrer: for x in lista:
            for i in range(len(lista)):
            for i, x in enumerate(lista, start=1):   # índice + valor
- Comprensión de listas: [x**2 for x in lista if x > 0]
- TABLAS (matrices) = listas de listas:   tabla[fila][columna]
    tabla = [[0] * columnas for _ in range(filas)]   # ✔ correcto
    tabla = [[0] * columnas] * filas                 # ✗ filas repetidas
"""


def listas_ej1():
    """Ej 1: 10 primeros múltiplos de un número (1-10) y su cuadrado."""
    n = pedir_entero("Número entre 1 y 10: ", 1, 10)
    multiplos = []
    for i in range(1, 11):
        multiplos.append(n * i)
    for m in multiplos:
        print(f"{m} -> cuadrado: {m ** 2}")
    # Versión corta: multiplos = [n * i for i in range(1, 11)]


def listas_ej2():
    """Ej 2: 5 cadenas por teclado, copiarlas en otra lista en orden inverso."""
    lista = []
    for i in range(5):
        lista.append(input(f"Cadena {i + 1}: "))

    inversa = []
    for elem in lista:
        inversa.insert(0, elem)      # siempre al principio -> queda invertida
    print("Original:", lista)
    print("Inversa: ", inversa)
    # Alternativas: lista[::-1]   o   copia = lista.copy(); copia.reverse()


def listas_ej3():
    """Ej 3: 5 notas (0-10): mostrar notas, media, máxima y mínima."""
    notas = []
    for i in range(5):
        notas.append(pedir_float(f"Nota {i + 1}: "))
        while not (0 <= notas[i] <= 10):
            print("  ✗ La nota debe estar entre 0 y 10.")
            notas[i] = pedir_float(f"Nota {i + 1}: ")

    print("Notas:", notas)
    print("Media:", sum(notas) / len(notas))
    print("Máxima:", max(notas))
    print("Mínima:", min(notas))


def listas_ej4():
    """Ej 4: Llenar una lista hasta introducir un número negativo."""
    lista = []
    n = pedir_entero("Número (negativo para terminar): ")
    while n >= 0:
        lista.append(n)
        n = pedir_entero("Número (negativo para terminar): ")
    # El negativo NO se guarda: solo imprimimos los introducidos
    print("Lista:", lista)


def listas_ej5():
    """Ej 5: Leer números, ordenarlos de menor a mayor y mostrarlos."""
    cantidad = pedir_entero("¿Cuántos números? ", 1)
    lista = [pedir_entero(f"Número {i + 1}: ") for i in range(cantidad)]
    lista.sort()                    # modifica la lista
    print("Ordenada:", lista)
    # sorted(lista) devuelve una NUEVA lista sin tocar la original
    # lista.sort(reverse=True) -> de mayor a menor


def listas_ej6():
    """Ej 6: Número de mes -> nombre y días (febrero = 28)."""
    # Dos listas "paralelas": misma posición = mismo mes
    nombres = ["enero", "febrero", "marzo", "abril", "mayo", "junio", "julio",
               "agosto", "septiembre", "octubre", "noviembre", "diciembre"]
    dias = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]

    mes = pedir_entero("Número de mes (1-12): ", 1, 12)
    # El usuario cuenta desde 1, las listas desde 0 -> mes - 1
    print(f"{nombres[mes - 1].capitalize()} tiene {dias[mes - 1]} días")


def listas_ej7():
    """Ej 7: lista3 = lista1 + lista2 (suma elemento a elemento)."""
    lista1, lista2, lista3 = [], [], []
    for i in range(5):
        lista1.append(pedir_entero(f"lista1[{i}]: "))
    for i in range(5):
        lista2.append(pedir_entero(f"lista2[{i}]: "))

    for i in range(5):
        lista3.append(lista1[i] + lista2[i])
    print("lista3:", lista3)
    # NOTA: lista1 + lista2 con el operador + CONCATENA (10 elementos),
    # no suma elemento a elemento. Aquí se pide lo segundo.
    # Corto: [a + b for a, b in zip(lista1, lista2)]


def listas_ej8():
    """Ej 8: Nombres y edades hasta '*'. Mostrar mayores de edad y los mayores."""
    nombres, edades = [], []
    nombre = input("Nombre (* para terminar): ")
    while nombre != "*":
        nombres.append(nombre)
        edades.append(pedir_entero("Edad: ", 0))
        nombre = input("Nombre (* para terminar): ")

    if not nombres:
        print("No se ha introducido ningún alumno.")
        return

    print("\nAlumnos mayores de edad:")
    for i in range(len(nombres)):
        if edades[i] >= 18:
            print(f"  {nombres[i]} ({edades[i]})")

    print("\nAlumno(s) con más edad:")
    max_edad = max(edades)
    for i in range(len(nombres)):
        if edades[i] == max_edad:       # puede haber empate
            print(f"  {nombres[i]} ({edades[i]})")


def listas_ej9():
    """Ej 9: Temperaturas mín y máx de 5 días."""
    minimas, maximas = [], []
    for i in range(5):
        print(f"Día {i + 1}")
        t_min = pedir_float("  Temperatura mínima: ")
        t_max = pedir_float("  Temperatura máxima: ")
        minimas.append(t_min)
        maximas.append(t_max)

    # 1) Media de cada día
    print("\nTemperatura media de cada día:")
    for i in range(5):
        print(f"  Día {i + 1}: {(minimas[i] + maximas[i]) / 2}")

    # 2) Días con menos temperatura (la mínima más baja de todas)
    menor = min(minimas)
    print(f"\nTemperatura más baja: {menor}. Días:")
    for i in range(5):
        if minimas[i] == menor:
            print(f"  Día {i + 1}")

    # 3) Buscar días cuya máxima coincide con una temperatura dada
    buscada = pedir_float("\nTemperatura máxima a buscar: ")
    encontrado = False
    for i in range(5):
        if maximas[i] == buscada:
            print(f"  Coincide el día {i + 1}")
            encontrado = True
    if not encontrado:
        print("  Ningún día tuvo esa temperatura máxima.")
    # Patrón "bandera" (encontrado = False/True): MUY típico de examen.


def listas_ej10():
    """Ej 10: Tabla 5x5, sumar filas y columnas."""
    tabla = cargar_tabla(5, 5)      # cargar_tabla(5, 5, manual=True) para teclado
    mostrar_tabla(tabla)

    print("\nSuma de cada fila:")
    for i in range(5):
        suma = 0
        for j in range(5):
            suma += tabla[i][j]
        print(f"  Fila {i}: {suma}")

    print("\nSuma de cada columna:")
    for j in range(5):
        suma = 0
        for i in range(5):          # aquí el bucle externo es la COLUMNA
            suma += tabla[i][j]
        print(f"  Columna {j}: {suma}")
    # NOTA: fila -> sum(tabla[i])     columna -> sum(f[j] for f in tabla)


def listas_ej11():
    """Ej 11: Tabla 5x5 'diagonal' con 1 en la diagonal y 0 en el resto."""
    diagonal = [[0] * 5 for _ in range(5)]
    for i in range(5):
        for j in range(5):
            if i == j:              # diagonal principal: fila == columna
                diagonal[i][j] = 1
    mostrar_tabla(diagonal)
    # Diagonal secundaria sería: i + j == 4  (o  j == 4 - i)


def listas_ej12():
    """Ej 12: Tabla 5x15 'marco': 1 en el borde, 0 dentro."""
    FILAS, COLS = 5, 15
    marco = [[0] * COLS for _ in range(FILAS)]
    for i in range(FILAS):
        for j in range(COLS):
            # Es borde si está en la primera/última fila o columna
            if i == 0 or i == FILAS - 1 or j == 0 or j == COLS - 1:
                marco[i][j] = 1
    for fila in marco:
        print("".join(str(v) for v in fila))


def listas_ej13():
    """Ej 13: Kilómetros por conductor y día -> total_kms."""
    n = pedir_entero("¿Cuántos conductores? ", 1)
    nombre, kms = [], []
    for i in range(n):
        nombre.append(input(f"Nombre del conductor {i + 1}: "))
        fila = []
        for d in range(7):
            fila.append(pedir_float(f"  Km del día {d + 1}: "))
        kms.append(fila)

    total_kms = []
    for i in range(n):
        total_kms.append(sum(kms[i]))   # suma de la fila del conductor i

    print("\nKilómetros totales:")
    for i in range(n):
        print(f"  {nombre[i]}: {total_kms[i]} km")
    # Listas paralelas: nombre[i] <-> kms[i] <-> total_kms[i]


def listas_ej14():
    """Ej 14: 5 artículos x 4 sucursales (precios y cantidades vendidas)."""
    ARTICULOS, SUCURSALES = 5, 4
    precios = [pedir_float(f"Precio del artículo {a + 1}: ")
               for a in range(ARTICULOS)]

    # cantidades[articulo][sucursal]
    cantidades = []
    for a in range(ARTICULOS):
        fila = []
        for s in range(SUCURSALES):
            fila.append(pedir_entero(
                f"Cantidad del artículo {a + 1} en sucursal {s + 1}: ", 0))
        cantidades.append(fila)

    # 1) Cantidades totales de cada artículo (suma de cada fila)
    print("\nCantidad total de cada artículo:")
    for a in range(ARTICULOS):
        print(f"  Artículo {a + 1}: {sum(cantidades[a])}")

    # 2) Cantidad de artículos en la sucursal 2 (suma de la columna índice 1)
    total_suc2 = sum(cantidades[a][1] for a in range(ARTICULOS))
    print(f"\nArtículos vendidos en la sucursal 2: {total_suc2}")

    # 3) Cantidad del artículo 3 en la sucursal 1  (índices 2 y 0)
    print(f"Artículo 3 en sucursal 1: {cantidades[2][0]}")

    # 4) Recaudación de cada sucursal = sum(precio * cantidad) por columna
    recaudacion = []
    for s in range(SUCURSALES):
        total = 0
        for a in range(ARTICULOS):
            total += precios[a] * cantidades[a][s]
        recaudacion.append(total)
    print("\nRecaudación por sucursal:")
    for s in range(SUCURSALES):
        print(f"  Sucursal {s + 1}: {recaudacion[s]}")

    # 5) Recaudación total
    print(f"\nRecaudación total de la empresa: {sum(recaudacion)}")

    # 6) Sucursal de mayor recaudación
    mejor = recaudacion.index(max(recaudacion))
    print(f"Sucursal con mayor recaudación: {mejor + 1}")
    # NOTA: los índices empiezan en 0, por eso sumamos 1 al mostrar.


def listas_ej15():
    """Ej 15: Quiniela de 15 partidos con tablas 'equipos' y 'resultados'."""
    PARTIDOS = 15
    equipos = []      # equipos[i] = [local, visitante]
    resultados = []   # resultados[i] = [goles_local, goles_visitante]

    for i in range(PARTIDOS):
        print(f"Partido {i + 1}")
        local = input("  Equipo local: ")
        visitante = input("  Equipo visitante: ")
        g_local = pedir_entero("  Goles local: ", 0)
        g_visit = pedir_entero("  Goles visitante: ", 0)
        equipos.append([local, visitante])
        resultados.append([g_local, g_visit])

    print("\n===== QUINIELA =====")
    for i in range(PARTIDOS):
        gl, gv = resultados[i]
        signo = "1" if gl > gv else ("2" if gl < gv else "X")
        print(f"{i + 1:2}. {equipos[i][0]} {gl} - {gv} {equipos[i][1]}  -> {signo}")

    # PREGUNTA DEL ENUNCIADO:
    # ¿Qué modificación para guardar todas las jornadas de la temporada?
    # RESPUESTA: añadir una dimensión más (tabla de 3 dimensiones):
    #     equipos[jornada][partido][equipo]      (jornada: 0..37)
    #     resultados[jornada][partido][equipo]
    # Es decir, una lista de jornadas, donde cada jornada es la tabla
    # de 15x2 de arriba:
    #     temporada_equipos = [ [[...], [...], ...], ... ]  # 38 jornadas
    #     temporada_resultados[jornada].append([gl, gv])


def listas_ej16():
    """Ej 16: Menú de operaciones sobre una lista de números."""
    lista = []
    opcion = 0
    while opcion != 9:
        print("\n1. Añadir número al final")
        print("2. Añadir número en una posición")
        print("3. Longitud de la lista")
        print("4. Eliminar el último número")
        print("5. Eliminar un número (por posición)")
        print("6. Contar apariciones de un número")
        print("7. Posiciones de un número")
        print("8. Mostrar números")
        print("9. Salir")
        opcion = pedir_entero("Opción: ", 1, 9)

        if opcion == 1:
            lista.append(pedir_entero("Número: "))

        elif opcion == 2:
            n = pedir_entero("Número: ")
            pos = pedir_entero("Posición (desde 1): ")
            if 1 <= pos <= len(lista):
                lista.insert(pos - 1, n)        # pos-1: pasamos a índice
            else:
                print("  ✗ La posición no existe.")

        elif opcion == 3:
            print("Longitud:", len(lista))

        elif opcion == 4:
            if lista:                           # lista no vacía
                print("Eliminado:", lista.pop())
            else:
                print("  ✗ La lista está vacía.")

        elif opcion == 5:
            pos = pedir_entero("Posición (desde 1): ")
            if 1 <= pos <= len(lista):
                del lista[pos - 1]
            else:
                print("  ✗ La posición no existe.")

        elif opcion == 6:
            n = pedir_entero("Número a contar: ")
            print(f"Aparece {lista.count(n)} veces")

        elif opcion == 7:
            n = pedir_entero("Número a buscar: ")
            posiciones = [i for i, x in enumerate(lista, start=1) if x == n]
            if posiciones:
                print("Posiciones:", posiciones)
            else:
                print("  El número no está en la lista.")

        elif opcion == 8:
            print("Lista:", lista)

    print("Adiós")
    # PATRÓN MENÚ: while opcion != SALIR + if/elif por opción.


def listas_ej17():
    """Ej 17: Lista hasta negativo y copia sin duplicados."""
    lista = []
    n = pedir_entero("Número (negativo para terminar): ")
    while n >= 0:
        lista.append(n)
        n = pedir_entero("Número (negativo para terminar): ")

    # Mantiene el orden original:
    sin_duplicados = []
    for x in lista:
        if x not in sin_duplicados:
            sin_duplicados.append(x)

    print("Original:      ", lista)
    print("Sin duplicados:", sin_duplicados)
    # Alternativa: list(set(lista))  -> quita duplicados PERO pierde el orden.
    # Alternativa que mantiene orden: list(dict.fromkeys(lista))


def listas_ej18():
    """Ej 18: Lista de palabras + menú (contar, modificar, eliminar, mostrar)."""
    palabras = []
    p = input("Palabra (vacío para terminar): ")
    while p != "":
        palabras.append(p)
        p = input("Palabra (vacío para terminar): ")

    opcion = ""
    while opcion != "5":
        print("\n1. Contar\n2. Modificar\n3. Eliminar\n4. Mostrar\n5. Terminar")
        opcion = input("Opción: ")

        if opcion == "1":
            c = input("Cadena a contar: ")
            print(f"Aparece {palabras.count(c)} veces")

        elif opcion == "2":
            vieja = input("Cadena a modificar: ")
            nueva = input("Cadena nueva: ")
            for i in range(len(palabras)):
                if palabras[i] == vieja:
                    palabras[i] = nueva         # modificamos por índice
            # (for x in palabras: x = nueva  NO funcionaría: x es una copia)

        elif opcion == "3":
            c = input("Cadena a eliminar: ")
            # remove() solo quita la 1ª aparición, así que filtramos todas:
            palabras = [x for x in palabras if x != c]

        elif opcion == "4":
            print(palabras)

        elif opcion != "5":
            print("  ✗ Opción no válida.")


# ---------------------------------------------------------------------
# CHULETA LISTAS
#   Añadir: append / insert      Quitar: pop / remove / del
#   Ordenar: sort() (modifica) / sorted() (nueva)
#   Max/min/suma: max() min() sum()     Posición del max: l.index(max(l))
#   Matriz: m[fila][col] ; filas = len(m) ; columnas = len(m[0])
#   Posiciones de usuario (desde 1) -> índice = posición - 1
# ---------------------------------------------------------------------


# =====================================================================
#  BLOQUE 3: DICCIONARIOS
# =====================================================================
"""
APUNTES DICCIONARIOS (dict)
---------------------------
- Pares clave: valor. Claves ÚNICAS e inmutables (str, int, tuple...).
  Los valores pueden ser de cualquier tipo (incluso listas u otros dict).
    d = {}        d = {"ana": 8, "luis": 5}
- Acceso:     d["ana"]            -> KeyError si la clave no existe
              d.get("ana")        -> None si no existe
              d.get("ana", 0)     -> 0 si no existe (valor por defecto)
- Añadir / modificar:   d["pepe"] = 7   (si existe, lo sobrescribe)
- Borrar:     del d["ana"]   o   d.pop("ana")
- Comprobar clave:   "ana" in d     (busca en las CLAVES, no en valores)
- Recorrer:
    for k in d:                  # claves
    for k, v in d.items():       # clave y valor
    d.keys()   d.values()   d.items()
- len(d) = número de pares.
- Patrón CONTADOR:   cuenta[c] = cuenta.get(c, 0) + 1
- Máximo por valor:  max(d, key=d.get)  -> clave con mayor valor
- Comprensión:       {i: i**2 for i in range(1, 6)}
"""


def dicc_ej1():
    """Ej 1: Diccionario con claves 1..n y valores = cuadrados."""
    n = pedir_entero("Número: ", 1)
    cuadrados = {}
    for i in range(1, n + 1):
        cuadrados[i] = i ** 2
    print(cuadrados)
    # Versión corta: {i: i ** 2 for i in range(1, n + 1)}


def dicc_ej2():
    """Ej 2: Diccionario con las apariciones de cada carácter."""
    cadena = input("Introduce una cadena: ")
    apariciones = {}
    for c in cadena:
        if c in apariciones:
            apariciones[c] += 1
        else:
            apariciones[c] = 1
    print(apariciones)
    # Forma compacta con get:  apariciones[c] = apariciones.get(c, 0) + 1


def dicc_ej3():
    """Ej 3: Alumnos y notas hasta '*': aprobados, nota más alta y más baja."""
    notas = {}
    nombre = input("Nombre (* para terminar): ")
    while nombre != "*":
        nota = pedir_float("Nota: ")
        notas[nombre] = nota
        nombre = input("Nombre (* para terminar): ")

    if not notas:
        print("No se ha introducido ningún alumno.")
        return

    print("\nAprobados:")
    for nombre, nota in notas.items():
        if nota >= 5:
            print(f"  {nombre}: {nota}")

    mejor = max(notas, key=notas.get)       # clave con el valor máximo
    peor = min(notas, key=notas.get)
    print(f"\nNota más alta: {mejor} ({notas[mejor]})")
    print(f"Nota más baja: {peor} ({notas[peor]})")
    # NOTA: si el mismo nombre se repite, se sobrescribe la nota anterior.


def dicc_ej4():
    """Ej 4: Precios de frutas: calcular el precio final de una venta."""
    precios = {"manzana": 1.5, "pera": 2.0, "plátano": 1.2, "naranja": 1.0,
               "fresa": 3.5}               # precio por kg (ejemplo)

    otra = "s"
    while otra.lower() == "s":
        fruta = input("Fruta: ").lower()
        if fruta in precios:
            cantidad = pedir_float("Cantidad vendida (kg): ")
            print(f"Precio final: {precios[fruta] * cantidad:.2f} €")
        else:
            print("  ✗ Error: esa fruta no existe.")
        otra = input("¿Otra consulta? (s/n): ")


def dicc_ej5():
    """Ej 5: Alumnos con listas de notas (distinta cantidad por alumno)."""
    alumnos = {}                            # nombre -> lista de notas
    n = pedir_entero("¿Cuántos alumnos? ", 1)

    for _ in range(n):
        nombre = input("Nombre del alumno: ")
        while nombre in alumnos:            # si ya existe -> error y repetir
            print("  ✗ Error: ese alumno ya existe.")
            nombre = input("Nombre del alumno: ")

        alumnos[nombre] = []                # valor = lista vacía
        nota = pedir_float("  Nota (negativa para terminar): ")
        while nota >= 0:
            alumnos[nombre].append(nota)    # añadimos a la lista del alumno
            nota = pedir_float("  Nota (negativa para terminar): ")

    print("\nNota media:")
    for nombre, lista_notas in alumnos.items():
        if lista_notas:
            media = sum(lista_notas) / len(lista_notas)
            print(f"  {nombre}: {media:.2f}")
        else:
            print(f"  {nombre}: sin notas")   # evita dividir entre 0


def dicc_ej6():
    """Ej 6: Agenda de contactos con diccionario nombre -> teléfono."""
    agenda = {}
    opcion = ""
    while opcion != "5":
        print("\n1. Añadir/modificar\n2. Buscar\n3. Borrar\n4. Listar\n5. Salir")
        opcion = input("Opción: ")

        if opcion == "1":
            nombre = input("Nombre: ")
            if nombre in agenda:
                print(f"  Teléfono actual: {agenda[nombre]}")
                if input("  ¿Modificar? (s/n): ").lower() == "s":
                    agenda[nombre] = input("  Nuevo teléfono: ")
            else:
                agenda[nombre] = input("  Teléfono: ")

        elif opcion == "2":
            inicio = input("Buscar contactos que empiecen por: ").lower()
            encontrados = False
            for nombre, tel in agenda.items():
                if nombre.lower().startswith(inicio):
                    print(f"  {nombre}: {tel}")
                    encontrados = True
            if not encontrados:
                print("  No hay coincidencias.")

        elif opcion == "3":
            nombre = input("Nombre a borrar: ")
            if nombre in agenda:
                if input(f"  ¿Borrar a {nombre}? (s/n): ").lower() == "s":
                    del agenda[nombre]
                    print("  Contacto borrado.")
            else:
                print("  ✗ No existe ese contacto.")

        elif opcion == "4":
            if agenda:
                for nombre, tel in agenda.items():
                    print(f"  {nombre}: {tel}")
            else:
                print("  La agenda está vacía.")

        elif opcion != "5":
            print("  ✗ Opción no válida.")


# ---------------------------------------------------------------------
# CHULETA DICCIONARIOS
#   Existe clave -> k in d     Añadir -> d[k] = v      Borrar -> del d[k]
#   Recorrer -> for k, v in d.items()
#   Contador -> d[k] = d.get(k, 0) + 1
#   Clave del máx. valor -> max(d, key=d.get)
#   Dict de listas -> d[k] = []  y luego  d[k].append(x)
# ---------------------------------------------------------------------


# =====================================================================
#  MENÚ PRINCIPAL
# =====================================================================
BLOQUES = {
    "1": ("CADENAS", {
        "1": cadenas_ej1, "2": cadenas_ej2, "3": cadenas_ej3,
        "4": cadenas_ej4, "5": cadenas_ej5, "6": cadenas_ej6,
        "7": cadenas_ej7, "8": cadenas_ej8, "9": cadenas_ej9,
        "10": cadenas_ej10,
    }),
    "2": ("LISTAS", {
        "1": listas_ej1, "2": listas_ej2, "3": listas_ej3,
        "4": listas_ej4, "5": listas_ej5, "6": listas_ej6,
        "7": listas_ej7, "8": listas_ej8, "9": listas_ej9,
        "10": listas_ej10, "11": listas_ej11, "12": listas_ej12,
        "13": listas_ej13, "14": listas_ej14, "15": listas_ej15,
        "16": listas_ej16, "17": listas_ej17, "18": listas_ej18,
    }),
    "3": ("DICCIONARIOS", {
        "1": dicc_ej1, "2": dicc_ej2, "3": dicc_ej3,
        "4": dicc_ej4, "5": dicc_ej5, "6": dicc_ej6,
    }),
}


def main():
    while True:
        print("\n===== EJERCICIOS DE PYTHON =====")
        for k, (nombre, ejercicios) in BLOQUES.items():
            print(f"{k}. {nombre} ({len(ejercicios)} ejercicios)")
        print("0. Salir")
        bloque = input("Bloque: ")

        if bloque == "0":
            break
        if bloque not in BLOQUES:
            print("✗ Bloque no válido.")
            continue

        nombre, ejercicios = BLOQUES[bloque]
        num = input(f"Ejercicio de {nombre} (1-{len(ejercicios)}): ")
        if num in ejercicios:
            print(f"\n--- {nombre} · Ejercicio {num} ---")
            ejercicios[num]()
        else:
            print("✗ Ejercicio no válido.")


# NOTA: este 'if' hace que main() solo se ejecute si lanzas ESTE archivo
# directamente (y no si lo importas desde otro).
if __name__ == "__main__":
    main()
