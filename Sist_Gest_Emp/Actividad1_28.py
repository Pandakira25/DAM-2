print("Ingrese el limite inferior del rango: ")
limite_inferior = int(input())
print("Ingrese el limite superior del rango: ") 
limite_superior = int(input())

while limite_inferior >= limite_superior:
    print("El limite inferior debe ser menor que el limite superior. Intente de nuevo.")
    print("Ingrese el limite inferior del rango: ")
    limite_inferior = int(input())
    print("Ingrese el limite superior del rango: ") 
    limite_superior = int(input())

num = -1
suma_dentro_rango = 0
nums_fuera_rango = 0
nums_iguales_limites = 0

while num != 0:
    print("Ingrse los número que quiera. Si ingresa 0 terminará el programa.")
    num = int(input())
    if num < limite_inferior or num > limite_superior:
        nums_fuera_rango += 1
    elif num == limite_inferior or num == limite_superior:
        nums_iguales_limites += 1
    else:
        suma_dentro_rango += num

print(f"La suma de los números dentro del rango es: {suma_dentro_rango}")
print(f"La cantidad de números fuera del rango es: {nums_fuera_rango}")
print(f"La cantidad de números iguales a los límites es: {nums_iguales_limites}")
