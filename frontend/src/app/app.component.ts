import { Component, OnInit,OnDestroy } from '@angular/core';

import { AuthService } from './services/auth.service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  isAuth = false;
  private updateAuth: Subscription;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {
    this.authService.autoLogin();
    this.updateAuth = this.authService.handleAuth.subscribe(data => {
      this.isAuth = !!data;
    });
  }

  logOut() {
    this.authService.logout();
    this.router.navigate(['/']);
  }

  ngOnDestroy() {
    this.updateAuth.unsubscribe();
  }
}
