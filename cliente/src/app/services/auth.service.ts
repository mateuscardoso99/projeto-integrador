import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService extends BaseService{
  private readonly PATH = `${environment.urlApi}/usuario/`;

  login(request: LoginRequest): Promise<any>{
    return new Promise(resolve => {
      this.http.post(this.PATH + 'login', request).subscribe((response: any) => {
        resolve(response);
      });
    });
  } 

  cadastro(request: CadastroRequest): Promise<any>{
    return new Promise(resolve => {
      this.http.post(this.PATH + 'cadastro', request).subscribe((response: any) => {
        resolve(response);
      });
    });
  } 
}

export class Usuario{
  id: number;
  email: string;
  nome: string;
  constructor(usuario: Usuario){
    this.id = usuario.id;
    this.nome = usuario.nome;
    this.email = usuario.email;
  }
}

export class UsuarioToken{
  token: string;
  usuario: Usuario;
  constructor(tk: string, usuario: Usuario){
    this.token = tk;
    this.usuario = usuario;
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
