import { Component, OnDestroy, OnInit } from '@angular/core';
import { EncerrarPartida, Partida, PartidaService, RespostasPartida } from '../services/partida.service';
import { ActivatedRoute, Params, Router, RouterModule } from '@angular/router';
import Swal from 'sweetalert2';
import { StorageService } from '../services/storage.service';

@Component({
  selector: 'app-jogar',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './jogar.component.html',
  styleUrl: './jogar.component.scss'
})
export class JogarComponent implements OnInit, OnDestroy{
  minutos: number = 20;
  segundos: number = 0;
  partida: Partida | null = null;
  questaoAtiva: number = 1;

  dadosEnviar: EncerrarPartida = new EncerrarPartida();

  constructor(private route: ActivatedRoute, private router: Router, private partidaService: PartidaService, private storageService: StorageService){}

  ngOnInit(): void {
    this.route.params.subscribe((p: Params) => {
      if(p['idPartida']){
        this.partidaService.getPartida(p['idPartida']).then(resp => {
          this.partida = resp;

          if(this.storageService.hasItem("mm") && this.storageService.hasItem("ss")){
            this.minutos = Number(this.storageService.getItem("mm"));
            this.segundos = Number(this.storageService.getItem("ss"));
          }
          this.setMinutosRestantes()
          
          setTimeout(() => this.cronometro(), 1000);
        }).catch(e => {
          Swal.fire({
            title: 'Erro',
            text: e.error.errors || e.error.errors[0] || 'ocorreu um erro ao buscar a partida',
            icon: 'error'
          }).then((result) => {
            if (result.isConfirmed) {
              this.router.navigate(['/usuario/escolher-categoria']);
            }
          })
        })
      }
    });

    //ao dar f5 ngOnDestroy não é chamado, preciso disso pra chamar com f5
    window.onbeforeunload = () => this.ngOnDestroy();
  }

  cronometro(){
    if(this.minutos == 0 && this.segundos == 0){
      this.enviarRespostas();
      return;
    }
    if(this.segundos == 0){
      this.minutos--;
      this.segundos = 59;
    }
    else{
      this.segundos--;
    }
    setTimeout(() => this.cronometro(), 1000);
  }

  proximaQuestao(){
    this.questaoAtiva++;
  }

  voltarQuestao(){
    this.questaoAtiva--;
  }

  enviarRespostas(){
    if(this.partida != null){
      this.dadosEnviar.idPartida = this.partida.id;

      this.partidaService.encerrarPartida(this.dadosEnviar).then(resp => {

      })
    }
    
  }

  ngOnDestroy(): void{
    this.storageService.setItem("mm", this.minutos);
    this.storageService.setItem("ss", this.segundos);    
  }

  setMinutosRestantes(){
    if(this.partida != null){
      const horaInicio: any = new Date(this.partida?.horaInicio);
      const agora: any = new Date();
      let diffSeconds = Math.floor((agora - (horaInicio))/1000);
      let diffMinutes = Math.floor(diffSeconds / 60);
      const hours = Math.floor(diffMinutes/60);
      const days = Math.floor(hours/24);

      diffMinutes = diffMinutes-(days*24*60)-(hours*60);
      diffSeconds = diffSeconds-(days*24*60*60)-(hours*60*60)-(diffMinutes*60);
      console.log(diffMinutes, diffSeconds);
      
      this.minutos = 20 - diffMinutes
      this.segundos = 60 - diffSeconds
    }
    
  }
}
