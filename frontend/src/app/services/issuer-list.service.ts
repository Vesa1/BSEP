import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class IssuerListService {

  constructor(private http: HttpClient) { }

  issuerList() {
    this.http.get(environment.link + "/certificate/issuerList")
      .subscribe()
    console.log("Issuer List");
  }
}
