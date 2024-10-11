import random


class Tesoro:
    """
    Clase que representa un tesoro que puede ser encontrado por un héroe.

    Atributos:
        beneficio (list): Lista de tipos de beneficios que
                          puede otorgar el tesoro.
    """

    def __init__(self):
        """Inicializa un nuevo tesoro con tipos de beneficios predefinidos."""
        self.beneficio = [
            "aumento de ataque",
            "aumento de defensa",
            "restauración de salud"
        ]

    def encontrar_tesoro(self, heroe):
        """
        Permite a un héroe encontrar un tesoro y aplicar sus beneficios.

        Args:
            heroe (Heroe): El héroe que encuentra el tesoro.
        """
        # Selección aleatoria de un beneficio
        beneficio_nombre = random.choice(self.beneficio)

        # Valor del beneficio entre 1 y 5
        beneficio_valor = random.randint(1, 5)

        print(f"{heroe.nombre} ha encontrado un tesoro: {beneficio_nombre}.")

        if beneficio_nombre == self.beneficio[0]:  # Aumento de ataque
            heroe.ataque += beneficio_valor
            print(f"El ataque de {heroe.nombre} aumenta en {beneficio_valor}.")
            print(f"El ataque actual de {heroe.nombre} es {heroe.ataque}.")

        elif beneficio_nombre == self.beneficio[1]:  # Aumento de defensa
            heroe.defensa += beneficio_valor
            print(
                f"La defensa de {heroe.nombre} aumenta en {beneficio_valor}.")
            print(f"La defensa actual de {heroe.nombre} es {heroe.defensa}.")

        elif beneficio_nombre == self.beneficio[2]:  # Restauración de salud
            heroe.salud = heroe._SALUD_MAXIMA
            print(
                f"La salud de {heroe.nombre} ha sido restaurada a {heroe.salud}.")
