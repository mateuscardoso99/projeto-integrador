import { Injectable } from '@angular/core';
import { BaseService } from './base.service';
import { environment } from '../../environments/environment';
import { Categoria } from './categoria.service';

@Injectable({
  providedIn: 'root'
})
export class QuestaoService extends BaseService{
  private readonly PATH = `${environment.urlApi}/questao/`;

  findAll(): Promise<Questao[]>{
    return new Promise(resolve => {
      this.http.get(this.PATH).subscribe((response: any) => {
        const questoes = [];
        for(let q of response){
          questoes.push(new Questao(q));
        }
        resolve(questoes);
      });
    });
  }

  findById(id: number): Promise<Questao>{
    return new Promise(resolve => {
      this.http.get(this.PATH + id).subscribe((response: any) => {
        resolve(new Questao(response));
      });
    });
  }

  findByCategoria(idCategoria: number, filtro: string, pageNumber: number, pageSize: number): Promise<any>{
    return new Promise(resolve => {
      this.http.get(`${this.PATH}categoria/${idCategoria}?pageNumber=${pageNumber}&pageSize=${pageSize}&filtro=${filtro}`).subscribe((response: any) => resolve(response));
    });
  }

  count(): Promise<number>{
    return new Promise(resolve => {
      this.http.get(this.PATH + 'count').subscribe((response: any) => {
        resolve(response);
      });
    });
  }

  save(request: SaveQuestao): Promise<any>{
    return new Promise((resolve, reject) => {
      this.http.post(this.PATH , request).subscribe({
        next: response => resolve(response),
        error: err => reject(err)
      });
    });
  } 

  update(request: SaveQuestao, id: number): Promise<any>{
    return new Promise((resolve, reject) => {
      this.http.put(this.PATH + id, request).subscribe({
        next: response => resolve(response),
        error: err => reject(err)
      });
    });
  } 

  delete(id: number): Promise<any>{
    return new Promise((resolve, reject) => {
      this.http.delete(this.PATH + id).subscribe({
        next: response => resolve(response),
        error: err => reject(err)
      });
    });
  } 
}


export class Resposta{
  id: number;
  descricao: string;
  certa: boolean;
  ativo: boolean;

  constructor(obj: any){
    this.id = obj.id;
    this.descricao = obj.descricao;
    this.certa = obj.certa;
    this.ativo = obj.ativo;
  }
}

export class Questao{
  id: number;
  descricao: string;
  categoria: Categoria;
  ativo: boolean;
  respostas: Resposta[] = [];

  constructor(obj: any){
    this.id = obj.id;
    this.descricao = obj.descricao;
    this.categoria = new Categoria(obj.categoria);
    this.ativo = obj.ativo;
    obj.respostas.forEach((r: any) => {
      this.respostas.push(new Resposta(r));
    });
  }
}

export class SaveQuestao{
  descricao: string = '';
  idCategoria: number | null = null;
  ativo: boolean = true;
  respostas: SaveResposta[] = [];

  constructor(){
    for(let i=0; i<5; i++){
      this.respostas.push(new SaveResposta());
    }
  }

}

export class SaveResposta{
  id: number | null = null;
  descricao: string = '';
  idQuestao: number | null = null;
  certa: boolean | null = false;
}