import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { LoginData } from '../model/login-data.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) {}

  login(loginData: LoginData) {
    this.http
      .post<LoginData>(`${environment.link}/${environment.userPath}/login`, loginData)
      .subscribe(
        response => console.log('RESPONSE ', response),
        error => alert('Woups, wrong credientals.')
      );
  }
}
