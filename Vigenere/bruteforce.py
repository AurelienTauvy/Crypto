class Pattern:
    def __init__(self, texte, positions):
        self.texte = texte
        self.positions = positions
        self.importance = None

def matters(pattern):
    return True

def checkForPattern(length, message):
    patterns = []
    index = 0
    while index <= len(message) - length:
        mot = message[index:index+length]
        alreadyDone = False
        for pattern in patterns:
            if pattern.texte is mot:
                alreadyDone = True
        if not alreadyDone:
            pos = 0
            listpos = []
            while pos is not -1:
                pos = message.find(mot, pos+1)
                if pos is not -1:
                    listpos.append(pos)
            if len(listpos) >= 2:
                patterns[mot] = listpos
        index+=1
    return patterns

def getKeyLength(message):
    index = 0
    patterns = []
    for length in range(2, 10):
        newpatterns = checkForPattern(length, message)
        if newpatterns:
            for pattern in newpatterns:
                patterns.append(pattern)
    print(patterns)
    diviseurs = []
