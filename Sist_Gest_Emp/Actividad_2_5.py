nombreCompleto = "zoe mariel jastreb"

separado = nombreCompleto.split(" ")

iniciales = ""

for x in separado:
    iniciales += x[0].capitalize() 
    
print(iniciales)