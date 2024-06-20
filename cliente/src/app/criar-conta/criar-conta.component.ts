import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AuthService, CadastroRequest } from '../services/auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PasswordPatternDirective } from '../directives/password-pattern.directive';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-criar-conta',
  standalone: true,
  imports: [RouterModule, CommonModule, FormsModule, PasswordPatternDirective],
  templateUrl: './criar-conta.component.html',
  styleUrl: './criar-conta.component.scss'
})
export class CriarContaComponent {
  dados: CadastroRequest = new CadastroRequest();

  constructor(private authService: AuthService){}

  submit(){
    this.authService.cadastro(this.dados)
      .then(resp => {
        console.log(resp.status);
        
        Swal.fire({
          title: 'Conta criada',
          text: "Cadastrado com sucesso",
          icon: 'success'
        })
      })
      .catch(error => {
        console.log(error);
        
        Swal.fire({
          title: 'Erro',
          text: error.error.errors[0] || "Ocorreu um erro ao realizar o cadastro",
          icon: 'error'
        })
      })    
  }
}
