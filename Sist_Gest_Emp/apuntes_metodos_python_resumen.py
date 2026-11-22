"""
=====================================================================
 UF2.4 FUNCIONES Y MÉTODOS DE PYTHON
 Resumen solo con el contenido de los apuntes, sin ejemplos.
 Convención: [x] = parámetro opcional
=====================================================================


=====================================================================
 1. FUNCIÓN print()
=====================================================================
- Muestra lo que le pasemos en los argumentos separados por espacios.
- Para eliminar ese espacio se puede reducir el número de argumentos,
  concatenando texto y variables con el operador suma (+).
- No se pueden concatenar cadenas y números con el operador +: hay que
  convertir los números a cadenas con la función str() antes de
  concatenarlos.


=====================================================================
 2. LITERALES DE CADENAS FORMATEADOS (f-strings)
=====================================================================
- Se colocan una f o F delante de la cadena e incluyendo en la cadena
  expresiones entre llaves ({expresión}); se obtiene la cadena
  formateada con el valor de la expresión.
- {x=}         Muestra el nombre de la variable y su valor.
- {nombre:8}   Reserva 8 huecos para la cadena.
- {edad:6d}    Reserva 6 huecos para un entero.
- {cadena!r}   Usa repr(): muestra las comillas.
- {cadena!s}   Usa str(): no muestra las comillas.
- {decimal:.2f}  Muestra el número con 2 decimales.


=====================================================================
 3. MÉTODOS DE UN str
=====================================================================

--- 3.1 DE FORMATO ---
capitalize()   Retorna una copia de la cadena con la primera letra en
               mayúsculas.
lower()        Retorna una copia de la cadena en minúsculas.
upper()        Retorna una copia de la cadena en mayúsculas.
swapcase()     Retorna una copia de la cadena convertidas las
               mayúsculas en minúsculas y viceversa.
title()        Retorna una copia de la cadena con la primera letra de
               cada palabra en mayúsculas.
zfill(longitud)
               Retorna una ampliación de la cadena, rellenada con ceros
               a la izquierda hasta alcanzar la longitud indicada.
center(longitud[, "caracter de relleno"])
               Retorna una ampliación de la cadena, rellenada con el
               carácter de relleno (por defecto un espacio en blanco)
               hasta alcanzar la longitud especificada, de manera que
               la cadena inicial quedará centrada.
ljust(longitud[, "caracter de relleno"])
               Igual que center, pero la cadena inicial quedará a la
               izquierda.
rjust(longitud[, "caracter de relleno"])
               Igual que center, pero la cadena inicial quedará a la
               derecha.

--- 3.2 DE BÚSQUEDA ---
count("subcadena"[, posicion_inicio, posicion_fin])
               Retorna un entero con la cantidad de apariciones de
               subcadena dentro de la cadena entre la posición de
               inicio y la posición de fin.
find("subcadena"[, posicion_inicio, posicion_fin])
               Retorna un entero con la posición donde inicia la
               subcadena dentro de la cadena. Si no la encuentra,
               retorna -1.
len(cadena)    Retorna el número de caracteres de una cadena.

--- 3.3 DE VALIDACIÓN ---
startswith("subcadena"[, posicion_inicio, posicion_fin])
               Retorna True o False en función de si una cadena
               comienza con una subcadena determinada.
endswith("subcadena"[, posicion_inicio, posicion_fin])
               Retorna True o False en función de si una cadena
               finaliza con una subcadena determinada.
islower()      Retorna True o False en función de si una cadena
               contiene solo minúsculas o no.
isupper()      Retorna True o False en función de si una cadena
               contiene solo mayúsculas o no.
isalnum()      Retorna True o False en función de si una cadena
               contiene únicamente caracteres alfanuméricos.
isalpha()      Retorna True o False en función de si una cadena es
               alfabética.
isdigit()      Retorna True o False en función de si una cadena es
               numérica.
isspace()      Retorna True o False en función de si una cadena
               contiene solo espacios en blanco.
istitle()      Retorna True o False en función de si una cadena tiene
               formato de título.

--- 3.4 DE SUSTITUCIÓN ---
format(args)   Retorna una cadena formateada, sustituyendo texto
               dinámicamente. Los huecos {} se pueden rellenar por
               posición ({0}, {1}...) o por nombre ({bruto}, {iva}...
               pasando bruto=100, iva=21...).
replace("subcadena a buscar", "subcadena para reemplazar")
               Retorna una cadena en la que se reemplaza el texto
               indicado.
strip(["caracter"])
               Retorna una cadena tras haber eliminado el carácter
               indicado. Por defecto es el espacio en blanco.
lstrip(["caracter"])
               Retorna una cadena tras haber eliminado del lado
               izquierdo el carácter indicado. Por defecto es el
               espacio en blanco.
rstrip(["caracter"])
               Retorna una cadena tras haber eliminado del lado derecho
               el carácter indicado. Por defecto es el espacio en
               blanco.

--- 3.5 DE UNIÓN Y PARTICIÓN ---
join(iterable) Retorna la cadena unida con el iterable (la cadena
               separa cada uno de los elementos del iterable).
partition("separador")
               Retorna una tupla de tres elementos: el primero es el
               contenido de la cadena previo a la primera ocurrencia
               del separador, el segundo es el separador mismo y el
               tercero es el contenido de la cadena posterior al
               separador.
split("separador")
               Retorna una lista con todos los elementos encontrados al
               dividir la cadena por un separador.
splitlines()   Retorna una lista donde cada elemento es una fracción de
               la cadena dividida en líneas.


=====================================================================
 4. MÉTODOS Y OPERACIONES DE LISTAS
=====================================================================

--- 4.1 OPERACIONES ---
+      Concatena listas. Es necesario que los dos operandos sean
       listas.
+=     Se puede utilizar para añadir elementos a una lista.

Copiar listas:
- Para copiar una lista1 en una lista2 no basta con igualarlas: si se
  igualan sin más y se modifica la lista original, la modificación
  afecta a ambas.
- Para copiar una lista conservando su valor aunque se modifique la
  original, hay que utilizar la notación de sublistas (lista[:]).

--- 4.2 DE AGREGACIÓN Y ELIMINACIÓN ---
append("elemento")          Agrega un elemento al final de la lista.
extend(otra_lista)          Agrega varios elementos al final de la
                            lista.
insert(posición, "elemento")  Agrega un elemento en una posición
                            determinada.
pop()                       Elimina el último elemento de la lista.
pop(índice)                 Elimina un elemento por su índice.
remove("valor")             Elimina un elemento por su valor.
del lista[índice]           Elimina el elemento de esa posición.

--- 4.3 DE ORDEN Y BÚSQUEDA ---
reverse()              Invierte el orden de una lista.
sort()                 Ordena una lista en forma ascendente.
sort(reverse=True)     Ordena una lista en forma descendente.
count(elemento)        Cuenta la cantidad de apariciones del elemento.
index(elemento[, indice_inicio, indice_fin])
                       Obtiene el índice en el que aparece el elemento
                       por primera vez.
max(lista)             Obtiene el máximo valor de la lista.
min(lista)             Obtiene el mínimo valor de la lista.
len(lista)             Obtiene la cantidad de elementos de la lista.


=====================================================================
 5. MÉTODOS DE DICCIONARIOS
=====================================================================

--- 5.1 CONSULTA ---
list(dic)      Retorna una lista de todas las claves usadas en el
               diccionario.
len(dic)       Obtiene la cantidad de elementos del diccionario.
items()        Retorna una nueva vista de los elementos del diccionario
               (pares (key, value)). Se puede pasar a lista con
               list(dic.items()).
keys()         Retorna una nueva vista de las claves del diccionario.
values()       Retorna una nueva vista de los valores del diccionario.
get(clave[, default=None])
               Retorna el elemento almacenado bajo la clave, si la
               clave está en el diccionario; si no, retorna default.
setdefault(clave, default=None)
               Si la clave está incluida en el diccionario, retorna el
               valor almacenado. Si no, inserta con esa clave el valor
               definido en default y retorna default. El valor por
               defecto de default es None.

--- 5.2 ELIMINACIÓN Y COPIA ---
clear()        Elimina todos los elementos del diccionario.
del(dic[clave])
               Elimina la entrada con esa clave del diccionario. Lanza
               una excepción si la clave no está en el diccionario.
pop(clave[, default])
               Si la clave está en el diccionario, la elimina y
               retorna su valor; si no está, retorna default. Si no se
               especifica default y la clave no se encuentra, se lanza
               la excepción.
popitem()      Elimina y retorna la última pareja (clave, valor)
               añadida al diccionario.
copy()         Copia un diccionario con distinta referencia, es decir,
               si modificamos el diccionario origen no se modifica la
               copia.

--- 5.3 ITERADORES ---
iter(dic)      Iterador que recorre las claves del diccionario (forma
               abreviada de iter(d.keys())).
reversed(dic)  Iterador que recorre las claves en orden inverso (forma
               abreviada de reversed(d.keys())). También se puede
               aplicar a .values() y .items().
ERRATA: en los apuntes originales las descripciones de iter() y
reversed() aparecen intercambiadas; aquí van como funcionan realmente.

--- 5.4 RECORRIDO DE DICCIONARIOS ---
Un diccionario se puede recorrer de tres formas:
- Recorriendo sus claves:     for clave in dict.keys():
- Recorriendo sus valores:    for valor in dict.values():
- Recorriendo las entradas:   for clave, valor in dict.items():


=====================================================================
 ENLACES (de los apuntes)
=====================================================================
https://docs.python.org/es/3/library/functions.html
https://docs.python.org/es/3/library/stdtypes.html#string-methods
https://docs.python.org/es/3/library/stdtypes.html#mapping-types-dict
"""
