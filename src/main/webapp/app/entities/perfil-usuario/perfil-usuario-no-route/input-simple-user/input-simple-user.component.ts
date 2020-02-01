import { Component, forwardRef, Input, OnInit } from '@angular/core';
import { concat, Observable, of, Subject } from 'rxjs';
import { ISimpleUser } from 'app/shared/model/simples-user.interface';
import { distinctUntilChanged, map, switchMap, tap } from 'rxjs/operators';
import { PerfilUsuarioService } from 'app/entities/perfil-usuario/perfil-usuario.service';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

interface IUserFullNameValue {
  id: number;
  fullName: string;
  simpleUser: ISimpleUser;
}

@Component({
  selector: 'jhi-input-simple-user',
  templateUrl: './input-simple-user.component.html',
  styleUrls: ['./input-simple-user.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      // eslint-disable-next-line @typescript-eslint/no-use-before-define
      useExisting: forwardRef(() => InputSimpleUserComponent),
      multi: true
    }
  ]
})
export class InputSimpleUserComponent implements OnInit, ControlValueAccessor {
  @Input() multiple = false;
  users$?: Observable<IUserFullNameValue[]>;
  usersLoading = false;
  usersInput$ = new Subject<string>();
  selectedUsers: IUserFullNameValue[] = [];
  constructor(protected perfilUsuarioService: PerfilUsuarioService) { }

  ngOnInit(): void {
    this.loadUsers();
  }

  trackByFn(item: ISimpleUser): number {
    return item.id;
  }

  private loadUsers(): void {
    this.users$ = concat(
      of([]), // default items
      this.usersInput$.pipe(
        distinctUntilChanged(),
        tap(() => this.usersLoading = true),
        switchMap(term => this.perfilUsuarioService.search(term).pipe(
          map(res => res.body ? res.body : []), // empty list on error
          map( res => this.toIUserFullNameValue(res)),
          tap(() => this.usersLoading = false)
        ))
      )
    );
  }

  toIUserFullNameValue(res: ISimpleUser[]): IUserFullNameValue[] {
    return res.map(simpleUser => ({
      id: simpleUser.id,
      fullName: simpleUser.firstName + ' ' + simpleUser.lastName,
      simpleUser,
    }));
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  propagateChange = (fn: any) => {};

  get value(): ISimpleUser[] {
    return this.selectedUsers.map(e => e.simpleUser);
  }

  set value(val: ISimpleUser[]) {
    if (val) {
      this.selectedUsers = this.toIUserFullNameValue(val);
      this.propagateChange(val);
    }
  }

  writeValue(value: ISimpleUser[]): void {
    if (value !== undefined) {
      this.selectedUsers = this.toIUserFullNameValue(value);
      this.propagateChange(value);
    }
  }
  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }
  registerOnTouched(fn: any): void {
  }

  onUsersChanged(value: any): void {
    this.selectedUsers = value;
    this.propagateChange(this.selectedUsers.map(e => e.simpleUser));
  }
}
