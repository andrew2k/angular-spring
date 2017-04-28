import {Directive, forwardRef, Input} from '@angular/core';
import {NG_VALIDATORS, FormControl} from '@angular/forms';

function validateRepPwdFactory() {
  return (c: FormControl, password1: string) => {
    if(c.value==password1){
      return null;
    }else{ 
      return {validateRepPwd: { valid: false}};
    }
  };
}

@Directive({
  selector: '[validateRepPwd][ngModel],[validateRepPwd][formControl]',
  providers: [
    { provide: NG_VALIDATORS, useExisting: forwardRef(() => RepPwdValidator), multi: true }
  ]
})
export class RepPwdValidator {

  validator: Function;

  @Input('validateRepPwd') password1: string;

  constructor() {
    this.validator = validateRepPwdFactory();
  }

  validate(c: FormControl) {
    console.log(this.password1);
    return this.validator(c, this.password1);
  }
}