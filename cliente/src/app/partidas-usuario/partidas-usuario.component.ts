import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { Partida, PartidaService } from '../services/partida.service';
import { NgxSkeletonLoaderModule } from 'ngx-skeleton-loader';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-partidas-usuario',
  standalone: true,
  imports: [NgxSkeletonLoaderModule, RouterModule, CommonModule],
  templateUrl: './partidas-usuario.component.html',
  styleUrl: './partidas-usuario.component.scss'
})
export class PartidasUsuarioComponent implements OnInit{
  partidas: Partida[] = [];
  isLoading: boolean = true;

  constructor(private router: Router, private partidaService: PartidaService){}
  ngOnInit(): void {
    this.partidaService.findPartidasUsuario().then((resp: Partida[]) => {
      this.partidas = resp;
    }).finally(() => this.isLoading = false)
  }
}
