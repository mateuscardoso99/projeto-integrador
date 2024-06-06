import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AuthService, LoginRequest, Usuario } from '../services/auth.service';
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
        if(resp.id){
          const usuario = new Usuario(resp);
          this.storageService.setItem("user", JSON.stringify(usuario));
          if(usuario.isAdmin){
            this.router.navigate(['/admin']);
          }
          else{
            this.router.navigate(['/usuario/escolher-categoria']);
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
