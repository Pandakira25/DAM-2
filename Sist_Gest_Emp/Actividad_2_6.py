agenda = {}

def add_modify():
    name = input("Ingrese el nombre: ").strip()
    if name in agenda:
        print(f"{name} ya está en la agenda. Teléfono: {agenda[name]}")
        if input("¿Quieres modificarlo? (s/n): ").lower() == "s":
            agenda[name] = input("Nuevo teléfono: ").strip()
            print("Teléfono modificado.")
        #-------------
    else:
        if input(f"{name} no existe. ¿Quieres añadirlo? (s/n): ").lower() == "s":
            agenda[name] = input("Teléfono: ").strip()
            print("Contacto añadido.")
        #-------------
    #-------------
#-------------

def search():
    text = input("Buscar contactos que empiecen por: ").strip().lower()
    found = {}
    for n, p in agenda.items():
        if n.lower().startswith(text):
            found[n] = p
        #-------------
    #-------------
    if found:
        for name, phone in found.items():
            print(f"{name}: {phone}")
        #-------------
    else:
        print("No hay contactos que coincidan.")
    #-------------
#-------------

def delete():
    name = input("Nombre a borrar: ").strip()
    if name in agenda:
        if input(f"¿Seguro que quieres borrar a {name}? (s/n): ").lower() == "s":
            del(agenda[name])
            print("Contacto borrado.")
        #-------------
    else:
        print("Ese contacto no existe.")
    #-------------
#-------------

def list_all():
    if agenda:
        for name, phone in agenda.items():
            print(f"{name}: {phone}")
        #-------------
    else:
        print("La agenda está vacía.")
    #-------------
#-------------

option = ""
while option != "5":
    print("\n--- AGENDA ---")
    print("1. Añadir/modificar")
    print("2. Buscar")
    print("3. Borrar")
    print("4. Listar")
    print("5. Salir")
    option = input().strip()

    if option == "1":
        add_modify()
    elif option == "2":
        search()
    elif option == "3":
        delete()
    elif option == "4":
        list_all()
    elif option == "5":
        print("Saliendo...")
    else:
        print("Opción no válida.")
    #-------------
#-------------