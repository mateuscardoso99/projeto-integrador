import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { CriarContaComponent } from './criar-conta/criar-conta.component';
import { DashboardComponent } from './admin/dashboard/dashboard.component';
import { CategoriaComponent } from './admin/categoria/categoria.component';
import { QuestaoComponent } from './admin/questao/questao.component';
import { RankingComponent } from './ranking/ranking.component';
import { JogarComponent } from './jogar/jogar.component';
import { HomeComponent } from './admin/home/home.component';

export const routes: Routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full'},
    { path: 'login', component: LoginComponent },
    { path: 'criar-conta', component: CriarContaComponent },
    { path: 'admin', component: DashboardComponent,
        children: [
            { path: '', component: HomeComponent, pathMatch: 'full' },
            { path: 'categorias', component: CategoriaComponent },
            { path: 'questoes', component: QuestaoComponent },
        ]
    },
    { path: 'ranking', component: RankingComponent },
    { path: 'partida', component: JogarComponent }
];
