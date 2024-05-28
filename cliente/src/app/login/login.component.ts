import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AuthService, LoginRequest } from '../services/auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';

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
    this.authService.login(this.loginRequest)
      .then(resp => console.log("11"))
      .catch(error => {
        console.log(error);
        
        Swal.fire({
          title: 'Erro',
          text: error.error.errors[0] || "Ocorreu um erro ao realizar o login",
          icon: 'error'
        })
      })
  }
}
