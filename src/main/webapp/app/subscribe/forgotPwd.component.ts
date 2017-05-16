import { Component, OnInit } from '@angular/core';
import {Router,  ActivatedRoute, Params} from '@angular/router';
import {Person} from './person'
import {RepPwdValidator} from './repPwdValidator.directive'
import {SubscribeService} from './subscribe.service';

@Component({
  selector: 'forgotPwd',
  templateUrl: 'app/subscribe/forgotPwd.component.html',
  styleUrls:  ['app/subscribe/subscribe.component.css']
})
export class ForgotPwdComponent implements OnInit{

  constructor(router: Router, route: ActivatedRoute, private subscribeService: SubscribeService) { 
  	this.router=router;
  	 this.route = route;
  }

  router:Router;
  route: ActivatedRoute
  person: Person = new Person('','','','');
  active = true;
  passwordFieldType = 'password';
  returnedCode: String;
  statusCode:string = '';
  statusCode2:string = '';
  
 


  newPerson(){
  	this.person = new Person('','','',this.statusCode2);
  	this.active = false;
  	setTimeout(() => this.active = true, 0);
  } 

  get diagnostic(){
  	return JSON.stringify(this.person);
  }

  hideShowPassword(){
    if (this.passwordFieldType == 'password') this.passwordFieldType = 'text';
    else this.passwordFieldType = 'password';
  }
  
  onBlurEmail(){
  	console.log('ONBLUREMAIL');
  }

  postForgotPwd(){
	this.subscribeService.postForgotPwd(this.person).then(
		rc => {
			this.returnedCode = rc; 
			console.log('returnedCode: '+rc);
			if(rc=='password_changed') {
				this.router.navigate(['/authenticate', rc]); 
			}
		}
	);
  }

  ngOnInit(): void { 
  	this.statusCode = this.route.snapshot.params['code']; 
  	this.statusCode2 = this.route.snapshot.params['code2']; 
  	this.person.captcha = this.statusCode2; 
  	console.log('statusCode: ' + this.statusCode);
  	console.log('this.person.captcha: ' + this.person.captcha);
  	
  }

}


