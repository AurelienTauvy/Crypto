#Retourne les diviseurs d'un nombre
def getDiviseurs(nb):
    diviseurs = []
    if nb >=2 :
        for i in range (2, int(nb/2 + 1)):
            if nb % i == 0:
                diviseurs.append(i)
    return diviseurs

#Permet de gerer un pattern de caractére
class Pattern:
    def __init__(self, texte, positions):
        self.texte = texte
        self.positions = positions
        self.diviseurs = []

    #le pattern calcule lui meme ses diviseurs et les ajoute dans la liste
    def calcDiviseurs(self):
        distances = []
        distance = self.positions[0]
        for pos in self.positions:
            distance = pos - distance
            distances.append(distance)
            # on crée d'abord la liste des distances entre les diviseurs
        for dist in distances:
            for diviseur in getDiviseurs(dist):
                #puis on ajoute les diviseurs a la liste de diviseurs de l'objet
                self.diviseurs.append(diviseur)

    #redifinition du str pour un meilleur affichage
    def __str__(self):
        msg = self.texte + ":"
        for diviseur in self.diviseurs:
            msg+=str(diviseur)+","
        return msg + " "

#Retourne un array qui contient touts les objets patterns d'une longeur donnée
def checkForPattern(length, message):
    patterns = []
    index = 0
    while index <= len(message) - length: # on reagrde chaque 'mot' de longeur donné dans la chaine
        mot = message[index:index+length] # on le stocke dans mot
        alreadyDone = False
        for pattern in patterns: # On regarde si le mot existe deja pour l'ignorer
            if pattern.texte == mot:
                alreadyDone = True
        if not alreadyDone: # Si il n'est pas deja fait
            pos = 0
            listpos = [] # la liste des positions ou on retrouve ce mot
            while pos is not -1:
                pos = message.find(mot, pos+1)
                if pos is not -1:
                    listpos.append(pos)
            if len(listpos) >= 2: # si on trouve au moins deux fois le meme distance entre deux mots
                patterns.append(Pattern(mot, listpos)) # on crée un pattern et on l'ajoute a la liste
        index+=1
    return patterns

# retourne un dictionnaire qui fait correspondre une longeur de clef a la probabilite que ce soit la clef qui a chiffre
# le message en parametre
def getKeyLength(message):
    maxKeyLength = 10
    importanceLongeur = 1.8
    # coeeficient qui permet d'ajuster l'imortance que l'algorithme donne a la longeur d'une
    # clef par rapport au nombre de fois qu'elle se repete
    index = 0
    patterns = []
    for length in range(2, maxKeyLength ): # on ajoute les patterns de chaque longeur entre 2 et la longeur max d'une clef a un array
        newpatterns = checkForPattern(length, message)
        if newpatterns:
            for pattern in newpatterns:
                patterns.append(pattern)

    diviseurs = []
    nbdiviseurs = 0
    for pattern in patterns: # on parcours touts les patterns pour creer un array de tous les diviseurs dont la longeur est mlondre
        pattern.calcDiviseurs()
        for div in pattern.diviseurs:
            if div <= maxKeyLength:
                nbdiviseurs += div**importanceLongeur # on prend en compte l'importance de la longeur d'un pattern
                diviseurs.append(div)

    proba = {} # avec la liste de tous les diviseurs, on crée le dictionnaire
    for diviseur in diviseurs:
        if diviseur in proba:
            proba[diviseur] += 1
        else:
            proba[diviseur] = 1

    for prob in proba.keys(): # on transforme le nombre d'occurence en proba en prenant en compte le coef de longeur
        proba[prob] /= nbdiviseurs / prob**importanceLongeur

    return proba
