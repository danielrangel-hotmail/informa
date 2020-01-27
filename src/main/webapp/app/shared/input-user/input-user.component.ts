import {
    AfterViewInit, Component, Host, Input, OnInit, SkipSelf, Optional, ViewChild,
    ElementRef
} from '@angular/core';
import { Observable, of } from 'rxjs';
import {ControlContainer, ControlValueAccessor, NG_VALUE_ACCESSOR} from '@angular/forms';
import {Subscription} from 'rxjs';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { debounceTime, distinctUntilChanged, switchMap, tap } from 'rxjs/operators';

@Component({
    selector: 'jhi-input-user',
    templateUrl: './input-user.component.html',
    styles: [],
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
          // eslint-disable-next-line @typescript-eslint/no-use-before-define
            useExisting: JhiInputUserComponent,
            multi: true
        }]
})
export class JhiInputUserComponent implements OnInit, AfterViewInit, ControlValueAccessor {

    searchUser: any;
    formatterUser: any;
    user?: IUser;
    onChangeLastUser: IUser | null;
    statusChangesSub?: Subscription;
    @ViewChild('userInput', {static: false}) userInput!: ElementRef;
    @Input() formControlName!: string;
    isDisabled = false;
    invalid = false;

    constructor(private userService: UserService,
        @Optional() @Host() @SkipSelf()
        private controlContainer: ControlContainer) {
      this.onChangeLastUser = null;
    }

    onChange = (obj: any) => {};

    onTouched = () => {};

    ngOnInit(): void {
        this.searchUser = (text$: Observable<string>) => {
            return text$
            .pipe(tap(() => this.informOnChange(null)))
            .pipe(debounceTime(400))
            .pipe(distinctUntilChanged())
            .pipe(switchMap((text) => {
                // if (text.length < 1) {
                    return of([]);
                // }
                // return this.userService.queryName(variables).map(({data}) => {
                //     return data.userTypeAhead;
                // });
            }));
        }

        if (this.controlContainer) {
            if (this.formControlName) {
                const control = this.controlContainer.control!.get(this.formControlName);
                this.invalid = (control!.status === 'INVALID');
                this.statusChangesSub = control!.statusChanges.subscribe((status) => {
                    this.invalid = (status === 'INVALID');
                });
            } else {
                console.warn('Missing FormControlName directive from host element of the component');
            }
        } else {
            console.warn('Can\'t find parent FormGroup directive');
        }
        this.formatterUser = (user: IUser) => (user.firstName + ' ' + user.lastName);
    }

    ngAfterViewInit(): void {
        this.userInput.nativeElement.onblur = () => {
            this.onTouched();
        };
    }

    informOnChange(newUser: IUser | null): void {
        if (this.onChangeLastUser === newUser) {
            return;
        }
        this.onChangeLastUser = newUser;
        this.onChange(newUser);
    }

    selectItem(obj: any): void {
        this.informOnChange(obj.item);
    }

    writeValue(obj: any): void {
        this.user = obj;
        this.onChangeLastUser = obj;
    }

    registerOnChange(fn: any): void {
        this.onChange = fn;
    }

    registerOnTouched(fn: any): void {
        this.onTouched = fn;
    }

    setDisabledState?(isDisabled: boolean): void {
        this.isDisabled = isDisabled;
    }
}
