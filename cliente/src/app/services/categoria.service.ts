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

  findById(id: number): Promise<Categoria>{
    return new Promise(resolve => {
      this.http.get(this.PATH + id).subscribe((response: any) => {
        resolve(new Categoria(response));
      });
    });
  }

  save(request: SaveCategoria): Promise<Categoria>{
    return new Promise(resolve => {
      this.http.post(this.PATH , request).subscribe((response: any) => {
        resolve(new Categoria(response));
      });
    });
  } 

  update(request: SaveCategoria, id: number): Promise<Categoria>{
    return new Promise(resolve => {
      this.http.put(this.PATH + id, request).subscribe((response: any) => {
        resolve(new Categoria(response));
      });
    });
  } 

  delete(id: number): Promise<any>{
    return new Promise(resolve => {
      this.http.delete(this.PATH + id).subscribe((response: any) => {
        resolve(response);
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
  nome: string;
  codigo: string;

  constructor(obj: any){
    this.nome = obj.nome;
    this.codigo = obj.codigo;
  }
}
