from Operaciones import suma, resta, multiplicacion, division
try:
    print("Bienvenido a la calculadora")
    num1 = int(input("Introduce un número:\n"))
    num2 = int(input("Introduce un segundo número:\n"))
    opcion = int(input(f"Elige un tipo de opreación\n1. suma\n2. resta\n3. multiplicación\n4. división\n"))
    if opcion == 1:
        suma(num1, num2)
    elif opcion == 2:
        resta(num1, num2)
    elif opcion == 3:
        multiplicacion(num1, num2)
    elif opcion == 4:
        division(num1, num2)
    else:
        print("Opción no válida")
except:
    print("Sólo válidas entradas numéricas")