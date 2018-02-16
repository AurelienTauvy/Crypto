
#Permet de chiffrer et dechiffrer vigenere a l'aide d'une clef
class Vigenere:
    def __init__(self, clef):
        self.clef = clef.lower()

    def chiffre(self, message):
        message = message.lower()
        index = 0
        phrase = ""
        while index <= len(message) - 1: # sur chaque caractere du message a dechiffere
            nummsg = ord(message[index])
            numclef = ord(self.clef[index % len(self.clef)])
            newnumber = (nummsg) + numclef - 96
            if newnumber > ord("z"): # on travail mod 26
                newnumber -= 26
            phrase += chr(newnumber) # on concatene le nouveau caractere calculée a la chaine
            index +=1
        return phrase

    def dechiffre(self, message):
        message = message.lower()
        index = 0
        phrase = ""
        while index <= len(message) - 1: # sur chaque caractere du message a dechiffere
            nummsg = ord(message[index])
            numclef = ord(self.clef[index % len(self.clef)])
            newnumber = nummsg - numclef + 96
            if newnumber < ord("a"): # on travail mod 26
                newnumber += 26
            phrase += chr(newnumber) # on concatene le nouveau caractere calculée a la chaine
            index +=1
        return phrase
