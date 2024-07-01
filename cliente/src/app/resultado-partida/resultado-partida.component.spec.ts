import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResultadoPartidaComponent } from './resultado-partida.component';

describe('ResultadoPartidaComponent', () => {
  let component: ResultadoPartidaComponent;
  let fixture: ComponentFixture<ResultadoPartidaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResultadoPartidaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ResultadoPartidaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
