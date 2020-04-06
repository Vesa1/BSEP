import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { NewCertificate } from '../model/new-certificate';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {
  constructor(private http: HttpClient) {}

  getAllCertificates() {
    return this.http.get<any>(environment.link + '/certificate/getAll');
  }

  getCertificateBySerialNumber(id: number) {
    return this.http.get<any>(environment.link + `/certificate/${id}`);
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
    this.http.get(environment.link + '/certificate/issuerList').subscribe();
    console.log('Issuer List');
  }
}
