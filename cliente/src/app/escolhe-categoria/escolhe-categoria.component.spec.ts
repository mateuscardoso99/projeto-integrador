import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EscolheCategoriaComponent } from './escolhe-categoria.component';

describe('EscolheCategoriaComponent', () => {
  let component: EscolheCategoriaComponent;
  let fixture: ComponentFixture<EscolheCategoriaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EscolheCategoriaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EscolheCategoriaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
