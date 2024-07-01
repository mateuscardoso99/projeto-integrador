import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { CriarContaComponent } from './criar-conta/criar-conta.component';
import { DashboardComponent } from './admin/dashboard/dashboard.component';
import { CategoriaComponent } from './admin/categoria/categoria.component';
import { QuestaoComponent } from './admin/questao/questao.component';
import { RankingComponent } from './ranking/ranking.component';
import { JogarComponent } from './jogar/jogar.component';
import { HomeComponent } from './admin/home/home.component';
import { EditComponent as EditCategoriaComponent} from './admin/categoria/edit/edit.component';
import { EditComponent as EditQuestaoComponent } from './admin/questao/edit/edit.component';
import { authGuard } from './guard/auth.guard';
import { EscolheCategoriaComponent } from './escolhe-categoria/escolhe-categoria.component';
import { UsuarioLayoutComponent } from './usuario-layout/usuario-layout.component';
import { PartidasUsuarioComponent } from './partidas-usuario/partidas-usuario.component';

export const routes: Routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full'},
    { path: 'login', component: LoginComponent, canActivate: [authGuard] },
    { path: 'criar-conta', component: CriarContaComponent, canActivate: [authGuard] },
    { path: 'admin', component: DashboardComponent,
        children: [
            { path: '', component: HomeComponent, pathMatch: 'full' },
            { path: 'categorias', component: CategoriaComponent },
            { path: 'categorias/edit', component: EditCategoriaComponent },
            { path: 'questoes', component: QuestaoComponent },
            { path: 'questoes/edit', component: EditQuestaoComponent },
        ],
        canActivate: [authGuard]
    },
    { path: 'usuario', component: UsuarioLayoutComponent,
        children: [
            { path: 'ranking', component: RankingComponent },
            { path: 'minhas-partidas', component: PartidasUsuarioComponent },
            { path: 'partida/:idPartida', component: JogarComponent },
            { path: 'escolher-categoria', component: EscolheCategoriaComponent }
        ],
        canActivate: [authGuard]
    },
];
