import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { LoginData } from '../model/login-data.model';
import { environment } from 'src/environments/environment';
import { BehaviorSubject } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  handleAuth = new BehaviorSubject<LoginData>(null);

  constructor(private http: HttpClient, private router: Router) {}

  login(loginData: LoginData) {
    this.http
      .post<LoginData>(
        `${environment.link}/${environment.userPath}/login`,
        loginData
      )
      .subscribe(
        response => {
          localStorage.setItem(
            environment.consts.USER,
            JSON.stringify(response)
          );
          const respData = new LoginData(response.username, response.username);
          this.handleAuth.next(respData);
          this.router.navigate(['/createNewCertificate']);
        },
        error => alert('Woups, wrong credientals.')
      );
  }

  isAuthenticated() {
    const user = localStorage.getItem(environment.consts.USER);

    if (user) {
      return true;
    }

    return false;
  }

  logout() {
    this.handleAuth.next(null);
    this.router.navigate(['/']);
    localStorage.clear();
  }

  autoLogin() {
    const userData = JSON.parse(localStorage.getItem(environment.consts.USER));
    if (!userData) {
      return;
    }
    
    if (userData) {
      this.handleAuth.next(userData);
    }
  }

}
