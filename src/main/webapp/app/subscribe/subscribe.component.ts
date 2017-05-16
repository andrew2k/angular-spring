import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {Person} from './person'
import {RepPwdValidator} from './repPwdValidator.directive'
import {SubscribeService} from './subscribe.service';

@Component({
  selector: 'subscription',
  templateUrl: 'app/subscribe/subscribe.component.html',
  styleUrls:  ['app/subscribe/subscribe.component.css']
})
export class SubscribeComponent implements OnInit{

  constructor(router: Router, private subscribeService: SubscribeService) { 
  	this.router=router;
  }

  router:Router;
  person = new Person('','','','');
  active = true;
  passwordFieldType = 'password';
  returnedCode: String;
  captchaUrl = 'api/showCaptcha';
 


  newPerson(){
  	this.person = new Person('','','','');
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

  register(){
	this.subscribeService.post(this.person).then(
		rc => {
			this.returnedCode = rc; 
			console.log('returnedCode: '+rc);
			if(rc=='registration_completed') {
				this.router.navigate(['/authenticate', rc]); 
			}
			if(rc=='wrong_captcha') {
				this.reloadCaptcha(); 
			}
		}
	);
  }

  reloadCaptcha(){
  	/*this.router.navigate(['/authenticate', 'registration_completed']); */
  	if(this.captchaUrl.includes('captchaTempArg1')) this.captchaUrl = 'api/showCaptcha?captchaTempArg2=1';
  	else this.captchaUrl = 'api/showCaptcha?captchaTempArg1=1';
  }

  ngOnInit(): void {  }

}


