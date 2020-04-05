import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {
  constructor(private http: HttpClient) {}

  getAllCertificates() {
    return this.http.get<any>(environment.link + '/certificate/getAll');
  }
}
