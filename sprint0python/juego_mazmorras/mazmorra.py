import random
from juego_mazmorras.monstruo import Monstruo
from juego_mazmorras.tesoro import Tesoro


class Mazmorra:
    """
    Clase que representa una mazmorra en el juego donde un héroe enfrenta
    monstruos y encuentra tesoros.

    Atributos:
        heroe (Heroe): El héroe que explora la mazmorra.
        monstruos (list): Lista de monstruos en la mazmorra.
        tesoro (Tesoro): Objeto tesoro que puede ser encontrado por el héroe.
    """

    def __init__(self, heroe):
        """
        Inicializa una nueva mazmorra con un héroe y una lista de monstruos.

        Args:
            heroe (Heroe): El héroe que entra en la mazmorra.
        """
        self.heroe = heroe
        self.monstruos = [
            Monstruo(),
            Monstruo(),
            Monstruo("Orco", 20, 10, 60)
        ]  # Lista de enemigos inicial

        # Lista de dificultad media de enemigos:
        # [Monstruo(), Monstruo(), Monstruo("Orco", 20, 10, 60),
        # Monstruo("Orco", 20, 10, 60),
        # Monstruo("Terrible Murciélago", 25, 20, 70)]

        # Lista mortal de enemigos:
        # [Monstruo("Terrible Murciélago", 30, 20, 70),
        # Monstruo("Terrible Murciélago", 30, 20, 70),
        # Monstruo("Terrible Murciélago", 30, 20, 70),
        # Monstruo("Terrible Murciélago", 30, 20, 70)]

        self.tesoro = Tesoro()  # Inicializa un objeto Tesoro

    def jugar(self):
        """Inicia el juego en la mazmorra, donde el héroe enfrenta a los monstruos."""
        print(f"{self.heroe.nombre} entra en la mazmorra.")
        while self.heroe.esta_vivo() and self.monstruos:
            self.heroe.print_estadisticas()  # Muestra las estadísticas del héroe
            monstruo_aux = random.choice(
                self.monstruos)  # Selección aleatoria de un monstruo
            print(f"Te has encontrado con un {monstruo_aux.nombre}.")
            self.enfrentar_enemigo(monstruo_aux)  # Héroe enfrenta al monstruo
            self.heroe.print_info()  # Muestra la información del héroe

        # Mensaje final basado en el estado de la mazmorra
        if not self.monstruos:
            print(
                f"¡{self.heroe.nombre} ha derrotado a todos los monstruos y ha "
                f"conquistado la mazmorra!")
        else:
            print(f"{self.heroe.nombre} ha sido derrotado en la mazmorra.")

        print("******************************************")
        print("*                 FIN                    *")
        print("******************************************")

    def enfrentar_enemigo(self, enemigo):
        """
        Permite al héroe elegir una acción para enfrentar a un enemigo.

        Args:
            enemigo (Monstruo): El monstruo a enfrentar.
        """
        while enemigo.esta_vivo() and self.heroe.esta_vivo():
            eleccion = 0
            while eleccion not in (1, 2, 3):
                try:
                    print(f"¿Qué deseas hacer, {self.heroe.nombre}?")
                    print("1. Atacar")
                    print("2. Defender")
                    print("3. Curarse")
                    eleccion = int(input())
                except ValueError:
                    print("Por favor, introduce un número válido (1, 2 o 3).")

            if eleccion == 1:
                self.heroe.atacar(enemigo)  # Héroe ataca al enemigo
            elif eleccion == 2:
                self.heroe.defender()  # Héroe se defiende
            elif eleccion == 3:
                self.heroe.curarse()  # Héroe se cura

            if enemigo.esta_vivo():
                enemigo.atacar(self.heroe)  # Monstruo ataca al héroe
                if self.heroe.defensa_aumentada:
                    self.heroe.reset_defensa()  # Restablece la defensa si estaba aumentada

            if not enemigo.esta_vivo():
                print(f"Has derrotado a un {enemigo.nombre}.")
                self.monstruos.remove(enemigo)  # Elimina al monstruo derrotado
                self.buscar_tesoro()  # Busca tesoros tras derrotar al monstruo

    def buscar_tesoro(self):
        """Permite al héroe buscar un tesoro después de derrotar a un enemigo."""
        print(
            "Buscando tesoro...")  # Mensaje informando que se busca un tesoro
        self.tesoro.encontrar_tesoro(self.heroe)  # Héroe encuentra un tesoro
