package com.example.demo.controller;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.crypto.paddings.ISO7816d4Padding;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.IssuerData;
import com.example.demo.data.SubjectData;
import com.example.demo.dto.CertificateDTO;
import com.example.demo.dto.CertificateDetailsDTO;
import com.example.demo.dto.IssuerCertificateDTO;
import com.example.demo.dto.SplitDataDTO;
import com.example.demo.enums.CertificateType;
import com.example.demo.dto.LoginDataDTO;
import com.example.demo.dto.NewCertificateDTO;
import com.example.demo.pki.keystores.CertificateGenerator;
import com.example.demo.pki.keystores.KeyStoreReader;
import com.example.demo.pki.keystores.KeyStoreWriter;
import com.example.demo.service.CertificateService;


@RestController
@RequestMapping(value = "/certificate")
public class CertificateController {

	private KeyPair keyPairIssuer;
	private KeyStoreReader keyStoreReader;
	private static final String  KEYSTORE_FILE = "globalKeyStore.p12";
	private static final String  KEYSTORE_PASSWORD = "sifra";

	
	@Autowired
	private CertificateService certificateService;
	
	@RequestMapping(
			value = "/createNewCertificate",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity createNewCertificate(@RequestBody NewCertificateDTO newCertificateDTO) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException {
		System.out.println("--------------------------------------------------");
		System.out.println("[CertificateController] -> createNewCertificate method");
		System.out.println("--------------------------------------------------");
		System.out.println("Post data: ");
		System.out.println(newCertificateDTO.toString());
		
		KeyStoreWriter ks = new KeyStoreWriter();
		ks.loadKeyStore(KEYSTORE_FILE, KEYSTORE_PASSWORD.toCharArray());
		
		Long serialId;
		if(newCertificateDTO.getCertificateType().equals("root")) {
			serialId = certificateService.saveCertificate(CertificateType.root); //Dodajemo u bazu serijski broj sert i da je root element
		} else {
			if(newCertificateDTO.getCertificateType().equals("endEntity")) {
				serialId = certificateService.saveCertificate(CertificateType.endEntity);
			} else {
				serialId = certificateService.saveCertificate(CertificateType.intermediate);
			}
		}
		SubjectData subjectData = generateSubjectData(newCertificateDTO, serialId.toString()); //Kreiramo subject
		
		if(newCertificateDTO.isSelfSigned()) {
			System.out.println("\nCertificate is root");
			Calendar calendar = Calendar.getInstance();
			subjectData.setStartDate(new Date()); //pocetak vazenja sertifikata
			calendar.add(Calendar.YEAR, 10);
			subjectData.setEndDate(calendar.getTime()); // kraj vazenja sertifikata
			
			System.out.println("\n---Subject data---");
			System.out.println(subjectData.toString());
			
			IssuerData issuerData = generateIssuerDataSelfSigned(serialId.toString(), subjectData.getPrivateKey(), newCertificateDTO);
			System.out.println("\n---Issuer Data---");
			System.out.println(issuerData.toString());
			
			CertificateGenerator cg = new CertificateGenerator();
			Certificate cert = cg.generateCertificate(subjectData, issuerData);
			System.out.println("\n---Certificate---");
			System.out.println(cert.toString());
			
			try {
				ks.write("alijas"+subjectData.getSerialNumber(), subjectData.getPrivateKey(), KEYSTORE_PASSWORD.toCharArray(), cert); //potpisujemo sertifikat privatnim kljucem subject-a
				ks.saveKeyStore(KEYSTORE_FILE, KEYSTORE_PASSWORD.toCharArray());
			} catch(Exception e) {
				
			}
		} else {
			System.out.println("\nCertificate is intermediate or end entity");
			System.out.println("Serial number od issuer certificate is: " + newCertificateDTO.getSerialNumber());
			
			Calendar calendar = Calendar.getInstance();
			subjectData.setStartDate(new Date()); //pocetak vazenja sertifikata
			calendar.add(Calendar.YEAR, 10);
			subjectData.setEndDate(calendar.getTime()); // kraj vazenja sertifikata
			
			System.out.println("\n---Subject data---");
			System.out.println(subjectData.toString());
			
			KeyStoreReader ksr = new KeyStoreReader();
			X509Certificate issuerCertificate = (X509Certificate)ksr.readCertificate("globalKeyStore.p12", "sifra","alijas" + newCertificateDTO.getSerialNumber());
			System.out.println(issuerCertificate.toString());
			
			PrivateKey pk = ksr.readPrivateKey("globalKeyStore.p12", "sifra", "alijas" + issuerCertificate.getSerialNumber(), "sifra");
			
			
			SplitDataDTO issuerDetails = mapDataFromCertificate(issuerCertificate.getSubjectDN().getName());
			System.out.println("\nIssuer details: " + issuerDetails);
			
			IssuerData createdIssuerData = generateIssuerData(issuerDetails.getCN(), issuerDetails.getSURNAME(),
					issuerDetails.getGIVENNAME(), issuerDetails.getO(), issuerDetails.getOU(), issuerDetails.getC(), 
					issuerDetails.getEMAILADDRESS(), issuerDetails.getUID(), pk);
			if(newCertificateDTO.getCertificateType().equals(CertificateType.intermediate)) {
				
			}
			CertificateGenerator cg = new CertificateGenerator();
			Certificate cert = cg.generateCertificate(subjectData, createdIssuerData);
			System.out.println("\n---Certificate---");
			System.out.println(cert.toString());
			
			try {
				ks.write("alijas"+subjectData.getSerialNumber(), pk, KEYSTORE_PASSWORD.toCharArray(), cert); //potpisujemo sertifikat privatnim kljucem subject-a
				ks.saveKeyStore(KEYSTORE_FILE, KEYSTORE_PASSWORD.toCharArray());
			} catch(Exception e) {
				
			}
		}

		return new ResponseEntity(HttpStatus.OK);
	}
	
	
	private IssuerData generateIssuerData(String commonName, String surname, String givenName, String organization, String organizationalUnit,
			String country, String email, String serialNumber, PrivateKey pk) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
	    builder.addRDN(BCStyle.CN, commonName);
	    builder.addRDN(BCStyle.SURNAME, surname);
	    builder.addRDN(BCStyle.GIVENNAME, givenName);
	    builder.addRDN(BCStyle.O, organization);
	    builder.addRDN(BCStyle.OU, organizationalUnit);
	    builder.addRDN(BCStyle.C, country);
	    builder.addRDN(BCStyle.E, email);
	    //UID (USER ID) je ID korisnika
	    builder.addRDN(BCStyle.UID, serialNumber);

		//Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
	    // - privatni kljuc koji ce se koristiti da potpise sertifikat koji se izdaje
	    // - podatke o vlasniku sertifikata koji izdaje nov sertifikat
		return new IssuerData(pk, builder.build());
	}
	
	
	private SubjectData generateSubjectData(NewCertificateDTO newCertificateDTO, String serialId) {
		try {
			KeyPair keyPairSubject = generateKeyPair();

			//Serijski broj sertifikata
			String sn= serialId;
			//klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o vlasniku
			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		    builder.addRDN(BCStyle.CN, newCertificateDTO.getCommonName());
		    builder.addRDN(BCStyle.SURNAME, newCertificateDTO.getSurname());
		    builder.addRDN(BCStyle.GIVENNAME, newCertificateDTO.getGivenName());
		    builder.addRDN(BCStyle.O, newCertificateDTO.getOrganization());
		    builder.addRDN(BCStyle.OU,newCertificateDTO.getOrganizationalUnit());
		    builder.addRDN(BCStyle.C, newCertificateDTO.getCountry());
		    builder.addRDN(BCStyle.E, newCertificateDTO.getEmail());
		    //UID (USER ID) je ID korisnika
		    builder.addRDN(BCStyle.UID, serialId);
		    
		    //Kreiraju se podaci za sertifikat, sto ukljucuje:
		    // - javni kljuc koji se vezuje za sertifikat
		    // - podatke o vlasniku
		    // - serijski broj sertifikata
		    // - od kada do kada vazi sertifikat
		    SubjectData ret = new SubjectData(keyPairSubject.getPublic(), builder.build(), sn);
		    ret.setPrivateKey(keyPairSubject.getPrivate());
		    return ret;
		} catch (Exception e) {
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
		
		ArrayList<X509Certificate> certificates = ksr.getAllCertifiaces(KEYSTORE_FILE, KEYSTORE_PASSWORD);
		
		for (X509Certificate certificate : certificates) {
			SplitDataDTO subjectData = mapDataFromCertificate(certificate.getSubjectDN().getName());
			SplitDataDTO issuerData = mapDataFromCertificate(certificate.getIssuerDN().getName());
			
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
		System.out.println("\n---Getting issuer list---");
		
		KeyStoreReader ksr = new KeyStoreReader(); 
		ArrayList<IssuerCertificateDTO> certs = new ArrayList<>();
		
		ArrayList<X509Certificate> certificates = ksr.getAllCertifiaces(KEYSTORE_FILE, KEYSTORE_PASSWORD); //citanje svih sertifikata 
		
		for (X509Certificate certificate : certificates) {
			SplitDataDTO subjectData = mapDataFromCertificate(certificate.getSubjectDN().getName());
			SplitDataDTO issuerData = mapDataFromCertificate(certificate.getIssuerDN().getName());
			
			System.out.println("Issuer id: " + certificate.getNotAfter());
			if(!(certificateService.isRevokedAndEndEntity(certificate.getSerialNumber())) && certificate.getNotAfter().after(new Date())) {
				if(chainCertificates(certificate)) {
					IssuerCertificateDTO cert = new IssuerCertificateDTO(certificate.getSerialNumber().toString(), subjectData.getCN(), subjectData.getC(), subjectData.getSURNAME(),
							subjectData.getGIVENNAME(), subjectData.getO(), subjectData.getOU(), subjectData.getEMAILADDRESS());
			
					certs.add(cert);
					System.out.println(cert);
				}
			}
		}
		return new ResponseEntity(certs, HttpStatus.OK);
		
	}
	
	public boolean chainCertificates(X509Certificate c) {
		return true;
	}
	 
	private IssuerData generateIssuerDataSelfSigned(String serialId, PrivateKey issuerKey, NewCertificateDTO newCertificateDTO) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
	    builder.addRDN(BCStyle.CN, newCertificateDTO.getCommonName());
	    builder.addRDN(BCStyle.SURNAME, newCertificateDTO.getSurname());
	    builder.addRDN(BCStyle.GIVENNAME, newCertificateDTO.getGivenName());
	    builder.addRDN(BCStyle.O, newCertificateDTO.getOrganization());
	    builder.addRDN(BCStyle.OU, newCertificateDTO.getOrganizationalUnit());
	    builder.addRDN(BCStyle.C, newCertificateDTO.getCountry());
	    builder.addRDN(BCStyle.E, newCertificateDTO.getEmail());
	    //UID (USER ID) je ID korisnika
	    builder.addRDN(BCStyle.UID, serialId);

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
	
	@RequestMapping(value = "/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getCertificateBySerialNumber( @PathVariable("id") Long id) throws UnrecoverableKeyException {
	 	KeyStoreReader ksr = new KeyStoreReader();
	 	X509Certificate cert = (X509Certificate)ksr.readCertificate(KEYSTORE_FILE,KEYSTORE_PASSWORD, "alijas"+id);
	 	
	 	String issuedTo = cert.getSubjectDN().toString();
	 	String issuedBy = cert.getIssuerDN().toString();
	 	String validFrom = cert.getNotBefore().toString() + " to " + cert.getNotAfter().toString();
	 	int version = cert.getVersion();
	 	BigInteger serialNumber = cert.getSerialNumber();
	 	String signatureAlgorithm = cert.getSigAlgName();
	 	String signatureHashAlgorithm = signatureAlgorithm.substring(0,6);
	 	String publicKey = cert.getPublicKey().toString();
	 	
	 	CertificateDetailsDTO retCert = new CertificateDetailsDTO(issuedTo, issuedBy, validFrom, version, serialNumber, signatureAlgorithm, signatureHashAlgorithm, publicKey);
	 	
		return new ResponseEntity( retCert, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/checkValidity/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity checkValidity( @PathVariable("id") Long id) {
		System.out.println("Check validity of certificate with serialNumber = "+id);
		return new ResponseEntity(  HttpStatus.OK);
	}
	
	@RequestMapping(value = "/chain/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getCertificateChain( @PathVariable("id") Long id) {
		System.out.println("Get chain of certificate with serialNumber = "+id);
		return new ResponseEntity(  HttpStatus.OK);
	}
	
	@RequestMapping(value = "/revoke/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity revokeCertificate( @PathVariable("id") Long id) throws UnrecoverableKeyException {
		System.out.println("Revoke certificate with serialNumber = "+id);
		ArrayList<String> inWaitForCheck = new ArrayList<>();
		KeyStoreReader ksr = new KeyStoreReader();
		X509Certificate cert = (X509Certificate)ksr.readCertificate(KEYSTORE_FILE,KEYSTORE_PASSWORD, "alijas"+id);
		Long idCert = (Long)id;
        com.example.demo.model.Certificate certDB = this.certificateService.getCertificateById(idCert);
        
        if(cert == null || certDB == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        
      /*
       * FIRST CASE => CURRENT CERTIFICATE IS END-ENTITY
       */
        if(certDB.getCertificateType().equals(CertificateType.endEntity)) {
        	boolean isRevoked = this.certificateService.revokeCertificate(idCert);
        	if(isRevoked) {
        		return new ResponseEntity(HttpStatus.OK);
        	}
        }
        
        ArrayList<X509Certificate> certificates = ksr.getAllCertifiaces(KEYSTORE_FILE, KEYSTORE_PASSWORD);
        /*
         * SECOND CASE => CURRENT CERTIFICATE IS INTERMEDIATE
         */
        if(certDB.getCertificateType().equals(CertificateType.intermediate)) {
        	boolean isRevoked = this.certificateService.revokeCertificate(idCert); //revoke chosen certificate
        	
        	if(isRevoked) {
        		for (X509Certificate certificate : certificates) {
    				if(certificate.getIssuerDN().equals(cert.getSubjectDN())) {
    					Long idCertificate = cert.getSerialNumber().longValue();
    					boolean isChildCertificateRevoked = this.certificateService.revokeCertificate(idCertificate);
    					if(isChildCertificateRevoked) {
    						System.out.println("Povucen");
    					}
    					if(checkIfIsIssuer(certificate, certificates)) {
    						System.out.println("Izbrisani sertifikat je issuer, povlacimo sve njegove potpisane sertifikate");
    						revokeCertificateRecursion(certificate, certificates);
    					}
    				}
    			}
        	}
        	return new ResponseEntity(HttpStatus.NOT_FOUND);
        	
        }
        
        
        return new ResponseEntity(HttpStatus.OK);
	}

	public boolean revokeCertificateRecursion(X509Certificate issuer, ArrayList<X509Certificate> certificates) {
		for (X509Certificate certificate : certificates) {
			if(certificate.getIssuerDN().equals(issuer.getSubjectDN())) {
				Long idCertificate = issuer.getSerialNumber().longValue();
				return this.certificateService.revokeCertificate(idCertificate);
				
			}
		}
		
		return false;
	}
	
	
	public boolean checkIfIsIssuer(X509Certificate issuer, ArrayList<X509Certificate> certificates) {
		for (X509Certificate certificate : certificates) {
			if(certificate.getIssuerDN().equals(issuer.getSubjectDN())) {
				return true;
			}
		}
		
		return false;
	}
	
	@RequestMapping(value = "/print/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity printCertificate( @PathVariable("id") Long id) throws CertificateEncodingException, UnrecoverableKeyException {
		
		KeyStoreReader ksr = new KeyStoreReader();
	 	X509Certificate cert = (X509Certificate)ksr.readCertificate(KEYSTORE_FILE,KEYSTORE_PASSWORD, "alijas"+id);
		
		try {
			final FileOutputStream os = new FileOutputStream("cert"+id+".cer");
			os.write("-----BEGIN CERTIFICATE-----\n".getBytes("US-ASCII"));
			os.write(Base64.encode(cert.getEncoded(),os));
			os.write("-----END CERTIFICATE-----\n".getBytes("US-ASCII"));
			os.close();
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	
		return new ResponseEntity(  HttpStatus.OK);
	}
}
