import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { NewCertificate } from '../model/new-certificate';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CreateNewCertificateService {

  constructor(private http: HttpClient) { }

  createNewCertificate(newCertificate: NewCertificate) {
    console.log("Usao u servis");
    this.http.post<NewCertificate>(environment.link + '/certificate/createNewCertificate', newCertificate)
      .subscribe(response=>{
        console.log("Uspesno");
      },
      error=>{
        console.log("Nije uspesno");
      })
  }
}
