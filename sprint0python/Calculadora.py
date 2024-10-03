from Operaciones import suma, resta, multiplicacion, division

def calculadora():
    opcion = "s"
    while opcion == "s":
        try:
            print("Bienvenido a la calculadora!")
            num1 = int(input("Introduce un número:\n"))
            num2 = int(input("Introduce un segundo número:\n"))

            opcion = int(input(f"Elige un tipo de opreación\n1. suma\n2. resta\n3. multiplicación\n4. división\n"))

            if opcion == 1:
                print(f"{num1} + {num2} = {suma(num1, num2)}")
            elif opcion == 2:
                print(f"{num1} - {num2} = {resta(num1, num2)}")
            elif opcion == 3:
                print(f"{num1} * {num2} = {multiplicacion(num1, num2)}")
            elif opcion == 4:
                print(f"{num1} / {num2} = {round(division(num1, num2),2)}")
            else:
                print("Opción no válida")
        except ValueError:
            print("Sólo válidas entradas numéricas")
        opcion = input("¿Quieres hacer más operaciones? (s: sí, n: no)\n").lower()
        while opcion not in ("s", "n"):
            opcion = input("Entrada no válida, solo válida 's' o 'n'\n¿Quieres hacer más operaciones? (s: sí, n: no)\n").lower()
        if opcion == "n":
            print("Gracias por usar la calculadora. ¡Chauuu!")

calculadora()