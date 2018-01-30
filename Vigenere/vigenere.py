        

class Vigenere:
    def __init__(self, clef):
        self.clef = clef.lower()

    def chiffre(self, message):
        message = message.lower()
        index = 0
        phrase = ""
        while index <= len(message) - 1:
            nummsg = ord(message[index])
            numclef = ord(self.clef[index % len(self.clef)])
            newnumber = (nummsg) + numclef - 96
            if newnumber > ord("z"):
                newnumber -= 26
            phrase += chr(newnumber)
            index +=1
        return phrase

    def dechiffre(self, message):
        message = message.lower()
        index = 0
        phrase = ""
        while index <= len(message) - 1:
            nummsg = ord(message[index])
            numclef = ord(self.clef[index % len(self.clef)])
            newnumber = nummsg - numclef + 96
            if newnumber < ord("a"):
                newnumber += 26
            phrase += chr(newnumber)
            index +=1
        return phrase
