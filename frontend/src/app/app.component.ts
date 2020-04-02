import { Component, OnInit,OnDestroy } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
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
