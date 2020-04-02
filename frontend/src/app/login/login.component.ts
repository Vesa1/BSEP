import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { LoginData } from '../model/login-data.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  submitedData: LoginData;
 

  constructor(private authService: AuthService) {}

  ngOnInit() {
  }

  onSubmitLogin(loginForm: NgForm) {
    this.submitedData = loginForm.value;
    this.authService.login(this.submitedData);
  }
}
