import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PartidasUsuarioComponent } from './partidas-usuario.component';

describe('PartidasUsuarioComponent', () => {
  let component: PartidasUsuarioComponent;
  let fixture: ComponentFixture<PartidasUsuarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PartidasUsuarioComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PartidasUsuarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
