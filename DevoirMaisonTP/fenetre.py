from tkinter import *

from DevoirMaisonTP.File_Attente_thread import *


class Fenetre(Frame):
    def __init__(self):
        fenetre1 = Tk()
        fenetre1.title("Opération sur file")
        fenetre1.geometry("250x200")

        Frame.__init__(self, fenetre1)

        self.grid()

        self.fileparent = Label(self, text = " < ")
        self.fileparent.grid(row=0, column=0)
        self.fileparent2 = Label(self, text = " > ")
        self.fileparent2.grid(row = 0, column = 2)

        self.file = Label(self, text = "   ")
        self.file.grid(row=0, column=1)

        self.prod1 = Label(self, text = "Producteur = en attente")
        self.prod1.grid(row=2, column=1)
        self.prod2 = Label(self, text="Producteur 2 = en attente")
        self.prod2.grid(row=3, column=1)

        self.conso1 = Label(self, text = "Consommateur = en attente")
        self.conso1.grid(row=4, column=1)
        self.conso2 = Label(self, text="Consommateur 2 = en attente")
        self.conso2.grid(row=5, column=1)

        self.quitter = Button(self, text="Quitter", command=self.quit)
        self.quitter.grid(row=6, column=0)

        self.start = Button(self, text ="Start", command= self.start1)
        self.start.grid(row=6, column=2)

    def start1(self):
        fa = FileAttente(self.file)
        p1 = Producteur(1, fa, self.prod1, 1)
        p2 = Producteur(1, fa, self.prod2, 2)

        c1 = Consommateur(2, fa, self.conso1, 1)
        c2 = Consommateur(10, fa, self.conso2, 2)

        p1.daemon = True
        p2.daemon = True
        c1.daemon = True
        c2.daemon = True

        p1.start()
        p2.start()
        c1.start()
        c2.start()


