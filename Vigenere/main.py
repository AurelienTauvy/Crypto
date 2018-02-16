from vigenere import Vigenere
from bruteforce import getKeyLength

vg = Vigenere("Clef")


print (getKeyLength(vg.chiffre("Votre message ici, de preference long et avec des mots se repetant souvent")))
