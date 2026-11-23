verbos = {
"SER/ESTAR": "BE-WAS/WERE-BEEN", "LLEGAR A SER": "BECOME-BECAME-BECOME",
"EMPEZAR": "BEGIN-BEGAN-BEGUN", "ROMPER": "BREAK-BROKE-BROKEN",
"TRAER": "BRING-BROUGHT-BROUGHT", "COMPRAR": "BUY-BOUGHT-BOUGHT",
"COGER": "CATCH-CAUGHT-CAUGHT", "ELEGIR/ESCOGER": "CHOOSE-CHOSE-CHOSEN",
"VENIR": "COME-CAME-COME", "COSTAR": "COST-COST-COST", "CORTAR": "CUT-CUT-CUT",
"HACER": "DO-DID-DONE", "DIBUJAR": "DRAW-DREW-DRAWN",
"SOÑAR": "DREAM-DREAMT-DREAMT", "BEBER": "DRINK-DRANK-DRUNK",
"CONDUCIR": "DRIVE-DROVE-DRIVEN", "COMER": "EAT-ATE-EATEN",
"CAER": "FALL-FELL-FALLEN", "SENTIR": "FEEL-FELT-FELT",
"ENCONTRAR": "FIND-FOUND-FOUND", "VOLAR": "FLY-FLEW-FLOWN",
"OLVIDAR": "FORGET-FORGOT-FORGOTTEN", "PERDONAR": "FORGIVE-FORGAVE-FORGIVEN",
"OBTENER": "GET-GOT-GOT", "DAR": "GIVE-GAVE-GIVEN", "IR": "GO-WENT-GONE",
"HABER/TENER": "HAVE-HAD-HAD", "OIR": "HEAR-HEARD-HEARD",
"ESCONDER": "HIDE-HID-HIDDEN", "AGARRAR/SOSTENER": "HOLD-HELD-HELD",
"CONSERVAR/MANTENER": "KEEP-KEPT-KEPT", "SABER/CONOCER": "KNOW-KNEW-KNOWN",
"APRENDER": "LEARN-LEARNT-LEARNT", "DEJAR/SALIR": "LEAVE-LEFT-LEFT",
"PERMITIR/DEJAR": "LET-LET-LET", "PERDER": "LOSE-LOST-LOST",
"HACER (FABRICAR)": "MAKE-MADE-MADE", "SIGNIFICAR": "MEAN-MEANT-MEANT",
"PAGAR": "PAY-PAID-PAID", "PONER": "PUT-PUT-PUT", "LEER": "READ-READ-READ",
"CORRER": "RUN-RAN-RUN", "DECIR": "SAY-SAID-SAID", "VER": "SEE-SAW-SEEN",
"VENDER": "SELL-SOLD-SOLD", "ENVIAR": "SEND-SENT-SENT",
"MOSTRAR": "SHOW-SHOWED-SHOWN", "CANTAR": "SING-SANG-SUNG",
"SENTARSE": "SIT-SAT-SAT", "DORMIR": "SLEEP-SLEPT-SLEPT",
"OLER": "SMELL-SMELT-SMELT", "HABLAR": "SPEAK-SPOKE-SPOKEN",
"GASTAR": "SPEND-SPENT-SPENT", "ROBAR": "STEAL-STOLE-STOLEN",
"NADAR": "SWIM-SWAM-SWUM", "COGER/TOMAR": "TAKE-TOOK-TAKEN",
"ENSEÑAR": "TEACH-TAUGHT-TAUGHT", "DECIR (CONTAR)": "TELL-TOLD-TOLD",
"PENSAR": "THINK-THOUGHT-THOUGHT", "ENTENDER": "UNDERSTAND-UNDERSTOOD-UNDERSTOOD",
"GANAR": "WIN-WON-WON", "ESCRIBIR": "WRITE-WROTE-WRITTEN"
}


flag = True
intentos = 0
aciertos = 0

while flag:
    m = input("Desea contunuar? (s/n): ").lower()
    if m == "n":
        flag = False
    else:
        verbo = input("Ingrese un verbo en español: ").upper()
        found = False
        for v in verbos.keys():
            if v == verbo:
                found = True
                intentos = intentos + 1
                verb_ingl = input(f"Ingrese las tres formas en inglés para el verbo {verbo} separadas por guion(-)").upper()
                if verbos[v] == verb_ingl:
                    aciertos = aciertos +1
                    print("¡CORRECTO!")
                else:
                    print(f"INCORRECTO; eran {verbos[v]}")
        if not found:
                print(f"El verbo {verbo} no se encuentra en la lista")
        print(f"Usted va {intentos} intentos y lleva {aciertos} aciertos")