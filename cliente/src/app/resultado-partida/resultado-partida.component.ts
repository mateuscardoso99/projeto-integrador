import { Component, OnInit } from '@angular/core';
import { Partida, PartidaService, ResultadoPartida } from '../services/partida.service';
import { ActivatedRoute, Params, Router, RouterModule } from '@angular/router';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-resultado-partida',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './resultado-partida.component.html',
  styleUrl: './resultado-partida.component.scss'
})
export class ResultadoPartidaComponent implements OnInit{
  resultado: ResultadoPartida | null = null;

  constructor(private route: ActivatedRoute, private router: Router, private partidaService: PartidaService){}

  ngOnInit(): void {
    this.route.params.subscribe((p: Params) => {
      if(p['idPartida']){
        this.partidaService.findResultado(p['idPartida']).then(resp => {
          this.resultado = resp;          
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
}

