class ModeloNotas:
    """
    Modelo para gestionar una lista de notas. Proporciona métodos para agregar,
    eliminar, obtener, guardar y cargar notas.
    """

    def __init__(self):
        """Inicializa el modelo con una lista vacía de notas."""
        self.notas = []

    def agregar_nota(self, nueva_nota):
        """
        Agrega una nueva nota a la lista de notas.

        Args:
            nueva_nota (str): El contenido de la nota a agregar.
        """
        self.notas.append(nueva_nota)

    def eliminar_nota(self, indice):
        """
        Elimina una nota de la lista por su índice.

        Args:
            indice (int): El índice de la nota a eliminar.
        """
        self.notas.pop(indice)

    def obtener_notas(self):
        """
        Obtiene todas las notas almacenadas.

        Returns:
            list: Lista de notas actuales.
        """
        return self.notas

    def guardar_notas(self):
        """
        Guarda las notas en un archivo de texto en el directorio 'res/datos.txt'.
        Cada nota se guarda en una línea nueva.
        """
        with open("res/datos.txt", "w") as archivo:
            for nota in self.notas:
                archivo.write(nota + "\n")

    def cargar_notas(self):
        """
        Carga las notas desde el archivo de texto 'res/datos.txt'.

        Returns:
            list: Lista de notas cargadas desde el archivo.
        """
        notas_txt = []
        with open("res/datos.txt", "r") as archivo:
            for linea in archivo:
                notas_txt.append(
                    linea.strip())  # Elimina espacios y saltos de línea
        return notas_txt
