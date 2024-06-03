import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService extends BaseService{
  private readonly PATH = `${environment.urlApi}/usuario/`;

  login(request: LoginRequest): Promise<any>{
    return new Promise((resolve, reject) => {
      this.http.post(this.PATH + 'login', request).subscribe({
        next: response => resolve(response),
        error: err => reject(err)
      });
    });
  } 

  cadastro(request: CadastroRequest): Promise<any>{
    return new Promise((resolve, reject) => {
      this.http.post(this.PATH + 'cadastro', request).subscribe({
        next: response => resolve(response),
        error: err => reject(err)
      });
    });
  } 

  logout(): Promise<any>{
    return new Promise((resolve, reject) => {
      this.http.get(this.PATH + 'logout').subscribe({
        next: response => resolve(response),
        error: err => reject(err)
      });
    });
  } 
}

export class Usuario{
  id: number;
  email: string;
  nome: string;
  isAdmin: boolean;
  constructor(usuario: any){
    this.id = usuario.id;
    this.nome = usuario.nome;
    this.email = usuario.email;
    this.isAdmin = usuario.admin;
  }
}

export class LoginRequest{
  email: string = '';
  senha: string = '';
}

export class CadastroRequest{
  nome: string = '';
  email: string = '';
  senha: string = '';
  confirmarSenha: string = '';
}
