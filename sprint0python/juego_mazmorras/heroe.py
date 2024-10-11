class Heroe:
    _SALUD_MAXIMA = 100
    """
    Clase que representa a un héroe en un juego de combate.

    Atributos:
        _SALUD_MAXIMA (int): Salud máxima del héroe.
        nombre (str): Nombre del héroe.
        ataque (int): Daño de ataque del héroe.
        defensa (int): Nivel de defensa del héroe.
        salud (int): Salud actual del héroe.
        defensa_aumentada (bool): Estado de la defensa aumentada.
    """

    def __init__(self, nombre, ataque=20, defensa=10):
        """
        Inicializa un nuevo héroe.

        Args:
            nombre (str): Nombre del héroe.
            ataque (int): Valor de ataque (predeterminado 20).
            defensa (int): Valor de defensa (predeterminado 10).
        """
        self.nombre = nombre
        self.ataque = ataque
        self.defensa = defensa
        self.salud = self._SALUD_MAXIMA
        self.defensa_aumentada = False

    def atacar(self, enemigo):
        """
        Realiza un ataque a un enemigo.

        Args:
            enemigo (Heroe): El héroe enemigo que recibe el ataque.
        """
        danho = self.ataque - enemigo.defensa
        print(f"{self.nombre} ataca a {enemigo.nombre}.")
        if danho > 0:
            print(f"{enemigo.nombre} ha recibido {danho} puntos de daño.")
            enemigo.salud -= danho
        else:
            print("El enemigo ha bloqueado el ataque.")

    def curarse(self):
        """Restaura 5 puntos de salud al héroe hasta el máximo permitido."""
        if self.salud < self._SALUD_MAXIMA:
            self.salud += 5
            if self.salud > self._SALUD_MAXIMA:
                self.salud = self._SALUD_MAXIMA
        print(f"{self.nombre} se ha curado. Salud actual: {self.salud}")

    def defender(self):
        """Aumenta temporalmente la defensa del héroe en 5 puntos."""
        self.defensa += 5
        print(
            f"{self.nombre} se defiende. Defensa aumentada temporalmente "
            f"a {self.defensa}."
        )
        self.defensa_aumentada = True

    def reset_defensa(self):
        """Restablece la defensa del héroe a su valor anterior."""
        self.defensa -= 5
        print(f"La defensa de {self.nombre} vuelve a la normalidad.")
        self.defensa_aumentada = False

    def esta_vivo(self):
        """Verifica si el héroe está vivo.

        Returns:
            bool: Verdadero si la salud es mayor que 0, falso en caso contrario.
        """
        return self.salud > 0

    def print_info(self):
        """Imprime la salud actual del héroe."""
        print(f"{self.nombre} tiene {self.salud} de salud")

    def print_estadisticas(self):
        """Imprime las estadísticas del héroe."""
        print("******************************************")
        print("*            ESTADÍSTICAS                *")
        print("******************************************")
        print(f"- {self.nombre} tiene {self.salud} de salud")
        print(f"- {self.nombre} tiene {self.ataque} de ataque")
        print(f"- {self.nombre} tiene {self.defensa} de defensa")
