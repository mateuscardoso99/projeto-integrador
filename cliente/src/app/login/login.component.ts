import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AuthService, LoginRequest } from '../services/auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterModule, FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  loginRequest: LoginRequest = new LoginRequest();

  constructor(private authService: AuthService){}

  login(){
    console.log(this.loginRequest);
    
    this.authService.login(this.loginRequest)
      .then(resp => console.log("11"))
      .catch(error => console.log("eee")
      )
  }
}
