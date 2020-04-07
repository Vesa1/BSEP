import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { NewCertificate } from '../model/new-certificate';
import { CertificateDetails } from '../model/certificate-details.model';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {
  constructor(private http: HttpClient) {}

  getAllCertificates() {
    return this.http.get<any>(environment.link + '/certificate/getAll');
  }

  getCertificateBySerialNumber(id: number) {
    return this.http.get<CertificateDetails>(
      environment.link + `/certificate/${id}`
    );
  }

  createNewCertificate(newCertificate: NewCertificate) {
    console.log('Usao u servis');
    this.http
      .post<NewCertificate>(
        environment.link + '/certificate/createNewCertificate',
        newCertificate
      )
      .subscribe(
        response => {
          console.log('Uspesno');
        },
        error => {
          console.log('Nije uspesno');
        }
      );
  }

  issuerList() {
    return this.http.get<any>(environment.link + '/certificate/issuerList');
  }

  checkValidity(id: number) {
    return this.http.get<any>(
      environment.link + `/certificate/checkValidity/${id}`
    );
  }

  getCertificateChain(id: number) {
    return this.http.get<any>(environment.link + `/certificate/chain/${id}`);
  }

  revokeCertificate(id: number){
    return this.http.get<any>(environment.link + `/certificate/revoke/${id}`);
  }

  printCertificate(id: number){
    return this.http.get<any>(environment.link + `/certificate/print/${id}`);
  }
}
