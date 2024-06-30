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
  minutos: number = 19;
  segundos: number = 59;
  partida: Partida | null = null;

  constructor(private route: ActivatedRoute, private partidaService: PartidaService){
    
  }

  ngOnInit(): void {
    this.route.queryParamMap.subscribe((p: any) => {
      if(p['params'].idPartida){
        this.partidaService.getPartida(p['params'].idPartida).then(resp => {
          this.partida = resp;
          this.cronometro();
        })
      }
    });
  }

  cronometro(){
    if(this.segundos = 0){
      this.minutos--;
      this.segundos = 59;
    }
    else{
      this.segundos--;
    }

    setTimeout(this.cronometro, 1000);
  }

}
