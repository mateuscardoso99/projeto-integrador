import { Injectable } from '@angular/core';
import { BaseService } from './base.service';
import { environment } from '../../environments/environment.development';
import { Categoria } from './categoria.service';

@Injectable({
  providedIn: 'root'
})
export class PartidaService extends BaseService{
  private readonly PATH = `${environment.urlApi}/partida/`;

  getCategorias(): Promise<Categoria[]>{
    return new Promise((resolve, reject) => {
      this.http.get(this.PATH + 'categorias').subscribe((resp: any) => {
        const categorias: Categoria[] = [];

        resp.forEach((cat: any) => {
          categorias.push(new Categoria(cat));
        });

        resolve(categorias);
      })
    });
  }
}
