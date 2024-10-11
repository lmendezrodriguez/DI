def suma(num1, num2):
    """
    Realiza la suma de dos números.

    Args:
        num1 (int/float): Primer número.
        num2 (int/float): Segundo número.

    Returns:
        int/float: Resultado de la suma de num1 y num2.
    """
    return num1 + num2


def resta(num1, num2):
    """
    Realiza la resta de dos números.

    Args:
        num1 (int/float): Primer número.
        num2 (int/float): Segundo número.

    Returns:
        int/float: Resultado de la resta (num1 - num2).
    """
    return num1 - num2


def multiplicacion(num1, num2):
    """
    Realiza la multiplicación de dos números.

    Args:
        num1 (int/float): Primer número.
        num2 (int/float): Segundo número.

    Returns:
        int/float: Resultado de la multiplicación de num1 y num2.
    """
    return num1 * num2


def division(num1, num2):
    """
    Realiza la división de dos números. Si el segundo número es 0, muestra un mensaje de error.

    Args:
        num1 (int/float): Primer número.
        num2 (int/float): Segundo número (no puede ser 0).

    Returns:
        float: Resultado de la división de num1 entre num2, o None si num2 es 0.
    """
    if num2 == 0:
        print("No se puede dividir por cero.")  # Mensaje de error
        return None
    else:
        return num1 / num2  # Devuelve el resultado de la división
