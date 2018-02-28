import java.io.BufferedReader;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
// Valeurs experimentales : 
// 		Pour une taille de 5 bits, il y a une collision toutes les 30 de paires de mots environ.
//		Pour une taille de 6 bits, il y a une collision toutes les 70 de paires de mots environ.
//		Pour une taille de 7 bits, il y a une collision toutes les 130 de paires de mots environ.
//		Pour une taille de 8 bits, il y a une collision toutes les 250 de paires de mots environ.
//		Pour une taille de 9 bits, il y a une collision toutes les 500 de paires de mots environ.
//		Pour une taille de 10 bits, il y a une collision toutes les 10000 de paires de mots environ.
//      Cala correspond aux resultats théoriques, en effet deux mots de N bits choisi "au hasard"
//		ont 1/(2**n) d'etre identique 



// La propriété que nous essayons de casser est la résistance à la seconde préimage:
// On fixe f(x) et on chechent y tel que f(y) = f(x), sans autres solutions que d'essayer pour des y 'aléatoires'

public class Main {
	 private static final String charset = "UTF-8";
	 static int NBBIT = 5;
	 static String algo = "HmacSHA1";

	public static void main(String[] args) throws Exception {
		
		// Chacune des fonctions ci-dessous correspondent à une partie du TP.
		// Pour les voir fonctionner, veuillez les décommenter une par une.
		
		//System.out.println(toSha("Poisson", 5)); 
		//trouverCollisionDic();
		//Poisson();
		//HMAC();
		
	}
	//Exercice sur le HMAC
	private static void HMAC() throws Exception {
		Mac mac = Mac.getInstance(algo);
		SecretKeySpec mdp = new SecretKeySpec("coucou".getBytes(charset), mac.getAlgorithm());
		
		mac.init(mdp);
		
		mac.update("Ceci est mon premier HMAC SHA1.".getBytes(charset));
		byte[] res = mac.doFinal();
		
		System.out.println(new String(res));
		System.out.println(bytesToHex(res));
		// On peut voir que la clef est plus longue, limitant les risques de collisions.
		
	}
	// Permet de trouver les collisions avec le mot "poisson"
	private static void Poisson() {
		Random rng = new Random();
		List<Boolean> poisson = toSha("poisson", NBBIT);
		List<Boolean> comp = new ArrayList<>();
		int nbessai = 0;
		while (true) {
			nbessai++;
			for (int i = 0 ; i < NBBIT ; i++) {
				comp.add(rng.nextBoolean());
			}
			if (poisson.equals(comp)) {
				System.out.println("Collision trouvée apres " + nbessai + " essai");
				nbessai = 0;
			}
		comp.clear();
		}

	}
		
	// Permet de trouver les collisions entre les mots du dictionnaire
	private static void trouverCollisionDic() {
		
		List <String> mots = readFile("dic");
		int nbtests = 0;
		
		for (String mot1 : mots) {
			for (String mot2 : mots) {
				if (!mot1.equals(mot2)) {
					List<Boolean> sh1 = toSha(mot1, NBBIT);
					List<Boolean> sh2 = toSha(mot2, NBBIT);
					nbtests++;
					if (sh1.equals(sh2)) {
						System.out.println("Collision entre " + mot1 + " et " + mot2 + "au bout de " + nbtests + " | hash : " + sh1);
						nbtests = 0;
					}
				}
			}
		}
		
	}
	
	private static List<String> readFile(String filename)
	{
	  List<String> records = new ArrayList<String>();
	  try
	  {
	    BufferedReader reader = new BufferedReader(new FileReader(filename));
	    String line;
	    while ((line = reader.readLine()) != null)
	    {
	      records.add(line);
	    }
	    reader.close();
	    return records;
	  }
	  catch (Exception e)
	  {
	    e.printStackTrace();
	    return null;
	  }
	}
	// Calcule le sha d'un string et ne garde que les nbBit premiers bits.
	// Retourne le resultat sous forme d'un array de booléens.
	public static List<Boolean> toSha(String str, int nbBit) {
		try {
			byte[] buffer = str.getBytes();
			byte[] result = null;
			MessageDigest sha = MessageDigest.getInstance("SHA");

			result = new byte[sha.getDigestLength()];
			sha.reset();
			sha.update(buffer);
			
			result = sha.digest();
			byte b;
			
			List<Boolean> aRet = new ArrayList<>();
			// Pour essayer d'améliorer l'efficacité on peut travailler bit par bit :  
			for (int i = 0 ; i < nbBit; i++) {
				b = result[i/8];
				Boolean nb = ((b >> i) & 1) == 1;
				aRet.add(nb);
			}
			
			return aRet;
			
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Un probléme est survenu : " + e);
			e.printStackTrace();
		}
		return null;
	}
	// Transforme des bytes en valeurs hexadecimales
	private static String bytesToHex(byte[] losByte){
		StringBuilder sb = new StringBuilder();
	    for (byte b : losByte) {
	        sb.append(String.format("%02X ", b));
	    }
	    return sb.toString();
	}
}
