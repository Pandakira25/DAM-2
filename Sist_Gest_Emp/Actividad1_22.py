import random

rd = random.randint(1, 100)

intentos = 0

while intentos < 5:
    intentos += 1
    num = int(input("Introduce un número: "))
    if num == rd:
        print(f"¡Acertaste! El número era {rd}")
        print(f"Lo lograste en {intentos} intentos")
        break
    elif num < rd:
        print("El número es mayor")
        print(f"Te quedan {5 - intentos} intentos")
    else:
        print("El número es menor")
        print(f"Te quedan {5 - intentos} intentos")

if intentos == 5 and num != rd:
    print(f"Lo siento, no lo lograste. El número era {rd}")