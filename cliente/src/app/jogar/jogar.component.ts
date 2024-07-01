import { Component, OnInit } from '@angular/core';
import { Partida, PartidaService } from '../services/partida.service';
import { ActivatedRoute, Params, Router, RouterModule } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-jogar',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './jogar.component.html',
  styleUrl: './jogar.component.scss'
})
export class JogarComponent implements OnInit{
  minutos: number = 20;
  segundos: number = 0;
  partida: Partida | null = null;

  constructor(private route: ActivatedRoute, private router: Router, private partidaService: PartidaService){}

  ngOnInit(): void {
    this.route.params.subscribe((p: Params) => {
      if(p['idPartida']){
        this.partidaService.getPartida(p['idPartida']).then(resp => {
          this.partida = resp;
          console.log(this.partida);
          
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
  }

  cronometro(){
    if(this.minutos == 0 && this.segundos == 0){
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

}
