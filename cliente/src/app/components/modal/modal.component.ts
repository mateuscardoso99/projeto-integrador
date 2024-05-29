import { Component, EventEmitter, Input, Output, booleanAttribute } from '@angular/core';

@Component({
  selector: 'app-modal',
  standalone: true,
  imports: [],
  templateUrl: './modal.component.html',
  styleUrl: './modal.component.scss'
})
export class ModalComponent {
  @Input() titulo!: string;
  @Input() texto!: string;
  @Input({transform: booleanAttribute}) comBotaoCancelar: boolean = true;
  @Input() textoBotaoConfirmar!: string;

  @Output() cancelarEvent = new EventEmitter<string>();
  @Output() confirmarEvent = new EventEmitter<string>();

  cancelar(){
    this.cancelarEvent.emit("cancelar");
  }

  confirmar(){
    this.confirmarEvent.emit("confirmar");
  }
}
