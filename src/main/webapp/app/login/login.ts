import {Component, OnInit} from '@angular/core';
import { Router, ActivatedRoute, Params} from '@angular/router';
import {Observable} from 'rxjs/Rx';
import {FormBuilder, Validators, FormGroup} from '@angular/forms';
import {LoginService} from './login.service';
import {Account} from '../account/account';
import {AccountEventsService} from '../account/account.events.service';

///<reference path="../../../../../typings/lodash/lodash.d.ts" />

@Component({
    selector: 'login',
    providers: [LoginService],
    templateUrl: './app/login/login.html'
})
export class Login implements OnInit{
    
    username:string;
    password:string;
    router:Router;
    route: ActivatedRoute
    wrongCredentials:boolean;
    loginForm:FormGroup;
    loginService:LoginService;
    account:Account;
    error:string;
    statusCode:string;
    

    constructor(router: Router, route: ActivatedRoute, form: FormBuilder,
        loginService:LoginService, accountEventService:AccountEventsService
    ) {
        this.router = router;
        this.route = route;
        this.wrongCredentials = false;
        this.loginService = loginService;
        this.loginForm = form.group({
            username: ['', Validators.required],
            password: ['', Validators.required]
        });


        accountEventService.subscribe((account) => {
        console.log('STO ENTRANDO IN SUBAUTH: ' + account.authenticated);
            if(!account.authenticated) {
            console.log('SONO IN SUBAUTH: ' + account.error);
                if(account.error) {
                    if(account.error.indexOf('BadCredentialsException') !== -1) {
                    console.log('BADBAD');
                        this.error = 'Username and/or password are invalid !';
                    } else {
                        this.error = account.error;
                    }
                }
            }
        });
console.log('COSTRUITO LOGIN OBJ');
console.log(this.error);
    }


    ngOnInit(){
        /*this.route.params.switchMap((params: Params) => {this.statusCode = params['code'];});*/
       
        this.statusCode = this.route.snapshot.params['code'];
        
        console.log('STCODE1: ' + this.statusCode);
        if(this.statusCode.indexOf('-sep-')!=-1){
        	console.log('STCODE2: ' + this.statusCode);
        	this.callAuthenticateWS();
        }
        
    }


    authenticate(event) {
     	console.log('SONO IN AUTHENTICATE: ');
        event.preventDefault();
        this.callAuthenticateWS();
    }
    
    callAuthenticateWS(){
    	this.loginService.authenticate(this.loginForm.value.username,this.loginForm.value.password, this.statusCode).subscribe(account => {
            this.account = account;
            console.log('Successfully logged',account);
            this.router.navigate(['/users']);
        });
    }

    gotoSubscribe(){ this.router.navigate(['/subscribe']); }

}
