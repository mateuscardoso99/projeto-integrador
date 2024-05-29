import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AuthService, LoginRequest, UsuarioToken } from '../services/auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { StorageService } from '../services/storage.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterModule, FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  loginRequest: LoginRequest = new LoginRequest();

  constructor(private authService: AuthService, private storageService: StorageService, private router: Router){}

  login(){    
    this.authService.login(this.loginRequest)
      .then(resp => {       
        if(resp.token){
          const usuarioToken = new UsuarioToken(resp);
          this.storageService.setItem("user", JSON.stringify(usuarioToken));
          if(usuarioToken.usuario.isAdmin){
            this.router.navigate(['/admin']);
          }
          else{
            this.router.navigate(['/escolher-categoria']);
          }
        }
        else{
          Swal.fire({
            title: 'Erro',
            text: "Email ou senha incorretos",
            icon: 'error'
          })
        }
      })
      .catch(error => {        
        Swal.fire({
          title: 'Erro',
          text: error.error.errors[0] || "Ocorreu um erro ao realizar o login",
          icon: 'error'
        });
      })
  }
}
