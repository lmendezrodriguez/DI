import tkinter as tk
from controlador import GameController

if __name__ == "__main__":
    root = tk.Tk()
    controller = GameController(root)
    root.mainloop()