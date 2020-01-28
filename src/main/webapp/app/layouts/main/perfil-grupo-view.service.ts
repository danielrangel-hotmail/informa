import { Injectable } from '@angular/core';
import { BreakpointObserver, BreakpointState } from '@angular/cdk/layout';
import { DeviceDetectorService } from 'ngx-device-detector';
import { BehaviorSubject, combineLatest, iif, Observable, of, Subject, merge } from 'rxjs';
import { combineAll, delay, map, mergeMap, scan, startWith } from 'rxjs/operators';

export const MOBILE = 'mobile';
export const UNDOCKED = 'undocked';
export const UNDOCKABLE = 'undockable';
export const DOCKED = 'docked';

const UNDOCKING = 'undocking';
const DOCKING = 'docking';
const INIT = 'init';

@Injectable({
  providedIn: 'root'
})
export class PerfilGrupoViewService {

  private docking$ = new Subject<string>();
  public status$: Observable<string>;
  public navegacao = 'Nehuma';
  public navegacao$: Observable<string>;
  private navegacaoCore$ = new BehaviorSubject("não sei");

  constructor(
    protected breakpointObserver: BreakpointObserver,
    protected deviceDetectorService: DeviceDetectorService) {

      // if (this.deviceDetectorService.isMobile()) {
      //   this.state$ = new BehaviorSubject(MOBILE);
      //   return;
      // }

    const command$ = this.docking$.pipe(
      startWith(DOCKED),
    );

    this.navegacao$ = this.navegacaoCore$.pipe(delay(0));
    const initialBreakpointState = this.breakpointObserver.isMatched('(min-width: 910px)');
    const breakpoint$ = this.breakpointObserver.observe(['(min-width: 910px)']).pipe(
      map((state : BreakpointState) => state.matches),
      startWith(initialBreakpointState),
      map(sateBoolean => sateBoolean ? DOCKED : UNDOCKED)
     );

    this.status$ = merge(command$, breakpoint$).pipe(
      scan((acc,curr)=> {
        if (acc === INIT) {
          if (curr === DOCKED) return DOCKED;
          if (curr === UNDOCKED) return UNDOCKED;
          return INIT;
        }
        if (acc === UNDOCKABLE) {
          if (curr === DOCKED) return UNDOCKED;
          return UNDOCKABLE;
        }
        if (acc === UNDOCKED) {
          if (curr === DOCKING) return DOCKED;
          if (curr === UNDOCKED) return UNDOCKABLE;
          return UNDOCKED;
        } else {
          if (curr === UNDOCKING) return UNDOCKED;
          if (curr === UNDOCKED) return UNDOCKABLE;
          return DOCKED;
        }
      }, INIT),
    );
  }

  undock(): void {
    this.docking$.next(UNDOCKING);
  }

  dock(): void {
    this.docking$.next(DOCKING);
  }

  navega(navega: string): void {
    this.navegacaoCore$.next(navega);
  }
}
