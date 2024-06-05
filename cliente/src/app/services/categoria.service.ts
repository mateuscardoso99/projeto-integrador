import { Injectable } from '@angular/core';
import { BaseService } from './base.service';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService extends BaseService{
  private readonly PATH = `${environment.urlApi}/categoria/`;

  findAll(): Promise<Categoria[]>{
    return new Promise(resolve => {
      this.http.get(this.PATH).subscribe((response: any) => {
        const categorias = [];
        for(let c of response){
          categorias.push(new Categoria(c));
        }
        resolve(categorias);
      });
    });
  }

  count(): Promise<number>{
    return new Promise(resolve => {
      this.http.get(this.PATH + 'count').subscribe((response: any) => {
        resolve(response);
      });
    });
  }

  findById(id: number): Promise<Categoria>{
    return new Promise(resolve => {
      this.http.get(this.PATH + id).subscribe((response: any) => {
        resolve(new Categoria(response));
      });
    });
  }

  save(request: SaveCategoria): Promise<any>{
    return new Promise((resolve, reject) => {
      this.http.post(this.PATH , request).subscribe({
        next: response => resolve(response),
        error: err => reject(err)
      });
    });
  } 

  update(request: SaveCategoria, id: number): Promise<any>{
    return new Promise((resolve, reject) => {
      this.http.put(this.PATH + id, request).subscribe({
        next: response => resolve(response),
        error: err => reject(err)
      });
    });
  } 

  delete(id: number): Promise<any>{
    return new Promise((resolve, reject)  => {
      this.http.delete(this.PATH + id).subscribe({
        next: response => resolve(response),
        error: err => reject(err)
      });
    });
  } 
}

export class Categoria{
  id: number;
  nome: string;
  codigo: string;
  ativo: boolean;

  constructor(obj: any){
    this.id = obj.id;
    this.nome = obj.nome;
    this.codigo = obj.codigo;
    this.ativo = obj.ativo;
  }
}

export class SaveCategoria{
  nome: string = '';
}
