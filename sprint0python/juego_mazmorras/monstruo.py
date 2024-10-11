class Monstruo:
    """
    Clase que representa a un monstruo en un juego de combate.

    Atributos:
        nombre (str): Nombre del monstruo.
        ataque (int): Daño de ataque del monstruo.
        defensa (int): Nivel de defensa del monstruo.
        salud (int): Salud actual del monstruo.
    """

    def __init__(self, nombre="Goblin", ataque=15, defensa=5, salud=50):
        """
        Inicializa un nuevo monstruo.

        Args:
            nombre (str): Nombre del monstruo (predeterminado "Goblin").
            ataque (int): Valor de ataque (predeterminado 15).
            defensa (int): Valor de defensa (predeterminado 5).
            salud (int): Salud inicial (predeterminado 50).
        """
        self.nombre = nombre
        self.ataque = ataque
        self.defensa = defensa
        self.salud = salud

    def atacar(self, heroe):
        """
        Realiza un ataque a un héroe.

        Args:
            heroe (Heroe): El héroe que recibe el ataque.
        """
        danho = self.ataque - heroe.defensa
        print(f"{self.nombre} ataca a {heroe.nombre}.")
        if danho > 0:
            print(f"{heroe.nombre} ha recibido {danho} puntos de daño.")
            heroe.salud -= danho
        else:
            print(f"{heroe.nombre} ha bloqueado el ataque.")

    def esta_vivo(self):
        """Verifica si el monstruo está vivo.

        Returns:
            bool: Verdadero si la salud es mayor que 0, falso en caso
                  contrario.
        """
        return self.salud > 0
