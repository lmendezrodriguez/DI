from operaciones import suma, resta, multiplicacion, division  # Importación de funciones aritméticas


def calculadora():
    """
    Función principal de la calculadora.
    Permite al usuario realizar operaciones aritméticas básicas (suma, resta, multiplicación y división).

    La función solicita al usuario dos números y un tipo de operación.
    Después de realizar la operación, pregunta si el usuario desea realizar otra operación.
    """

    opcion = "s"  # Inicializa la variable para controlar el bucle de operaciones
    while opcion == "s":  # Bucle que se ejecuta mientras el usuario quiera hacer operaciones
        try:
            print("Bienvenido a la calculadora!")  # Mensaje de bienvenida
            num1 = int(input("Introduce un número:\n"))  # Solicita el primer número
            num2 = int(input("Introduce un segundo número:\n"))  # Solicita el segundo número

            # Solicita al usuario elegir el tipo de operación
            opcion = int(input(
                "Elige un tipo de operación\n1. suma\n2. resta\n3. multiplicación\n4. división\n"
            ))

            # Realiza la operación correspondiente según la opción elegida
            if opcion == 1:  # Suma
                print(f"{num1} + {num2} = {suma(num1, num2)}")
            elif opcion == 2:  # Resta
                print(f"{num1} - {num2} = {resta(num1, num2)}")
            elif opcion == 3:  # Multiplicación
                print(f"{num1} * {num2} = {multiplicacion(num1, num2)}")
            elif opcion == 4:  # División
                if num2 == 0:
                    print("No se puede dividir por cero.")  # Mensaje de error
                else:
                    print(f"{num1} / {num2} = {round(division(num1, num2), 2)}")  # Redondea a 2 decimales
            else:
                print("Opción no válida")  # Mensaje para opción no válida
        except ValueError:  # Captura errores en la entrada numérica
            print("Sólo válidas entradas numéricas")  # Mensaje de error si la entrada no es numérica

        # Pregunta al usuario si desea realizar otra operación
        opcion = input(
            "¿Quieres hacer más operaciones? (s: sí, n: no)\n"
        ).lower()  # Conversión a minúsculas
        while opcion not in ("s", "n"):  # Validación de la entrada para continuar
            opcion = input(
                "Entrada no válida, solo válida 's' o 'n'\n"
                "¿Quieres hacer más operaciones? (s: sí, n: no)\n"
            ).lower()

        if opcion == "n":  # Mensaje de despedida si el usuario elige no continuar
            print("Gracias por usar la calculadora. ¡Chauuu!")  # Despedida
