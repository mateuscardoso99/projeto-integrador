import { Component, OnInit } from '@angular/core';
import { EncerrarPartida, Partida, PartidaService } from '../services/partida.service';
import { ActivatedRoute, Params, Router, RouterModule } from '@angular/router';
import Swal from 'sweetalert2';
import { StorageService } from '../services/storage.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LoaderComponent } from '../components/loader/loader.component';
import { animate, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'app-jogar',
  standalone: true,
  imports: [RouterModule, FormsModule, CommonModule, LoaderComponent],
  templateUrl: './jogar.component.html',
  styleUrl: './jogar.component.scss',
  animations: [
    trigger(
      'inOutAnimation',
      [
        transition(
          ':enter',
          [
            style({ opacity: 0 }),
            animate('1s ease-in', style({ opacity: 1 }))
          ]
        )
      ]
    )
]
})
export class JogarComponent implements OnInit{
  minutos: number = 20;
  segundos: number = 0;
  partida: Partida | null = null;
  questaoAtiva: number = 1;
  isLoading: boolean = false;

  dadosEnviar: EncerrarPartida = new EncerrarPartida();

  constructor(private route: ActivatedRoute, private router: Router, private partidaService: PartidaService, private storageService: StorageService){}

  ngOnInit(): void {
    this.route.params.subscribe((p: Params) => {
      if(p['idPartida']){
        this.isLoading = true;

        this.partidaService.getPartida(p['idPartida']).then(resp => {
          this.partida = resp;

          /*if(this.storageService.hasItem("mm") && this.storageService.hasItem("ss")){
            this.minutos = Number(this.storageService.getItem("mm"));
            this.segundos = Number(this.storageService.getItem("ss"));
          }*/
          this.setMinutosRestantes()
          this.setIdsQuestoes()
          
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
        }).finally(() => this.isLoading = false)
      }
    });

    //ao dar f5 ngOnDestroy não é chamado, preciso disso pra chamar com f5
    //window.onbeforeunload = () => this.ngOnDestroy();
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
    console.log(this.dadosEnviar);
    
    this.questaoAtiva++;
  }

  voltarQuestao(){
    console.log(this.dadosEnviar);
    
    this.questaoAtiva--;
  }

  enviarRespostas(){
    if(this.partida){
      this.isLoading = true;
      this.dadosEnviar.idPartida = this.partida.id;
      console.log('dadosEnviar',this.dadosEnviar);

      this.partidaService.encerrarPartida(this.dadosEnviar).then(() => {
        this.router.navigate(['/usuario/resultado-partida/', this.partida?.id]);
      }).catch(error => {
        Swal.fire({
          title: 'Erro',
          text: error.error.errors[0] || 'Ocorreu um erro ao enviar as respostas',
          icon: 'error'
        })
      }).finally(() => this.isLoading = false)
    }
    
  }

  /*ngOnDestroy(): void{
    this.storageService.setItem("mm", this.minutos);
    this.storageService.setItem("ss", this.segundos);    
  }*/

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
      
      if(diffSeconds == 0 && diffMinutes == 0){
        this.minutos = 20;
        this.segundos = 0;
      }
      else{
        this.minutos = 19 - diffMinutes;
        this.segundos = 60 - diffSeconds;
      }
    }
    
  }

  //seta no objeto de envio das respostas os ids das questões recebidas da api
  setIdsQuestoes(){
    this.partida?.partidaQuestoes.forEach((p, index) => {
      this.dadosEnviar.respostas[index].idQuestao = p.questao.id
    });
    console.log(this.dadosEnviar);
  }
}
