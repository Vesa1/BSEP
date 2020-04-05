package com.example.demo.controller;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.IssuerData;
import com.example.demo.data.SubjectData;
import com.example.demo.dto.NewCertificateDTO;
import com.example.demo.pki.keystores.CertificateGenerator;
import com.example.demo.pki.keystores.KeyStoreReader;
import com.example.demo.pki.keystores.KeyStoreWriter;


@RestController
@RequestMapping(value = "/certificate")
public class CertificateController {

	private KeyPair keyPairIssuer;
	
	@RequestMapping(
			value = "/createNewCertificate",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity createNewCertificate(@RequestBody NewCertificateDTO newCertificateDTO) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		System.out.println("---------------------------------");
		System.out.println("[CertificateController] -> createNewCertificate method");
		System.out.println("Post data: ");
		System.out.println(newCertificateDTO.toString());
		
		KeyStore ks = KeyStore.getInstance("PKCS12");
		ks.load(null, null);
		
		KeyPair keyPairIssuer = generateKeyPair();
		SubjectData subjectData = generateSubjectData();
		IssuerData issuerData = generateIssuerData(keyPairIssuer.getPrivate());
		
		CertificateGenerator cg = new CertificateGenerator();
		Certificate cert = cg.generateCertificate(subjectData, issuerData);
		System.out.println(cert.toString());
		
		try {
			ks.setKeyEntry("alijas", issuerData.getPrivateKey(), "sifra".toCharArray(), new Certificate[] {cert});
			ks.store(new FileOutputStream("globalKeyStore.p12"), "sifra".toCharArray());
		} catch(Exception e) {
			
		}
		
		KeyStoreReader ksr = new KeyStoreReader();
		Certificate c = ksr.readCertificate("globalKeyStore.p12", "sifra", "alijas");
		
		System.out.println(c.toString());
		
		System.out.println("---------------------------------");
		return new ResponseEntity(HttpStatus.OK);
	}
	
	private SubjectData generateSubjectData() {
		try {
			KeyPair keyPairSubject = generateKeyPair();
			
			//Datumi od kad do kad vazi sertifikat
			SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = iso8601Formater.parse("2017-12-31");
			Date endDate = iso8601Formater.parse("2022-12-31");
			
			//Serijski broj sertifikata
			String sn="1";
			//klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o vlasniku
			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		    builder.addRDN(BCStyle.CN, "Goran Sladic");
		    builder.addRDN(BCStyle.SURNAME, "Sladic");
		    builder.addRDN(BCStyle.GIVENNAME, "Goran");
		    builder.addRDN(BCStyle.O, "UNS-FTN");
		    builder.addRDN(BCStyle.OU, "Katedra za informatiku");
		    builder.addRDN(BCStyle.C, "RS");
		    builder.addRDN(BCStyle.E, "sladicg@uns.ac.rs");
		    //UID (USER ID) je ID korisnika
		    builder.addRDN(BCStyle.UID, "123456");
		    
		    //Kreiraju se podaci za sertifikat, sto ukljucuje:
		    // - javni kljuc koji se vezuje za sertifikat
		    // - podatke o vlasniku
		    // - serijski broj sertifikata
		    // - od kada do kada vazi sertifikat
		    return new SubjectData(keyPairSubject.getPublic(), builder.build(), sn, startDate, endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void testItCreateAndWrite() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		KeyStore ks = KeyStore.getInstance("PKCS12");
		
		ks.load( null, null );

		SubjectData subjectData = generateSubjectData();
		KeyPair keyPairIssuer = generateKeyPair();
		IssuerData issuerData = generateIssuerData(keyPairIssuer.getPrivate());
		
		CertificateGenerator cg = new CertificateGenerator();
		X509Certificate cert = cg.generateCertificate(subjectData, issuerData);
		System.out.println(cert.toString());

		try {
			ks.setKeyEntry("keyAlias", subjectData.getPublicKey(), "keyAlias".toCharArray(), new Certificate[] {cert});
			ks.store( new FileOutputStream( "radiiiplsaaa.p12" ), "keyAlias".toCharArray() );
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(
			value = "/issuerList",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity issuerList() {
		System.out.println("Issuer List");
		
		return new ResponseEntity(HttpStatus.OK);
	}
	

	
	private void testItReadFromKeyStore() {
		KeyStoreReader keyStoreReader = new KeyStoreReader();
		keyStoreReader.readCertificate("radiiiplsaaa.p12", "keyAlias",  "keyAlias");
	}
	
	private IssuerData generateIssuerData(PrivateKey issuerKey) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
	    builder.addRDN(BCStyle.CN, "Nikola Luburic");
	    builder.addRDN(BCStyle.SURNAME, "Luburic");
	    builder.addRDN(BCStyle.GIVENNAME, "Nikola");
	    builder.addRDN(BCStyle.O, "UNS-FTN");
	    builder.addRDN(BCStyle.OU, "Katedra za informatiku");
	    builder.addRDN(BCStyle.C, "RS");
	    builder.addRDN(BCStyle.E, "nikola.luburic@uns.ac.rs");
	    //UID (USER ID) je ID korisnika
	    builder.addRDN(BCStyle.UID, "654321");

		//Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
	    // - privatni kljuc koji ce se koristiti da potpise sertifikat koji se izdaje
	    // - podatke o vlasniku sertifikata koji izdaje nov sertifikat
		return new IssuerData(issuerKey, builder.build());
	}

	

	private KeyPair generateKeyPair() {
        try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA"); 
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(2048, random);
			return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
        return null;
	}
}
