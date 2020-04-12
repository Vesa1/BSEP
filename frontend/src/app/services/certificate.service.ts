import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { NewCertificate } from '../model/new-certificate';
import { CertificateDetails } from '../model/certificate-details.model';
import { Router } from '@angular/router';
import { Filter } from '../model/filter.model';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {
  constructor(private http: HttpClient, private router: Router) {}

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

    if (newCertificate.serialNumber === null && newCertificate.selfSigned === false ) {
      alert('Something went wrong! Check issuer field.');
      return;
    }

    this.http
      .post<NewCertificate>(
        environment.link + '/certificate/createNewCertificate',
        newCertificate
      )
      .subscribe(
        response => {
          this.router.navigate(['/showAllCertificates']);
          alert('Successful');
          console.log('Uspesno');
          this.router.navigate(['/showAllCertificates']);
        },
        error => {
          alert('Something went wrong!');
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

  revokeCertificate(id: number) {
    return this.http.get<any>(environment.link + `/certificate/revoke/${id}`);
  }

  printCertificate(id: number) {
    return this.http.get<any>(environment.link + `/certificate/print/${id}`);
  }

  getByFilter(filter: Filter){
    return this.http.post<any>(environment.link + '/certificate/getByFilter', filter);
  }
}
