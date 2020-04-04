package com.example.demo.pki.keystores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class KeyStoreWriter {
	//KeyStore je Java klasa za citanje specijalizovanih datoteka koje se koriste za cuvanje kljuceva
	//Tri tipa entiteta koji se obicno nalaze u ovakvim datotekama su:
	// - Sertifikati koji ukljucuju javni kljuc
	// - Privatni kljucevi
	// - Tajni kljucevi, koji se koriste u simetricnima siframa
	private KeyStore keyStore;
	
	public KeyStoreWriter() {
		try {
			keyStore = KeyStore.getInstance("JKS");
		} catch (KeyStoreException e) {
			System.out.println("ERROR uhvacen\n\n NE MOZE SE INICIJALIZOVATI");
			e.printStackTrace();
		} 
	}
	
	public void loadKeyStore(String fileName, char[] password) {
		try {
			if(fileName != null) {
				InputStream keyStoreData = new FileInputStream(fileName);
				System.out.println("Nije NULA KREIRAM");
				keyStore.load(keyStoreData, password);
			} else {
				//Ako je cilj kreirati novi KeyStore poziva se i dalje load, pri cemu je prvi parametar null
				keyStore.load(null, password);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("USAOOOO U CATCH ***********************************************************************************");

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveKeyStore(String fileName, char[] password) {
		System.out.println("USAOOOO U SAVEEE ***********************************************************************************");

		try {
			keyStore.store(new FileOutputStream(fileName), password);
			System.out.println("PROOASAOOOO U SAVEEE ***********************************************************************************");

		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void write(String alias, PrivateKey privateKey, char[] password, Certificate certificate) {
		try {
			keyStore.setKeyEntry(alias, privateKey, password, new Certificate[] {certificate});
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
	}
}
