import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SaveQuestao } from '../../../services/questao.service';

@Component({
  selector: 'app-edit',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './edit.component.html',
  styleUrl: './edit.component.scss'
})
export class EditComponent {
  questao: SaveQuestao = new SaveQuestao();
}
