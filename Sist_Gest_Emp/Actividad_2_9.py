tempsMax = [10, 50, -5, 25, 500]
tempsMin = [-4, 25, -20, 10, 300]

media = 0
dias = 5
tempMin = tempsMin[0]

tempIngres = int(input("ingrese una temperatura: "))

for i in tempsMin:
    media += i
    if tempMin > i:
        tempMin = i
    #-------------
#-------------

diasCoinciden = []

for i in tempsMax:
    if tempIngres == i:
        diasCoinciden.append(i)
    #-------------
#-------------

media = media/dias

if diasCoinciden.__len__() > 0:
    syso = f"Media: {media} Temperatura mínima: {tempMin}  La cantidad de dias cuya temperatura máxima coincide con la que ingresó son: "
    x = 0
    ds = []
    for i in diasCoinciden:
        d = tempsMax.index(i) + 1
        
        ds.append(f"Día {d}")
        
        x += 1
    #-------------
        
    syso += ",".join(ds)
        
    print(syso)
else:
    print("Media: ", media, " Temperatura mínima: ", tempMin, " No se encontraron coincidencias de temperatura máxima con la que ingresó")
#-------------
    