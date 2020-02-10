import { Component, EventEmitter, Output } from '@angular/core';
import { Observable } from 'rxjs';
import { ITopico } from '../../../shared/model/topico.model';
import { TopicoService } from 'app/entities/topico/topico.service';
import { map } from 'rxjs/operators';

@Component({
  selector: 'jhi-perfil-grupo-topico',
  templateUrl: './perfil-grupo-topico.component.html',
  styleUrls: ['./perfil-grupo-topico.component.scss']
})
export class PerfilGrupoTopicoComponent {
  @Output() topicoSelecionado: EventEmitter<ITopico> = new EventEmitter();
  topico$: Observable<ITopico[]>;
  selecao?: ITopico = undefined;
  constructor(protected topicoService: TopicoService) {
    this.topico$ = this.topicoService.query().pipe(
      map(res => res.body ? res.body.sort((a,b) => a.nome!.localeCompare(b.nome!)) : [])
    );
  }

  trackId(topico: ITopico): number {
    return topico.id!;
  }

  selecionado(valor: ITopico | string): boolean {
    return valor === 'todos' ? this.selecao === undefined : this.selecao === valor;
  }

  novaSelecao(valor: ITopico | 'todos'): void {
    if (valor === 'todos') {
      this.selecao = undefined;
    } else {
      this.selecao = valor;
    }
    this.topicoSelecionado.emit(this.selecao);
  }
}
