package com.example.demo.pki.keystores;

import java.awt.List;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

import com.example.demo.data.IssuerData;

import net.bytebuddy.dynamic.TypeResolutionStrategy.Passive;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;



public class KeyStoreReader {
	//KeyStore je Java klasa za citanje specijalizovanih datoteka koje se koriste za cuvanje kljuceva
	//Tri tipa entiteta koji se obicno nalaze u ovakvim datotekama su:
	// - Sertifikati koji ukljucuju javni kljuc
	// - Privatni kljucevi
	// - Tajni kljucevi, koji se koriste u simetricnima siframa
	private KeyStore keyStore;
	
	public KeyStoreReader() {
		try {
			keyStore = KeyStore.getInstance("PKCS12");
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Zadatak ove funkcije jeste da ucita podatke o izdavaocu i odgovarajuci privatni kljuc.
	 * Ovi podaci se mogu iskoristiti da se novi sertifikati izdaju.
	 * 
	 * @param keyStoreFile - datoteka odakle se citaju podaci
	 * @param alias - alias putem kog se identifikuje sertifikat izdavaoca
	 * @param password - lozinka koja je neophodna da se otvori key store
	 * @param keyPass - lozinka koja je neophodna da se izvuce privatni kljuc
	 * @return - podatke o izdavaocu i odgovarajuci privatni kljuc
	 */
	
	public PrivateKey getPrivateKey(String alijas) {
		
		try {
			PrivateKey pk = (PrivateKey) keyStore.getKey(alijas, "sifra".toCharArray());
			return pk;
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public IssuerData readIssuerFromStore(String keyStoreFile, String alias, char[] password, char[] keyPass) {
		try {
			//Datoteka se ucitava
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
			keyStore.load(in, password);
			//Iscitava se sertifikat koji ima dati alias
			Certificate cert = keyStore.getCertificate(alias);
			//Iscitava se privatni kljuc vezan za javni kljuc koji se nalazi na sertifikatu sa datim aliasom
			PrivateKey privKey = (PrivateKey) keyStore.getKey(alias, keyPass);

			X500Name issuerName = new JcaX509CertificateHolder((X509Certificate) cert).getSubject();
			return new IssuerData(privKey, issuerName);
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Ucitava sertifikat is KS fajla
	 */
    public Certificate readCertificate(String keyStoreFile, String keyStorePass, String alias) {
		try {
			//kreiramo instancu KeyStore
			KeyStore ks = KeyStore.getInstance("PKCS12");
			//ucitavamo podatke
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
			ks.load(in, keyStorePass.toCharArray());
			
			if(ks.isKeyEntry(alias)) {
				Certificate cert = ks.getCertificate(alias);
				return cert;
			}
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}  catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
    
    /**
	 * Izlistava sve sertifikate iz KS fajla
	 */
    public ArrayList<X509Certificate> getAllCertifiaces(String keyStoreFile, String keyStorePass) {
    	ArrayList<X509Certificate> certificates = new ArrayList();
    	try {
			//kreiramo instancu KeyStore
			KeyStore ks = KeyStore.getInstance("PKCS12");
			//ucitavamo podatke
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
			ks.load(in, keyStorePass.toCharArray());
			
			Enumeration<String> aliases = ks.aliases();
			
			while(aliases.hasMoreElements()) {
				String aliasName = aliases.nextElement();
				if(ks.isKeyEntry(aliasName)) {
					X509Certificate cert = (X509Certificate)ks.getCertificate(aliasName);
					
					certificates.add(cert);
				}
			}

			return certificates;
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}  catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Ucitava privatni kljuc is KS fajla
	 */
	public PrivateKey readPrivateKey(String keyStoreFile, String keyStorePass, String alias, String pass) {
		try {
			//kreiramo instancu KeyStore
			KeyStore ks = KeyStore.getInstance("PKCS12");
			//ucitavamo podatke
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
			ks.load(in, keyStorePass.toCharArray());
			
			if(ks.isKeyEntry(alias)) {
				PrivateKey pk = (PrivateKey) ks.getKey(alias, pass.toCharArray());
				return pk;
			}
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}  catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}
		return null;
	}
}