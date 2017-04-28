"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
const core_1 = require("@angular/core");
const forms_1 = require("@angular/forms");
function validateRepPwdFactory() {
    return (c, password1) => {
        if (c.value == password1) {
            return null;
        }
        else {
            return { validateRepPwd: { valid: false } };
        }
    };
}
let RepPwdValidator_1 = class RepPwdValidator {
    constructor() {
        this.validator = validateRepPwdFactory();
    }
    validate(c) {
        console.log(this.password1);
        return this.validator(c, this.password1);
    }
};
let RepPwdValidator = RepPwdValidator_1;
__decorate([
    core_1.Input('validateRepPwd'),
    __metadata("design:type", String)
], RepPwdValidator.prototype, "password1", void 0);
RepPwdValidator = RepPwdValidator_1 = __decorate([
    core_1.Directive({
        selector: '[validateRepPwd][ngModel],[validateRepPwd][formControl]',
        providers: [
            { provide: forms_1.NG_VALIDATORS, useExisting: core_1.forwardRef(() => RepPwdValidator_1), multi: true }
        ]
    }),
    __metadata("design:paramtypes", [])
], RepPwdValidator);
exports.RepPwdValidator = RepPwdValidator;
//# sourceMappingURL=repPwdValidator.directive.js.map