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
import java.util.ArrayList;
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
import com.example.demo.dto.CertificateDTO;
import com.example.demo.dto.SplitDataDTO;
import com.example.demo.dto.LoginDataDTO;
import com.example.demo.dto.NewCertificateDTO;
import com.example.demo.pki.keystores.CertificateGenerator;
import com.example.demo.pki.keystores.KeyStoreReader;
import com.example.demo.pki.keystores.KeyStoreWriter;


@RestController
@RequestMapping(value = "/certificate")
public class CertificateController {

	private KeyPair keyPairIssuer;
	private KeyStoreReader keyStoreReader;
	
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
			String sn="2222";
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
		    builder.addRDN(BCStyle.UID, "1111111");
		    
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
	
	@RequestMapping(
			value = "/getAll",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getAllCertificates() {
		KeyStoreReader ksr = new KeyStoreReader();
		ArrayList<CertificateDTO> certs = new ArrayList<>();
		
		ArrayList<X509Certificate> certificates = ksr.getAllCertifiaces("majaKeyStore.p12", "sifra");
		
		for (X509Certificate certificate : certificates) {
			SplitDataDTO subjectData = mapDataFromCertificate(certificates.get(0).getSubjectDN().getName());
			SplitDataDTO issuerData = mapDataFromCertificate(certificates.get(0).getIssuerDN().getName());
			
			CertificateDTO cert = new CertificateDTO(certificate.getSerialNumber(), issuerData.getO(),issuerData.getOU(), subjectData.getO(), subjectData.getOU());
			certs.add(cert);
		}
		return new ResponseEntity(certs, HttpStatus.OK);
	}
	
	public SplitDataDTO mapDataFromCertificate(String data) {

		String[] fields = (data.trim()).split(",");
		String[] args = new String[10];
		for(int i = 0; i < fields.length; i++){
			String[] splitEachField = fields[i].split("=");
			args[i] = splitEachField[1];
		}
	    
		SplitDataDTO retData = new SplitDataDTO(args[0], args[1], args[2], args[3], args[4], args[5],args[6], args[7]);
		return retData;
	}
	
	@RequestMapping(
			value = "/issuerList",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity issuerList() {
		System.out.println("Issuer List");
		
		return new ResponseEntity(HttpStatus.OK);
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
	    builder.addRDN(BCStyle.UID, "651");

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
