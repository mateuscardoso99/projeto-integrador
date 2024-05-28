import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CadastroRequest } from '../services/auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PasswordPatternDirective } from '../directives/password-pattern.directive';

@Component({
  selector: 'app-criar-conta',
  standalone: true,
  imports: [RouterModule, CommonModule, FormsModule, PasswordPatternDirective],
  templateUrl: './criar-conta.component.html',
  styleUrl: './criar-conta.component.scss'
})
export class CriarContaComponent {
  dados: CadastroRequest = new CadastroRequest();

  submit(){
    console.log(this.dados);
    
  }
}
