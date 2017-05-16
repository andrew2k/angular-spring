import { Injectable }    from '@angular/core';
import { Headers, Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { Person } from './person';

@Injectable()
export class SubscribeService {

  private subscribeUrl:string = 'api/register';  // URL to web api
  private forgotPwdUrl:string = 'api/forgotPwd';  // URL to web api

  constructor(private http: Http) { }



  // Subscribe
  public post(person: Person): Promise<String> {
   	return this.postPersonUrl(person, this.subscribeUrl);
  }
  
  // Subscribe
  public postForgotPwd(person: Person): Promise<String> {
   	return this.postPersonUrl(person, this.forgotPwdUrl);
  }


	public postPersonUrl(person: Person, url:string): Promise<String> {
	    let headers = new Headers({
	      'Content-Type': 'application/json'});
	
	    return this.http
	               .post(url, JSON.stringify(person), {headers: headers})
	               .toPromise()
	               .then(res => {console.log(res); return res.text(); })
	               .catch(this.handleError);
	               
	               /*.then(res => {console.log(res.json()); return res.json() as Person; })*/
	               
	               
  }


  private handleError(error: any) {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }
}
