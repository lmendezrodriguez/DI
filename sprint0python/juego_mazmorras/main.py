from juego_mazmorras.heroe import Heroe
from juego_mazmorras.mazmorra import Mazmorra


def main():
    """
    Función principal que inicia el juego.

    Solicita al usuario el nombre del héroe, crea el héroe y la mazmorra,
    y comienza la aventura en la mazmorra.
    """
    nombre_heroe = input("Introduce el nombre de tu héroe: ")  # Solicita el nombre del héroe
    heroe = Heroe(nombre_heroe)  # Crea una instancia del héroe con el nombre proporcionado

    mazmorra = Mazmorra(heroe)  # Crea una mazmorra para el héroe
    mazmorra.jugar()  # Inicia el juego en la mazmorra


if __name__ == "__main__":
    main()  # Llama a la función principal al ejecutar el script
