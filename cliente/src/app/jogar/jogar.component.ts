import { Component, OnInit } from '@angular/core';
import { Partida, PartidaService } from '../services/partida.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-jogar',
  standalone: true,
  imports: [],
  templateUrl: './jogar.component.html',
  styleUrl: './jogar.component.scss'
})
export class JogarComponent implements OnInit{
  minutos: number = 20;
  segundos: number = 0;
  milisegundos: number = 999;
  partida: Partida | null = null;

  constructor(private route: ActivatedRoute, private partidaService: PartidaService){
    
  }

  ngOnInit(): void {
    this.route.queryParamMap.subscribe((p: any) => {
      if(p['params'].idPartida){
        this.partidaService.getPartida(p['params'].idPartida).then(resp => {
          this.partida = resp;
          setTimeout(() => this.cronometro(), 1000);
        })
      }
    });
    
  }

  cronometro(){
    if(this.segundos == 0){
      this.minutos--;
      this.segundos = 59;
    }
    this.segundos--;
    setTimeout(() => this.cronometro(), 1000);
  }

}
