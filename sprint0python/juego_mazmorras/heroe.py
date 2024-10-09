class Heroe:
    _salud_maxima = 100
    """
    Clase que representa a un héroe en un juego de combate.

    Atributos:
        _salud_maxima (int): Salud máxima del héroe.
        nombre (str): Nombre del héroe.
        ataque (int): Daño de ataque del héroe.
        defensa (int): Nivel de defensa del héroe.
        salud (int): Salud actual del héroe.
        defensaAumentada (bool): Estado de la defensa aumentada.
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
        self.salud = self._salud_maxima
        self.defensaAumentada = False

    def atacar(self, enemigo):
        """
        Realiza un ataque a un enemigo.

        Args:
            enemigo (Heroe): El héroe enemigo que recibe el ataque.
        """
        danho = self.ataque - enemigo.defensa
        print(f"{self.nombre} ataca a {enemigo.nombre}.")
        if danho > 0:
            print(f"El {enemigo.nombre} ha recibido {danho} puntos de daño.")
            enemigo.salud -= danho
        else:
            print("El enemigo ha bloqueado el ataque.")

    def curarse(self):
        """Restaura 5 puntos de salud al héroe hasta el máximo permitido."""
        if self.salud < self._salud_maxima:
            self.salud += 5
            if self.salud > self._salud_maxima:
                self.salud = self._salud_maxima
        print(f"{self.nombre} se ha curado. Salud actual: {self.salud}")

    def defender(self):
        """Aumenta temporalmente la defensa del héroe en 5 puntos."""
        self.defensa += 5
        print(f"{self.nombre} se defiende. Defensa aumentada temporalmente a {self.defensa}.")
        self.defensaAumentada = True

    def reset_defensa(self):
        """Restablece la defensa del héroe a su valor anterior."""
        self.defensa -= 5
        print(f"La defensa de {self.nombre} vuelve a la normalidad.")
        self.defensaAumentada = False

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
        print(f"******************************************")
        print(f"*            ESTADÍSTICAS                *")
        print(f"******************************************")
        print(f"- {self.nombre} tiene {self.salud} de salud")
        print(f"- {self.nombre} tiene {self.ataque} de ataque")
        print(f"- {self.nombre} tiene {self.defensa} de defensa")
