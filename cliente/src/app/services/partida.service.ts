import { Injectable } from '@angular/core';
import { BaseService } from './base.service';
import { environment } from '../../environments/environment.development';
import { Categoria } from './categoria.service';
import { Questao, Resposta } from './questao.service';

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

  getRanking(idCategoria: number): Promise<Ranking[]>{
    return new Promise((resolve, reject) => {
      this.http.get(this.PATH + 'ranking/' + idCategoria).subscribe((resp: any) => {
        const ranking: Ranking[] = [];

        resp.forEach((r: any) => {
          ranking.push(new Ranking(r));
        });

        resolve(ranking);
      })
    });
  }

  getPartida(idPartida: number): Promise<Partida>{
    return new Promise((resolve, reject) => {
      this.http.get(this.PATH + idPartida).subscribe({
        next: response => resolve(new Partida(response)),
        error: err => reject(err)
      })
    });
  }

  findPartidasUsuario(): Promise<Partida[]>{
    return new Promise((resolve) => {
      this.http.get(this.PATH + 'usuario').subscribe((resp: any) => {
        const partidas: Partida[] = [];
        resp.forEach((p: Partida) => {
          partidas.push(new Partida(p));
        })
        resolve(partidas);
      })
    });
  }

  iniciarPartida(idCategoria: number): Promise<any>{
    return new Promise((resolve, reject) => {
      this.http.post(this.PATH + 'iniciarpartida', {idCategoria}).subscribe({
        next: response => {console.log(response);
         return resolve(new Partida(response))},
        error: err => reject(err)
      });
    });
  }

  encerrarPartida(request: EncerrarPartida): Promise<any>{
    return new Promise((resolve, reject) => {
      this.http.post(this.PATH + 'encerrar', request).subscribe({
        next: response => resolve(new ResultadoPartida(response)),
        error: err => reject(err)
      });
    });
  }

  findResultado(idPartida: number): Promise<ResultadoPartida>{
    return new Promise((resolve, reject) => {
      this.http.get(this.PATH + 'resultado/' + idPartida).subscribe((resp: any) => {
        resolve(new ResultadoPartida(resp));
      })
    });
  }
}

export class Ranking{
  partida: string;
  categoria: string;
  acertos: number;
  usuario: string;
  constructor(obj: any){
    this.partida = obj.partida;
    this.categoria = obj.categoria;
    this.acertos = obj.acertos;
    this.usuario = obj.usuario;
  }
}

export class ResultadoPartida{
  totalAcertos: number;
  partida: Partida;
  constructor(obj: any){
    this.totalAcertos = obj.totalAcertos;
    this.partida = new Partida(obj);
  }
}

export class Partida{
  id: number;
  horaInicio: Date;
  encerrado: boolean;
  categoria: Categoria;
  partidaQuestoes: PartidaResposta[] = [];

  constructor(obj: any){
    this.id = obj.id;
    this.horaInicio = new Date(obj.horaInicio);
    this.encerrado = obj.encerrado;
    this.categoria = new Categoria(obj.categoria);
    obj.partidaQuestoes.forEach((p: PartidaResposta) => {
      this.partidaQuestoes.push(new PartidaResposta(p));
    })
  }
}

export class PartidaResposta{
  id: number;
  questao: Questao;

  constructor(obj: any){
    this.id = obj.id;
    this.questao = new Questao(obj.questao);
  }
}

export class EncerrarPartida{
  idPartida: number | null = null;
  respostas: RespostasPartida[] = [];

  constructor(){
    for(let i=0; i<10; i++){
      this.respostas.push(new RespostasPartida());
    }
  }

}

export class RespostasPartida{
  idQuestao: number | null = null;
  idResposta: number | null = null;
}