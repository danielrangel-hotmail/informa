import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { InsistenceEnvironmentService } from '../../shared/insistence-environment/insistence-environment.service';
import { Observable } from 'rxjs';
import { IInsistenceEnvironment } from './insistence.environment.interface';

@Component({
  selector: 'jhi-insistence-environment',
  templateUrl: './insistence-environment.component.html',
  styleUrls: ['./insistence-environment.component.scss']
})
export class InsistenceEnvironmentComponent implements OnInit {
  @Output() selectedEnvironment = new EventEmitter<IInsistenceEnvironment>();

  environments$: Observable<IInsistenceEnvironment[]>;

  constructor(insistenceEnvironmentService: InsistenceEnvironmentService) {
    this.environments$ = insistenceEnvironmentService.getEnvironments();
  }

  ngOnInit(): void {
  }

  onChange(environment: IInsistenceEnvironment): void {
    this.selectedEnvironment.emit(environment);
  }
}
